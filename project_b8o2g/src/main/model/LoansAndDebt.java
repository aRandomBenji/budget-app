package model;

// A BudgetCategory Class that covers the Loans and Debt budget category
public class LoansAndDebt extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public LoansAndDebt(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);
        this.title = "Loans & Debt";
        this.notes = "line of Credit, Credit Card Debt, etc.";
    }
}
