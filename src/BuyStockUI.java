import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

public class BuyStockUI extends JFrame {

    private JPanel panel;
    private JLabel loanInfo;
    private JLabel choice;
    private JButton okButton;
    private JButton backButton;
    private JTextField amount;
    private JTextArea info;
    private Stock currentStock;
    Customer c = (Customer) ATM.getInstance().getCurrUser();

    public BuyStockUI(Stock s){
        panel = new JPanel();
        okButton = new JButton("OK");
        backButton = new JButton("Back");
        amount = new JTextField(100);
        loanInfo = new JLabel("Current Stock Information");
        info = new JTextArea();
        choice = new JLabel("How many stocks do you want to buy?");

        currentStock = s;

        this.add(panel);
        this.setTitle("Stock Buying Board");
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel){
        panel.setLayout(null);

        info.setEditable(false);
        info.setText(currentStock.toString());
        Customer currUser = Database.getUsers().get(c.getUserName());
        for (Stock st : currUser.getStocks()){
            if (st.compareTo(currentStock) == 0){
                info.setText(st.toString());
            }
        }

        okButton.setBounds(150,300,100,50);
        backButton.setBounds(350,300,100,50);
        info.setBounds(100,60,400,150);
        loanInfo.setBounds(100,20,200,30);
        choice.setBounds(100,240,250,50);
        amount.setBounds(350,240,100,30);

        panel.add(loanInfo);
        panel.add(choice);
        panel.add(info);
        panel.add(okButton);
        panel.add(amount);
        panel.add(backButton);


        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer currUser = Database.getUsers().get(c.getUserName());
                int num = Integer.parseInt(amount.getText().trim());
                if (num == 0 || num > currentStock.getVolume()){
                    JOptionPane.showMessageDialog(null, "Incorrect Stock Amount!", "Failed", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    try {
                        if (!currUser.add_stock(currentStock, num)){
                            JOptionPane.showMessageDialog(null, "Not Enough Balance!", "Failed", JOptionPane.INFORMATION_MESSAGE);
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new StockUI();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StockUI();
            }
        });
    }

}


