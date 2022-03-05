/**
 * An instance of this AccountDatabase class is an array-based container that holds a list of
 * accounts with different types. The initial capacity of container will be 4. It will automatically
 * grow by the capacity of 4 if array is full.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
import java.text.DecimalFormat;

public class AccountDatabase {
    private Account [] accounts;
    private int numAcct;

    public static final int NOT_FOUND = -1;

    /**
     * This is the constructor that creates an array-based data structure.
     * It's initial capacity is 4 and numAcct is set to 0.
     */
    public AccountDatabase ()
    {
        this.accounts = new Account[4];
        this.numAcct = 0;
    }

    /**
     * @return number of accounts that AccountDatabase object is currently holding.
     */
    public int getNumAcct()
    {
        return numAcct;
    }

    /**
     * This function finds whether the passed in account object exists in the AccountDatabase object or not.
     * @param account
     * @return index at which the passed in account is found, otherwise returns NOT_FOUND.
     */
    private int find(Account account)
    {
        Account[] accounts = this.accounts;
        for(int i = 0; i < this.numAcct; i++)
        {
            if(account.equals(accounts[i]))
            {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * This function finds whether the passed in account object exists in the AccountDatabase and returns that account.
     * @param account
     * @return account if found, null if NOT_FOUND
     */
    public Account getAccountIfExists(Account account){
        int index = find(account);
        if(index == NOT_FOUND){
            return null;
        }
        return accounts[index];
    }

    /**
     * This function grows the array-based container size by 4 when the array gets full, by creating
     * a new array and copying all the elements from current array to the new array.
     */
    private void grow()
    {
        Account[] currentAccounts = this.accounts;
        int updatedLength = currentAccounts.length + 4;
        Account[] newAccounts = new Account[updatedLength];

        for (int i = 0; i < currentAccounts.length; i++)
        {
            newAccounts[i] = currentAccounts[i];
        }
        this.accounts = newAccounts;
    }

    /**
     * This function adds a new account object to AccountDatabase array.
     * @param account - The account object that needs to be added.
     * @return true only after when the account gets added to array successfully.
     */
    public boolean open(Account account)
    {
        Account[] accounts = this.accounts;
        for(int i = 0; i < accounts.length; i++)
        {
            if(accounts[i] == null)
            {
                accounts[i] = account;
                numAcct++;
                return true;
            }
        }
        this.grow();
        this.open(account);
        return true;

    }

    /**
     * This function finds and reopens an account object from the array-based container in AccountDatabase
     */
     public boolean reOpen(Account account)
    {
        Account[] accounts = this.accounts;
        int location = this.find(account);
        if(location == NOT_FOUND)
        {
            return false;
        }
        else
        {
            accounts[location].reOpen(account);
            return true;
        }
    }

    /**
     * This function finds and sets the balance of the account object from the array-based container in AccountDatabase to 0
     * and sets the account's boolean closed variable to true.
     * @param account - The account object that needs to be closed.
     * @return false if the account object does not exist, true if account is successfully closed.
     */
    public boolean close(Account account)
    {
        Account[] accounts = this.accounts;
        int location = this.find(account);
        if(location == NOT_FOUND)
        {
            return false;
        }
        else
        {
            accounts[location].close();
            return true;
        }
    }

    /**
     * This function finds the account in the database and adds in an amount to the current balance of the account.
     * @param account - account object in which the balance needs to be added.
     */
    public void deposit(Account account)
    {
        int index = find(account);
        accounts[index].deposit(account.getBalance());

    }

    /**
     * This function deducts an amount from the current balance of the account.
     * @param account - Account object from which the balance needs to be removed.
     * @return - true if the amount is available to withdraw from the account's balance
     *           and the account exists.
     *         - false if the amount is greater than the available balance or if the account doesn't exist.
     */
    public boolean withdraw(Account account)
    {
        Account existingAccount = getAccountIfExists(account);
        if(existingAccount!=null && existingAccount.canBeWithdrawn(account.getBalance()))
        {
            existingAccount.withdraw(account.getBalance());
            return true;
        }
        return false;

    }

    /**
     * This function prints all the accounts in the database.
     */
    public void print()
    {
        for(int i = 0; i < numAcct; i++)
        {
            System.out.println(accounts[i]);
        }
    }

    /**
     * This function prints all the accounts in the database by their account type.
     */
    public void printByAccountType()
    {
       this.sortByAccountType();
       this.print();
    }

    /**
     * This function prints the monthly fees and interests of all account types in the database.
     */
    public void printFeeAndInterest()
    {
        Account[] accounts = this.accounts;
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        for(int i = 0; i < numAcct; i++)
        {
            System.out.println(accounts[i] + "::fee " + df.format(accounts[i].fee()) + "::monthly interest " + df.format(accounts[i].monthlyInterest()));
        }
    }

    /**
     * This function prints all account types in the database with the updated balances after deposits, withdrawals, closures,
     * fee and monthly interest.
     */
    public void printWithUpdatedBalance()
    {
        updateBalance();
        print();
    }

    /**
     * This function is a helper to sort all accounts by their type alphabetically.
     */
    private void sortByAccountType()
    {
        Account[] accounts = this.accounts;
        Account temp;

        for(int i = 0; i < this.numAcct; i++)
        {
            for(int j = i + 1; j < numAcct; j++)
            {

                if(accounts[i].getType().compareTo(accounts[j].getType()) > 0)
                {
                    temp = accounts[i];
                    accounts[i] = accounts[j];
                    accounts[j] = temp;
                }
            }
        }
    }

    /**
     * This function is a helper to update the balances after deposits, withdrawals, and closures, fee and monthly interest.
     */
    private void updateBalance()
    {
        Account[] accounts = this.accounts;
        for(int i = 0; i < numAcct; i++)
        {
            accounts[i].updateBalanceWithFeeAndMonthlyInterest();
        }

    }
}
