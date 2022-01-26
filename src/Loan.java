import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *
 *   Predefined Class Currency:
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
 *  ---------------------------------------------------------------------------------------------------------------------
 * Class Loan:
 *
 *  Variables:
 *
 *              boolean hasBeenWithdrawn :

 *  Constructors:
 *
 *          Loan():
 *                                  create a loan started from now(Current date)
 *
 *          Loan( CurrencyType currencyType,double amount):
 *
 *  Methods:
 */
public class Loan extends Currency{

    private boolean hasBeenWithdrawn;

    public Loan()
    {
        super();
        this.hasBeenWithdrawn=false;
        this.startDate= LocalDateTime.now().format(Database.formatter);; // Current date
    }
    public Loan( CurrencyType currencyType,double amount)
    {
        super();
        this.hasBeenWithdrawn=false;
        this.startDate= LocalDateTime.now().format(Database.formatter);; // Current date
        this.typeOfCurrency=currencyType;
        this.amount=-1*amount;
    }

    public boolean isHasBeenWithdrawn() {
        return hasBeenWithdrawn;
    }

    public void setHasBeenWithdrawn(boolean hasBeenWithdrawn) {
        this.hasBeenWithdrawn = hasBeenWithdrawn;
    }
}
