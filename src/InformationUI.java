import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.Color;

public class InformationUI extends JFrame{

    private JPanel panel;
    private JLabel account;
    private JLabel trans;
    private JTextArea info;
    private JTextArea transaction;
    private JButton backButton;
    private JLabel time;
    private JLabel accountHeader;
    private JLabel transHeader;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String content = df.format(new Date());
    private ArrayList<Transaction> transList;
    ATM atm = ATM.getInstance();

    public InformationUI(){
        panel = new JPanel();
        backButton = new JButton("Back");
        trans = new JLabel("Transaction Record");
        account = new JLabel("Account information");
        time = new JLabel();
        info = new JTextArea();
        transaction = new JTextArea();
        transList = new ArrayList<Transaction>();
        accountHeader = new JLabel();
        transHeader = new JLabel();
        account.setForeground(Color.BLUE);
        trans.setForeground(Color.BLUE);

        this.add(panel);
        this.setTitle("Information Board");
        this.setSize(800, 700);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel){

        String temp = "";
        Customer c = (Customer) atm.getCurrUser();
        Customer cs = Database.getUsers().get(c.getUserName());
        String cName = cs.getUserName();
        double cSavingBalanceUSD, cSavingBalanceEUR, cSavingBalanceCNY;
        double cCheckingBalanceUSD, cCheckingBalanceEUR, cCheckingBalanceCNY;
        double cSecurityBalanceUSD, cSecurityBalanceEUR, cSecurityBalanceCNY;
        double cSavingLoan,cCheckingLoan, cSecurityLoan;
        if(cs.getSavingAccount()!=null) {
            cSavingBalanceUSD = cs.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
            cSavingBalanceEUR = cs.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.EUR).getAmount();
            cSavingBalanceCNY = cs.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.CNY).getAmount();
            cSavingLoan = cs.getSavingAccount().getLoans().get(CurrencyType.USD).getAmount();
            if (cSavingBalanceUSD > 0){
                temp += cName;
                temp += "  Saving Account  ";
                temp += "  USD  ";
                temp += "  " + cSavingBalanceUSD + "  ";
                temp += "  " + cSavingLoan;
                temp += "\n";
            }

            if (cSavingBalanceEUR > 0){
                temp += cName;
                temp += "  Saving Account  ";
                temp += "  EUR  ";
                temp += "  " + cSavingBalanceEUR + "  ";
                temp += "  " + cSavingLoan;
                temp += "\n";
            }

            if (cSavingBalanceCNY > 0){
                temp += cName;
                temp += "  Saving Account  ";
                temp += "  CNY  ";
                temp += "  " + cSavingBalanceCNY + "  ";
                temp += "  " + cSavingLoan;
                temp += "\n";
            }

        }
        if(cs.getCheckingAccount()!=null) {
            cCheckingBalanceUSD = cs.getCheckingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
            cCheckingBalanceEUR = cs.getCheckingAccount().getCurrenciesDeposit().get(CurrencyType.EUR).getAmount();
            cCheckingBalanceCNY = cs.getCheckingAccount().getCurrenciesDeposit().get(CurrencyType.CNY).getAmount();
            cCheckingLoan=cs.getCheckingAccount().getLoans().get(CurrencyType.USD).getAmount();

            if(cCheckingBalanceUSD > 0){
                temp += cName;
                temp += "  Checking Account  ";
                temp += "  USD  ";
                temp += "  " + cCheckingBalanceUSD + "  ";
                temp += "  " + cCheckingLoan;
                temp += "\n";
            }
            if(cCheckingBalanceEUR > 0){
                temp += cName;
                temp += "  Checking Account  ";
                temp += "  EUR  ";
                temp += "  " + cCheckingBalanceEUR + "  ";
                temp += "  " + cCheckingLoan;
                temp += "\n";
            }
            if(cCheckingBalanceCNY > 0){
                temp += cName;
                temp += "  Checking Account  ";
                temp += "  CNY  ";
                temp += "  " + cCheckingBalanceCNY + "  ";
                temp += "  " + cCheckingLoan;
                temp += "\n";
            }

        }

        if(cs.getSecurityAccount()!=null) {
            cSecurityBalanceUSD = cs.getSecurityAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
            cSecurityBalanceEUR = cs.getSecurityAccount().getCurrenciesDeposit().get(CurrencyType.EUR).getAmount();
            cSecurityBalanceCNY = cs.getSecurityAccount().getCurrenciesDeposit().get(CurrencyType.CNY).getAmount();
            cSecurityLoan = cs.getSecurityAccount().getLoans().get(CurrencyType.USD).getAmount();
            if (cSecurityBalanceUSD > 0){
                temp += cName;
                temp += "  Security Account  ";
                temp += "  USD  ";
                temp += "  " + cSecurityBalanceUSD + "  ";
                temp += "  " + cSecurityLoan;
                temp += "\n";
            }
            if (cSecurityBalanceEUR > 0){
                temp += cName;
                temp += "  Security Account  ";
                temp += "  EUR  ";
                temp += "  " + cSecurityBalanceEUR + "  ";
                temp += "  " + cSecurityLoan;
                temp += "\n";
            }
            if (cSecurityBalanceCNY > 0){
                temp += cName;
                temp += "  Security Account  ";
                temp += "  CNY  ";
                temp += "  " + cSecurityBalanceCNY + "  ";
                temp += "  " + cSecurityLoan;
                temp += "\n";
            }

        }


        if (cs.getCheckingAccount() != null){
            transList.addAll(cs.getCheckingAccount().getTransactions());
        }
        if (cs.getSavingAccount() != null){
            transList.addAll(cs.getSavingAccount().getTransactions());
        }
        if (cs.getSecurityAccount() != null){
            transList.addAll(cs.getSecurityAccount().getTransactions());
        }

        String tr = "";
        if (!transList.isEmpty()){
            for (Transaction t : transList){
                tr += (t.toString()) + "\n";
            }
        }

        panel.setLayout(null);
        info.setEditable(false);
        transaction.setEditable(false);
        time.setText(content);
        info.setText(temp);
        transaction.setText(tr);
        accountHeader.setText("Name  |  Account Type  |  Currency  | Balance  |  Loan");
        transHeader.setText("Name  |  Account Type  |  Transaction Time  |  Currency  |  Original  |  Action  |  Fee  |  Balance");


        backButton.setBounds(350,620,100,50);
        time.setBounds(10,10,200,30);
        account.setBounds(100,30,200,20);
        accountHeader.setBounds(100,70,600,20);
        info.setBounds(100,90,600,100);
        trans.setBounds(100,210,200,20);
        transHeader.setBounds(100,250,600,20);
        transaction.setBounds(100,270,600,330);

        panel.add(accountHeader);
        panel.add(transHeader);
        panel.add(transaction);
        panel.add(account);
        panel.add(trans);
        panel.add(info);
        panel.add(time);
        panel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomerUI();
            }
        });
    }
}
