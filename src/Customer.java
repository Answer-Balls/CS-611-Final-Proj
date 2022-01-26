import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 *   Predefined Class User :
 *
 *   Variables:
 *
 *              String userId:
 *
 *              String password:
 *
 *
 *
 *   Constructors:
 *
 *              User(String userName, String password):
 *
 *   Methods:
 *              getters & setters
 *
 *---------------------------------------------------------------------------------------------------------------
 *   Class Customer :
 *
 *   Variables:
 *
 *              float balance:
 *
 *              //List<Account>:
 *
 *              SavingAccount savingAccount:
 *
 *              CheckingAccount checkingAccount:
 *
 *              SecurityAccount securityAccount:
 *
 *   Constructors:
 *
 *              Customer(String customerId, String password)
 *
 *   Methods:
 *
 *              createAccount(AccountType accountType, CurrencyType cType, double amount):
 *
 *              closeAccount(AccountType accountType, CurrencyType cType):
 *
 */

public class Customer extends User{

    //private float balance;
    //private List<Account> accountList;
    private double profit;
    private ArrayList<Stock> stocks;
    private SavingAccount savingAccount;
    private CheckingAccount checkingAccount;
    private SecurityAccount securityAccount;

    public double getProfit() { return this.profit; }

    public SavingAccount getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(SavingAccount savingAccount) {
        this.savingAccount = savingAccount;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public SecurityAccount getSecurityAccount() {
        return securityAccount;
    }

    public void setSecurityAccount(SecurityAccount securityAccount) {
        this.securityAccount = securityAccount;
    }

    public ArrayList<Stock> getStocks() {
        return stocks;
    }


    public Customer(String customerId, String password){
        super(customerId, password);
        stocks = new ArrayList<>();
        profit = 0;
    }


    public void  createAccount(AccountType accountType, CurrencyType cType, double amount) throws IOException, URISyntaxException {

        switch(accountType)// set this.xxxAccount
        {
            case SAVING -> {
                this.savingAccount = new SavingAccount(cType, amount,this.getUserName());

                break;
            }

            case CHECKING -> {
                this.checkingAccount = new CheckingAccount(cType, amount,this.getUserName());
                break;
            }

            case SECURITY -> {
                this.securityAccount = new SecurityAccount(cType, amount,this.getUserName());
                break;
            }
        }

        // update to database
        if(Database.getUsers().get(this.getUserName())==null) {
            Database.addUser(this);
        }

        Database.WriteUserToLocal(this.getUserName());
        Database.WriteCustomersToLocal();

    }

    public void  closeAccount(AccountType accountType, CurrencyType cType) throws IOException, URISyntaxException
    {
        switch(accountType)// set this.xxxAccount
        {
            case SAVING -> {
                this.savingAccount.closeAccount(cType);
                this.savingAccount=null;
                break;
            }

            case CHECKING -> {
                this.checkingAccount.closeAccount(cType);
                this.checkingAccount=null;
                break;
            }

            case SECURITY -> {
                this.securityAccount.closeAccount(cType);
                this.securityAccount=null;
                break;
            }
        }

        // update to database
        if(Database.getUsers().get(this.getUserName())==null) {
            Database.addUser(this);
        }

        Database.WriteUserToLocal(this.getUserName());
        Database.WriteCustomersToLocal();
    }

    public boolean add_stock(Stock s, int num) throws IOException, URISyntaxException {
        System.out.println();
        double amount = this.securityAccount.getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
        if (s.getPrice()*num > amount){
            return false;
        }
        for (Stock o : stocks){
            if (o.compareTo(s) == 0){
                double totalValue = o.getAvgPrice()*o.getHolding();
                o.add_holding(num);
                o.changeAvgPrice((totalValue + num*s.getPrice())/o.getHolding());

                //Record BuyStock action into transactions
                double nb = s.getPrice()*num;
                nb = (Math.round(nb*100)/100.0);
                this.securityAccount.writeToTransactionsLog(CurrencyType.USD,ActionType.BUYSTOCK,-1*nb);
                this.securityAccount.getCurrenciesDeposit().get(CurrencyType.USD).setAmount(amount - nb);
                Database.WriteUserToLocal(this.getUserName());
                Database.WriteCustomersToLocal();
                return true;
            }
        }
        s.add_holding(num);
        s.changeAvgPrice(s.getPrice());

        //Record BuyStock action into transactions

        double nb = s.getPrice()*num;
        nb = (Math.round(nb*100)/100.0);
        this.securityAccount.writeToTransactionsLog(CurrencyType.USD,ActionType.BUYSTOCK,-1*nb);
        this.securityAccount.getCurrenciesDeposit().get(CurrencyType.USD).setAmount(amount - nb);
        stocks.add(s);
        Database.WriteUserToLocal(this.getUserName());
        Database.WriteCustomersToLocal();
        return true;
    }

    public boolean remove_stock(Stock s, int num) throws IOException, URISyntaxException {
        double amount = this.securityAccount.getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
        for (Stock o : stocks){
            if (o.compareTo(s) == 0){
                if (num > o.getHolding()){
                    return false;
                }
                else{
                    double totalValue = o.getAvgPrice()*o.getHolding();
                    o.add_holding(-num);
                    o.changeAvgPrice((totalValue - num*s.getPrice())/o.getHolding());
                    this.profit += (o.getPrice()-o.getAvgPrice())*num;

                    //Record BuyStock action into transactions
                    double nb = s.getPrice()*num;
                    nb = (Math.round(nb*100)/100.0);
                    this.securityAccount.writeToTransactionsLog(CurrencyType.USD,ActionType.SELLSTOCK,nb);
                    this.securityAccount.getCurrenciesDeposit().get(CurrencyType.USD).setAmount(amount + nb);
                    Database.WriteUserToLocal(this.getUserName());
                    Database.WriteCustomersToLocal();
                    return true;
                }
            }
        }
        return false;
    }



}
