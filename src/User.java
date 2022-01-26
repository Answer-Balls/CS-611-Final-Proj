/**
 *   Class User :
 *
 *   Variables:
 *
 *              String userId:
 *
 *              String password:
 *
 *
 *
 *   Constructors:
 *
 *              User(String userName, String password):
 *
 *   Methods:
 *              getters & setters
 *
 *
 */

public abstract class User {
    protected String userId;
    protected String password;

    public User()
    { this.userId = " ";
        this.password = " ";

    }
    public User(String userName, String password) {
        this.userId = userName;
        this.password = password;
    }

    public String getUserName() {
        return userId;
    }

    public void setUserName(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
