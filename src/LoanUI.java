import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoanUI extends JFrame{
    private JPanel panel;
    private JLabel loanInfo;
    private JLabel choice;
    private JLabel header;
    private JButton borrowButton;
    private JButton returnButton;
    private JButton backButton;
    private JTextArea info;
    private JLabel time;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String content = df.format(new Date());
    ATM atm = ATM.getInstance();

    public LoanUI(){
        panel = new JPanel();
        header = new JLabel();
        borrowButton = new JButton("Borrow");
        returnButton = new JButton("Return");
        backButton = new JButton("Back");
        info = new JTextArea();
        loanInfo = new JLabel("Current Loan Information");
        choice = new JLabel("Do you want to borrow or return?");
        time = new JLabel();

        this.add(panel);
        this.setTitle("Loan Board");
        this.setSize(600, 400);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel){
        panel.setLayout(null);

        String temp = "";
        Customer curr = (Customer) atm.getCurrUser();
        Customer cs = Database.getUsers().get(curr.getUserName());
        String cName = cs.getUserName();
        double cSavingBalance,cCheckingBalance,cSecurityBalance;
        double cSavingLoan,cCheckingLoan,cSecurityLoan;
        if(cs.getSavingAccount()!=null) {
            cSavingBalance = cs.getSavingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
            cSavingLoan = cs.getSavingAccount().getLoans().get(CurrencyType.USD).getAmount();
            temp += cName;
            temp += "  Saving Account  ";
            temp += "  " + cSavingBalance + "  ";
            temp += "  " + cSavingLoan;
            temp += "\n";
        }
        if(cs.getCheckingAccount()!=null) {
            cCheckingBalance=cs.getCheckingAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
            cCheckingLoan=cs.getCheckingAccount().getLoans().get(CurrencyType.USD).getAmount();
            temp += cName;
            temp += "  Checking Account  ";
            temp += "  " + cCheckingBalance + "  ";
            temp += "  " + cCheckingLoan;
            temp += "\n";
        }

        if(cs.getSecurityAccount()!=null) {
            cSecurityBalance = cs.getSecurityAccount().getCurrenciesDeposit().get(CurrencyType.USD).getAmount();
            cSecurityLoan = cs.getSecurityAccount().getLoans().get(CurrencyType.USD).getAmount();
            temp += cName;
            temp += "  Security Account  ";
            temp += "  " + cSecurityBalance + "  ";
            temp += "  " + cSecurityLoan;
            temp += "\n";
        }

        info.setEditable(false);
        info.setText(temp);
        time.setText(content);
        loanInfo.setForeground(Color.BLUE);
        choice.setForeground(Color.BLUE);
        header.setText("Name  |  Account Type  |  Balance  |  Load");
        borrowButton.setBounds(100,310,100,50);
        returnButton.setBounds(250,310,100,50);
        loanInfo.setBounds(100,20,200,30);
        header.setBounds(100,70,400,20);
        info.setBounds(100,90,400,140);
        choice.setBounds(100,250,300,50);
        time.setBounds(10,10,200,30);
        backButton.setBounds(400,310,100,50);
        panel.add(header);
        panel.add(time);
        panel.add(loanInfo);
        panel.add(choice);
        panel.add(info);
        panel.add(borrowButton);
        panel.add(returnButton);
        panel.add(backButton);


        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BorrowLoanUI();
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ReturnLoanUI();
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
