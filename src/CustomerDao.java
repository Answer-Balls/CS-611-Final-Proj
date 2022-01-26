import java.util.List;


public interface CustomerDao {
    public List<Customer> getAllCustomers();
    public Customer getCustomer(String customerID);
    public boolean generateCustomer(String ID, String password);
    public void addCustomer(Customer customer);
    public void removeCustomer(Customer customer);
    public Customer searchCustomer(String ID, String password);

}
