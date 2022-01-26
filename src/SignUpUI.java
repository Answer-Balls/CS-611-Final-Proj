import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SignUpUI extends JFrame{

    private JPanel panel;
    private JButton okButton;
    private JButton backButton;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel confirmLabel;
    private JTextField idField;
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private String type;
    private JLabel time;
    ATM atm = ATM.getInstance();
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());


    public SignUpUI(int identity) {

        if (identity == 1){
            type = "Customer";
        } else{
            type = "Manager";
        }
        panel = new JPanel();
        idLabel = new JLabel("UserID: ");
        passwordLabel = new JLabel("Password: ");
        confirmLabel = new JLabel("Confirm Password: ");
        idField = new JTextField(200);
        passwordField = new JPasswordField(200);
        confirmField = new JPasswordField(200);
        okButton = new JButton("OK");
        backButton = new JButton("Back");
        time = new JLabel();


        this.setTitle("Sign Up Board");
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
        idLabel.setBounds(60,50,150,40);
        passwordLabel.setBounds(60,110,150,40);
        confirmLabel.setBounds(60,170,150,40);
        idField.setBounds(250,50,200,40);
        passwordField.setBounds(250,110,200,40);
        confirmField.setBounds(250,170,200,40);
        okButton.setBounds(175,280,100,50);
        backButton.setBounds(325,280,100,50);
        time.setBounds(10,10,200,30);

        panel.add(time);
        panel.add(idLabel);
        panel.add(passwordLabel);
        panel.add(confirmLabel);
        panel.add(idField);
        panel.add(passwordField);
        panel.add(confirmField);
        panel.add(okButton);
        panel.add(backButton);


        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = idField.getText().trim();
                String password = String.valueOf(passwordField.getPassword());
                String confirm = String.valueOf(confirmField.getPassword());
                if( password.length() == 0 || userId.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Empty username or password! Please try again.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    if (type.equalsIgnoreCase("Customer")){
                        new SignUpUI(1);
                    } else{
                        new SignUpUI(2);
                    }
                }

                else if (!password.equals(confirm)){
                    JOptionPane.showMessageDialog(null, "Password Inconsistent! Please try again.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
                    dispose();
                    if (type.equalsIgnoreCase("Customer")){
                        new SignUpUI(1);
                    } else{
                        new SignUpUI(2);
                    }
                }

                else if (type.equalsIgnoreCase("Customer")){
                    atm.getCustomerDao().addCustomer(new Customer(userId, password));
                    JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginUI(1);
                }
                else if (type.equalsIgnoreCase("Manager")){
                    atm.getManagerDao().addManager(new Manager(userId, password));
                    JOptionPane.showMessageDialog(null, "Operation Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new LoginUI(2);
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



