package model;

import exceptions.NegativeIntegerException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//NOTE: The JSON implementation is adapted from the JsonSerializationDemo file

//Abstract class which instantiates budget categories (e.g. Health category, Education category)
public abstract class BudgetCategory implements Writable {

    protected ArrayList<Entry> entryList;
    protected String title;
    protected String notes;
    protected double budgetMax;
    protected double budgetUsed;

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public BudgetCategory(double budgetMax, double budgetUsed) {

        entryList = new ArrayList<>();
        this.budgetMax = budgetMax;
        this.budgetUsed = budgetUsed;


    }

    //MODIFIES: this
    //EFFECTS: adds entry to entryList
    public void addEntryToList(Entry entry) {
        entryList.add(entry);
    }

    //EFFECTS: returns entry at index(int) from entryList
    public Entry getEntryAtIndex(int index) {
        return entryList.get(index);
    }

    public Entry getEntry(String entryLabel) {
        for (Entry e : entryList) {
            if (e.getLabel() == entryLabel) {
                return e;
            }
        }
        return null;
    }

    //MODIFIES: this
    //EFFECTS: sets this.notes
    public void setNotes(String notes) {
        this.notes = notes;
    }

    //MODIFIES: this
    //EFFECTS: sets this.title
    public void setTitle(String title) {
        this.title = title;
    }

    //REQUIRES: budgetMax > 0
    //MODIFIES: this
    //EFFECTS: sets budgetMax, logs event
    public void setBudgetMax(double budgetMax) throws NegativeIntegerException {

        if (budgetMax < 0) {
            throw new NegativeIntegerException();
        }
        this.budgetMax = budgetMax;
        EventLog.getInstance().logEvent(new Event(" user updated budget max for " + this.getTitle()
                + " to " + budgetMax));
    }

    //EFFECTS: returns this.title
    public String getTitle() {
        return this.title;
    }

    //EFFECTS: returns this.notes
    public String getNotes() {
        return this.notes;
    }

    //EFFECTS: returns this.budgetMax;
    public double getBudgetMax() {
        return budgetMax;
    }

    //MODIFIES: this
    //EFFECTS: returns sum of all entry amounts
    public double getBudgetUsed() {
        double total = 0;
        for (int i = 0; i < entryListSize(); i++) {
            double entryAmount = entryList.get(i).getAmount();
            total += entryAmount;
        }
        budgetUsed = total;
        return budgetUsed;
    }

    //EFFECTS: returns budgetMax - budgetUsed
    public double getBudgetRemaining() {
        return budgetMax - getBudgetUsed();
    }

    //EFFECTS: returns this.entryList as ArrayList<Entry>
    public ArrayList<Entry> getEntryList() {
        return entryList;
    }

    //EFFECTS: returns size of this.entryList
    public int entryListSize() {
        return entryList.size();
    }

    // MODIFIES: this
    // EFFECTS: removes given entry from entryList, logs event
    public boolean removeEntry(Entry entry) {
        for (Entry e : entryList) {
            if (e.equals(entry)) {
                entryList.remove(e);
                EventLog.getInstance().logEvent(new Event(" user removed entry: " + e.getLabel()));
                return true;
            }
        }
        return false;
    }

    // MODIFIES: json
    // EFFECTS: assigns BudgetCategory fields to JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        getBudgetUsed();
        json.put("title", title);
        json.put("budgetMax", budgetMax);
        json.put("budgetUsed", budgetUsed);
        json.put("entries", entriesToJson());
        return json;
    }

    // MODIFIES: jsonArray
    // EFFECTS: assigns Entries to JSONArray
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Entry entry : entryList) {
            jsonArray.put(entry.toJson());
        }

        return jsonArray;
    }


}
