package model;

// A BudgetCategory Class that covers the Vacation budget category
public class Vacation extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Vacation(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Vacation";
        this.notes = "Airplanes, Hotels, Restaurants, etc.";
    }
}
