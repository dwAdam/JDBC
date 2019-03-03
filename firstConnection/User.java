package firstConnection;

/**
 *
 * @author Adam
 */

    //POJO > Plain Old Java Object > Egyszeru jo oreg java osztaly
public class User {

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    private String name;
    private String address;
    
    public User() {
        
    }
    
    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

}
