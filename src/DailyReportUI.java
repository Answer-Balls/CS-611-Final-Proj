import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyReportUI extends JFrame {

    private JPanel panel;
    private JLabel header;
    private JButton backButton;
    private JLabel record;
    private JTextArea transRecord;
    private JLabel time;
    private List<Transaction> transaction;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String temp = df.format(new Date());

    public DailyReportUI() throws IOException, URISyntaxException {
        panel = new JPanel();
        backButton = new JButton("Back");
        record = new JLabel("Daily Transaction Record");
        transRecord = new JTextArea();
        time = new JLabel();
        header = new JLabel();
        transaction = new ArrayList<Transaction>();

        this.add(panel);
        this.setTitle("Daily Report Board");
        this.setSize(800, 600);
        this.setLocation(200, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        Init(panel);
    }

    private void Init(JPanel panel) throws IOException, URISyntaxException {
        panel.setLayout(null);
        String content = "";
        time.setText(temp);

        transaction = Database.ReadOneDateTransactionFromLocal(temp);
        for (Transaction t: transaction){
            content += (t.toString()) + "\n";
        }
        System.out.println(content);
        transRecord.setText(content);
        transRecord.setEditable(false);
        header.setText("Name  |  Account Type  |  Transaction Time  |  Currency  |  Original  |  Action  |  Fee  |  Balance");
        record.setForeground(Color.BLUE);
        record.setBounds(100,30,300,30);
        header.setBounds(100,80,600,20);
        transRecord.setBounds(100,100,600,350);
        backButton.setBounds(350,500,100,50);
        time.setBounds(10,10,200,30);
        panel.add(time);
        panel.add(record);
        panel.add(header);
        panel.add(transRecord);
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
