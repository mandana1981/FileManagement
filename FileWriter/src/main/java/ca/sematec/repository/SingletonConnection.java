package ca.sematec.repository;



import ca.sematec.utility.PassEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * @author Mandana Soleimani Nia
 * SingletonConnection is a class to create a database connection
 * and uses ResourceBundle class to manage resources.
 * Use singletone pattern to create just one connection
 */

public class SingletonConnection {
    static String DB_URL = null;
    static String USER = null;
    static String PASS = null;
    private static Connection connection =null;
    private static final Logger logger = LoggerFactory.getLogger(SingletonConnection.class);
    private SingletonConnection() {
    }
    /**
     * read database information from @see DB.properties
     * @return connection
     * @throws SQLException and ClassNotFoundException
     */
    public static Connection getConnection() throws SQLException,ClassNotFoundException {
        logger.debug("start the connection method");
            Class.forName("com.mysql.cj.jdbc.Driver");
            ResourceBundle resourceBundle = ResourceBundle.getBundle("DB");
        DB_URL = resourceBundle.getString("DB_URL");
        USER = resourceBundle.getString("USER");
        PASS = PassEncoding.decryption(resourceBundle.getString("PASS"));
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        logger.debug("connected to DB successfully");
        return connection;
    }
}



















