import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @ClassName AccountUI
 * @Description This is the interface for creating account.
 * @Author Ziyang Sheng
 */

public class AccountUI extends JFrame{
    private JPanel panel;
    private JLabel accountType;
    private JComboBox<String> types;
    private JScrollPane selectType;
    private JLabel currencyType;
    private JComboBox<String> currencyTypes;
    private JScrollPane selectCurrencyType;
    private JLabel principal;
    private JTextField amount;
    private JLabel notice;
    private JButton okButton;
    private JButton backButton;
    private JLabel time;
    ATM atm = ATM.getInstance();

    // display time
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());

    public AccountUI()
    {
        panel = new JPanel();
        accountType = new JLabel("Account Type");

        // three types of account
        String []at= {"Checking","Saving","Security"};
        types = new JComboBox<String>(at);
        selectType = new JScrollPane(types);
        currencyType = new JLabel("Currency Type");

        // three types of currency
        String []ct= {"USD","CNY","EUR"};
        currencyTypes = new JComboBox<String>(ct);
        selectCurrencyType = new JScrollPane(currencyTypes);

        principal = new JLabel("Principal: ");
        amount = new JTextField(100);
        notice = new JLabel();
        okButton = new JButton("OK");
        backButton = new JButton("Back");
        time = new JLabel();

        this.add(panel);
        this.setTitle("Create Account Board");
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }


    private void Init(JPanel panel){
        panel.setLayout(null);
        accountType.setBounds(100,40,100,40);
        selectType.setBounds(250,40,200,40);
        currencyType.setBounds(100,110,100,40);
        selectCurrencyType.setBounds(250,110,200,40);
        time.setText(temp);
        principal.setBounds(100,180,100,40);
        amount.setBounds(250,180,200,40);
        notice.setBounds(100,250,400,40);
        okButton.setBounds(150,280,100,50);
        backButton.setBounds(350,280,100,50);
        time.setBounds(10,10,200,30);

        panel.add(time);
        panel.add(accountType);
        panel.add(selectType);
        panel.add(currencyType);
        panel.add(selectCurrencyType);
        panel.add(principal);
        panel.add(amount);
        panel.add(notice);
        panel.add(okButton);
        panel.add(backButton);

        // pressing the ok button
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeTemp = types.getItemAt(types.getSelectedIndex());
                String currencyTemp = currencyTypes.getItemAt(currencyTypes.getSelectedIndex());
                double amountTemp = Double.valueOf(amount.getText()).doubleValue();


                Customer curr = (Customer) atm.getCurrUser();
                Customer currUser = Database.getUsers().get(curr.getUserName());
                CurrencyType cTemp;
                AccountType aTemp;

                // create account according to the input account type
                switch (typeTemp){
                    case "Saving" -> {
                        if (currUser == null || currUser.getSavingAccount() == null){
                            aTemp = AccountType.SAVING;
                        } else{
                            System.out.println(currUser.getSavingAccount());
                            JOptionPane.showMessageDialog(null, "Saving Account already exist!", "Account repetition Error", JOptionPane.ERROR_MESSAGE);
                            aTemp = null;
                        }
                        break;
                    }
                    case "Security" -> {
                        if (currUser == null || currUser.getSecurityAccount() == null){
                            aTemp = AccountType.SECURITY;
                        } else{
                            JOptionPane.showMessageDialog(null, "Security Account already exist!", "Account repetition Error", JOptionPane.ERROR_MESSAGE);
                            aTemp = null;
                        }
                        break;
                    }
                    default -> {
                        if (currUser == null || currUser.getCheckingAccount() == null){
                            aTemp = AccountType.CHECKING;
                        } else{
                            JOptionPane.showMessageDialog(null, "Checking Account already exist!", "Account repetition Error", JOptionPane.ERROR_MESSAGE);
                            aTemp = null;
                        }
                        break;
                    }
                }

                // create account according to the input currency type
                switch (currencyTemp){
                    case "USD" -> {
                        cTemp = CurrencyType.USD;
                        break;
                    }
                    case "EUR" -> {
                        cTemp = CurrencyType.EUR;
                        break;
                    }
                    default -> {
                        cTemp = CurrencyType.CNY;
                        break;
                    }
                }

                if (typeTemp.equalsIgnoreCase("Security") && currUser.getSavingAccount() == null ){
                    System.out.println(currUser.getSavingAccount());
                    JOptionPane.showMessageDialog(null, "Saving account is required!", "No Saving Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (typeTemp.equalsIgnoreCase("Security") && currUser.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount() < 5000.0){
                    JOptionPane.showMessageDialog(null, "Saving account balance is low!", "Low Balance Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (typeTemp.equalsIgnoreCase("Security") && amountTemp < 1000.0) {
                    JOptionPane.showMessageDialog(null, "The initial value cannot be less than 1000!", "Low Value Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (amountTemp < 5){
                    JOptionPane.showMessageDialog(null, "Initial value must be greater than 5!", "Low Value Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (aTemp == null){
                    dispose();
                    new AccountUI();
                }

                else{
                    try {
                        currUser.createAccount(aTemp, cTemp, amountTemp);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }

                    // create a new account and record it in the database
                    dispose();
                    JOptionPane.showMessageDialog(null, "Account Created Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    if (currUser.getSavingAccount() != null && currUser.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount() > 5000.0){
                        JOptionPane.showMessageDialog(null, "You have enough balance to enjoy the stock market! Go make some money!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                    }
                    new CustomerUI();
                }
            }
        });

        // pressing the back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomerUI();
            }
        });
    }
}






