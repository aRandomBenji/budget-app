package model;

// A BudgetCategory Class that covers the Transportation budget category
public class Transportation extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Transportation(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Transportation";
        this.notes = "Car Payments, Gas, Tires, Maintenance, Parking Permits, Repairs, Car Insurance, etc.";
    }
}
