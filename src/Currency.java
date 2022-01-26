import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 *   Class Currency :
 *
 *   Variables:
 *
 *
 *              CurrencyType typeOfCurrency:
 *
 *              double amount:
 *
 *              String startDate: LocalDateTime formatted
 *
 *   Constructors:
 *
 *                  Currency():
 *                                  empty constructor
 *
 *   Methods:
 *
 *              void deductedBy(double d):
 *
 *              void increasedBy(double d):
 *
 *              getters & setters
 *
 *
 */


public class Currency {

    protected CurrencyType typeOfCurrency;

    protected double amount;

    protected String startDate;



    public Currency(){

    }

    public void deductedBy(double d)
    {
        this.amount-=d;
    }

    public void increasedBy(double d)
    {
        this.amount+=d;
    }

    public CurrencyType getTypeOfCurrency() {
        return typeOfCurrency;
    }

    public void setTypeOfCurrency(CurrencyType typeOfCurrency) {
        this.typeOfCurrency = typeOfCurrency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDateOfLoan) {
        this.startDate = startDateOfLoan;
    }


//    public static void main( String args[]) throws IOException, URISyntaxException {
//        Database. ReadTransactionFromLocal();
//        Transaction temp = new Transaction("hello",LocalDateTime.now(),CurrencyType.USD,300,ActionType.TRANSFEROUT,-100,200);
//
//        Database. WriteTransactionToLocal(temp);
//
//        Database. ReadTransactionFromLocal();
//        for ( Transaction s: Database.transactions)
//        {
//            System.out.println(s);
//        }
//
//    }
}
