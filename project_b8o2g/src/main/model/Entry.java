package model;

import exceptions.NegativeIntegerException;
import exceptions.WrongDateException;
import org.json.JSONObject;
import persistence.Writable;

// NOTE: The JSON implementation is adapted from the JsonSerializationDemo file

// Creates a budget entry with a label, amount, date, and notes
public class Entry implements Writable {

    private String label;
    private String notes;
    private double amount;
    private String date;
    private int dateID;

    //EFFECTS: instantiates entry object with all fields at 0 or ""
    public Entry(String label, double amount, String notes, String date, int id) {
        this.label = label;
        this.amount = amount;
        this.notes = notes;
        this.date = date;
        dateID = id;

    }

    //MODIFIES: this
    //EFFECTS: sets labels, logs event
    public void setLabel(String label) {
        this.label = label;
        EventLog.getInstance().logEvent(new Event("Entry name set to: " + label));
    }

    //MODIFIES: this
    //EFFECTS: sets notes, logs event
    public void setNote(String notes) {
        this.notes = notes;
        EventLog.getInstance().logEvent(new Event("Entry notes set to: " + notes));
    }

    //MODIFIES: this
    //EFFECTS: sets amount, logs event
    public void setAmount(double amount) throws NegativeIntegerException {

        if (amount < 0) {
            throw new NegativeIntegerException();
        }
        this.amount = amount;
        EventLog.getInstance().logEvent(new Event("Entry amount set to: " + amount));
    }

    //MODIFIES: this
    //EFFECTS: sets date, checking the days, month, and year are reasonable, also sets dateID, logs event
    public void setDate(int day, int month, int year) throws WrongDateException {

        if (day < 1 || day > 31) {
            throw new WrongDateException();
        }
        if (month < 1 || month > 12) {
            throw new WrongDateException();
        }
        if (year < 0 || year > 2200) {
            throw new WrongDateException();
        }

        setDateNumber(day, month, year);
        this.date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
        EventLog.getInstance().logEvent(new Event("Entry date set to: " + this.date));
    }

    //MODIFIES: this
    //EFFECTS: creates dateID (to order each entry in entry list)
    public int setDateNumber(int day, int month, int year) {
        dateID = (year * 10000) + (month * 100) + day;
        return dateID;
    }

    //EFFECTS: returns label as String
    public String getLabel() {
        return this.label;
    }

    //EFFECTS: returns notes as String
    public String getNote() {
        return this.notes;
    }

    //EFFECTS: returns amount as double
    public double getAmount() {
        return this.amount;
    }

    //EFFECTS: returns date as String
    public String getDate() {
        return this.date;
    }

    //EFFECTS: returns dateID as int
    public int getDateID() {
        return this.dateID;
    }

    // MODIFIES: json
    // EFFECTS: assigns Entry fields to JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("label", label);
        json.put("notes", notes);
        json.put("amount", amount);
        json.put("date", date);
        json.put("dateID", dateID);
        return json;
    }

}
