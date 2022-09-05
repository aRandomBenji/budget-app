package model;

// A BudgetCategory Class that covers the Education budget category
public class Education extends BudgetCategory {

    //EFFECTS: creates budgetCategory with empty ArrayList, and all values at 0
    public Education(double budgetMax, double budgetUsed) {
        super(budgetMax, budgetUsed);

        this.title = "Education";
        this.budgetMax = budgetMax;
        this.budgetUsed = budgetUsed;
        this.notes = "Tuition, Textbooks, Student Loan Payments, etc.";
    }

}
