import java.io.*;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Class Database
 *
 *  Variables:
 *              final static String curDir: System.getProperty("user.dir")
 *
 *              static Map<String,User> users:  these User s are indeed all Customer s
 *
 *              static final DateTimeFormatter formatter:
 *
 *
 *  Constructors:
 *
 *          Database():
 *
 *  Methods:
 *
 *          void ReadUserFromLocal(String userName):
 *
 *                  try to read userName.json from local, if not found create such a file
 *
 *         void WriteUserToLocal(String userName):
 *
 *                 try to write userName.json from local, if not found create such a file then write
 *
 *         void addUser(User u):
 *
 *                  add a new User to users
 *
 */


public class Database {



    private final static String curDir=  System.getProperty("user.dir");

    private static Map<String,Customer> users =  new HashMap<String,Customer>() ;

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    // Empty constructor
    public Database() throws URISyntaxException {
    }

    public static List<Transaction> ReadOneDateTransactionFromLocal(String date) throws IOException, URISyntaxException {




        //String path =curDir+ "/ATM/"+dateName+".json";

        String path =curDir+ "/"+date+".json";

        File f = new File(path);

        if(!f.exists())
        {
            f.createNewFile();
        }


        //read
        Reader reader = Files.newBufferedReader( Paths.get(path));

        Type listOfTransactionObject = new TypeToken<ArrayList<Transaction>>() {}.getType();

        Gson gson = new Gson();



//    if(outputList!=null)
//        for( User u : outputList)
//        {
//
//            // insert local record to list
//            users.add(u);
//        }
        List<Transaction> result =gson.fromJson(reader,listOfTransactionObject);

        reader.close();

        return result;

    }

    public static void WriteTransactionLocal(LocalDateTime date, Transaction t) throws IOException, URISyntaxException {


        String dateName = date.format(formatter2).toString();




        //String path =curDir+ "/ATM/"+dateName+".json";

        String path =curDir+ "/"+dateName+".json";

        File f = new File(path);

        List<Transaction> tempTransaction;

        Gson gson = new Gson();

        if(!f.exists())
        {
            f.createNewFile();
            tempTransaction = new ArrayList<Transaction>();
            tempTransaction.add(t);
        }
        else {
            //try read previous
            Reader reader = Files.newBufferedReader(Paths.get(path));

            Type listOfTransactionObject = new TypeToken<ArrayList<Transaction>>() {
            }.getType();

            tempTransaction = gson.fromJson(reader, listOfTransactionObject);


            tempTransaction.add(t);

            reader.close();

        }
        //write
        FileWriter jsonWriter = new FileWriter(path,false);

        String jsonString = gson.toJson(tempTransaction);//https://www.baeldung.com/gson-list

        jsonWriter.append(jsonString);

        jsonWriter.flush();
        jsonWriter.close();
    }

    public static void ReadUserFromLocal(String userName) throws IOException, URISyntaxException {
        //String path =curDir+ "/ATM/"+userName+".json";

        String path =curDir+ "/"+userName+".json";

        File f = new File(path);

        if(!f.exists())
        {
            f.createNewFile();
        }


        //read
        Reader reader = Files.newBufferedReader( Paths.get(path));

        //Type listOfUserObject = new TypeToken<ArrayList<Customer>>() {}.getType();

        Gson gson = new Gson();

        users.put(userName,gson.fromJson(reader, Customer.class));


//    if(outputList!=null)
//        for( User u : outputList)
//        {
//
//            // insert local record to list
//            users.add(u);
//        }
        reader.close();

    }

    public static void WriteUserToLocal(String userName) throws IOException, URISyntaxException {

        //String path = curDir+ "/ATM/"+userName+".json";

        String path =curDir+ "/"+userName+".json";

        File f = new File(path);

        if(!f.exists())
        {
            f.createNewFile();
        }

        //write
        FileWriter jsonWriter = new FileWriter(path,false);

        Gson gson = new Gson();

        String jsonString = gson.toJson(users.get(userName));

        jsonWriter.append(jsonString);

        jsonWriter.flush();
        jsonWriter.close();
    }

    public static Map<String, Customer> getUsers() {
        return users;
    }

    public static void setUsers(Map<String, Customer> users) {
        Database.users = users;
    }
    public static void addUser(Customer u)
    {
        Database.users.put(u.getUserName(),u);
    }

    public static void ReadCustomersFromLocal() throws IOException, URISyntaxException {

        //String path =curDir+ "/ATM/"+dateName+".json";

        String path =curDir+ "/Customers.json";

        File f = new File(path);

        Gson gson = new Gson();

        if(!f.exists())
        {
            f.createNewFile();
        }
        else {
            //try read previous
            Reader reader = Files.newBufferedReader(Paths.get(path));

            Type MapCustomerObject = new TypeToken<HashMap<String, Customer>>() {
            }.getType();

            users = gson.fromJson(reader, MapCustomerObject);

            // in case no customer
            if(users==null)
            {
                users=new HashMap<String,Customer>() ;
            }

            reader.close();

        }
    }

    public static void WriteCustomersToLocal() throws IOException, URISyntaxException {



        String path =curDir+ "/"+"Customers"+".json";

        File f = new File(path);

        Gson gson = new Gson();

        //ReadCustomersFromLocal();

        //write
        FileWriter jsonWriter = new FileWriter(path,false);

        String jsonString = gson.toJson(users);//https://www.baeldung.com/gson-list

        jsonWriter.append(jsonString);

        jsonWriter.flush();
        jsonWriter.close();
    }

    public static void main(String args[]) throws IOException, URISyntaxException {
       // ReadUserFromLocal("C");
//        User u = new Customer("C","alan");
//        Customer c = (Customer) u ;
//

  //     Customer c =  users.get("C");
        //c.createAccount(AccountType.SAVING,CurrencyType.USD,100);
//        c.getSavingAccount().makeDeposit(CurrencyType.USD,6000);
//        WriteUserToLocal(c.getUserName());

       // c.getSavingAccount().requestLoan(CurrencyType.USD,300);

        for(Transaction t :ReadOneDateTransactionFromLocal ("2021-12-14"))
        {
            System.out.println(t.toString());
        }
        //WriteUserToLocal(c.getUserName());
//        User u2 = new Customer("B","alan");
//        Customer c2 = (Customer) u2 ;
//        c2.createAccount(AccountType.SAVING,CurrencyType.USD,100);

       // Transaction temp = new Transaction("hello",LocalDateTime.now(),CurrencyType.USD,200,ActionType.TRANSFEROUT,-100,100);

        //WriteUserToLocal();
        System.out.println(users.size());


        //System.out.println(LocalDateTime.now().format(Database.formatter).toString());
    }

    }
