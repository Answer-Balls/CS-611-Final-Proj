import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ViewClientUI extends JFrame{

    private JPanel panel;
    private JButton backButton;
    private JLabel list;
    private JTextArea clientList;
    private JLabel header;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String content = df.format(new Date());
    private JLabel time;
    ATM atm = ATM.getInstance();

    public ViewClientUI(){
        panel = new JPanel();
        backButton = new JButton("Back");
        list = new JLabel("Clients Information");
        clientList = new JTextArea();
        time = new JLabel();
        header = new JLabel();

        this.add(panel);
        this.setTitle("Client Info Board");
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel){

        panel.setLayout(null);
        String temp = "";
        for (Map.Entry<String,Customer> entry : Database.getUsers().entrySet()) {
            //Customer object

            Customer cs = entry.getValue();

            //Name
            String cName = cs.getUserName();

            //Deposit and loan
            double cSavingBalance,cCheckingBalance,cSecurityBalance;
            double cSavingLoan,cCheckingLoan,cSecurityLoan;
            if(cs.getSavingAccount()!=null) {
                cSavingBalance = cs.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
                cSavingLoan = cs.getSavingAccount().getLoans().get(CurrencyType.USD).getAmount();
                temp += cName;
                temp += "  Saving  ";
                temp += "  " + cSavingBalance + "  ";
                temp += "  " + cSavingLoan;
                temp += "\n";
            }
            if(cs.getCheckingAccount()!=null) {
                cCheckingBalance=cs.getCheckingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
                cCheckingLoan=cs.getCheckingAccount().getLoans().get(CurrencyType.USD).getAmount();
                temp += cName;
                temp += "  Checking  ";
                temp += "  " + cCheckingBalance + "  ";
                temp += "  " + cCheckingLoan;
                temp += "\n";
            }

            if(cs.getSecurityAccount()!=null) {
                cSecurityBalance = cs.getSecurityAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
                cSecurityLoan = cs.getSecurityAccount().getLoans().get(CurrencyType.USD).getAmount();
                temp += cName;
                temp += "  Security  ";
                temp += "  " + cSecurityBalance + "  ";
                temp += "  " + cSecurityLoan;
                temp += "\n";
            }
        }

        clientList.setEditable(false);
        time.setText(content);
        header.setText("Customer Name  |  Account  |  Balance  |  Loan");

        clientList.setText(temp);
        list.setBounds(100,20,200,30);
        list.setForeground(Color.BLUE);
        header.setBounds(100,70,400,20);
        clientList.setBounds(100,100,400,200);
        backButton.setBounds(250,320,100,50);
        time.setBounds(10,10,200,30);

        panel.add(time);
        panel.add(header);
        panel.add(list);
        panel.add(clientList);
        panel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ManagerUI();
            }
        });
    }
}





