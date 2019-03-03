package firstConnection;

import java.util.ArrayList;

/**
 *
 * @author Adam
 */
public class FirstConnection {

    public static void main(String[] args) {
        DB db = new DB();
        //db.addUser("Adam", "Budapest");
        //db.showAllUsers();
        //db.showUsersMeta();
        //System.out.println(db.getAllUsers());
        ArrayList<User> users = db.getAllUsers();
        for (User u : users) {
            System.out.println(u.getName());
        }
    }

}
