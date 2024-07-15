package repository;



import utility.PassEncoding;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SingletonConnection {
    static String DB_URL = null;
    static String USER = null;
    static String PASS = null;
    private static Connection connection =null;
    private SingletonConnection() {
    }
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("DB");
        DB_URL = resourceBundle.getString("DB_URL");
        USER = resourceBundle.getString("USER");
        PASS = PassEncoding.decryption(resourceBundle.getString("PASS"));


            connection = DriverManager.getConnection(DB_URL, USER, PASS);

        return connection;
    }
}



















