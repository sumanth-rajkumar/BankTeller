/**
 * Profile class defines the information of an account holder, which are first name, last name and
 * Date of Birth. It also has some getters to retrive account holder's data when needed.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
public class Profile {
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob)
    {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * This method checks whether the parameter is an instance of profile or not.
     * @param obj
     * @return true if the parameter if parameter is an instance of Profile, otherwise returns false.
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Profile)
        {
            Profile profile = (Profile) obj;
            return profile.fname.equalsIgnoreCase(fname) && profile.lname.equalsIgnoreCase(lname) && (profile.dob.compareTo(dob) == 0);
        }
        return false;
    }
    /**
     * Returns a string that contains the first name, last name and date of birth of a user.
     * @return a string that contains the first name, last name and date of birth of a user.
     */
    @Override
    public String toString()
    {
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }
}
