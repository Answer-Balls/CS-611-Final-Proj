import java.util.ArrayList;
import java.util.List;

public class ManagerDaoImpl implements ManagerDao{
    List<Manager> managers;

    public ManagerDaoImpl(){
//        managers = new ArrayList<Manager>();
        managers = FileUtils.readManager();
        Manager manager1 = new Manager("test1", "test1");
        Manager manager2 = new Manager("test2", "test2");
        Manager manager3 = new Manager("1", "1");
        addManager(manager1);
        addManager(manager2);
        addManager(manager3);

    }

    @Override
    public List<Manager> getAllManagers(){
        return managers;
    }

    @Override
    public Manager getManager(String managerID){
        for(Manager manager: managers){
            if (manager.getUserName().equals(managerID)){
                return manager;
            }
        }
        return null;
    }

    @Override
    public void addManager(Manager manager){
        managers.add(manager);

        FileUtils.writeManager(managers);

    }

    @Override
    public void removeManager(Manager manager){

    }

    @Override
    public boolean generateManager(String ID, String password){
        Manager manager = getManager(ID);
        if (manager != null){
            return false;
        }else{
            manager = new Manager(ID, password);
            managers.add(manager);
            FileUtils.writeManager(managers);
        }
        return true;
    }

    @Override
    public Manager searchManager(String ID, String password){
        for (Manager manager: managers){
            if (manager.getUserName().equals(ID) && manager.getPassword().equals(password)){
                return manager;
            }
        }
        return null;
    }
}
