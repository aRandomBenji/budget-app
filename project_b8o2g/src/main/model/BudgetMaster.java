package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// NOTE: The JSON implementation is adapted from the JsonSerializationDemo file

// The BudgetMaster which holds all the BudgetCategories as well as total budgets and expenses
public class BudgetMaster implements Writable {

    private double totalBudget;
    private double totalExpenses;
    private String name;
    final ArrayList<BudgetCategory> masterList;

    final BudgetCategory education = new Education(0, 0);
    final BudgetCategory entertainment = new Entertainment(0, 0);
    final BudgetCategory food = new FoodAndHouseholdItems(0, 0);
    final BudgetCategory health = new Health(0, 0);
    final BudgetCategory housing = new Housing(0, 0);
    final BudgetCategory investments = new InvestmentsAndSaving(0, 0);
    final BudgetCategory loans = new LoansAndDebt(0, 0);
    final BudgetCategory other = new Other(0, 0);
    final BudgetCategory personal = new Personal(0, 0);
    final BudgetCategory transportation = new Transportation(0, 0);
    final BudgetCategory utilities = new Utilities(0, 0);
    final BudgetCategory vacation = new Vacation(0, 0);

    // EFFECT: creates a new masterList with all budget categories and all values at 0.
    public BudgetMaster(String name, double totalBudget, double totalExpenses) {
        masterList = new ArrayList<>();
        addAllCategories();

        this.name = name;
        this.totalBudget = totalBudget;
        this.totalExpenses = totalExpenses;

    }

    public void addAllCategories() {

        masterList.add(education);
        masterList.add(entertainment);
        masterList.add(food);
        masterList.add(health);
        masterList.add(housing);
        masterList.add(investments);
        masterList.add(loans);
        masterList.add(other);
        masterList.add(personal);
        masterList.add(transportation);
        masterList.add(utilities);
        masterList.add(vacation);
    }

    public ArrayList<BudgetCategory> getMasterList() {
        return masterList;
    }

    // EFFECTS: returns BudgetCategory with same title as given title
    public BudgetCategory getBudgetCategory(String title) {
        for (BudgetCategory category : masterList) {
            if (category.getTitle() == title) {
                return category;
            }
        }
        return null;
    }

    public BudgetCategory getBudgetCategoryAtIndex(int index) {
        return masterList.get(index);
    }

    // EFFECTS: returns size of masterList
    public int sizeOfMasterList() {
        return masterList.size();
    }

    // MODIFIES: this
    // EFFECTS: sums all assigned budget amounts from all categories and assigns to totalBudget and returns true
    public void setTotalBudget() {
        for (BudgetCategory category : masterList) {
            totalBudget += category.getBudgetMax();
        }
    }

    // MODIFIES: this
    // EFFECTS: sums all expenses from all categories and assigns to totalExpenses and returns true
    public void setTotalExpenses() {
        for (BudgetCategory category : masterList) {
            totalExpenses += category.getBudgetUsed();
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // MODIFIES: this
    // EFFECTS: Iterates through all categories and returns the sum of their combined budgets
    public double getTotalBudget() {
        int budgetTally = 0;
        for (int i = 0; i < masterList.size(); i++) {
            budgetTally += masterList.get(i).getBudgetMax();
        }
        this.totalBudget = budgetTally;
        return totalBudget;
    }

    // MODIFIES: this
    // EFFECTS: gets total expenses by getting budget expenses from each BudgetCategory
    public double getTotalExpenses() {
        int expenseTally = 0;
        for (int i = 0; i < masterList.size(); i++) {
            expenseTally += masterList.get(i).getBudgetUsed();
        }
        this.totalExpenses = expenseTally;
        return totalExpenses;
    }

    // EFFECTS: returns Budget - Expenses
    public double getTotalBudgetRemaining() {
        return getTotalBudget() - getTotalExpenses();
    }

    // EFFECTS: sets BudgetCategory to given index
    public void setBudgetCategoryAtIndex(int index, BudgetCategory category) {
        masterList.set(index, category);
    }


    // MODIFIES: json
    // EFFECTS: assigns BudgetMaster fields to JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("totalBudget", totalBudget);
        json.put("totalExpenses", totalExpenses);
        json.put("budget categories", categoriesToJson());
        return json;
    }

    // MODIFIES: jsonArray
    // EFFECTS: assigns BudgetCategories to JSONArray
    private JSONArray categoriesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (BudgetCategory category : masterList) {
            jsonArray.put(category.toJson());
        }

        return jsonArray;
    }
}


