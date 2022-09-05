package model;

// A BudgetCategory Class that covers the Utilities budget category
public class Utilities extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Utilities(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Utilities";
        this.notes = "Electricity, Water, Garbage, Phones, Cable, Internet";
    }
}
