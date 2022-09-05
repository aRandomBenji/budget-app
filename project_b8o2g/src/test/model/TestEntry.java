package model;

import exceptions.NegativeIntegerException;
import exceptions.WrongDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestEntry {
    Entry entry;

    @BeforeEach
    public void setup() {

        entry = new Entry("test",0,"test","0/0/0", -1);
    }

    @Test
    public void testEntry() {
        entry.setLabel("label2");

        try {
            entry.setAmount(10);
        } catch (NegativeIntegerException ne) {
            fail("This shouldn't be negative");
        }

        entry.setNote("note2");

        assertEquals(entry.getAmount(), 10);
        assertEquals(entry.getLabel(), "label2");
        assertEquals(entry.getNote(), "note2");

    }

    @Test
    public void testSetDateAllGood() {
        try {
            entry.setDate(1, 1, 1);
        } catch (WrongDateException wd) {
            fail();
        }
        assertEquals(entry.getDate(), "1/1/1");

    }

    @Test
    public void testSetDateDayWrong() {
        //day is too low
        try {
            entry.setDate(0, 1, 1);
            fail();
        } catch (WrongDateException wd) {

        }

        //day is too high
        try {
            entry.setDate(32, 1, 1);
            fail();
        } catch (WrongDateException wd) {

        }

    }

    @Test
    public void testSetDateMonthWrong() {
        //day is too low
        try {
            entry.setDate(1, 0, 1);
            fail();
        } catch (WrongDateException wd) {
            //
        }

        //day is too high
        try {
            entry.setDate(1, 13, 1);
            fail();
        } catch (WrongDateException wd) {

        }

    }

    @Test
    public void testSetDateYearWrong() {
        //day is too low
        try {
            entry.setDate(1, 1, -1);
            fail();
        } catch (WrongDateException wd) {

        }

        //day is too high
        try {
            entry.setDate(1, 1, 2201);
            fail();
        } catch (WrongDateException wd) {
            //
        }

    }

    @Test
    public void testSetDateNumber() {
        assertEquals(entry.setDateNumber(1,1,1), 10101);
        assertEquals(entry.getDateID(), 10101);
        assertEquals(entry.setDateNumber(23, 10, 1987), 19871023);
        assertEquals(entry.getDateID(), 19871023);
    }

    @Test
    public void testSetAmount() {
        // basic amount passes
        try {
            entry.setAmount(100);
        } catch (NegativeIntegerException ne) {
            fail();
        }

        try {
            entry.setAmount(9.99);
        } catch (NegativeIntegerException ne) {
            fail();
        }

        try {
            entry.setAmount(-5);
            fail();
        } catch (NegativeIntegerException ne) {
           //should pass
        }
    }



}
