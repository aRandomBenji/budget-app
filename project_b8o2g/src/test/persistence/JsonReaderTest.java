package persistence;

import model.BudgetCategory;
import model.BudgetMaster;
import model.Entry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            BudgetMaster budgetMaster = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadEmptyBudgetMaster() {

        try {
            JsonReader reader = new JsonReader("./data/testWriterEmptyBudgetMaster.json");
            BudgetMaster budgetMaster = reader.read();
            assertEquals("test1", budgetMaster.getName());
            assertEquals(12, budgetMaster.getMasterList().size());//12: all categories added when instantiated
            for (int i = 0; i < budgetMaster.getMasterList().size(); i++) {
                assertEquals(0, budgetMaster.getBudgetCategoryAtIndex(i).entryListSize());
            }
        } catch (IOException e) {
            fail("Reading empty-list error");
        }
    }

    @Test
    void testReadBudgetMaster() {

        try {
            JsonReader reader = new JsonReader("./data/testWriterTestBudgetMaster.json");
            BudgetMaster budget = reader.read();
            assertEquals("test1", budget.getName());
            ArrayList<Entry> entries = budget.getBudgetCategoryAtIndex(0).getEntryList();
            assertEquals(2, entries.size());
            checkEntry("test", 10, "test111", "1/1/1", 10101, entries.get(0));
            checkEntry("test2", 20, "test222", "2/2/2", 20202, entries.get(1));


        } catch (IOException e) {
            fail("Should not catch error");
        }
    }

    @Test
    void testReadBudgetMasterUsingCategoryName() {

        try {
            JsonReader reader = new JsonReader("./data/testWriterTestBudgetMaster.json");
            BudgetMaster budget = reader.read();
            assertEquals("test1", budget.getName());
            ArrayList<Entry> entries = budget.getBudgetCategory("Education").getEntryList();
            assertEquals(2, entries.size());
            checkEntry("test", 10, "test111", "1/1/1", 10101, entries.get(0));
            checkEntry("test2", 20, "test222", "2/2/2", 20202, entries.get(1));


        } catch (IOException e) {
            fail("Should not catch error");
        }
    }

    @Test
    void testGetCorrectCategory() {
        try {
            JsonReader reader = new JsonReader("./data/testWriterTestBudgetMaster.json");
            BudgetMaster budgetMaster = reader.read();
            BudgetCategory category = reader.getCorrectCategory("Health", 100, 30);
            budgetMaster.setBudgetCategoryAtIndex(3, category);
            assertEquals(100, budgetMaster.getBudgetCategoryAtIndex(3).getBudgetMax());
            //NOTE: getBudgetUsed() calculates expenses by iterating through entries - since there are no entries
            //      this will remain at "0." This is a fail safe in the project to ensure getBudgetUsed is always
            //      calculated the actual sum of all entries, and not the previous set amount for used budget
            assertEquals(0, budgetMaster.getBudgetCategory("Health").getBudgetUsed());

            assertNull(reader.getCorrectCategory("halth", 100, 22));
        } catch (IOException e) {
            fail("Should not catch error");
        }
    }


}
