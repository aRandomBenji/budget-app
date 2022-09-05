# Simple Budget App


Hello, and welcome to The Budget App (TBA)! TBA is an 
easy-to-use budget app that covers all your monthly expenditures 
and expenses. It has been designed both for beginners and experts 
who are experienced in creating their own budgets. 

I've tried out a number of different budget apps over the years, 
but I've never found one that I really like. Some of them have 
weird functionality, or have unneeded complexity. Others are 
littered with advertisements, or try to charge an expensive fee
for their services. 

In creating the TBA, I wanted to create a simple, easy-to-use 
App which would allow me to perform all the functions I wanted
it to without all the advertisements or fees. I fully intend to 
use this to record my own finances, and would like to continue 
to upgrade it with new features (like pie-charts, graphs, etc.).

**User Stories:** 
- (1) As a user, I want to be able to create a new budget item and assign it to a category
- (2) As a user, I want to be able to modify and remove entries
- (3) As a user, I want to be able to see a list of all the budget categories
- (4) As a user, I want to be able to see the total amount spent as well as the total budget
- (5) As a user, I want to be able to see the percentage allocation of all of my budget items
broken down by category [NOTE: although I am filling the requirements here, at some point I'd love to make a 
pie-chart and make this look pretty]


- (6) As a user, I want to be able to save my budget to file
- (7) As a user, I want to be able to be able to load my budget from file 


- (8) "Add multiple Xs to a Y" requirement
- (9) "Save / Load" requirement
- (10) "Graphical" requirement

# Instructions for Grader:
NOTE: the number beside each instruction represents the User Story it covers
- (10) On running the app, you will see a banner (picture) that welcomes the user to The Budget App
- (click "start Budget App")
- (7, 9) You are prompted to either load an old budget app or create a new one
- (click "Load Old Budget" ... "Create New Budget" also works, but this demo works better if there
are already some entries in the system)
- (click "Entry Manager")
- (click "Create a New Entry")
- (1, 8) follow through the process of creating a new entry - once you have finished, the entry will be added to a list
of entries assigned to the category you choose. You can do this process as many times as you would like
- (click "Modify an Entry" or "Remove an Entry")
- (2) depending on your decision, you will either remove your previous entry or modify it
- (If not already at main menu... click "Return to Main Menu")
- (click "View Budget Categories")
- (3) this displays a list of all the Budget Categories as well as some suggestions of what to put in them
- (click "Return to Main Menu")
- (click "Budget Current Totals")
- (4) this displays the budgetMax, expenses, and budget remaining for each category, as well as their summed totals
- (click "Return to Main Menu")
- (click "Metrics")
- (5) eventually, this should display a pie chart, but currently it displays the percentage allocation of each budget 
of the total budget
- (click "Return to Main Menu")
- (click "Save this Budget")
- (6, 9) this will save all the changes you made to the current budget - when you load up the program next time
you will see these changes reflected in the Budget App
- (click "Return to Main Menu")
- (click "Quit Budget App")
- All done!



"PHASE 4: TASK 2" 

Tue Aug 09 17:19:10 PDT 2022
Entry name set to: Power Puff Girls IRL Concert
Tue Aug 09 17:19:15 PDT 2022
Entry amount set to: 299.0
Tue Aug 09 17:19:31 PDT 2022
Entry date set to: 31/12/2021
Tue Aug 09 17:19:40 PDT 2022
Entry notes set to: I hope my mom doesn't see this...




"PHASE 4: TASK 3"

I think that, overall, I arranged everything in my project very well. The cohesion, in particular, is very strong: each
of my classes have one clear, distinct purpose, and fulfil that well.
The only changes I might make are a few small
refactoring choices in the MyBudgetFrame app. Especially near the end of the project, there were 3 or 4 button methods
which could have partially been refactored into one (instead of four equally sized methods with duplicate code, 
I could have made one larger method and 3 small helper ones). It started to get really complex, and so I opted to save 
myself the stress and keep it as it was, but I think I could figure out a better re-factorization. 