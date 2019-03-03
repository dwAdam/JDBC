package firstConnection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adam
 */
public class DB {
    
    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:sampleDB;create=true";
//    final String USERNAME = "";
//    final String PASSWORD = "";
    
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;
    ResultSet rs1 = null;
    
    public DB() {
        
        try {
            conn = DriverManager.getConnection(URL/*, USERNAME, PASSWORD*/);
            System.out.println("Connection 1");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection 0");
        }
        
        if(conn != null) {
            try {
                createStatement = conn.createStatement();
                System.out.println("Statement 1");
            } catch (SQLException ex) {
                System.out.println("Statement 0");
                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            dbmd = conn.getMetaData();
            System.out.println("DatabaseMetaData 1");
        } catch (SQLException ex) {
            System.out.println("DatabaseMetaData 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            rs1 = dbmd.getTables(null, "APP", "USERS", null);
            //System.out.println("ResultSet 1");
            if (!rs1.next()) {
                createStatement.execute("CREATE TABLE users(name varchar(20), address varchar(20))");
            }
        } catch (SQLException ex) {
            System.out.println("ResultSet 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //*************************************************************
    
    public void addUser(String name, String address) {
        try {
//            String sql = "INSERT INTO users VALUES ('"+ name +"','"+ address +"')";
//            createStatement.execute(sql);
//            System.out.println("addUser 1");
              String sql = "INSERT INTO users VALUES(?, ?)";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setString(1, name);
              preparedStatement.setString(2, address);
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("addUser 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //*****************************************
    
    public void showAllUsers() {
        String sql = "SELECT * FROM users";  //kuldj nekem vissza minden usert
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            System.out.println("showAllUsers 1");
            while(rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                System.out.println(name + " | " + address);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("showAllUsers 0");
        }
    }
    
    //*************************************************
    
    public void showUsersMeta(){
        String sql = "SELECT * FROM users";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            rs = createStatement.executeQuery(sql);
            System.out.println("showUsersMeta 1");
            rsmd = rs.getMetaData(); //ez a lekerdezesunknek az eredmeny tablajabol veszi ki a MetaDatat
            //a metaadat mindent elmond a tablarol > Az rsmdb-ben mar minden benne van
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + " | ");
            }
        } catch (SQLException ex) {
            System.out.println("showUsersMeta 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //*******************************
    
    public ArrayList<User> getAllUsers() {
        String sql = "SELECT * FROM users";  //kuldj nekem vissza minden usert
        ArrayList<User> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            System.out.println("getAllUsers 1");
            
            users = new ArrayList<>();
            
            while(rs.next()) {
                User actualUser = new User(rs.getString("name"), rs.getString("address"));
                users.add(actualUser);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("getAllUsers 0");
        }
        return users;
    }
    
    //*******************************
    
        public void addUser(User user) {
        try {
//            String sql = "INSERT INTO users VALUES ('"+ name +"','"+ address +"')";
//            createStatement.execute(sql);
//            System.out.println("addUser 1");
              String sql = "INSERT INTO users VALUES(?, ?)";
              PreparedStatement preparedStatement = conn.prepareStatement(sql);
              preparedStatement.setString(1, user.getName());
              preparedStatement.setString(2, user.getAddress());
              preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("addUser(User user) 0");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
