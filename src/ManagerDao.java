import java.util.List;

public interface ManagerDao {

    public List<Manager> getAllManagers();
    public Manager getManager(String managerID);
    public boolean generateManager(String ID, String password);
    public void addManager(Manager manager);
    public void removeManager(Manager manager);
    public Manager searchManager(String ID, String password);
}
