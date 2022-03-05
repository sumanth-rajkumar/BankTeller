/**
 * BankTeller class is the user interface class. This class performs read/write tasks to console.
 * This class also handles exceptions and invalid data given by user.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
import java.util.Scanner;


public class BankTeller {


    public static final String missingOpeningData = "Missing data for opening an account.";
    public static final String missingClosingData = "Missing data for closing an account.";
    private static final int ExpectedArgumentLength = 1;
    private static final int LengthOfArgumentsWithFirstAndLastName = 3;
    private static final int LengthOfArgumentsWithFirstAndLastNameAndDOB = 5;
    private static final int LengthOfArgumentsWithFirstAndLastNameAndBalance = 6;
    private static final int LengthOfArgumentsToOpenCollegeCheckingAndSavings = 7;

    /**
     * This function is called by main function. This function calls command function which handles
     * the operations as per user input.
     */
    public void run()
    {
        System.out.println("Bank Teller is running.");
        Scanner s = new Scanner(System.in);

        AccountDatabase accountDatabase = new AccountDatabase();
        while (s.hasNextLine()) {
            String c = s.nextLine();
            String[] transaction = c.split("[ \t]+");
            if(transaction.length <= 0 || transaction[0].isBlank()){
                continue;
            }
            switch (transaction[0]) {
                case "O" -> caseOpen(accountDatabase, transaction);
                case "C" -> caseClose(accountDatabase, transaction);
                case "D" -> caseDeposit(accountDatabase, transaction);
                case "W" -> caseWithdraw(accountDatabase, transaction);
                case "P" -> casePrint(accountDatabase);
                case "PT" -> casePrintByAccountType(accountDatabase);
                case "PI" -> casePrintWithFeeAndInterest(accountDatabase);
                case "UB" -> caseUpdateBalance(accountDatabase);
                case "Q" -> {System.out.println("Bank Teller is terminated."); System.exit(0);}
                default -> System.out.println("Invalid command!");
            }

        }

    }

    /**
     * This function opens an account, and adds it to accountDatabase object.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseOpen(AccountDatabase accountDatabase, String[] inputs)
    {
        if(inputs.length <= ExpectedArgumentLength){
            System.out.println(missingOpeningData);
            return;
        }
        switch (inputs[1])
        {
            case "C" -> caseCheckingForOpen(accountDatabase, inputs);
            case "CC" -> caseCollegeCheckingForOpen(accountDatabase, inputs);
            case "S" -> caseSavingsForOpen(accountDatabase, inputs);
            case "MM" -> caseMoneyMarketForOpen(accountDatabase, inputs);
            default -> System.out.println("Invalid Account Type");
        }
    }


    /**
     * This function is a helper that checks if the user enters info only until last name
     * @param forOpen - a boolean that says if it's the case of opening and account
     * @param inputs - transaction given by the user.
     */
    private boolean validateFirstLastNames(boolean forOpen, String[] inputs){
        if(inputs.length <= LengthOfArgumentsWithFirstAndLastName){
            System.out.println(forOpen?missingOpeningData:missingClosingData);
           return false;
        }
        return true;
    }

    /**
     * This function is a helper that checks if the user enters info only until dob
     * and validates the dob
     * @param forOpen - a boolean that says if it's the case of opening and account
     * @param inputs - transaction given by the user.
     */
    private Date validateAndParseDOB(boolean forOpen, String[] inputs){
        if(inputs.length < LengthOfArgumentsWithFirstAndLastNameAndDOB){
            System.out.println(forOpen?missingOpeningData:missingClosingData);
            return null;
        }
        try {
            Date dob = new Date(inputs[4]);
            if(!dob.isValid() || dob.isInTheFuture())
            {
                System.out.println("Date of birth invalid.");
                return null;
            }
            return dob;
        }
        catch(Exception e){
            System.out.println("Date of birth invalid.");
            return null;
        }
    }

    /**
     * This function is a helper that populates the account object with given profile info
     * @param forOpen - a boolean that says if it's the case of opening and account
     * @param account - account being populated
     * @param inputs - transaction given by the user.
     */
    private boolean populateHolder(boolean forOpen, Account account, String[] inputs)
    {
        if(!validateFirstLastNames(forOpen, inputs)) {
            return false;
        }

        Date dob = validateAndParseDOB(forOpen, inputs);
        if(dob==null){
            return false;
        }
        account.setHolder(inputs[2], inputs[3], dob);
        return true;
    }

    /**
     * This function is a helper that populates the account object with
     * given profile info and initial deposit info
     * @param account - account being populated
     * @param inputs - transaction given by the user.
     */
    private boolean populateHolderAndBalance(Account account, String[] inputs, String errorMessage)
    {
        if(!populateHolder(true, account, inputs)){
            return false;
        }

        if(inputs.length < LengthOfArgumentsWithFirstAndLastNameAndBalance){
            System.out.println(missingOpeningData);
            return false;
        }
        double balance;
        try {
            balance = Double.parseDouble(inputs[5]);
        }catch (Exception e){
            System.out.println("Not a valid amount.");
            return false;
        }
        if(balance <= 0d){
            System.out.println(errorMessage);
            return false;
        }
        account.setBalance(balance);
        return true;
    }


    /**
     * This function is a helper that checks if account exists or closed
     * before opening an account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     * @param newAccount - account being deposited to.
     */
    private void checkExistingAndOpenAccount(AccountDatabase accountDatabase, Account newAccount, String[] inputs){
        if(!populateHolderAndBalance(newAccount, inputs,"Initial deposit cannot be 0 or negative.")){
            return;
        }
        Account existing = accountDatabase.getAccountIfExists(newAccount);
        if(existing!=null) {
            if(!existing.isClosed() || !existing.getType().equals(newAccount.getType())){
                System.out.println(inputs[2] + " " + inputs[3] + " " + inputs[4] + " same account(type) is in the database.");
            }else{
                accountDatabase.reOpen(newAccount);
                System.out.println("Account reopened.");
            }
        }else {
            if(newAccount instanceof  MoneyMarket){
                    MoneyMarket moneyMarket = (MoneyMarket) newAccount;
                    if(!moneyMarket.hasMinimumInitialDeposit()){
                        System.out.println("Minimum of $" + MoneyMarket.ExpectedBalance + " to open a MoneyMarket account.");
                        return;
                    }

            }
            accountDatabase.open(newAccount);
            System.out.println("Account opened.");
        }
    }

    /**
     * This function is used to open a Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCheckingForOpen(AccountDatabase accountDatabase, String[] inputs)
    {
        Checking checking = new Checking();
        checkExistingAndOpenAccount(accountDatabase, checking, inputs);
    }

    /**
     * This function is used to open a College Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCollegeCheckingForOpen(AccountDatabase accountDatabase, String[] inputs)
    {
        if(inputs.length < LengthOfArgumentsToOpenCollegeCheckingAndSavings){
            System.out.println(missingOpeningData);
            return;
        }
        College college;
        try{
            int enumIndex = Integer.parseInt(inputs[6]);
            college = College.values()[enumIndex];
        }catch (Exception e){
            System.out.println("Invalid campus code.");
            return;
        }

        CollegeChecking collegeChecking = new CollegeChecking(college);
        checkExistingAndOpenAccount(accountDatabase, collegeChecking, inputs);

    }

    /**
     * This function is used to open a Savings account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseSavingsForOpen(AccountDatabase accountDatabase, String[] inputs)
    {
        if(inputs.length < LengthOfArgumentsToOpenCollegeCheckingAndSavings){
            System.out.println(missingOpeningData);
        }
        String loyalty = inputs[6];
        if(!loyalty.equals(Savings.LOYAL) && !loyalty.equals(Savings.NON_LOYAL)){
            System.out.println("Invalid loyalty code");
        }
        Savings savings = new Savings();
        savings.setLoyalty(loyalty.equals(Savings.LOYAL));
        checkExistingAndOpenAccount(accountDatabase, savings, inputs);
    }

    /**
     * This function is used to open a Money Market account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseMoneyMarketForOpen(AccountDatabase accountDatabase, String[] inputs)
    {
        MoneyMarket savings = new MoneyMarket();
        checkExistingAndOpenAccount(accountDatabase, savings, inputs);

    }

    /**
     * This function is used to close an account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseClose(AccountDatabase accountDatabase, String[] inputs)
    {
        switch (inputs[1]) {
            case "C" -> caseCheckingForClose(accountDatabase, inputs);
            case "CC" -> caseCollegeCheckingForClose(accountDatabase, inputs);
            case "S" -> caseSavingsForClose(accountDatabase, inputs);
            case "MM" -> caseMoneyMarketForClose(accountDatabase, inputs);
            default -> System.out.println("Invalid Account Type");
        }
    }

    /**
     * This function is a helper that checks if account exists or closed
     * before closing an account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     * @param account - account being deposited to.
     */
    private void closeExistingAccount(AccountDatabase accountDatabase, Account account, String[] inputs ){
        if(!populateHolder(false, account, inputs)){
            return;
        }
        Account existing = accountDatabase.getAccountIfExists(account);
        if(existing!=null) {
            if(!existing.isClosed()){
                accountDatabase.close(existing);
                System.out.println("Account closed.");
            }else{
                System.out.println("Account is closed already.");
            }
        }else{
            System.out.println(inputs[2] + " " + inputs[3] + " " + inputs[4] + " " + account.getShortType() + " is not in the database.");
        }

    }

    /**
     * This function is used to close a Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCheckingForClose(AccountDatabase accountDatabase, String[] inputs)
    {
        Checking checking = new Checking();
        closeExistingAccount(accountDatabase, checking, inputs);
    }

    /**
     * This function is used to close a College Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCollegeCheckingForClose(AccountDatabase accountDatabase, String[] inputs)
    {
        CollegeChecking ClgCheck = new CollegeChecking();
        closeExistingAccount(accountDatabase, ClgCheck, inputs);
    }

    /**
     * This function is used to close a Savings account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseSavingsForClose(AccountDatabase accountDatabase, String[] inputs)
    {
        Savings savings = new Savings();
        closeExistingAccount(accountDatabase, savings, inputs);
    }

    /**
     * This function is used to close a Money Market account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseMoneyMarketForClose(AccountDatabase accountDatabase, String[] inputs)
    {
        MoneyMarket moneyMarket = new MoneyMarket();
        closeExistingAccount(accountDatabase, moneyMarket, inputs);
    }

    /**
     * This function is a helper that checks if account exists or closed
     * before depositing an amount to an account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     * @param account - account being deposited to.
     */
    private void depositToAccount(AccountDatabase accountDatabase, Account account, String[] inputs ){
        if(!populateHolderAndBalance(account,inputs,"Deposit - amount cannot be 0 or negative.")){
            return;
        }
        Account existing = accountDatabase.getAccountIfExists(account);
        if(existing!=null && existing.getType().equals(account.getType())) {
            if(!existing.isClosed()){
                accountDatabase.deposit(account);
                System.out.println("Deposit - balance updated.");
            }else{
                System.out.println("Account is closed already.");
            }
        }else{
            System.out.println(inputs[2] + " " + inputs[3] + " " + inputs[4] + " " + account.getShortType() + " is not in the database.");
        }

    }

    /**
     * This function is used to deposit to a College Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCollegeCheckingForDeposit(AccountDatabase accountDatabase, String[] inputs)
    {
        CollegeChecking clgCheck = new CollegeChecking();
        depositToAccount(accountDatabase, clgCheck, inputs);

    }

    /**
     * This function is used to deposit to a Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCheckingForDeposit(AccountDatabase accountDatabase, String[] inputs)
    {
        Checking checking = new Checking();
        depositToAccount(accountDatabase, checking, inputs);
    }

    /**
     * This function is used to deposit to a Savings account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseSavingsForDeposit(AccountDatabase accountDatabase, String[] inputs)
    {
        Savings savings = new Savings();
        depositToAccount(accountDatabase, savings, inputs);
    }

    /**
     * This function is used to deposit to a Money Market account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseMoneyMarketForDeposit(AccountDatabase accountDatabase, String[] inputs)
    {
        MoneyMarket moneyMarket = new MoneyMarket();
        depositToAccount(accountDatabase, moneyMarket, inputs);
    }

    /**
     * This function is used to deposit to an account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseDeposit(AccountDatabase accountDatabase, String[] inputs)
    {
        switch (inputs[1]) {
            case "C" -> caseCheckingForDeposit(accountDatabase, inputs);
            case "CC" -> caseCollegeCheckingForDeposit(accountDatabase, inputs);
            case "S" -> caseSavingsForDeposit(accountDatabase, inputs);
            case "MM" -> caseMoneyMarketForDeposit(accountDatabase, inputs);
            default -> System.out.println("Invalid Account Type");
        }
    }

    /**
     * This function is used to withdraw an amount from an account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseWithdraw(AccountDatabase accountDatabase, String[] inputs)
    {
        switch (inputs[1]) {
            case "C" -> caseCheckingForWithdraw(accountDatabase, inputs);
            case "CC" -> caseCollegeCheckingForWithdraw(accountDatabase, inputs);
            case "S" -> caseSavingsForWithdraw(accountDatabase, inputs);
            case "MM" -> caseMoneyMarketForWithdraw(accountDatabase, inputs);
            default -> System.out.println("Invalid Account Type");
        }
    }

    /**
     * This function is a helper that checks if account exists or closed and if
     * the amount being withdrawn is sufficient before withdrawing an amount from an account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     * @param account - account being deposited to.
     */
    private void withDrawFromAccount(AccountDatabase accountDatabase, Account account, String[] inputs ){
        if(!populateHolderAndBalance(account, inputs,"Withdraw - amount cannot be 0 or negative.")){
            return;
        }
        Account existing = accountDatabase.getAccountIfExists(account);
        if(existing!=null && existing.getType().equals(account.getType())) {
            if(!existing.isClosed()){
                if(accountDatabase.withdraw(account)) {
                    System.out.println("Withdraw - balance updated.");
                }else{
                    System.out.println("Withdraw - insufficient fund.");
                }
            }else{
                System.out.println("Account is closed already.");
            }
        }else{
            System.out.println(inputs[2] + " " + inputs[3] + " " + inputs[4] + " " + account.getShortType() + " is not in the database.");
        }

    }

    /**
     * This function is used to withdraw an amount from a Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCheckingForWithdraw(AccountDatabase accountDatabase, String[] inputs)
    {
        Checking checking = new Checking();
        withDrawFromAccount(accountDatabase, checking, inputs);

    }

    /**
     * This function is used to withdraw an amount from a College Checking account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseCollegeCheckingForWithdraw(AccountDatabase accountDatabase, String[] inputs)
    {
        CollegeChecking clgCheck = new CollegeChecking();
        withDrawFromAccount(accountDatabase, clgCheck, inputs);
    }

    /**
     * This function is used to withdraw an amount from a Savings account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseSavingsForWithdraw(AccountDatabase accountDatabase, String[] inputs)
    {
        Savings savings = new Savings();
        withDrawFromAccount(accountDatabase, savings, inputs);
    }

    /**
     * This function is used to withdraw an amount from a Money Market account.
     * @param accountDatabase - Array-based container that holds account objects.
     * @param inputs - transaction given by the user.
     */
    private void caseMoneyMarketForWithdraw(AccountDatabase accountDatabase, String[] inputs)
    {
        MoneyMarket moneyMarket = new MoneyMarket();
        withDrawFromAccount(accountDatabase, moneyMarket, inputs);
    }

    /**
     * This function is used to print all accounts
     * @param accountDatabase - Array-based container that holds account obj
     */
    private void casePrint(AccountDatabase accountDatabase)
    {
        if(accountDatabase.getNumAcct() > 0)
        {
            System.out.println("");
            System.out.println("*list of accounts in the database*");
            accountDatabase.print();
            System.out.println("*end of list*");
            System.out.println("");
        }
        else
        {
            System.out.println("Account Database is empty!");
        }
    }
    /**
     * This function is used to print all accounts by order account type.
     * @param accountDatabase - Array-based container that holds account objects.
     */
    private void casePrintByAccountType(AccountDatabase accountDatabase)
    {
        if(accountDatabase.getNumAcct() > 0)
        {
            System.out.println("");
            System.out.println("*list of accounts by account type.");
            accountDatabase.printByAccountType();
            System.out.println("*end of list.");
            System.out.println("");
        }
        else
        {
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * This function is used to print all accounts with calculated fees and interests.
     * @param accountDatabase - Array-based container that holds account objects.
     */
    private void casePrintWithFeeAndInterest(AccountDatabase accountDatabase)
    {
        if(accountDatabase.getNumAcct() > 0)
        {
            System.out.println("");
            System.out.println("*list of accounts with fee and monthly interest");
            accountDatabase.printFeeAndInterest();
            System.out.println("*end of list.");
            System.out.println("");
        }
        else
        {
            System.out.println("Account Database is empty!");
        }
    }

    /**
     * This function is used to update the balances of all accounts and print them.
     * @param accountDatabase - Array-based container that holds account objects.
     */
    private void caseUpdateBalance(AccountDatabase accountDatabase)
    {
        if(accountDatabase.getNumAcct() > 0)
        {
            System.out.println("");
            System.out.println("*list of accounts with updated balance");
            accountDatabase.printWithUpdatedBalance();
            System.out.println("*end of list.");
            System.out.println("");
        }
        else
        {
            System.out.println("Account Database is empty!");
        }
    }


}