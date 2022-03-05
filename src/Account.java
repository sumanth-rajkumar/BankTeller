/**
 * Account Class is an abstract class that defines the common data and operations for all account
 * type; each account has a profile that uniquely identifies the account holder. This is the superclass
 * of all account types, and it is an abstract class with 3 methods.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
import java.text.DecimalFormat;

public abstract class Account
{
    protected Profile holder;
    protected boolean closed;
    protected double balance;

    /**
     * @return boolean representing the closing status of the account
     */
    public boolean isClosed() {
        return this.closed;
    }

    /**
     * This function sets the passed in account object's balance and sets the boolean closed variable to false.
     * @param newAccount
     */
    public void reOpen(Account newAccount){
        balance = newAccount.getBalance();
        closed = false;
    }

    /**
     * This function sets the balance to 0 and sets the boolean closed variable to true.
     */
    public void close(){
       balance = 0.0;
       closed = true;
    }

    /**
     * @return double containing balance of an account
     */
    public double getBalance()
    {
        return this.balance;
    }

    /**
     * Checks if the passed in object is an instance of Account or not.
     * @param obj - object
     * @return true if profile of passed in object is equal to the profile of this
     * Account otherwise false.
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Account)
        {
            Account account = (Account) obj;
            return account.holder.equals(holder);
        }
        return false;
    }

    /**
     * This function updates the balance calculated with the fee and monthly interest.
     */
    public void updateBalanceWithFeeAndMonthlyInterest(){
        if(!this.closed) {
            balance = balance - fee() + monthlyInterest();
        }
    }

    /**
     *
     * @return a string that has profile information of account holder and the amount of balance in their account.
     */
    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return holder.toString() + "::" + "Balance " + df.format(this.balance);
    }

    /**
     * This function deducts the balance from an account, the amount that should be deducted from the
     * balance is passed in as a parameter.
     * @param amount - The amount that needs to be deducted or withdrawn from an account.
     */
    public void withdraw(double amount)
    {
        this.balance -= amount;
    }

    /**
     * This function checks if the requested amount can be withdrawn or not.
     * @param amount - The amount requested by the user that needs to be withdrawn.
     * @return true if amount is available in the user's account, else returns false.
     */
    public boolean canBeWithdrawn(double amount)
    {
        if(amount > this.balance)
        {
            return false;
        }
        return true;
    }

    /**
     * This function adds an amount to the current balance of the account
     * @param amount - The amount that needs to be added in the current balance.
     */
    public void deposit(double amount)
    {
        this.balance += amount;
    }

    /**
     * This function sets the balance of an account.
     * @param balance - balance left in the account.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * This function sets the profile information of an account holder.
     * @param fName - first name of account holder.
     * @param lName - last name of account holder.
     * @param dob - Date of birth of account holder.
     */
    public void setHolder(String fName, String lName, Date dob)
    {
        this.holder = new Profile(fName,lName,dob);
    }

    /**
     * This function returns a monthly interest depending on the type of the account.
     * @return The monthly interest that a account holder gets on his account.
     */
    public abstract double monthlyInterest(); //return the monthly interest

    /**
     * This function returns a monthly fee that an account holder needs to pay on a monthly basis.
     * @return - a double that has a monthly fee that an account holder needs to pay on a monthly basis
     * depending on the account type.
     */
    public abstract double fee(); //return the monthly fee

    /**
     * @return a String, which is the name of the account type.
     */
    public abstract String getType();

    /** This method returns a different way of saying the name of an account type.
     * @return a String, which is the name of the account type.
     */
    public String getShortType() {
       return getType();
    }
}
