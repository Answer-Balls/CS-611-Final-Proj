# CS-611-Final-Proj
The Final project "ATM" for CS 611 in BU

# 2021-fall CS611 Final-Project: Online ATM #
This projcet is completed through the joint effort from Zimou Sun, Wanzhi Wang and Ziyang Sheng. 
I really appreciate their dedication on this project!

Files
-------------------------------------------------------------------------------------------------
<Account.java> -- The abstract class for all types of accounts.
<AccountType.java> -- The enum class for three account types.
<AccountUI.java> -- The user interface for customers to create a new account.
<ATM.java> -- The manager class for both customer and manager login.
<BorrowLoanUI.java> -- The user interface for customers to apply for a loan.
<CheckingAccount.java> -- The class for checking account.
<Currency.java> -- The class in charge of all kinds of currency.
<CurrencyType.java> -- The enum class for three currency types.
<Customer.java> -- The class in charge of customers.
<CustomerDao.java> -- The Interface for customer DAO.
<CustomerDaoImpl.java> -- The class in charge of the operations on file "Customer.csv".
<CustomerUI.java> -- The user interface for customers to choose an action.
<DailyReportUI.java> -- The user interface for the manager to view daily transaction record.
<Database.java> -- The database that stores all the user data in this system.
<Deposit.java> -- The class for customer's savings.
<FileUtils.java> -- The class for utility functions on csv files.
<InformationUI.java> -- The user interface for customers to view their account information.
<Loan.java> -- The class in charge the loan business.
<LoanUI.java> -- The user interface for loan business.
<LoginUI.java> -- The user interface for customer/manager login.
<Main.java> -- The Main class of the whole system.
<Manager.java> -- The class in charge of managers.
<ManagerDao.java> -- The Interface for manager DAO.
<ManagerDaoImpl.java> -- The class in charge of the operations on file "Manager.csv".
<ManagerUI.java> -- The user interface for managers to choose an action.
<ReturnLoanUI.java> -- The user interface for customers to return the loan.
<SaveUI.java> -- The user interface for customers to make a deposit.
<SavingAccount.java> -- The class for saving account.
<SecurityAccount.java> -- The class for security account.
<SignUpUI.java> -- The user interface for users to sign up.
<Stock.java> -- The class in charge of the stock business.
<StockUI.java> -- The user interface for the stock business.
<Transaction.java> -- The class in charge of all kinds of transactions.
<TransactionUI.java> -- The user interface for customers to make transactions.
<User.java> -- The abstract class for all kinds of users.
<ViewClientUI.java> -- The user interface for managers to view customer information.
<WelcomeUI.java> -- The entrance of the whole system and the welcome interface.
<WithDrawUI.java> -- The user interface for customers to withdraw money.



Notes & Highlights:
-------------------------------------------------------------------------------------------------
1. The usage of DAO and singleton design pattern to maintain data and connect front end with back end.
2. The ability to handle various of illegal inputs and exceptions.
3. The concise and convenient design of database.
4. The fancy implementation of stock part, which we acquire real data from API online.


Compile Instruction:
-------------------------------------------------------------------------------------------------
Go to the directory where all codes and csv files .etc are stored.
Use the following commands in the terminal to execute the program.

javac -cp ATM_final_version.jar *.java
java -cp ATM_final_version.jar  Main



















