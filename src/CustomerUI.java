import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerUI extends JFrame{
    private JPanel panel;
    private JLabel textLabel;
    private JButton createAccountButton;
    private JButton saveButton;
    private JButton withdrawButton;
    private JButton transactionButton;
    private JButton informationButton;
    private JButton loanButton;
    private JButton stockButton;
    private JButton logOutButton;
    private JLabel time;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());
    ATM atm = ATM.getInstance();

    public CustomerUI(){

        panel = new JPanel();
        textLabel = new JLabel();
        createAccountButton = new JButton("Create Account");
        saveButton = new JButton("Save");
        withdrawButton = new JButton("Withdraw");
        transactionButton = new JButton("Transaction");
        informationButton = new JButton("Information");
        loanButton = new JButton("Loan");
        stockButton = new JButton("Stock");
        logOutButton = new JButton("Log Out");
        time = new JLabel();

        this.setTitle("Customer Board");
        this.add(panel);
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }


    public void Init(JPanel panel){
        panel.setLayout(null);

        String TC = "Welcome, " + atm.getCurrUser().getUserName();
        time.setText(temp);
        textLabel.setText(TC);
        textLabel.setForeground(Color.BLUE);
        textLabel.setBounds(200,150,200,50);
        createAccountButton.setBounds(0,40,150,50);
        saveButton.setBounds(0,130,150,50);
        withdrawButton.setBounds(0,220,150,50);
        transactionButton.setBounds(0,310,150,50);
        informationButton.setBounds(450,40,150,50);
        loanButton.setBounds(450,130,150,50);
        stockButton.setBounds(450,220,150,50);
        logOutButton.setBounds(450,310,150,50);
        time.setBounds(10,10,200,30);
        panel.add(time);
        panel.add(textLabel);
        panel.add(createAccountButton);
        panel.add(saveButton);
        panel.add(withdrawButton);
        panel.add(transactionButton);
        panel.add(informationButton);
        panel.add(loanButton);
        panel.add(stockButton);
        panel.add(logOutButton);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AccountUI();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SaveUI();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WithDrawUI();
            }
        });

        transactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TransactionUI();
            }
        });
        informationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new InformationUI();
            }
        });
        loanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //frame.dispose();
                System.out.print(1);
                dispose();
                new LoanUI();
            }
        });
        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer currUser = (Customer)atm.getCurrUser();
                Customer c = Database.getUsers().get(currUser.getUserName());
                if (c.getSecurityAccount() == null){
                    JOptionPane.showMessageDialog(null, "You need a security account to invest in stocks.", "No Stock Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    dispose();
                    new StockUI();
                }
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Customer c = (Customer) atm.getCurrUser();
                try { Database.WriteCustomersToLocal();
                    Database.WriteUserToLocal(c.getUserName());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException ex) {
                    ex.printStackTrace();
                }
                dispose();
                new WelcomeUI();
            }
        });

    }
}
