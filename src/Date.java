import java.util.Calendar;
/**
 * A class that stores the date based off an input string and the current date.
 *
 * We break down the string and save the year, the month, and
 * the day within the respective instance variables.
 * @author Sumanth Rajkumar, Shantanu Jain
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    //constants
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int FEBRUARY_LEAP = 29;
    public static final int DAYS_END = 31;
    public static final int MONTHS_END = 12;

    /**
     * Creates a Date object based off the given string.
     * Using StringTokenizer, it assigns the proper values
     * to the instance variables month, day, and year
     *
     * @param date - a string in the form mm/dd/yyyy
     */
    public Date(String date) {
        String[] s = date.split("/");
        int y = Integer.parseInt(s[2]);
        int m = Integer.parseInt(s[0]);
        int d = Integer.parseInt(s[1]);
        this.year=y;
        this.month=m;
        this.day=d;
    }

    /**
     * Creates a Date object that represents today's date
     * with the help of the Java Calendar library.
     * In the event the user doesn't specify a string,
     * this constructor will run
     */
    public Date() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH)+1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        this.year=y;
        this.month=m;
        this.day=d;
    }

    /**
     * Checks if the day is valid based off the given month,
     * taking into account of leap years as well.
     *
     * @return true if the day is within the range of the given month,
     * false otherwise.
     */
    public boolean isValid()
    {
        if((this.day < 1) || (this.day > DAYS_END))
        {
            return false;
        }
        switch (this.month)
        {
            case Calendar.JANUARY + 1:
            case Calendar.MARCH + 1:
            case Calendar.MAY + 1:
            case Calendar.OCTOBER + 1:
            case Calendar.JULY + 1:
            case Calendar.AUGUST + 1:
            case Calendar.DECEMBER + 1:
                return true;
            case Calendar.FEBRUARY + 1:
                if(isLeapYear() && this.day == FEBRUARY_LEAP)
                {
                    return true;
                }
                else
                {
                    return this.day < FEBRUARY_LEAP;
                }
            case Calendar.APRIL + 1:
            case Calendar.JUNE + 1:
            case Calendar.SEPTEMBER + 1:
            case Calendar.NOVEMBER + 1:
                return this.day < DAYS_END;
            default:
                return false;
        }
    }

    public boolean isInThePast()
    {
        Date todayDate = new Date();

        return this.compareTo(todayDate) < 0;
    }

    public boolean isInTheFuture()
    {
        Date todayDate = new Date();

        return this.compareTo(todayDate) > 0;
    }

    /**
     * Checks whether the date's year is a leap year
     *
     * @return true if the date is in a leap year, false otherwise
     */
    private boolean isLeapYear()
    {
        if(this.year % QUADRENNIAL == 0 && this.year % CENTENNIAL != 0)
        {
            return true;
        }
        else if(this.year % QUADRENNIAL == 0 && this.year % CENTENNIAL == 0 && this.year % QUATERCENTENNIAL == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns a string of the date in the format mm/dd/yyyy
     *
     * @return String representing the date object
     */
    public String toString()
    {
        return this.month + "/" + this.day + "/" + this.year;
    }

    /**
     * Takes in a date object and compares to see if one date is higher, lesser,
     * or equal to each other
     *
     * @return int representing each case of comparison (0 - equal, 1 - greater, -1 - lesser)
     */
    @Override
    public int compareTo(Date date)
    {
        if (this.year > date.year)
        {
            return 1;
        }
        else if(this.year == date.year)
        {
            if (this.month > date.month)
            {
                return 1;
            }
            else if(this.month == date.month)
            {
                if (this.day > date.day)
                {
                    return 1;
                }
                else if(this.day == date.day)
                {
                    return 0;
                }
                else
                {
                    return -1;
                }
            }
            else
            {
                return -1;
            }

        }
        else
        {
            return -1;
        }
    }
}
