package model;

// A BudgetCategory Class that covers the Personal budget category
public class Personal extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Personal(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Personal";
        this.notes = "Clothing, Cosmetics, Haircuts, Salon, etc.";
    }
}
