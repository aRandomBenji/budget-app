package model;

// A BudgetCategory Class that covers the Housing budget category
public class Housing extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Housing(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Housing";
        this.notes = "Mortgage, Rent, Property Taxes, Repairs, House Insurance";
    }
}
