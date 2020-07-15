/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 *
 */
public class DBConnection {
    
    private static final String databaseName = "U05vuW";
    private static final String DB_URL = "jdbc:mysql://3.227.166.251/" + databaseName; 
    private static final String username = "U05vuW";
    private static final String password = "53688622326";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn;
    
    //Establishes connection to the database
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        
        Class.forName(driver);
        conn = DriverManager.getConnection(DB_URL, username, password);
        System.out.println("Connection established successfully!");
        
    }
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException, Exception {
    
        return conn;
        
    }
    
    //Terminates the connection to the database 
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception {
    
        conn.close();
        System.out.println("Connection has been terminated!");
        
    }
    
    //
}
