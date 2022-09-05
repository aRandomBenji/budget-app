package persistence;

import model.Entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    public void checkEntry(String label, double amount, String notes, String date, int dateID, Entry entry) {

        assertEquals(label, entry.getLabel());
        assertEquals(notes, entry.getNote());
        assertEquals(amount, entry.getAmount());
        assertEquals(date, entry.getDate());
        assertEquals(dateID, entry.getDateID());
    }
}
