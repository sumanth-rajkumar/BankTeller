/**
 * College Checking class is the extension of Checking class. It contains the data and specific operations
 * needed to run a college checking account.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
public class CollegeChecking extends Checking{

    private static final double annualInterest = 0.25;
    private static final String accountType = "College Checking";
    private College college;

    /**
     * This function is used to get the annualInterest rate of a College Checking account.
     */
    @Override
    protected double getAnnualInterestRate(){
       return annualInterest;
    }

     /**
     * Constructor of this class. Takes in College parameter
     */
     public CollegeChecking(College college)
     {
        this.college = college;
     }

     /**
     * Empty constructor of this class.
     */
     public CollegeChecking()
     {

     }

    /**
     * This function sets the passed in account object's balance and sets the boolean
     * closed variable to false by polymorphism.
     * @param newAccount
     */
    @Override
    public void reOpen(Account newAccount){
        super.reOpen(newAccount);
        this.college=((CollegeChecking)newAccount).college;
    }

    /**
     * This function returns a monthly fee that an account holder needs to pay on a monthly basis.
     * @return - a double that has a monthly fee that an account holder needs to pay on a monthly basis
     * depending on the account type.
     */
    @Override
    public double fee() {
        return 0;
    }

    /**
     * @return a String, which is the name of the account type.
     */
    @Override
    public String getType() {
        return accountType;
    }

    /**
     * @return the type of account and whether it's opened or close.
     */
    @Override
    public String toString()
    {
        return super.toString() + "::" + college.toString();
    }



}
