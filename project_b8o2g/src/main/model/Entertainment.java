package model;

// A BudgetCategory Class that covers the Entertainment budget category
public class Entertainment extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Entertainment(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Entertainment";
        this.notes = "Streaming Subscriptions, Video Games, Movies, etc.";
    }
}
