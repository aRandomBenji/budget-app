package ui;

import exceptions.NegativeIntegerException;
import exceptions.WrongDateException;
import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

// The BudgetApp: this runs the BudgetApp program
public class BudgetApp {
    private static final String JSON_STORE = "./data/budgetmaster.json";
    private boolean stillRunning;
    private String command;
    private BudgetMaster budgetMaster;
    private Scanner scan = new Scanner(System.in);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: Adds all BudgetCategories, starts BudgetApp
    public BudgetApp() {

        stillRunning = true;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        runBudgetApp();
    }

    // MODIFIES: this
    // EFFECTS: runs BudgetApp
    public void runBudgetApp() {

        System.out.println("Welcome to the BudgetApp!");

        newOrLoadBudgetMenu();
        createOrLoadBudget();

        while (stillRunning) {
            titleMenu();
            titleSelection();
        }

    }

    // EFFECTS: prints out newOrLoad budget menu
    private void newOrLoadBudgetMenu() {
        System.out.println("Would you like to create a new budget or open a previous one?");
        System.out.println("n -> new budget\ne -> open existing budget");
    }

    private void createOrLoadBudget() {
        String command = scan.nextLine();
        if (command.equals("n")) {
            budgetMaster = new BudgetMaster("test", 0, 0);
        } else if (command.equals("e")) {
            loadBudgetMaster();
        } else {
            System.out.println("Wrong input! Please try again: ");
            newOrLoadBudgetMenu();
            createOrLoadBudget();
        }
    }

    //EFFECTS: prints out title menu
    private void titleMenu() {
        System.out.println("Please select from the options below: ");
        System.out.println("e -> edit, create or remove a new budget entry\nv -> view all budget categories\n"
                + "s -> summary of budget" + "\nm -> metrics and statistics" + "\ny -> set category budget"
                + "\nc -> print a single budget category\np -> print out full budget"
                + "\nt -> save a version of this Budget App\nq -> quit BudgetApp");
    }

    // MODIFIES: this
    // EFFECTS: uses user input to navigate menu
    @SuppressWarnings("methodlength")
    private void titleSelection() {
        command = scan.nextLine();

        switch (command) {
            case "e":
                entryMenu();
                break;
            case "v":
                printAllBudgetCategoriesWithNotes();
                break;
            case "s":
                printSummary();
                break;
            case "m":
                metricsAndStatistics();
                break;
            case "y":
                setCategoryBudget(categoryChooser(budgetCategoryMenu()));
                break;
            case "c":
                printCategoryBudget(categoryChooser(budgetCategoryMenu()));
                break;
            case "p":
                printFullBudget();
                break;
            case "t":
                saveBudgetMaster();
                break;
            case "q":
                stillRunning = false;
                break;
            default:
                System.out.println("Invalid Input: please try again.");

        }

    }

    // MODIFIES: this
    // EFFECTS: uses user input to navigate entry menu
    private void entryMenu() {
        System.out.println("Would you like to edit or create a budget entry? \ne -> edit\nc -> create\nr -> remove");
        String command = scan.nextLine();
        if (command.equals("e")) {
            editEntry();
        } else if (command.equals("c")) {
            addNewEntry(categoryChooser(budgetCategoryMenu()));
        } else if (command.equals("r")) {
            removeEntry();
        }
    }

    // EFFECTS: displays budget category menu
    private String budgetCategoryMenu() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please choose a budget category: ");
        System.out.println("edu -> Education\nent -> Entertainment\nfoo -> Food & Household Items\nhlt -> Health"
                + "\nhou -> Housing & Rent\ninv -> Investments & Savings\ndeb -> Debts & Loans\nper -> Personal"
                + "\ntra -> Transportation\nuti -> Utilities\nvac -> Vacation\noth -> Other");

        return scan.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: uses userInput to select category, repeats if incorrect input
    @SuppressWarnings("methodlength")
    private BudgetCategory categoryChooser(String userInput) {

        BudgetCategory category;

        switch (userInput) {
            case "edu":
                category = budgetMaster.getBudgetCategory("Education");
                break;
            case "ent":
                category = budgetMaster.getBudgetCategory("Entertainment");
                break;
            case "foo":
                category = budgetMaster.getBudgetCategory("Food & Household Items");
                break;
            case "hlt":
                category = budgetMaster.getBudgetCategory("Health");
                break;
            case "hou":
                category = budgetMaster.getBudgetCategory("Housing");
                break;
            case "inv":
                category = budgetMaster.getBudgetCategory("Investments & Savings");
                break;
            case "deb":
                category = budgetMaster.getBudgetCategory("Loans & Debt");
                break;
            case "per":
                category = budgetMaster.getBudgetCategory("Personal");
                break;
            case "tra":
                category = budgetMaster.getBudgetCategory("Transportation");
                break;
            case "uti":
                category = budgetMaster.getBudgetCategory("Utilities");
                break;
            case "vac":
                category = budgetMaster.getBudgetCategory("Vacation");
                break;
            case "oth":
                category = budgetMaster.getBudgetCategory("Other & Miscellaneous");
                break;
            default:
                category = null;
                System.out.println("Incorrect input, please try again: ");
                categoryChooser(budgetCategoryMenu());

        }
        return category;
    }

    //MODIFIES: this, entry
    //EFFECTS: creates new entry, modifies, and adds to category
    private void addNewEntry(BudgetCategory category) {
        Entry entry = new Entry("test", 0, "blank","0/0/0", -1);
        entry = entryModification(entry, category);
        category.addEntryToList(entry);
    }

    //MODIFIES: this
    //EFFECTS: modifies entry from user selected category
    private void editEntry() {
        String command;
        BudgetCategory category;

        System.out.println("First, let's find the category in which your item is located: ");
        command = budgetCategoryMenu();
        category = categoryChooser(command);
        System.out.println("Please enter the name of the entry you'd like to edit from the list of entries below: ");
        printCategoryEntries(category);
        System.out.print("Name: ");
        String name = scan.nextLine().toLowerCase();

        for (Entry e : category.getEntryList()) {
            if (e.getLabel().equals(name)) {
                System.out.println("Enter the info for the new entry below: ");
                entryModification(e, category);
                break;
            }
        }
        System.out.println("Entry not found: please try again.");
    }

    //MODIFIES: this
    //EFFECTS: modifies entry from user selected category
    private void removeEntry() {

        String command;
        BudgetCategory category;

        System.out.println("First, let's find the category in which your item is located: ");
        command = budgetCategoryMenu();
        category = categoryChooser(command);
        System.out.println("Please enter the name of the entry you'd like to remove from the list of entries below: ");
        printCategoryEntries(category);
        System.out.print("Name: ");
        String name = scan.nextLine().toLowerCase();

        for (Entry e : category.getEntryList()) {
            if (e.getLabel().equals(name)) {
                category.getEntryList().remove(e);
                break;
            }
        }

        System.out.println("Entry not found: please try again.");
    }

    //MODIFIES: entry, this
    //EFFECTS: modifies an existing entry
    private Entry entryModification(Entry entry, BudgetCategory category) {
        boolean stillWorkingOnIt = true;
        String userChoice;

        while (stillWorkingOnIt) {
            System.out.println("Please enter the name for this entry below: ");
            entry.setLabel(scan.nextLine());

            setEntryAmount(entry);

            setEntryDate(entry);

            System.out.println("Please enter any notes you have for this entry: ");
            scan.nextLine();
            entry.setNote(scan.nextLine());

            System.out.println("Here is what you have inputted so far: \nName: " + entry.getLabel() + "\nAmount: "
                    + entry.getAmount() + "\nDate: " + entry.getDate() + "\nNotes: " + entry.getNote());
            checkOverBudget(category, entry.getAmount());
            System.out.println("Enter 'y' to create this entry, or enter any key to redo.");
            userChoice = scan.nextLine();

            if (userChoice.toLowerCase(Locale.ROOT).equals("y")) {
                stillWorkingOnIt = false;
            }
        }
        return entry;
    }

    //MODIFIES: entry, this
    //EFFECTS: sets the amount for a given entry and returns entry, also sets totalBudget
    private Entry setEntryAmount(Entry entry) {
        System.out.println("Please enter the amount spent on this entry below: ");
        double amount = Double.parseDouble(scan.next());
        try {
            entry.setAmount(amount);
        } catch (NegativeIntegerException neg) {
            System.out.println("Whoops! Can't input a negative amount - please try again!");
            setEntryAmount(entry);
        }
        budgetMaster.setTotalExpenses();
        return entry;
    }

    //MODIFIES: entry, this
    //EFFECTS: sets the date for a given entry and return entry
    private Entry setEntryDate(Entry entry) {
        System.out.println("Please enter the date below: ");
        try {
            entry.setDate(getDayFromUser(), getMonthFromUser(), getYearFromUser());
        } catch (WrongDateException wde) {
            System.out.println("Invalid Date");
            setEntryDate(entry);
        }
        return entry;
    }

    //EFFECTS: prints all budget categories and their notes
    private void printAllBudgetCategoriesWithNotes() {
        for (BudgetCategory c : budgetMaster.getMasterList()) {
            System.out.println(c.getTitle());
            System.out.println(c.getNotes());
        }
    }

    //EFFECTS: prints all category names and their amounts
    private void printSummary() {

        for (int i = 0; i < budgetMaster.sizeOfMasterList(); i++) {
            double budgetUsed = budgetMaster.getMasterList().get(i).getBudgetUsed();
            System.out.println(budgetMaster.getMasterList().get(i).getTitle() + ": " + budgetUsed);
        }
        System.out.println("Total: " + totalExpenseSum());
    }

    //EFFECTS: sums total amounts from all entries in all categories
    private double totalExpenseSum() {
        double budgetTotal = 0;
        for (int i = 0; i < budgetMaster.sizeOfMasterList(); i++) {
            double budgetUsed = budgetMaster.getMasterList().get(i).getBudgetUsed();
            budgetTotal += budgetUsed;
        }
        return budgetTotal;
    }

    // EFFECTS: prints the titles of all the entries for given category
    private void printCategoryEntries(BudgetCategory category) {
        for (int i = 0; i < category.entryListSize(); i++) {
            Entry e = category.getEntryAtIndex(i);
            System.out.println(e.getLabel());
        }
    }

    // EFFECTS: prints all the entries (and their data) for a given category
    private void printCategoryBudget(BudgetCategory category) {
        for (int i = 0; i < category.getEntryList().size(); i++) {
            Entry e = category.getEntryAtIndex(i);
            System.out.print("Name: " + e.getLabel() + " ");
            System.out.print("Amount: " + e.getAmount() + " ");
            System.out.print("Date: " + e.getDate() + " ");
            System.out.println("Notes: " + e.getNote() + " ");
        }
        System.out.println("Budget: " + category.getBudgetMax() + "\nBudget Used: " + category.getBudgetUsed()
                + "\nBudget Remaining: "
                + category.getBudgetRemaining());
    }

    // EFFECTS: prints out all entries from all categories as well as the collective sum of their amounts
    private void printFullBudget() {
        for (int i = 0; i < budgetMaster.sizeOfMasterList(); i++) {
            System.out.println(budgetMaster.getMasterList().get(i).getTitle());
            printCategoryBudget(budgetMaster.getMasterList().get(i));
            System.out.println();
        }

        System.out.println("Total budget used: " + totalExpenseSum());
    }

    // EFFECTS: returns int from userInput
    private int getDayFromUser() {
        System.out.println("Enter day (e.g. 21): ");
        return Integer.parseInt(scan.next());
    }

    // EFFECTS: returns int from userInput
    private int getMonthFromUser() {
        System.out.println("Enter Month (e.g. 10): ");
        return Integer.parseInt(scan.next());
    }

    // EFFECTS: returns int from userInput
    private int getYearFromUser() {
        System.out.println("Enter Year (e.g. 2022): ");
        return Integer.parseInt(scan.next());
    }

    //EFFECTS: prints out metrics/statistics about the program
    private void metricsAndStatistics() {
        System.out.println("Here is the budget allocation: ");
        budgetAllocation();
    }

    //EFFECTS: prints out budget allocation
    private void budgetAllocation() {
        if (budgetMaster.getTotalBudget() <= 0) {
            System.out.println("Please set budget limits first: ");
            setCategoryBudget(categoryChooser(budgetCategoryMenu()));
        } else {
            System.out.println("\nThe total budget is: $" + budgetMaster.getTotalBudget());

            for (BudgetCategory b : budgetMaster.getMasterList()) {
                System.out.println("\n" + b.getTitle() + ": " + "the total amount used is $" + b.getBudgetUsed()
                        + ", this comprises " + ((b.getBudgetUsed() / budgetMaster.getTotalBudget()) * 100)
                        + "% of total budget.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets maxBudget for a given category
    private void setCategoryBudget(BudgetCategory category) {
        System.out.println("Please set the total budget for " + category.getTitle());
        try {
            category.setBudgetMax(scan.nextDouble());
            scan.nextLine();
        } catch (NegativeIntegerException ne) {
            System.out.println("Must be a positive number, please try again.");
            setCategoryBudget(category);
        }
        budgetMaster.setTotalBudget();
    }

    // EFFECTS: prints warning when an expense will put them over-budget
    private void checkOverBudget(BudgetCategory category, double amount) {
        if (category.getBudgetMax() < category.getBudgetUsed() + amount) {
            System.out.println("\n\nWARNING! THIS ENTRY WILL PUT YOU OVER BUDGET\n\n");
        }
    }

    // EFFECTS: saves the budget master to file
    private void saveBudgetMaster() {
        try {
            jsonWriter.open();
            jsonWriter.write(budgetMaster);
            jsonWriter.close();
            System.out.println("Saved " + budgetMaster.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads budget master from file
    private void loadBudgetMaster() {
        try {
            budgetMaster = jsonReader.read();
            System.out.println("Loaded " + budgetMaster.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
