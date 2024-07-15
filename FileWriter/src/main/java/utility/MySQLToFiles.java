package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.AccountDTO;
import model.CustomerDTO;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import repository.SingletonConnection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLToFiles {


//    public  List<AccountDTO> getAccounts() {
//        String query = "SELECT  * FROM ACCOUNT";
//        List<AccountDTO> myRecords = new ArrayList<>();
//        try {
//            Connection connection = SingletonConnection.getConnection();
//
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                AccountDTO myRecord = new AccountDTO();
//
//                myRecord.setAccountNumber(resultSet.getString(1));
//                myRecord.setAccountType(resultSet.getString(2));
//                myRecord.setAccountCustomerId(resultSet.getInt(3));
//                myRecord.setAccountLimit(resultSet.getString(4));
//                myRecord.setAccountOpenDate(resultSet.getString(5));
//                myRecord.setAccountBalance(resultSet.getString(6));
//                myRecords.add(myRecord);
//            }
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return myRecords;
//
//    }

    public  List<AccountDTO> getAccounts() {
        String query = "SELECT  * FROM ACCOUNT";
        List<AccountDTO> myRecords = new ArrayList<>();
        try {
            Connection connection = SingletonConnection.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                AccountDTO myRecord = new AccountDTO();
                myRecord.setRecordNumber(resultSet.getInt(1));
                myRecord.setAccountNumber(resultSet.getString(2));
                myRecord.setAccountType(resultSet.getString(3));
                myRecord.setAccountLimit(resultSet.getString(4));
                myRecord.setAccountOpenDate(resultSet.getString(5));
                myRecord.setAccountBalance(resultSet.getString(6));
                myRecord.setAccountCustomerId(resultSet.getInt(7));
                myRecords.add(myRecord);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myRecords;

    }


    public void writeAccountInJson(List<AccountDTO> myRecords) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("register.json");
        try {
            objectMapper.writeValue(file, myRecords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public  void writeAccountInXml(List<AccountDTO> myRecords) {
        Document doc = new Document();
        Element root = new Element("accounts");
        doc.setRootElement(root);
        for (AccountDTO accountDTO : myRecords) {

            Element accountNumberElement = new Element("accountNumber").
                    setText(accountDTO.getAccountNumber());
            Element accountTypeElement = new Element("accountType").
                    setText(accountDTO.getAccountType());
            Element accountCustomerIdElement = new Element("accountCustomerId").
                    setText(""+accountDTO.getAccountCustomerId());
            Element accountLimitElement = new Element("accountLimit").
                    setText(accountDTO.getAccountLimit());
            Element accountOpenDateElement = new Element("accountOpenDate").
                    setText(accountDTO.getAccountOpenDate());
            Element accountBalanceElement = new Element("accountBalance").
                    setText(accountDTO.getAccountBalance());


            root.addContent(accountNumberElement);
            root.addContent(accountTypeElement);
            root.addContent(accountCustomerIdElement);
            root.addContent(accountLimitElement);
            root.addContent(accountOpenDateElement);
            root.addContent(accountBalanceElement);

        }

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());

        try {
            xmlOutput.output(doc, new FileWriter("register.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /*public  List<CustomerDTO> getCustomers() {
        String query = "SELECT  * FROM CUSTOMERS";
        List<CustomerDTO> myRecords = new ArrayList<>();
        try {
            Connection connection = SingletonConnection.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                CustomerDTO myRecord = new CustomerDTO();
                myRecord.setCustomerId(resultSet.getInt(1));
                myRecord.setCustomerName(resultSet.getString(2));
                myRecord.setCustomerSurName(resultSet.getString(3));
                myRecord.setCustomerAddress(resultSet.getString(4));
                myRecord.setCustomerZipCode(resultSet.getString(5));
                myRecord.setCustomerNationalId(resultSet.getString(6));
                myRecord.setCustomerBirthDate(resultSet.getString(7));
                myRecords.add(myRecord);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myRecords;

    }*/
    public void writeCustomerInJson(List<CustomerDTO> myRecords) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("register.json");
        try {
            objectMapper.writeValue(file, myRecords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public  void writeCustomerInXml(List<CustomerDTO> myRecords) {
        Document doc = new Document();
        Element root = new Element("customers");
        doc.setRootElement(root);
        for (CustomerDTO customerDTO : myRecords) {
            Element customerElement = new Element("customer");

            Element customerIdElement = new Element("customerId").
                    setText(""+customerDTO.getCustomerId());
            Element customerNameElement = new Element("customerName").
                    setText(customerDTO.getCustomerName());
            Element customerSurNameElement = new Element("customerSurName").
                    setText(customerDTO.getCustomerSurName());
            Element customerAddressElement = new Element("customerAddress").
                    setText(""+customerDTO.getCustomerAddress());
            Element customerZipCodeElement = new Element("customerZipCode").
                    setText(customerDTO.getCustomerZipCode());
            Element customerNationalIdElement = new Element("customerNationalId").
                    setText(customerDTO.getCustomerNationalId());
            Element customerBirthDateElement = new Element("customerBirthDate").
                    setText(customerDTO.getCustomerBirthDate());

            customerElement.addContent(customerIdElement);
            customerElement.addContent(customerNameElement);
            customerElement.addContent(customerSurNameElement);
            customerElement.addContent(customerAddressElement);
            customerElement.addContent(customerZipCodeElement);
            customerElement.addContent(customerNationalIdElement);
            customerElement.addContent(customerBirthDateElement);

            root.addContent(customerElement);


        }

        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());

        try {
            xmlOutput.output(doc, new FileWriter("register.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public  List<CustomerDTO> getCustomers() {
        String query = "select c.* from account a join customer c on a.account_customer_id = c.id\n" +
                "         where a.account_balance>1000";
        List<CustomerDTO> myRecords = new ArrayList<>();
        try {
            Connection connection = SingletonConnection.getConnection();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                CustomerDTO myRecord = new CustomerDTO();
                myRecord.setCustomerId(resultSet.getInt(1));
                myRecord.setCustomerName(resultSet.getString(2));
                myRecord.setCustomerSurName(resultSet.getString(3));
                myRecord.setCustomerAddress(resultSet.getString(4));
                myRecord.setCustomerZipCode(resultSet.getString(5));
                myRecord.setCustomerNationalId(resultSet.getString(6));
                myRecord.setCustomerBirthDate(resultSet.getString(7));
                myRecords.add(myRecord);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return myRecords;

    }


}
