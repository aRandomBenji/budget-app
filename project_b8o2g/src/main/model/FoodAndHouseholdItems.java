package model;

// A BudgetCategory Class that covers the FoodAndHouseholdItems budget category
public class FoodAndHouseholdItems extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public FoodAndHouseholdItems(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Food & Household Items";
        this.notes = "Groceries, Pet Food, Detergent, Cleaning Supplies, Toiletries";
    }
}
