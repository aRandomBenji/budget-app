package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAllCategories {

    BudgetCategory education = new Education(0,0);
    BudgetCategory entertainment = new Entertainment(0,0);
    BudgetCategory food = new FoodAndHouseholdItems(0,0);
    BudgetCategory health = new Health(0,0);
    BudgetCategory housing = new Housing(0,0);
    BudgetCategory investments = new InvestmentsAndSaving(0,0);
    BudgetCategory loans = new LoansAndDebt(0,0);
    BudgetCategory other = new Other(0,0);
    BudgetCategory personal = new Personal(0,0);
    BudgetCategory transportation = new Transportation(0,0);
    BudgetCategory utilities = new Utilities(0,0);
    BudgetCategory vacation = new Vacation(0,0);

    @Test
    public void testCategories() {
        assertEquals(education.getTitle(), "Education");
        assertEquals(education.getNotes(), "Tuition, Textbooks, Student Loan Payments, etc.");

        assertEquals(entertainment.getTitle(), "Entertainment");
        assertEquals(entertainment.getNotes(), "Streaming Subscriptions, Video Games, Movies, etc.");

        assertEquals(food.getTitle(), "Food & Household Items");
        assertEquals(food.getNotes(), "Groceries, Pet Food, Detergent, Cleaning Supplies, Toiletries");

        assertEquals(health.getTitle(), "Health");
        assertEquals(health.getNotes(),
                "Gym Membership, Dental Work, Drugs & Medications, Eyeware, Health Insurance");

        assertEquals(housing.getTitle(), "Housing");
        assertEquals(housing.getNotes(), "Mortgage, Rent, Property Taxes, Repairs, House Insurance");

        assertEquals(investments.getTitle(), "Investments & Savings");
        assertEquals(investments.getNotes(), "Stocks, Bonds, GICs, Savings");

        assertEquals(loans.getTitle(), "Loans & Debt");
        assertEquals(loans.getNotes(), "line of Credit, Credit Card Debt, etc.");

        assertEquals(other.getTitle(), "Other & Miscellaneous");
        assertEquals(other.getNotes(), "Wedding Gifts, Charity, etc.");

        assertEquals(personal.getTitle(), "Personal");
        assertEquals(personal.getNotes(), "Clothing, Cosmetics, Haircuts, Salon, etc.");

        assertEquals(transportation.getTitle(), "Transportation");
        assertEquals(transportation.getNotes(),
                "Car Payments, Gas, Tires, Maintenance, Parking Permits, Repairs, Car Insurance, etc.");

        assertEquals(utilities.getTitle(), "Utilities");
        assertEquals(utilities.getNotes(), "Electricity, Water, Garbage, Phones, Cable, Internet");

        assertEquals(vacation.getTitle(), "Vacation");
        assertEquals(vacation.getNotes(), "Airplanes, Hotels, Restaurants, etc.");
    }
}
