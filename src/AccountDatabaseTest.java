/**
 * Test class to test open() and close() methods of AccountDatabase class.
 * @author Sumanth Rajkumar, Shantanu Jain
 */

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountDatabaseTest {

    /**
     * This Test method checks whether the open() has correctly opened and added account object
     * to AccountDatabase array or not.
     * This method should return true only after the account object is successfully added to the array.
     */
    @Test
    public void open_Test() {
        AccountDatabase accountDatabase = new AccountDatabase();
        Savings savings = new Savings();
        Date date = new Date("02/12/1982");
        savings.setHolder("Joe","Doe",date);
        savings.setBalance(12000);
        savings.setLoyalty(true);
        assertTrue(accountDatabase.open(savings));

    }
    /**
     * This Test method checks whether the close() method has correctly removed account object
     * from AccountDatabase array or not.
     * This method should return true only after the account object is successfully added to the array.
     */
    @Test
    public void close_Test() {
        Date date = new Date("02/12/1982");
        AccountDatabase accountDatabase = new AccountDatabase();
        Savings savings = new Savings();
        savings.setHolder("Joe","Doe",date);
        savings.setBalance(12000);
        savings.setLoyalty(true);
        assertTrue(accountDatabase.open(savings));
        assertTrue(accountDatabase.close(savings));

    }
}