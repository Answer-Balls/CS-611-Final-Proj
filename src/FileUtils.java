import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileUtils {
    public static void writeCustomer(List<Customer> customers){
        File customerFile = new File("customer.csv");
        if (!customerFile.exists()){
            customerFile = new File("../customer.csv");
        }
        try {
            FileWriter fw = new FileWriter(customerFile);
            fw.write("Index  CustomerID  Password");
            fw.write(System.lineSeparator());
            for(int i = 0; i < customers.size(); i++){
                fw.write(i + "  " + customers.get(i).getUserName() + "  " + customers.get(i).getPassword());
                fw.write(System.lineSeparator());
            }
            fw.flush();
            fw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void writeManager(List<Manager> managers){
        File managerFile = new File("manager.csv");
        if (!managerFile.exists()){
            managerFile = new File("../manager.csv");
        }
        try {
            FileWriter fw = new FileWriter(managerFile);
            fw.write("Index  ManagerID  Password");
            fw.write(System.lineSeparator());
            for(int i = 0; i < managers.size(); i++){
                fw.write(i + "  " + managers.get(i).getUserName() + "  " + managers.get(i).getPassword());
                fw.write(System.lineSeparator());
            }
            fw.flush();
            fw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<Customer> readCustomer(){
        String customerFile = "customer.csv";
        String line;
        List<Customer> customers = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(customerFile));
            line = br.readLine();
            while ((line = br.readLine()) != null){
                String[] content = line.split("  ");
                customers.add(new Customer(content[1], content[2]));
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return customers;
    }

    public static List<Manager> readManager(){
        String managerFile = "manager.csv";
        String line;
        List<Manager> managers = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(managerFile));
            line = br.readLine();
            while ((line = br.readLine()) != null){
                String[] content = line.split("  ");
                managers.add(new Manager(content[1], content[2]));
            }
        } catch(IOException e){
            e.printStackTrace();
        }

        return managers;
    }

}
