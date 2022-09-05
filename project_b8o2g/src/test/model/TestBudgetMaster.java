package model;

import exceptions.NegativeIntegerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBudgetMaster {

    BudgetMaster budgetMaster = new BudgetMaster("test", 0, 0);

    @Test
    public void testBudgetMasterConstructor() {
        assertEquals(0, budgetMaster.getTotalBudget());
        assertEquals(0, budgetMaster.getTotalExpenses());
        assertEquals(0, budgetMaster.getTotalBudgetRemaining());

        assertEquals(budgetMaster.sizeOfMasterList(), 12);

        assertEquals("Education", budgetMaster.getBudgetCategory("Education").getTitle());
        assertEquals("Entertainment", budgetMaster.getBudgetCategory("Entertainment").getTitle());
        assertEquals("Food & Household Items",
                budgetMaster.getBudgetCategory("Food & Household Items").getTitle());
        assertEquals("Health", budgetMaster.getBudgetCategory("Health").getTitle());
        assertEquals("Housing", budgetMaster.getBudgetCategory("Housing").getTitle());
        assertEquals("Investments & Savings",
                budgetMaster.getBudgetCategory("Investments & Savings").getTitle());
        assertEquals("Loans & Debt", budgetMaster.getBudgetCategory("Loans & Debt").getTitle());
        assertEquals("Personal", budgetMaster.getBudgetCategory("Personal").getTitle());
        assertEquals("Transportation", budgetMaster.getBudgetCategory("Transportation").getTitle());
        assertEquals("Utilities", budgetMaster.getBudgetCategory("Utilities").getTitle());
        assertEquals("Vacation", budgetMaster.getBudgetCategory("Vacation").getTitle());
        assertEquals("Other & Miscellaneous",
                budgetMaster.getBudgetCategory("Other & Miscellaneous").getTitle());
        assertEquals(null, budgetMaster.getBudgetCategory("pizza"));

        assertEquals(12, budgetMaster.getMasterList().size());
        assertEquals(12, budgetMaster.sizeOfMasterList());
    }

    @Test
    public void testSetBudget() {
        try {
            budgetMaster.getBudgetCategoryAtIndex(0).setBudgetMax(100);
        } catch (NegativeIntegerException e) {
            fail("should not fail");
        }
        assertEquals(100, budgetMaster.getTotalBudget());
        budgetMaster.setTotalBudget();
        assertEquals(100, budgetMaster.getTotalBudget());
    }

    @Test
    public void testSetExpense() {
        assertEquals(budgetMaster.getTotalExpenses(), 0);
        Entry entry = new Entry("test", 100, "test2", "1/1/1", 10101);
        budgetMaster.getBudgetCategoryAtIndex(0).addEntryToList(entry);
        assertEquals(budgetMaster.getTotalExpenses(), 100);
        budgetMaster.setTotalExpenses();
        assertEquals(budgetMaster.getTotalExpenses(), 100);
    }

    @Test
    public void testGetTotalBudgetRemaining() {
        Entry entry = new Entry("test", 60, "test2", "1/1/1", 10101);
        budgetMaster.getBudgetCategoryAtIndex(0).addEntryToList(entry);
        try {
            budgetMaster.getBudgetCategoryAtIndex(3).setBudgetMax(100);
        } catch (NegativeIntegerException e) {
            fail("should not fail");
        }
        assertEquals(40, budgetMaster.getTotalBudgetRemaining());
    }

    @Test
    public void testGetName() {
        assertEquals("test", budgetMaster.getName());
        budgetMaster.setName("pizza");
        assertEquals("pizza", budgetMaster.getName());
    }
}
