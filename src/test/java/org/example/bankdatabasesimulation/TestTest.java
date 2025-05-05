package org.example.bankdatabasesimulation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


class TestTest {
    @Test
    void testViewInterestWithPositiveBalance() {
        // Arrange
        AccountType accountType = AccountType.INVESTMENT; // 8% interest
        Account testAccount = new Account(1, 1, accountType, 1000.0, Status.ACTIVE);

        // Act
        ArrayList<InterestObject> interestList = DatabaseHelper.viewInterest(testAccount);

        // Assert
        assertEquals(10, interestList.size()); // Should return 10 years of growth
        assertEquals(1, interestList.get(0).getYear());
        assertTrue(interestList.get(9).getMoney() > 1000.0); // Should have grown over time
    }
    @Test
    void testComputeStatusForNegativeBalance() {
        int status = DatabaseHelper.computeStatus(AccountType.DEBIT, -100.0);
        assertEquals(2, status); // IN_DEBT
    }

    @Test
    void testComputeStatusForInvestmentAccount() {
        int status = DatabaseHelper.computeStatus(AccountType.INVESTMENT, 5000.0);
        assertEquals(1, status); // FROZEN (as per logic)
    }

    @Test
    void testComputeStatusForRegularAccount() {
        int status = DatabaseHelper.computeStatus(AccountType.DEBIT, 100.0);
        assertEquals(0, status); // ACTIVE
    }
}