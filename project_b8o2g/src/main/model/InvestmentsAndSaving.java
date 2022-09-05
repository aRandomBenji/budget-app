package model;

// A BudgetCategory Class that covers the InvestmentsAndSaving budget category
public class InvestmentsAndSaving extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public InvestmentsAndSaving(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Investments & Savings";
        this.notes = "Stocks, Bonds, GICs, Savings";
    }
}
