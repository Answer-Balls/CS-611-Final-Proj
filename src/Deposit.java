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
 *
 *  ---------------------------------------------------------------------------------------------------------------------
 *
 *  Class Deposit:
 *
 *  Constructors:
 *
 *              Deposit():
 *                          empty constructor
 *
 *              Deposit( CurrencyType currencyType,double amount):
 */
public class Deposit extends Currency{


    public Deposit()
    {
        super();
        this.startDate= LocalDateTime.now().format(Database.formatter);; // Current date
    }

    public Deposit( CurrencyType currencyType,double amount)
    {
        super();
        this.startDate= LocalDateTime.now().format(Database.formatter);; // Current date
        this.typeOfCurrency=currencyType;
        this.amount=amount;
    }

}

