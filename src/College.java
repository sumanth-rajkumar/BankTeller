/**
 * College class is an enum class to set the campus. This class is used to handle CollegeChecking
 * accounts.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
public enum College {
    NEW_BRUNSWICK("0"),
    NEWARK("1"),
    CAMDEN("2");

    /**
     * Defines the college campus by code.
     * 0 for NEW BRUNSWICK
     * 1 for NEWARK
     * 2 for CAMDEN
     */
    private final String collegeCode;

    /**
     * Constructor of this class. Takes in String parameter, should be 0 or 1 or 2
     * to define college campus.
     * @param collegeCode
     */
    College(String collegeCode)
    {
        this.collegeCode = collegeCode;
    }


    /**
     * Returns the name of constant. In this case, it would be
     * 0 for NEW BRUNSWICK
     * 1 for NEWARK
     * 2 for CAMDEN
     * @return a string, which is the name of college campus.
     */
    @Override
    public String toString()
    {
        return name();
    }
}
