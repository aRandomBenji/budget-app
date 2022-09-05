package ui;

import exceptions.NegativeIntegerException;
import exceptions.WrongDateException;
import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// A Class which runs the graphical ui for the Budget App
public class MyBudgetFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 1000;
    private static final String JSON_STORE = "./data/budgetmaster.json";

    private BudgetMaster budgetMaster;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel loadBudgetPanel;
    private JPanel titleMenuPanel;
    private JPanel entrySelectionPanel;
    private JPanel categoryPanel;
    private JPanel categoryBudgetSetter;
    private JPanel entryCategoryChooser;
    private JPanel entryNameEditor;
    private JPanel entryAmountEditor;
    private JPanel entryNotesEditor;
    private JPanel entryDateEditor;
    private JPanel viewAllBudgetCategoryInfo;
    private JPanel metrics;
    private JPanel currentBudgetTotals;
    private JPanel welcomePanel;

    private Map<String, ArrayList<JButton>> panelMap = new HashMap<>();

    private JPanel currentJPanel;
    private BudgetCategory currentCategory = new Other(0, 0);
    private Entry currentEntry;
    private double userInputDouble;
    private int userInputInt1;
    private int userInputInt2;
    private int userInputInt3;
    private String stringStorage;

    private boolean viewCategoriesInitialized = false;
    private boolean metricsInitialized = false;
    private boolean currentTotalsInitialized = false;

    // EFFECTS: sets up main frame for Budget App
    public MyBudgetFrame() {
        this.setTitle("Budget App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(125, 50, 250));

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        //this.setLayout(new GridLayout(3,3));
        //this.add(createNewJPanel(null, null));

        runBudgetApp();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates panelMap and all panels
    private void runBudgetApp() {
        createAllMapKeys();
        setUpAllStaticPanels();
        welcomePanel();
    }

    // MODIFIES: this
    // EFFECTS: creates welcomePanel and makes visible
    private void welcomePanel() {
        JLabel label = labelSetter("~  ~  ~");
        welcomePanel = createJPanel(label, "notNeeded", false);
        welcomePanel.setLayout(new FlowLayout());
        JLabel budgetImage = new JLabel(new ImageIcon("./data/theBudgetApp7.png"));
        welcomePanel.add(budgetImage);
        JLabel label2 = labelSetter("                                                                     "
                + "~  ~  ~                                                                     ");
        welcomePanel.add(label2);
        welcomePanel.add(welcomeButton());

        this.add(welcomePanel);
        welcomePanel.setVisible(true);

    }

    // MODIFIES: button
    // EFFECTS: creates a button with specified bounds for welcomePanel
    private JButton welcomeButton() {
        JButton button = new JButton();
        button.setBounds(0, 0, 400, 400);
        button.setText("Start Budget App");
        button.setFont(new Font("Ariel", Font.PLAIN, 40));
        button.setFocusable(false);
        button.addActionListener(e -> welcomeButtonHelper());

        return button;
    }

    // MODIFIES: this
    // EFFECTS: buttonAction of welcomeButton: switches screen to loadBudgetPanel
    private void welcomeButtonHelper() {
        setAllPanelInvisible();
        welcomePanel.setVisible(false);
        loadBudgetPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates all the ArrayLists that will be stored in panelMap
    @SuppressWarnings("methodlength")
    private void createAllMapKeys() {
        ArrayList<JButton> loadList = new ArrayList<>();
        ArrayList<JButton> titleList = new ArrayList<>();
        ArrayList<JButton> entryList = new ArrayList<>();
        ArrayList<JButton> categoryList = new ArrayList<>();
        ArrayList<JButton> otherList = new ArrayList<>();
        ArrayList<JButton> entryCategoryList = new ArrayList<>();

        ArrayList<JButton> educationEntryList = new ArrayList<>();
        ArrayList<JButton> entertainmentEntryList = new ArrayList<>();
        ArrayList<JButton> fooEntryList = new ArrayList<>();
        ArrayList<JButton> healthEntryList = new ArrayList<>();
        ArrayList<JButton> housingEntryList = new ArrayList<>();
        ArrayList<JButton> investmentsEntryList = new ArrayList<>();
        ArrayList<JButton> loansEntryList = new ArrayList<>();
        ArrayList<JButton> otherEntryList = new ArrayList<>();
        ArrayList<JButton> personalEntryList = new ArrayList<>();
        ArrayList<JButton> transportationEntryList = new ArrayList<>();
        ArrayList<JButton> utilitiesEntryList = new ArrayList<>();
        ArrayList<JButton> vacationEntryList = new ArrayList<>();

        panelMap.put("Budget Load Menu", loadList);
        panelMap.put("Main Menu", titleList);
        panelMap.put("Entry Menu", entryList);
        panelMap.put("Set Budget for Category Menu", categoryList);
        panelMap.put("Entry Category Menu", entryCategoryList);
        panelMap.put("Other Menu", otherList);

        panelMap.put("Education", educationEntryList);
        panelMap.put("Entertainment", entertainmentEntryList);
        panelMap.put("Food & HouseholdItems", fooEntryList);
        panelMap.put("Health", healthEntryList);
        panelMap.put("Housing", housingEntryList);
        panelMap.put("Investments & Savings", investmentsEntryList);
        panelMap.put("Loans & Debt", loansEntryList);
        panelMap.put("Other & Miscellaneous", otherEntryList);
        panelMap.put("Personal", personalEntryList);
        panelMap.put("Transportation", transportationEntryList);
        panelMap.put("Utilities", utilitiesEntryList);
        panelMap.put("Vacation", vacationEntryList);

    }

    // MODIFIES: label
    // EFFECTS: creates a generic JLabel with a single line of center-aligned text
    private JLabel labelSetter(String inputText) {
        JLabel label = new JLabel();
        label.setText(inputText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Ariel", Font.PLAIN, 20));
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 200, 200);
        return label;
    }

    // MODIFIES: button, this
    // EFFECTS: creates menu buttons and assigns to appropriate key in panelMap
    private void menuButtonSetter(String menuSelect, String buttonChoice) {
        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        button.setText(buttonChoice);
        button.addActionListener(e -> masterMenuList(menuSelect, buttonChoice));

        panelMap.get(menuSelect).add(button);
    }

    // MODIFIES: button
    // EFFECTS: creates buttons for edit/remove entry
    private JButton entryButtonSetter(Entry e, Boolean takeOutEntry) {
        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        String str1 = e.getLabel();
        double str2 = e.getAmount();
        String str3 = e.getDate();
        button.setText("Name: " + str1 + " $" + str2 + " Date: " + str3);
        button.addActionListener(z -> entryButtonSetterHelper(e, takeOutEntry));
        return button;
    }

    // MODIFIES: this
    // EFFECTS: changes screen based on whether entry is removed/edited
    private void entryButtonSetterHelper(Entry e, Boolean takeOutEntry) {
        currentCategory.removeEntry(e);
        if (takeOutEntry) {
            setAllPanelInvisible();
            entrySelectionPanel.setVisible(true);
        } else {
            setAllPanelInvisible();
            entryNameEditor.setVisible(true);
        }
    }

    // MODIFIES: button
    // EFFECTS: creates button which returns user from current panel to titlePanel
    private JButton returnToMainMenuButton() {
        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        button.setText("Return to Main Menu");
        button.addActionListener(e -> returnToMain());
        return button;
    }

    // MODIFIES: this
    // EFFECTS: buttonAction which returns user to titlePanel
    private void returnToMain() {
        setAllPanelInvisible();
        currentJPanel.setVisible(false);
        titleMenuPanel.setVisible(true);
    }

    // MODIFIES: panel
    // EFFECTS: creates one of two JPanels: menu (with menu buttons) or empty panel
    private JPanel createJPanel(JLabel jlabel, String menuSelect, boolean isMenu) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(160, 100, 200));
        panel.setBounds(200, 0, 800, 1000);
        panel.setLayout(new GridLayout(18, 1, 0, 0));

        panel.add(jlabel);

        if (isMenu) {
            //createHashButtonList(menuSelect);
            menuButtonCreator(menuSelect);
            ArrayList<JButton> buttonList = panelMap.get(menuSelect);

            for (int i = 0; i < buttonList.size(); i++) {
                panel.add(buttonList.get(i));
            }
        }
        panel.setVisible(false);
        currentJPanel = panel;
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates panel to set budget for individual category
    private void createAddBudgetJPanel() {
        JTextField textInput = new JTextField();
        textInput.setFont(new Font("Ariel", Font.PLAIN, 20));
        categoryBudgetSetter.add(textInput);

        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        button.setText("Save Budget Amount for category");
        button.addActionListener(e -> setBudgetMaxHelper(textInput));

        categoryBudgetSetter.add(button);
    }

    // MODIFIES: panel
    // EFFECTS: creates all 4 panels used in creating an Entry
    @SuppressWarnings("methodlength")
    private void addEntryItemJPanel(String panelLabel, JPanel panel) {
        JTextField textInput = new JTextField();
        JLabel label1 = labelSetter(panelLabel);
        JLabel label2 = labelSetter("Set Day:");
        JTextField textInput1 = new JTextField();
        JLabel label3 = labelSetter("Set Month:");
        JTextField textInput2 = new JTextField();
        JLabel label4 = labelSetter("Set Year:");

        textInput.setFont(new Font("Ariel", Font.PLAIN, 20));
        textInput1.setFont(new Font("Ariel", Font.PLAIN, 20));
        textInput2.setFont(new Font("Ariel", Font.PLAIN, 20));

        panel.add(label1);

        if (panelLabel == "Set Date:") {
            panel.add(label2);
            panel.add(textInput1);
            panel.add(label3);
            panel.add(textInput2);
            panel.add(label4);
        }

        panel.add(textInput);

        if (panelLabel.equals("Set Name:")) {
            panel.add(entryNameButton(textInput));
        } else if (panelLabel.equals("Set Amount:")) {
            panel.add(entryAmountButton(textInput));
        } else if (panelLabel.equals("Set Notes:")) {
            panel.add(entryNotesButton(textInput));
        } else if (panelLabel.equals("Set Date:")) {
            panel.add(entryDateButton(textInput1, textInput2, textInput));
        }
    }

    // MODIFIES: button
    // EFFECTS: creates name button
    private JButton entryNameButton(JTextField textInput) {
        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        button.setText("Save Name");
        button.addActionListener(e -> setEntryNameHelper(textInput));
        return button;
    }

    // MODIFIES: button
    // EFFECTS: creates amount button
    private JButton entryAmountButton(JTextField textInput) {
        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        button.setText("Save Amount");
        button.addActionListener(e -> setEntryAmountHelper(textInput));
        return button;
    }

    // MODIFIES: button
    // EFFECTS: creates notes button
    private JButton entryNotesButton(JTextField textInput) {
        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        button.setText("Save Notes");
        button.addActionListener(e -> setEntryNotesHelper(textInput));
        return button;
    }

    // MODIFIES: button
    // EFFECTS: creates date button
    private JButton entryDateButton(JTextField textInput1, JTextField textInput2, JTextField textInput3) {
        JButton button = new JButton();
        button.setBounds(0, 0, 40, 40);
        button.setText("Save Date");
        button.addActionListener(e -> setEntryDateHelper(textInput1, textInput2, textInput3));
        return button;
    }

    // MODIFIES: this, currentEntry
    // EFFECTS: creates new entry, assigns name as userInput, and proceeds to next screen
    private void setEntryNameHelper(JTextField textInput) {
        currentEntry = new Entry("", 0, "", "", 0);
        budgetMaster.getBudgetCategory(currentCategory.getTitle()).addEntryToList(currentEntry);
        currentEntry.setLabel(textInput.getText());
        setAllPanelInvisible();
        entryAmountEditor.setVisible(true);

    }

    // MODIFIES: this, currentEntry
    // EFFECTS: assigns userInput as notes in entry, and proceeds to next screen
    private void setEntryNotesHelper(JTextField textInput) {
        currentEntry.setNote(textInput.getText());
        setAllPanelInvisible();
        entrySelectionPanel.setVisible(true);
    }

    // MODIFIES: this, currentEntry
    // EFFECTS: assigns userInput as amount in entry, and proceeds to next screen
    private void setEntryAmountHelper(JTextField textInput) {
        try {
            userInputDouble = Double.parseDouble(textInput.getText());
            currentEntry.setAmount(userInputDouble);
        } catch (NegativeIntegerException e) {
            System.out.println("Can't be negative!");
        }
        setAllPanelInvisible();
        entryDateEditor.setVisible(true);

    }

    // MODIFIES: this, currentEntry
    // EFFECTS: assigns userInput as date in entry, and proceeds to next screen
    private void setEntryDateHelper(JTextField textInput1, JTextField textInput2, JTextField textInput3) {
        try {
            userInputInt1 = Integer.parseInt(textInput1.getText());
            userInputInt2 = Integer.parseInt(textInput2.getText());
            userInputInt3 = Integer.parseInt(textInput3.getText());

            currentEntry.setDate(userInputInt1, userInputInt2, userInputInt3);
        } catch (WrongDateException e) {
            System.out.println("Not a date!");
        }
        setAllPanelInvisible();
        entryNotesEditor.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: assigns textInput as the new maxBudget for currentCategory
    private void setBudgetMaxHelper(JTextField textInput) {
        try {
            userInputDouble = Double.parseDouble(textInput.getText());
            currentCategory.setBudgetMax(userInputDouble);
        } catch (NegativeIntegerException e) {
            System.out.println("Can't be a negative number!");
        }
        budgetMaster.setTotalBudget();
        System.out.println(budgetMaster.getTotalBudget());
        setAllPanelInvisible();
        titleMenuPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: makes all panels invisible
    private void setAllPanelInvisible() {
        loadBudgetPanel.setVisible(false);
        titleMenuPanel.setVisible(false);
        entrySelectionPanel.setVisible(false);
        categoryPanel.setVisible(false);
        categoryBudgetSetter.setVisible(false);
        entryCategoryChooser.setVisible(false);
        currentJPanel.setVisible(false);
        entryAmountEditor.setVisible(false);
        entryNameEditor.setVisible(false);
        entryNotesEditor.setVisible(false);
        entryDateEditor.setVisible(false);
        welcomePanel.setVisible(false);

        if (viewCategoriesInitialized) {
            viewAllBudgetCategoryInfo.setVisible(false);
        }
        if (metricsInitialized) {
            metrics.setVisible(false);
        }
        if (currentTotalsInitialized) {
            currentBudgetTotals.setVisible(false);
        }
    }

    // MODIFIES: this
    // EFFECTS: switches to categoryBudgetSetter panel
    private void setCategoryBudget() {
        setAllPanelInvisible();
        categoryBudgetSetter.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates all the static panels for the budget App and adds to this
    private void setUpAllStaticPanels() {
        JLabel label = labelSetter("Welcome to Budget App!\n Create or load existing Budget?");
        loadBudgetPanel = createJPanel(label, "Budget Load Menu", true);
        titleMenuPanel = createJPanel(labelSetter("Main Menu"), "Main Menu", true);
        entrySelectionPanel = createJPanel(labelSetter("Entry Menu"), "Entry Menu", true);
        categoryPanel = createJPanel(labelSetter("Category Menu"), "Set Budget for Category Menu", true);
        JLabel label2 = labelSetter("Please input the max budget for this category below: ");
        entryCategoryChooser = createJPanel(labelSetter("Choose a category: "), "Entry Category Menu", true);
        categoryBudgetSetter = createJPanel(label2, "notNeeded", false);
        createAddBudgetJPanel();
        entryNameEditor = createJPanel(labelSetter("Entry Editor:"), "notNeeded", false);
        addEntryItemJPanel("Set Name:", entryNameEditor);
        entryAmountEditor = createJPanel(labelSetter("Entry Editor:"), "notNeeded", false);
        addEntryItemJPanel("Set Amount:", entryAmountEditor);
        entryNotesEditor = createJPanel(labelSetter("Entry Editor:"), "notNeeded", false);
        addEntryItemJPanel("Set Notes:", entryNotesEditor);
        entryDateEditor = createJPanel(labelSetter("Entry Editor:"), "notNeeded", false);
        addEntryItemJPanel("Set Date:", entryDateEditor);

        addPanelsToThis();
    }

    // MODIFIES: this
    // EFFECTS: creates viewCategory panel
    private void setupViewCategoryPanel() {
        JLabel label = labelSetter("All Budget Categories:");
        viewAllBudgetCategoryInfo = createJPanel(label, "notNeeded", false);
        for (int i = 0; i < budgetMaster.sizeOfMasterList(); i++) {
            viewAllBudgetCategoryInfo.add(labelSetter(budgetMaster.getMasterList().get(i).getTitle()
                    + ": " + budgetMaster.getMasterList().get(i).getNotes()));
        }
        viewAllBudgetCategoryInfo.add(returnToMainMenuButton());
        this.add(viewAllBudgetCategoryInfo);
        viewCategoriesInitialized = true;
    }

    // MODIFIES: this
    // EFFECTS: creates panel which displays totals for current budget
    private void setupCurrentBudgetTotals() {
        JLabel label = labelSetter("Current Budget Totals: ");
        currentBudgetTotals = createJPanel(label, "notNeeded", false);

        for (int i = 0; i < budgetMaster.sizeOfMasterList(); i++) {
            label = labelSetter(budgetMaster.getMasterList().get(i).getTitle() + ": "
                    + " Budget: $" + budgetMaster.getMasterList().get(i).getBudgetMax() + " Expenses: $"
                    + budgetMaster.getMasterList().get(i).getBudgetUsed() + " Remaining: $"
                    + budgetMaster.getMasterList().get(i).getBudgetRemaining());
            label.setFont(new Font("Ariel", Font.PLAIN, 15));
            label.setBounds(0,0,10,10);
            currentBudgetTotals.add(label);
        }

        currentBudgetTotals.add(labelSetter("Total Budget: $" + budgetMaster.getTotalBudget()));
        currentBudgetTotals.add(labelSetter("Total Expenses: $" + budgetMaster.getTotalExpenses()));
        currentBudgetTotals.add(labelSetter("Total Remaining: $" + budgetMaster.getTotalBudgetRemaining()));
        currentBudgetTotals.add(returnToMainMenuButton());

        this.add(currentBudgetTotals);
        currentTotalsInitialized = true;
    }

    // MODIFIES: this
    // EFFECTS: creates metrics panel
    // NOTE: Work in Progress
    private void metricsPanel() {
        JLabel label = labelSetter("Metrics for this project");
        metrics = createJPanel(label, "notNeeded", false);

        for (int i = 0; i < budgetMaster.sizeOfMasterList(); i++) {
            metrics.add(labelSetter(budgetMaster.getMasterList().get(i).getTitle() + ": "
                    + metricsHelper(budgetMaster.getMasterList().get(i)) + "%"));
        }
        metrics.add(returnToMainMenuButton());

        this.add(metrics);
        metricsInitialized = true;
    }

    // MODIFIES: budgetEqualsOne
    // EFFECTS: calculates percentage value of each budget category, ensures no division by "0"
    private int metricsHelper(BudgetCategory category) {
        int percent;
        double numerator = category.getBudgetMax();
        double denominator = budgetMaster.getTotalBudget();

        if (denominator == 0) {
            denominator = 1;
        }

        percent = (int) ((numerator / denominator) * 100);

        return percent;
    }

    // MODIFIES: this
    // EFFECTS: adds all panels to this
    private void addPanelsToThis() {
        this.add(loadBudgetPanel);
        this.add(titleMenuPanel);
        this.add(entrySelectionPanel);
        entrySelectionPanel.add(returnToMainMenuButton());
        this.add(categoryPanel);
        categoryPanel.add(returnToMainMenuButton());
        this.add(categoryBudgetSetter);
        categoryBudgetSetter.add(returnToMainMenuButton());
        this.add(entryCategoryChooser);
        entryCategoryChooser.add(returnToMainMenuButton());
        this.add(currentJPanel);
        this.add(entryAmountEditor);
        this.add(entryDateEditor);
        this.add(entryNameEditor);
        this.add(entryNotesEditor);
    }

    // MODIFIES: panelMap
    // EFFECTS: sorts menu Buttons to correct key
    private void menuButtonCreator(String menuChoice) {
        switch (menuChoice) {
            case "Budget Load Menu":
                createLoadMenuButtons(menuChoice);
                break;
            case "Main Menu":
                createTitleMenuButtons(menuChoice);
                break;
            case "Entry Menu":
                createEntryMenuButtons(menuChoice);
                break;
            case "Set Budget for Category Menu":
                createCategoryBudgetMenuButtons(menuChoice);
                break;
            case "Entry Category Menu":
                createCategoryBudgetMenuButtons(menuChoice);
        }
    }

    // MODIFIES: panelMap
    // EFFECTS: assigns buttons to key for loadMenu
    private void createLoadMenuButtons(String menuChoice) {
        menuButtonSetter(menuChoice, "Create New Budget");
        menuButtonSetter(menuChoice, "Load Old Budget");
    }

    // MODIFIES: panelMap
    // EFFECTS: assigns buttons to key for titleMenu
    private void createTitleMenuButtons(String menuChoice) {
        menuButtonSetter(menuChoice, "Entry Manager");
        menuButtonSetter(menuChoice, "View Budget Categories");
        menuButtonSetter(menuChoice, "Metrics and Statistics");
        menuButtonSetter(menuChoice, "Set Category Budget");
        menuButtonSetter(menuChoice, "Budget Current Totals");
        menuButtonSetter(menuChoice, "Save this Budget");
        menuButtonSetter(menuChoice, "Quit Budget App");
    }

    // MODIFIES: panelMap
    // EFFECTS: assigns buttons to key for entryMenu
    private void createEntryMenuButtons(String menuChoice) {
        menuButtonSetter(menuChoice, "Create Entry");
        menuButtonSetter(menuChoice, "Edit Entry");
        menuButtonSetter(menuChoice, "Remove Entry");
    }

    // MODIFIES: panelMap
    // EFFECTS: assigns buttons to key for categoryMenu
    private void createCategoryBudgetMenuButtons(String menuChoice) {
        menuButtonSetter(menuChoice, "Education");
        menuButtonSetter(menuChoice, "Entertainment");
        menuButtonSetter(menuChoice, "Food & Household Items");
        menuButtonSetter(menuChoice, "Health");
        menuButtonSetter(menuChoice, "Housing");
        menuButtonSetter(menuChoice, "Investments & Savings");
        menuButtonSetter(menuChoice, "Loans & Debt");
        menuButtonSetter(menuChoice, "Personal");
        menuButtonSetter(menuChoice, "Transportation");
        menuButtonSetter(menuChoice, "Utilities");
        menuButtonSetter(menuChoice, "Vacation");
        menuButtonSetter(menuChoice, "Other & Miscellaneous");
    }

    // MODIFIES: panelMap
    // EFFECTS: assigns buttons to key for masterList
    private void masterMenuList(String menuChoice, String buttonChoice) {
        switch (menuChoice) {
            case "Budget Load Menu":
                newOrLoadBudget(buttonChoice);
                break;
            case "Main Menu":
                titleSelection(buttonChoice);
                break;
            case "Entry Menu":
                entrySelectionPrep(buttonChoice);
                break;
            case "Set Budget for Category Menu":
                categoryChooser(buttonChoice);
                setCategoryBudget();
                break;
            case "Entry Category Menu":
                categoryChooser(buttonChoice);
                entrySelection();
        }
    }

    // MODIFIES: this
    // EFFECTS: saves buttonChoice and opens next screen
    private void entrySelectionPrep(String buttonChoice) {
        setAllPanelInvisible();
        entryCategoryChooser.setVisible(true);
        stringStorage = buttonChoice;
    }

    // MODIFIES: this
    // EFFECTS: makes entry selection based on stringStorage
    private void entrySelection() {

        switch (stringStorage) {
            case "Create Entry":
                setAllPanelInvisible();
                entryNameEditor.setVisible(true);
                break;
            case "Edit Entry":
                chooseEntryPanel(false);
                break;
            case "Remove Entry":
                chooseEntryPanel(true);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes user chosen entry from entryList
    private void chooseEntryPanel(boolean takeOutEntry) {
        String a;

        if (takeOutEntry) {
            a = "remove";
        } else {
            a = "edit";
        }
        currentJPanel = createJPanel(labelSetter("Please select an entry to " + a),
                currentCategory.getTitle(), false);
        currentJPanel.add(returnToMainMenuButton());
        for (Entry e : currentCategory.getEntryList()) {
            currentJPanel.add(entryButtonSetter(e, takeOutEntry));
        }
        setAllPanelInvisible();
        this.add(currentJPanel);
        currentJPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: makes decision in title menu based off of the button chosen by user
    @SuppressWarnings("methodlength")
    public void titleSelection(String buttonChoice) {

        switch (buttonChoice) {
            case "Entry Manager":
                titleMenuPanel.setVisible(false);
                entrySelectionPanel.setVisible(true);
                break;
            case "View Budget Categories":
                setupViewCategoryPanel();
                setAllPanelInvisible();
                viewAllBudgetCategoryInfo.setVisible(true);
                break;
            case "Metrics and Statistics":
                metricsPanel();
                setAllPanelInvisible();
                metrics.setVisible(true);
                break;
            case "Set Category Budget":
                titleMenuPanel.setVisible(false);
                categoryPanel.setVisible(true);
                break;
            case "Budget Current Totals":
                setupCurrentBudgetTotals();
                setAllPanelInvisible();
                currentBudgetTotals.setVisible(true);
                break;
            case "Save this Budget":
                saveBudgetMaster();
                break;
            case "Quit Budget App":
                printBudgetMasterEventLog(EventLog.getInstance());
                System.exit(0);
                break;
        }

    }

    // MODIFIES: this
    // EFFECTS: uses userInput to select category
    @SuppressWarnings("methodlength")
    private void categoryChooser(String userInput) {
        BudgetCategory category;

        switch (userInput) {
            case "Education":
                category = budgetMaster.getBudgetCategory("Education");
                break;
            case "Entertainment":
                category = budgetMaster.getBudgetCategory("Entertainment");
                break;
            case "Food & Household Items":
                category = budgetMaster.getBudgetCategory("Food & Household Items");
                break;
            case "Health":
                category = budgetMaster.getBudgetCategory("Health");
                break;
            case "Housing":
                category = budgetMaster.getBudgetCategory("Housing");
                break;
            case "Investments & Savings":
                category = budgetMaster.getBudgetCategory("Investments & Savings");
                break;
            case "Loans & Debt":
                category = budgetMaster.getBudgetCategory("Loans & Debt");
                break;
            case "Personal":
                category = budgetMaster.getBudgetCategory("Personal");
                break;
            case "Transportation":
                category = budgetMaster.getBudgetCategory("Transportation");
                break;
            case "Utilities":
                category = budgetMaster.getBudgetCategory("Utilities");
                break;
            case "Vacation":
                category = budgetMaster.getBudgetCategory("Vacation");
                break;
            case "Other & Miscellaneous":
                category = budgetMaster.getBudgetCategory("Other & Miscellaneous");
                break;
            default:
                category = null;
        }
        currentCategory = category;
    }

    // MODIFIES: this
    // EFFECTS: creates new budgetMaster or loads old budgetMaster (based on user choice)
    private void newOrLoadBudget(String buttonChoice) {

        if (buttonChoice.equals("Create New Budget")) {
            budgetMaster = new BudgetMaster("test", 0, 0);
        } else if (buttonChoice.equals("Load Old Budget")) {
            try {
                budgetMaster = jsonReader.read();
                System.out.println("Loaded " + budgetMaster.getName() + " from " + JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
        loadBudgetPanel.setVisible(false);
        titleMenuPanel.setVisible(true);
    }

    // EFFECTS: saves the budgetMaster to file
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

    // EFFECTS: prints out all user events
    public void printBudgetMasterEventLog(EventLog log) {
        for (Event e : log) {
            System.out.println(e.toString());
        }
    }
}
