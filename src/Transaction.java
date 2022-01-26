import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  Class Transaction:
 *
 *  Variables:
 *
 *          static final DateTimeFormatter formatter
 *
 *          String accountId:
 *
 *          String evenTime:
 *
 *          CurrencyType currencyType:
 *
 *          double before:
 *
 *          ActionType actionType:
 *
 *          double transactionAmount:
 *
 *          double after:
 *
 *  Constructor:
 *                  Transaction():
 *
 *
 *                  Transaction( String accountId,LocalDateTime evenTime, CurrencyType currencyType, double before, ActionType actionType,double transactionAmount, double after)
 *
 *  Methods:
 *
 *            @override String toString()
 *
 *            getters & setters
 */

public class Transaction {

    private String accountId;

    private String evenTime;

    private CurrencyType currencyType;

    private double before;

    private ActionType actionType;

    private double transactionAmount;

    private double after;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(){};

    public Transaction( String accountId,LocalDateTime evenTime, CurrencyType currencyType, double before, ActionType actionType,double transactionAmount, double after)
    {
        this.accountId=accountId;
        this.evenTime=evenTime.format(formatter);
        this.currencyType=currencyType;
        this.before=before;
        this.actionType=actionType;
        this.transactionAmount=transactionAmount;
        this.after=after;
    }


    @Override
    public String toString()
    {   String temp = "   ";
        return String.format(accountId+temp +evenTime+ temp+currencyType.toString()+ temp+before+ temp+actionType.toString()+ temp+transactionAmount+ temp+after);
    }
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEvenTime() {
        return evenTime;
    }

    public void setEvenTime(String evenTime) {
        this.evenTime = evenTime;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public double getBefore() {
        return before;
    }

    public void setBefore(double before) {
        this.before = before;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public double getAfter() {
        return after;
    }

    public void setAfter(double after) {
        this.after = after;
    }
}
