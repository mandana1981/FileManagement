package repository;

import model.AccountDTO;
import model.CustomerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author is M.nia
 * RepositoryActions is a class to insert data to the database using preparedStatements
 */
public class RepositoryActions {
    /**
     * inserts a list of AccountDTOs to a table in database called ACCOUNT
     * @param accountDTOList a list of account objects
     */


    public static void insertDBAccountBatch(List<AccountDTO> accountDTOList) {
        String query = "insert into ACCOUNT  (id,account_number,account_type,account_customer_id,account_limit," +
                "account_open_date,account_balance) values (?,?,?,?,?,?,?)";

        try {
            Connection connection = SingletonConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (AccountDTO accountDTO : accountDTOList) {
                preparedStatement.setInt(1,accountDTO.getRecordNumber());
                preparedStatement.setString(2, accountDTO.getAccountNumber());
                preparedStatement.setString(3, accountDTO.getAccountType());
                preparedStatement.setInt(4, accountDTO.getAccountCustomerId());
                preparedStatement.setString(5, accountDTO.getAccountLimit());
                preparedStatement.setString(6, accountDTO.getAccountOpenDate());
                preparedStatement.setString(7, accountDTO.getAccountBalance());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * inserts a list of CustomerDTOs to a table in database called CUSTOMER
     * @param customerDTOList a list of customer objects
     */

    public static void insertDBCustomerBatch(List<CustomerDTO> customerDTOList) {
        String query = "insert into CUSTOMER  (id,customer_name,customer_surname,customer_address," +
                "customer_zipcode,customer_nationalId,customer_birthdate) values (?,?,?,?,?,?,?)";

        try {
            Connection connection = SingletonConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (CustomerDTO customerDTO : customerDTOList) {
                preparedStatement.setInt(1, customerDTO.getCustomerId());
                preparedStatement.setString(2, customerDTO.getCustomerName());
                preparedStatement.setString(3, customerDTO.getCustomerSurName());
                preparedStatement.setString(4, customerDTO.getCustomerAddress());
                preparedStatement.setString(5, customerDTO.getCustomerZipCode());
                preparedStatement.setString(6, customerDTO.getCustomerNationalId());
                preparedStatement.setString(7, customerDTO.getCustomerBirthDate());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();


            preparedStatement.close();
            connection.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}




