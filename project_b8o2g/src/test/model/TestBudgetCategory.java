package model;

import exceptions.NegativeIntegerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestBudgetCategory {

    ArrayList<Entry> testList;
    BudgetCategory testCategory;
    Entry testEntry1;
    Entry testEntry2;
    Entry testEntry3;

    @BeforeEach
    public void setup() {
        testList = new ArrayList<>();
        testCategory = new Entertainment(0,0);
        testEntry1 = new Entry("test", 0, "test", "1/1/1", 111);
        testEntry1.setLabel("test1");
        testEntry2 = new Entry("test", 0, "test", "1/1/1", 111);
        testEntry2.setLabel("test2");
        testEntry3 = new Entry("test", 0, "test", "1/1/1", 111);
        testEntry3.setLabel("test3");
    }

    @Test
    public void testBudgetCategory() {
        assertEquals(testCategory.getBudgetUsed(), 0);
        assertEquals(testCategory.getBudgetMax(), 0);
        assertEquals(testCategory.getBudgetRemaining(), 0);
    }

    @Test
    public void testGetEntryAtIndex() {
        testCategory.addEntryToList(testEntry1);
        testCategory.addEntryToList(testEntry2);
        testCategory.addEntryToList(testEntry3);

        assertEquals(testCategory.getEntryAtIndex(0).getLabel(), "test1");
        assertEquals(testCategory.getEntryAtIndex(1).getLabel(), "test2");
        assertEquals(testCategory.getEntryAtIndex(2).getLabel(), "test3");
    }

    @Test
    public void testSetNotes() {
        testCategory.setNotes("test1");
        assertEquals(testCategory.getNotes(), "test1");
    }

    @Test
    public void testSetTitle() {
        testCategory.setTitle("Test1");
        assertEquals(testCategory.getTitle(), "Test1");
    }

    @Test
    public void testSetBudgetMax() {

        try {
            testCategory.setBudgetMax(1000);
        } catch (NegativeIntegerException ne) {
            fail();
        }
        assertEquals(testCategory.getBudgetMax(), 1000);
        assertEquals(testCategory.getBudgetUsed(), 0);
        assertEquals(testCategory.getBudgetRemaining(), 1000);
    }

    @Test
    public void testSetBudgetMaxFail() {
        try {
            testCategory.setBudgetMax(-100);
            fail();
        } catch (NegativeIntegerException ne) {
            //
        }
    }

    @Test
    public void testGetBudgetUsed() {
        try {
            testEntry1.setAmount(50);
            testEntry2.setAmount(100);
            testEntry3.setAmount(150);

        } catch (NegativeIntegerException ne) {
            fail();
        }

        testCategory.addEntryToList(testEntry1);
        testCategory.addEntryToList(testEntry2);
        testCategory.addEntryToList(testEntry3);

        assertEquals(testCategory.getBudgetUsed(), 300);
        assertEquals(testCategory.getBudgetRemaining(), -300);
        assertEquals(testCategory.getBudgetMax(), 0);


    }

    @Test
    public void testGetEntryList() {
        assertEquals(testCategory.entryListSize(), 0);

        testCategory.addEntryToList(testEntry1);
        testCategory.addEntryToList(testEntry2);
        testCategory.addEntryToList(testEntry3);

        assertEquals(testCategory.entryListSize(), 3);

        assertEquals(testCategory.getEntryList().get(0).getLabel(), "test1");
    }

    @Test
    public void testGetEntryWithLabel() {
        testCategory.addEntryToList(testEntry1);
        testCategory.addEntryToList(testEntry2);
        testCategory.addEntryToList(testEntry3);

        assertEquals(testEntry2, testCategory.getEntry("test2"));
        assertNull(testCategory.getEntry("asdfkljasdf"));
    }


}
