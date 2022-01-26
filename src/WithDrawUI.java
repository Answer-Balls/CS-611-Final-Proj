import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WithDrawUI extends JFrame{

    private JPanel panel;
    private JLabel accountType;
    private JLabel amount;
    private JTextField withdrawAmount;
    private JComboBox<String> types;
    private JScrollPane selectType;
    private JButton okButton;
    private JButton backButton;
    private JLabel time;
    private JLabel currencyType;
    private JComboBox<String> currencyTypes;
    private JScrollPane selectCurrencyType;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());
    ATM atm = ATM.getInstance();

    public WithDrawUI()
    {
        panel = new JPanel();
        accountType = new JLabel("Account Type");
        amount = new JLabel("Amount");
        String []act= {"Checking","Saving","Security"};
        types = new JComboBox<String>(act);
        selectType = new JScrollPane(types);
        withdrawAmount = new JTextField(30);
        okButton = new JButton("OK");
        backButton = new JButton("Back");
        time = new JLabel();
        currencyType = new JLabel("Currency Type");
        String []ct= {"USD","CNY","EUR"};
        currencyTypes = new JComboBox<String>(ct);
        selectCurrencyType = new JScrollPane(currencyTypes);

        this.add(panel);
        this.setTitle("Withdraw Board");
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
        withdrawAmount.setBounds(250,210,200,40);

        okButton.setBounds(150,300,100,50);
        backButton.setBounds(350,300,100,50);
        time.setBounds(10,10,200,30);

        panel.add(time);
        panel.add(accountType);
        panel.add(amount);
        panel.add(selectType);
        panel.add(withdrawAmount);
        panel.add(currencyType);
        panel.add(selectCurrencyType);
        panel.add(okButton);
        panel.add(backButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String typeTemp = types.getItemAt(types.getSelectedIndex());
                String currencyTemp = currencyTypes.getItemAt(currencyTypes.getSelectedIndex());
                double amountTemp = Double.valueOf(withdrawAmount.getText()).doubleValue();
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
                            c.getSavingAccount().withdrawal(cTemp, amountTemp);
                            JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new CustomerUI();
                        } else {
                            JOptionPane.showMessageDialog(null, "No Saving Account!", "Empty Account Error", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            new WithDrawUI();
                        }
                        break;
                    }
                    case "Security" -> {
                        if (c.getSecurityAccount() != null){
                            c.getSecurityAccount().withdrawal(cTemp, amountTemp);
                            JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new CustomerUI();
                        } else {
                            JOptionPane.showMessageDialog(null, "No Security Account!", "Empty Account Error", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            new WithDrawUI();
                        }
                        break;
                    }
                    default -> {
                        if (c.getCheckingAccount() != null){
                            c.getCheckingAccount().withdrawal(cTemp, amountTemp);
                            JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new CustomerUI();
                        } else {
                            JOptionPane.showMessageDialog(null, "No Checking Account!", "Empty Account Error", JOptionPane.ERROR_MESSAGE);
                            dispose();
                            new WithDrawUI();
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
