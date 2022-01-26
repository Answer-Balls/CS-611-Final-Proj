import com.google.gson.JsonParser;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StockUI extends JFrame{

    private JPanel panel;
    private JButton backButton;
    private JButton sellButton;
    private JButton searchButton;
    private JButton buyButton;
    private JLabel companyLabel;
    private JTextField companyField;
    //private JList stockList;
    private JTextArea stockInfo;
    private JLabel profit;

    private JLabel time;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());

    //DefaultListModel<String> l;
    Customer c = (Customer) ATM.getInstance().getCurrUser();
    Stock s = null;

    public StockUI(){
        panel = new JPanel();
        backButton = new JButton("Back");
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        searchButton = new JButton("Search");
        time = new JLabel();
        companyField = new JTextField(70);
        companyLabel = new JLabel("Company Symbol: ");
        stockInfo = new JTextArea("");
        Customer currUser = Database.getUsers().get(c.getUserName());
        profit = new JLabel("Realized Profit: "+currUser.getProfit());


        this.add(panel);
        this.setTitle("Stock Board");
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel){
        panel.setLayout(null);

        time.setText(temp);

        companyLabel.setBounds(80,30,150,35);
        companyField.setBounds(250,30,100,35);

        buyButton.setBounds(120,300,100,50);
        sellButton.setBounds(245, 300, 100, 50);
        backButton.setBounds(370,300,100,50);
        searchButton.setBounds(400, 25, 75, 30);
        time.setBounds(10,10,200,30);
        stockInfo.setBounds(100,75, 400,200);
        stockInfo.setLineWrap(true);
        profit.setBounds(360,265,200,35);
        panel.add(time);
        panel.add(companyLabel);
        panel.add(companyField);
        panel.add(backButton);
        panel.add(buyButton);
        panel.add(sellButton);
        panel.add(searchButton);
        panel.add(stockInfo);
        panel.add(profit);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (s == null){
                    JOptionPane.showMessageDialog(null, "Please search the stock you want to buy.", "No Stock Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    dispose();
                    new BuyStockUI(s);
                }
            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (s == null){
                    JOptionPane.showMessageDialog(null, "Please search the stock you want to sell.", "No Stock Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    dispose();
                    new SellStockUI(s);
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

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String CompanyName = companyField.getText().trim();

                String path = "https://financialmodelingprep.com/api/v3/search-name?query=meta&limit=10&exchange=NASDAQ&apikey=8c2e87d9de958123a08420ecd0c747bf";

                path = "https://financialmodelingprep.com/api/v3/quote-short/"+ CompanyName + "?apikey=8c2e87d9de958123a08420ecd0c747bf";
                URL url = null;
                try {
                    url = new URL(path);
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
                    String result = "";
                    for (String line; (line = reader.readLine()) != null;) {
                        result += line;
                    }
                    if (result.equals("[ ]")){
                        System.out.println("Nope");
                    }
                    else{
                        String name = new JsonParser().parse(result).getAsJsonArray().get(0).getAsJsonObject().get("symbol").getAsString();
                        int volume = new JsonParser().parse(result).getAsJsonArray().get(0).getAsJsonObject().get("volume").getAsInt();
                        double price = new JsonParser().parse(result).getAsJsonArray().get(0).getAsJsonObject().get("price").getAsDouble();
                        price = (Math.round(price*100)/100.0);
                        s = new Stock(name, volume, price);
                        Customer currUser = Database.getUsers().get(c.getUserName());
                        stockInfo.setText(s.toString());
                        for (Stock st : currUser.getStocks()){
                            if (st.compareTo(s) == 0){
                                stockInfo.setText(st.toString());
                            }
                        }
                    }


                } catch (UnsupportedEncodingException er) {
                    er.printStackTrace();
                } catch (IOException er) {
                    er.printStackTrace();
                }
            }
        });
    }

}


