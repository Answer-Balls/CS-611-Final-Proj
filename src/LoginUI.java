import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginUI extends JFrame{

    private JPanel panel;
    private JButton loginButton;
    private JButton backButton;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JTextField idField;
    private JPasswordField passwordField;
    private String ID;
    private JButton signUpButton;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());
    private JLabel time;
    ATM atm = ATM.getInstance();


    public LoginUI(int identity) {
        if (identity == 1){
            ID = "Customer";
        } else{
            ID = "Manager";
        }

        panel = new JPanel();
        idLabel = new JLabel("UserID: ");
        passwordLabel = new JLabel("Password: ");
        idField = new JTextField(30);
        passwordField = new JPasswordField(30);
        loginButton = new JButton("Login");
        backButton = new JButton("Back");
        signUpButton = new JButton("Sign Up");
        time = new JLabel();

        this.setTitle(ID + " Login Board");
        this.add(panel);
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel){

        panel.setLayout(null);
        time.setText(temp);

        idLabel.setBounds(150,75,100,35);
        passwordLabel.setBounds(150,125,100,35);
        idField.setBounds(250,75,200,35);
        passwordField.setBounds(250,125,200,35);
        loginButton.setBounds(100,250,100,50);
        backButton.setBounds(400,250,100,50);
        signUpButton.setBounds(250,250,100,50);
        time.setBounds(10,10,200,30);
        panel.add(time);
        panel.add(idLabel);
        panel.add(passwordLabel);
        panel.add(idField);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(backButton);
        panel.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (ID.equalsIgnoreCase("Customer")){
                    new SignUpUI(1);
                } else{
                    new SignUpUI(2);
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = idField.getText().trim();
                String password = String.valueOf(passwordField.getPassword());
                if( password.length() == 0 || userId.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Empty username or password!", "Login Error ", JOptionPane.ERROR_MESSAGE);
                } else{

                    if (ID.equalsIgnoreCase("Customer")){
                        if(atm.customerLogin(userId, password)){
                            dispose();
                            new CustomerUI();
                        } else {
                            JOptionPane.showMessageDialog(null,"Wrong CustomerID or password! Please try again.", "Login Error",JOptionPane.ERROR_MESSAGE);
                            idField.setText("");
                            passwordField.setText("");
                        }
                    } else{
                        if(atm.managerLogin(userId, password)){
                            dispose();
                            new ManagerUI();
                        } else {
                            JOptionPane.showMessageDialog(null,"Wrong CustomerID or password! Please try again.", "Login Error",JOptionPane.ERROR_MESSAGE);
                            idField.setText("");
                            passwordField.setText("");
                        }
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WelcomeUI();
            }
        });
    }
}