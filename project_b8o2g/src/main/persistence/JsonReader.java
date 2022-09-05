package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.*;
import org.json.*;


//NOTE: The JSON implementation is adapted from the JsonSerializationDemo file

// Represents a reader that reads BudgetMaster from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads BudgetMaster from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BudgetMaster read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudgetMaster(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses budgetMaster from JSON object and returns it
    private BudgetMaster parseBudgetMaster(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double totalBudget = jsonObject.getDouble("totalBudget");
        double totalExpenses = jsonObject.getDouble("totalExpenses");
        BudgetMaster bm = new BudgetMaster(name, totalBudget, totalExpenses);
        addBudgetCategories(bm, jsonObject);
        return bm;
    }

    // MODIFIES: bm
    // EFFECTS: parses budgetCategories from JSON object and adds them to BudgetMaster
    private void addBudgetCategories(BudgetMaster bm, JSONObject jsonObject) {
        int counter = 0;
        JSONArray jsonArray = jsonObject.getJSONArray("budget categories");
        for (Object json : jsonArray) {
            JSONObject nextBudgetCategory = (JSONObject) json;
            addCategory(bm, nextBudgetCategory, counter);
            counter++;
        }
    }

    // MODIFIES: bm
    // EFFECTS: parses Entry from JSON object and adds it to BudgetCategory
    private void addCategory(BudgetMaster bm, JSONObject jsonObject, int counter) {
        String title = jsonObject.getString("title");
        double budgetMax = jsonObject.getDouble("budgetMax");
        double budgetExpenses = jsonObject.getDouble("budgetUsed");
        BudgetCategory category = getCorrectCategory(title, budgetMax, budgetExpenses);
        bm.setBudgetCategoryAtIndex(counter, category);
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(category, nextEntry);
        }
    }

    // MODIFIES: this
    // EFFECTS: finds correct BudgetCategory and assigns its budgetMax and budgetExpenses
    // NOTE: MADE PUBLIC FOR TESTING PURPOSES
    @SuppressWarnings("methodlength")
    public BudgetCategory getCorrectCategory(String title, double budgetMax, double budgetExpenses) {
        BudgetCategory budgetCategory;

        switch (title) {
            case "Education":
                budgetCategory = new Education(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Entertainment":
                budgetCategory = new Entertainment(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Food & Household Items":
                budgetCategory = new FoodAndHouseholdItems(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Health":
                budgetCategory = new Health(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Housing":
                budgetCategory = new Housing(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Investments & Savings":
                budgetCategory = new InvestmentsAndSaving(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Loans & Debt":
                budgetCategory = new LoansAndDebt(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Personal":
                budgetCategory = new Personal(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Transportation":
                budgetCategory = new Transportation(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Utilities":
                budgetCategory = new Utilities(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Vacation":
                budgetCategory = new Vacation(budgetMax, budgetExpenses);
                return budgetCategory;
            case "Other & Miscellaneous":
                budgetCategory = new Other(budgetMax, budgetExpenses);
                return budgetCategory;
            default:
                return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: parses Entry from JSON Object and adds it to appropriate BudgetCategory
    private void addEntry(BudgetCategory category, JSONObject jsonObject) {
        String label = jsonObject.getString("label");
        double amount = jsonObject.getDouble("amount");
        String notes = jsonObject.getString("notes");
        String date = jsonObject.getString("date");
        int id = jsonObject.getInt("dateID");
        Entry entry = new Entry(label, amount, notes, date, id);
        category.addEntryToList(entry);


    }





}
