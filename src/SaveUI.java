import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveUI extends JFrame {
    private JPanel panel;
    private JLabel accountType;
    private JLabel amount;
    private JTextField saveAmount;
    private JComboBox<String> types;
    private JScrollPane selectType;
    private JLabel time;
    private JLabel currencyType;
    private JComboBox<String> currencyTypes;
    private JScrollPane selectCurrencyType;
    private JButton okButton;
    private JButton backButton;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());
    ATM atm = ATM.getInstance();

    public SaveUI()
    {
        panel = new JPanel();
        accountType = new JLabel("Account Type");
        amount = new JLabel("Amount");
        String []act= {"Checking","Saving","Security"};
        types = new JComboBox<String>(act);
        selectType = new JScrollPane(types);
        saveAmount = new JTextField(30);
        okButton = new JButton("OK");
        backButton = new JButton("Back");
        time = new JLabel();
        currencyType = new JLabel("Currency Type");
        String []ct= {"USD","CNY","EUR"};
        currencyTypes = new JComboBox<String>(ct);
        selectCurrencyType = new JScrollPane(currencyTypes);

        this.add(panel);
        this.setTitle("Saving Board");
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel){
        panel.setLayout(null);
        time.setText(temp);
        accountType.setBounds(100,50,100,40);
        selectType.setBounds(250,50,200,40);

        currencyType.setBounds(100,130,100,40);
        selectCurrencyType.setBounds(250,130,200,40);

        amount.setBounds(100,210,100,40);
        saveAmount.setBounds(250,210,200,40);

        okButton.setBounds(150,300,100,50);
        backButton.setBounds(350,300,100,50);
        time.setBounds(10,10,200,30);


        panel.add(currencyType);
        panel.add(selectCurrencyType);
        panel.add(time);
        panel.add(accountType);
        panel.add(amount);
        panel.add(selectType);
        panel.add(saveAmount);
        panel.add(okButton);
        panel.add(backButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String typeTemp = types.getItemAt(types.getSelectedIndex());
                String currencyTemp = currencyTypes.getItemAt(currencyTypes.getSelectedIndex());
                double amountTemp = Double.valueOf(saveAmount.getText()).doubleValue();
                System.out.println(typeTemp);
                System.out.println(amountTemp);

                String currId = atm.getCurrUser().getUserName();
                Customer c = Database.getUsers().get(currId);
                System.out.println(c);

                CurrencyType cTemp;
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

                switch (typeTemp){
                    case "Saving" -> {
                        if (c.getSavingAccount() != null){
                            c.getSavingAccount().makeDeposit(cTemp, amountTemp);
                            JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            if (c.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount() > 5000.0){
                                JOptionPane.showMessageDialog(null, "You have enough balance to enjoy the stock market! Go make some money!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
                            }
                            dispose();
                            new CustomerUI();
                        } else {
                            JOptionPane.showMessageDialog(null, "No Saving Account!", "Empty Account Error", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            new SaveUI();
                        }
                        break;
                    }
                    case "Security" -> {
                        if (c.getSecurityAccount() != null){
                            if (amountTemp < 1000){
                                JOptionPane.showMessageDialog(null, "The deposit cannot be less than 1000!", "Low Deposit Error", JOptionPane.ERROR_MESSAGE);
                                dispose();
                                new SaveUI();
                            }
                            else if (c.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount() < 5000){
                                JOptionPane.showMessageDialog(null, "Saving Balance is too low!", "Low Balance Error", JOptionPane.ERROR_MESSAGE);
                                dispose();
                                new SaveUI();
                            }
                            else{
                                c.getSecurityAccount().makeDeposit(cTemp, amountTemp);
                                JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                dispose();
                                new CustomerUI();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No Security Account!", "Empty Account Error", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            new SaveUI();
                        }
                        break;
                    }
                    default -> {
                        if (c.getCheckingAccount() != null){
                            c.getCheckingAccount().makeDeposit(cTemp, amountTemp);
                            JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new CustomerUI();
                        } else {
                            JOptionPane.showMessageDialog(null, "No Checking Account!", "Empty Account Error", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            new SaveUI();
                        }
                        break;
                    }
                }


            }
        });

            backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CustomerUI();
            }
        });
    }
}





