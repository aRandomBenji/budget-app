package model;

// A BudgetCategory Class that covers the Health budget category
public class Health extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Health(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Health";
        this.notes = "Gym Membership, Dental Work, Drugs & Medications, Eyeware, Health Insurance";
    }
}
