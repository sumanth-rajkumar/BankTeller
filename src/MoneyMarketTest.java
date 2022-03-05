/**
 * Test class to test monthlyInterest() methods of extensions of Account class.
 * @author Sumanth Rajkumar, Shantanu Jain
 */
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyMarketTest {
    private static final int interestPercentDividedByTotalMonths=1200;
    @Test
    public void monthlyInterestTest() {
        MoneyMarket m = new MoneyMarket();
        m.setBalance(2600);
        /**
         * This test checks whether the monthlyInterest is correct or not when loyalty is set to true.
         */
        m.setLoyalty(true);
        assertEquals(m.getBalance()*m.getAnnualInterestRate()/interestPercentDividedByTotalMonths
                ,m.monthlyInterest(),0.01);
        m.setLoyalty(false);
        /**
         * This test checks whether the monthlyInterest is correct or not when loyalty is set to true.
         */
        assertEquals(m.getBalance()*m.getAnnualInterestRate()/interestPercentDividedByTotalMonths
                ,m.monthlyInterest(),0.001);
    }
}
