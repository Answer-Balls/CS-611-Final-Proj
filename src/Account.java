import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 *   Abstract Class Account
 *
 *   Variables:
 *              String ownerName:
 *
 *              String accountId:
 *
 *              Map<CurrencyType,Deposit> currenciesDeposit:
 *
 *              Map<CurrencyType,Loan>     loans:
 *
 *              List<Transaction>  transactions:  date ,currencyType, before, action type, transaction amount, after
 *
 *              double depositInterestRate:
 *
 *              double loanInterestRate:
 *
 *   Constructors:
 *              Account():
 *
 *              Account(String ownerName):
 *
 *   Methods:
 *
 *      Account:
 *              void openAccount(CurrencyType cType):  start an account with deposit in cType currency
 *
 *              void closeAccount(CurrencyType cType):
 *
 *      Deposit:
 *              abstract void makeDeposit(): maintain deposits in at least three different currencies
 *
 *              abstract void withdrawal(): withdraw money from the account
 *
 *              void transferTo(AccountType aType, CurrencyType cType,double amount): transfer money
 *
 *
 *     Loan:
 *              abstract boolean requestLoan(): request loan, return true if request get approved, otherwise false
 *
 *              abstract void takeOutLoan(): take out loans (if they have collateral)
 *
 *              abstract void payBackLoan(): pay the loans on this account
 *
 *      DisplayInfo:
 *
 *              //abstract void viewTransactions():
 *
 *              //abstract void viewCurrentBalance():
 *
 *      Helpers:
 *
 *              void writeToTransactionsLogCurrencyType cType,ActionType AType, double transAmount)
 *
 *              double calculateInterest( double interestRate, int durationDays, double baseAmountMoney):
 *
 *              int calculatePeriod( String startDate):
 *
 *              getters & setters
 *
 *
 */






public abstract class Account {

    protected String ownerName;
    protected String accountId;
    protected Map<CurrencyType,Deposit> currenciesDeposit;
    protected Map<CurrencyType,Loan>  loans;
    protected List<Transaction> transactions;
    protected double depositInterestRate=0.00001;
    protected double loanInterestRate=0.00001;

    public Account(){
        this.currenciesDeposit=new HashMap<CurrencyType,Deposit>();
        this.loans= new HashMap<CurrencyType,Loan>();
        this.transactions= new ArrayList<Transaction >();
        this.currenciesDeposit.put(CurrencyType.USD,new Deposit(CurrencyType.USD,0));
        this.currenciesDeposit.put(CurrencyType.CNY,new Deposit(CurrencyType.CNY,0));
        this.currenciesDeposit.put(CurrencyType.EUR,new Deposit(CurrencyType.EUR,0));
        this.loans.put(CurrencyType.USD,new Loan(CurrencyType.USD,0));
        this.loans.put(CurrencyType.CNY,new Loan(CurrencyType.CNY,0));
        this.loans.put(CurrencyType.EUR,new Loan(CurrencyType.EUR,0));
    }

    public Account(String ownerName){
        this.ownerName= ownerName;
        this.currenciesDeposit=new HashMap<CurrencyType,Deposit>();
        this.loans= new HashMap<CurrencyType,Loan>();
        this.transactions= new ArrayList<Transaction >();
        this.currenciesDeposit.put(CurrencyType.USD,new Deposit(CurrencyType.USD,0));
        this.currenciesDeposit.put(CurrencyType.CNY,new Deposit(CurrencyType.CNY,0));
        this.currenciesDeposit.put(CurrencyType.EUR,new Deposit(CurrencyType.EUR,0));
        this.loans.put(CurrencyType.USD,new Loan(CurrencyType.USD,0));
        this.loans.put(CurrencyType.CNY,new Loan(CurrencyType.CNY,0));
        this.loans.put(CurrencyType.EUR,new Loan(CurrencyType.EUR,0));
    }

    // Account:
    public void openAccount(CurrencyType cType){
        double fee = 5;


        //Record  fee charging action into transactions
        writeToTransactionsLog(cType,ActionType.OPENACCOUNT,-1*fee);

        //Charge a fee
        this.currenciesDeposit.get(cType).deductedBy(fee);

    }

    public void closeAccount(CurrencyType cType) {

        double fee = 5;


        //Record  fee charging action into transactions
        writeToTransactionsLog(cType,ActionType.CLOSEACCOUNT,-1*fee);

        //Charge a fee
        this.currenciesDeposit.get(cType).deductedBy(fee);


    }


    //Deposit:
    abstract void makeDeposit(CurrencyType cType, double amount);
    abstract void withdrawal(CurrencyType cType, double amount);

    public void transferTo(AccountType aType,CurrencyType cType,double amount)
    {
        double fee = 5;

        //Saving to Security special term apply
        if(this instanceof SavingAccount && aType ==AccountType.SECURITY )
        {
            SavingAccount s = (SavingAccount) this;
            if(s.willBeLowBalance(amount+fee))
                return;

        }

        //Record  fee charging action into transactions
        writeToTransactionsLog(cType,ActionType.SERVICEFEE,-1*fee);

        //Charge a fee
        this.currenciesDeposit.get(cType).deductedBy(fee);

        //Record TRANSFEROUT action into transactions
        writeToTransactionsLog(cType, ActionType.TRANSFEROUT,-1*amount);

        // Perform actual balance decrement at this.currenciesDeposit
        this.currenciesDeposit.get(cType).deductedBy(amount);

        Account receiver;

        switch (aType)
        {
            case SAVING -> {
                receiver= ((Customer)Database.getUsers().get(ownerName)).getSavingAccount();
                break;
            }

            case CHECKING -> {
                receiver= ((Customer)Database.getUsers().get(ownerName)).getCheckingAccount();
                break;
            }

            case SECURITY -> {
                receiver= ((Customer)Database.getUsers().get(ownerName)).getSecurityAccount();
                break;
            }

            default -> receiver= this;
        }

        //Record TRANSFERIN action into transactions
        receiver.writeToTransactionsLog(cType, ActionType.TRANSFERIN,amount);

        // Perform actual balance increment at this.currenciesDeposit
        receiver.currenciesDeposit.get(cType).increasedBy(amount);
    }
//    public void getTransferFrom(AccountType aType,CurrencyType cType, double transAmount)
//    {
//        //Record TRANSFERIN action into transactions
//
//        // Perform actual balance decrement at this.currenciesDeposit
//
//    }

    //Loan:
    abstract boolean requestLoan(CurrencyType cType, double amount);
    abstract void takeOutLoan(CurrencyType cType, double amount);
    abstract void payBackLoan(CurrencyType cType, double amount);

    //DisplayInfo:
//    abstract void viewTransactions();
//    abstract void viewCurrentBalance();


    //Helpers:

    public double calculateInterest( double interestRate, int durationDays, double baseAmountMoney){
        return interestRate * durationDays * baseAmountMoney;
    }

    public void writeToTransactionsLog(CurrencyType cType,ActionType AType, double transAmount)

    {   double before,after;
        // Loan
        if(AType ==ActionType.PAYBACKLOAN ||AType ==ActionType.REQUESTLOAN)
        {
            before = loans.get(cType).getAmount();
             after = before + transAmount; // +/- positive or negative
        }
        // Deposit
        else {
             before = currenciesDeposit.get(cType).getAmount();
             after = before + transAmount; // +/- positive or negative
        }

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //get account type
        String accountId_tail="";
        if (this instanceof SavingAccount)
        {accountId_tail="SavingAccount";

        }else if (this instanceof CheckingAccount)
        {
            accountId_tail="CheckingAccount";
        }else if (this instanceof SecurityAccount)
        {
            accountId_tail="SecurityAccount";
        }

        Transaction t =new Transaction( this.ownerName+"'s  " + accountId_tail,LocalDateTime.now(), cType,before, AType,transAmount, after);
        this.transactions.add(t);

        try {
            Database.WriteCustomersToLocal();
            Database.WriteTransactionLocal(LocalDateTime.now(),t);
            Database.WriteUserToLocal(this.ownerName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public int calculatePeriod( String startDate)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(startDate, formatter);
        return Period.between(date,LocalDate.now()).getDays();
    }



    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Map<CurrencyType, Deposit> getCurrenciesDeposit() {
        return currenciesDeposit;
    }

    public void setCurrenciesDeposit(Map<CurrencyType, Deposit> currenciesDeposit) {
        this.currenciesDeposit = currenciesDeposit;
    }

    public Map<CurrencyType, Loan> getLoans() {
        return loans;
    }

    public void setLoans(Map<CurrencyType, Loan> loans) {
        this.loans = loans;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getDepositInterestRate() {
        return depositInterestRate;
    }

    public void setDepositInterestRate(double depositInterestRate) {
        this.depositInterestRate = depositInterestRate;
    }

    public double getLoanInterestRate() {
        return loanInterestRate;
    }

    public void setLoanInterestRate(double loanInterestRate) {
        this.loanInterestRate = loanInterestRate;
    }
}
