package persistence;

import model.BudgetMaster;
import model.Entry;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {


    @Test
    void testWriterInvalidFile() {
        try {
            BudgetMaster bm = new BudgetMaster("testBudgetMaster", 0, 0);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    //NOTE: although this is "empty" it should still contain all the budget categories
    //      which are added as soon as BudgetMaster is instantiated
    @Test
    void testEmptyBudgetMaster() {
        try {
            BudgetMaster budgetMaster = new BudgetMaster("test1", 0, 0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudgetMaster.json");
            writer.open();
            writer.write(budgetMaster);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudgetMaster.json");
            budgetMaster = reader.read();
            assertEquals("test1", budgetMaster.getName());
            assertEquals(12, budgetMaster.getMasterList().size());//12: all categories added when instantiated
            for(int i = 0; i < budgetMaster.getMasterList().size(); i++) {
                assertEquals(0, budgetMaster.getBudgetCategoryAtIndex(i).entryListSize());
            }
        } catch (IOException e) {
            fail("This isn't supposed to fail here");
        }


    }

    @Test
    void testWriterBudgetMaster() {

        try {
            BudgetMaster budget = new BudgetMaster("test1", 0, 0);
            budget.getBudgetCategoryAtIndex(0).addEntryToList
                    (new Entry("test", 10, "test111", "1/1/1", 10101));
            budget.getBudgetCategoryAtIndex(0).addEntryToList
                    (new Entry("test2", 20, "test222", "2/2/2", 20202));
            JsonWriter writer = new JsonWriter("./data/testWriterTestBudgetMaster.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTestBudgetMaster.json");
            budget = reader.read();
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
    void testWriterBudgetMasterAddLastCategories() {

        try {
            BudgetMaster budget = new BudgetMaster("test1", 0, 0);
            budget.getBudgetCategoryAtIndex(11).addEntryToList
                    (new Entry("test", 10, "test111", "1/1/1", 10101));
            budget.getBudgetCategoryAtIndex(5).addEntryToList
                    (new Entry("test2", 20, "test222", "2/2/2", 20202));
            JsonWriter writer = new JsonWriter("./data/testWriterTestBudgetMaster.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTestBudgetMaster.json");
            budget = reader.read();
            assertEquals("test1", budget.getName());
            ArrayList<Entry> entries = budget.getBudgetCategoryAtIndex(11).getEntryList();
            assertEquals(1, entries.size());
            checkEntry("test", 10, "test111", "1/1/1", 10101, entries.get(0));

            entries = budget.getBudgetCategoryAtIndex(5).getEntryList();
            assertEquals(1, entries.size());
            checkEntry("test2", 20, "test222", "2/2/2", 20202, entries.get(0));


        } catch (IOException e) {
            fail("Should not catch error");
        }
    }


}
