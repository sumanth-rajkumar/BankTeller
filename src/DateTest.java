/**
 * Test class to whether the .isValid() methods of Date class.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {

    @Test
    public void isValid_Test() {
        /**
         * Testing a valid Date
         */
        Date date1 = new Date("12/12/1234");
        assertTrue(date1.isValid());

        /**
         * Testing a leap year date.
         */
        Date date2 = new Date("02/29/2024");
        assertTrue(date2.isValid());
        /**
         * Testing a non-leap year date.
         */
        Date date3 = new Date("02/29/2021");
        assertFalse(date3.isValid());

        /**
         * Testing a past leap year date.
         */
        Date date4 = new Date("02/29/2020");
        assertTrue(date4.isValid());
    }
}
