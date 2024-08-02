package ca.sematec.repository;

import ca.sematec.model.Account;
import ca.sematec.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Mandana Soleimani Nia
 * RepositoryActions is a class to insert data to the database using preparedStatements
 */
public class RepositoryActions {
    private static final Logger logger= LoggerFactory.getLogger(RepositoryActions.class);
    /**
     * inserts a list of Accounts to a table in database called ACCOUNT
     * @param accountList a list of account objects
     * @throws SQLException,ClassNotFoundException
     */


    public static void insertDBAccountBatch(List<Account> accountList) throws SQLException,ClassNotFoundException {
        logger.debug("batch insert to database for account is started for {} accounts", accountList.size());
        String query = "insert into ACCOUNT  (record_number,account_number,account_type,account_customer_id,account_limit," +
                "account_open_date,account_balance) values (?,?,?,?,?,?,?)";


        Connection connection = SingletonConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (Account account : accountList) {
            preparedStatement.setInt(1, account.getRecordNumber());
            preparedStatement.setString(2, account.getAccountNumber());
            preparedStatement.setString(3, account.getAccountType());
            preparedStatement.setInt(4, account.getAccountCustomerId());
            preparedStatement.setString(5, account.getAccountLimit());
            preparedStatement.setString(6, account.getAccountOpenDate());
            preparedStatement.setString(7, account.getAccountBalance());
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();

        preparedStatement.close();
        connection.close();
        logger.info("batch insert to database for account is completed for {} accounts", accountList.size());
    }

    /**
     * inserts a list of Customers to a table in database called CUSTOMER
     * @param customerList a list of customer objects
     * @throws SQLException,ClassNotFoundException
     */

    public static void insertDBCustomerBatch(List<Customer> customerList) throws SQLException,ClassNotFoundException {
        logger.debug("batch insert to database for customer is started for {} customers", customerList.size());
        String query = "insert into CUSTOMER  (id,customer_name,customer_surname,customer_address," +
                "customer_zipcode,customer_nationalId,customer_birthdate) values (?,?,?,?,?,?,?)";


            Connection connection = SingletonConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Customer customer : customerList) {
                preparedStatement.setInt(1, customer.getCustomerId());
                preparedStatement.setString(2, customer.getCustomerName());
                preparedStatement.setString(3, customer.getCustomerSurName());
                preparedStatement.setString(4, customer.getCustomerAddress());
                preparedStatement.setString(5, customer.getCustomerZipCode());
                preparedStatement.setString(6, customer.getCustomerNationalId());
                preparedStatement.setString(7, customer.getCustomerBirthDate());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();


            preparedStatement.close();
            connection.close();
            logger.info("batch insert to database for customer is completed for {} customers", customerList.size());
        }
    }





