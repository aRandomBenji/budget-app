package model;

// A BudgetCategory Class that covers a Miscellaneous budget category
public class Other extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Other(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Other & Miscellaneous";
        this.notes = "Wedding Gifts, Charity, etc.";
    }
}
