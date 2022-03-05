/**
 * Savings class is the extension of Account class. It contains the data and specific operations
 * needed to run a Savings account.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
public class Savings extends Account{
    private static final double annualInterest = 0.3;
    private static final String accountType = "Savings";
    private static final int expectedBalance = 300;
    private static final int monthlyFee = 6;
    private static final double loyaltyBonus = 0.15;
    private static final int interestPercentDividedByTotalMonths = 1200;
    private boolean loyalty;

    public static final String LOYAL = "1";
    public static final String NON_LOYAL = "0";
    /**
     * Checks if the passed in object is an instance of Savings or not.
     * @param obj - object
     * @return true if profile of passed in object is equal to the profile of this
     * Account otherwise false.
     */
    @Override
    public boolean equals(Object obj)
    {
        if((obj instanceof Savings) && !(obj instanceof MoneyMarket))
        {
            Savings account = (Savings) obj;
            return account.holder.equals(holder);
        }
        return false;
    }

    /**
     * This function is used to get the annualInterest rate without loyalty bonus of a Savings account.
     */
    protected double getBaseAnnualInterestRate(){
        return annualInterest;
    }

    /**
     * This function is used to get the annualInterest rate after loyalty rate is added.
     */
    protected double getLoyaltyBonusRate(){
        return loyaltyBonus;
    }

    /**
     * This function is used to get the annualInterest rate of a Savings account depending
     * on whether the holder has been loyal or not.
     */
    protected double getAnnualInterestRate(){
        if(this.isLoyal()){
            return getBaseAnnualInterestRate() + getLoyaltyBonusRate();
        }
        return getBaseAnnualInterestRate();
    }

    /**
     * This function returns a monthly interest, which in this case would be balance*annualInterest/1200.
     * @return The monthly interest that an account holder gets on his account.
     */
    @Override
    public double monthlyInterest() {
        return getBalance()*getAnnualInterestRate()/interestPercentDividedByTotalMonths;
    }

    /**
     * This function returns a monthly fee that an account holder needs to pay on a monthly basis.
     * @return - a double that has a monthly fee that an account holder needs to pay on a monthly basis
     * depending on the account type.
     */
    @Override
    public double fee() {
        if(balance>expectedBalance)
            return 0;
        else return monthlyFee;
    }

    /**
     * @return a String, which is the name of the account type.
     */
    @Override
    public String getType() {
        return accountType;
    }

    /**
     * This methods returns a String that describes the account type and weather the account holder
     * is loyal or not.
     * @return a String that describes the account type and weather the account holder
     * is loyal or not.
     */
    @Override
    public String toString()
    {
        if(this.isClosed())
        {
            return getType() + "::" + super.toString() + "::CLOSED";
        }
        else if(this.isLoyal()) {
            return getType() + "::" + super.toString() + "::Loyal";
        }

        return getType() + "::" + super.toString();
    }

    /**
     * This meathods sets the loyalty of the account holder.
     * @param loyalty - 0 for not loyal, 1 for loyal
     */
    public void setLoyalty(boolean loyalty){
        this.loyalty = loyalty;
    }

    /**
     * @return true if account holder is loyal, false if account holder is not loyal.
     */
    public boolean isLoyal()
    {
        return this.loyalty;
    }


}
