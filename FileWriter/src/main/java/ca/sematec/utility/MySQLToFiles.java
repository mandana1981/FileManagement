package ca.sematec.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import ca.sematec.model.Customer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ca.sematec.repository.SingletonConnection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mandana Soleimani Nia
 * MySQLToFiles is to read data from database and register them in a xml file and in a json file.
 */

public class MySQLToFiles {
    private static final Logger logger= LoggerFactory.getLogger(MySQLToFiles.class);

    /**
     * getCustomers connects to database and gets the customers within a joint query that their accountBalance is more than 1000.
     * @return a list of customers
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public  List<Customer> getCustomers() throws SQLException, ClassNotFoundException {
        logger.debug("getCustomers method called !");
        String query = "select c.* from account a join customer c on a.account_customer_id = c.id\n" +
                "         where a.account_balance>1000";
        List<Customer> myRecords = new ArrayList<>();

        Connection connection = SingletonConnection.getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            Customer myRecord = new Customer();
            myRecord.setCustomerId(resultSet.getInt(1));
            myRecord.setCustomerName(resultSet.getString(2));
            myRecord.setCustomerSurName(resultSet.getString(3));
            myRecord.setCustomerAddress(resultSet.getString(4));
            myRecord.setCustomerZipCode(resultSet.getString(5));
            myRecord.setCustomerNationalId(resultSet.getString(6));
            myRecord.setCustomerBirthDate(resultSet.getString(7));
            myRecords.add(myRecord);
        }
        logger.debug("reading data from database is completed and returned {}  records" , myRecords.size());
        return myRecords;
    }

    /**
     * the method writes a list of customers in a json file
     * @param myRecords that is a list of customers
     * @throws IOException
     */

    public void writeCustomerInJson(List<Customer> myRecords) throws IOException {
        logger.debug("writeCustomerInJson method called !");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("register.json");
        objectMapper.writeValue(file, myRecords);
        logger.debug("writeCustomerInJson method completed !");
    }
    /**
     * the method writes a list of customers in a xml file
     * @param myRecords that is a list of customers
     * @throws IOException
     */

    public  void writeCustomerInXml(List<Customer> myRecords) throws IOException {
        logger.debug("writeCustomerInXml method called !");
        Document doc = new Document();
        Element root = new Element("customers");
        doc.setRootElement(root);
        for (Customer customer : myRecords) {
            Element customerElement = new Element("customer");

            Element customerIdElement = new Element("customerId").
                    setText(""+ customer.getCustomerId());
            Element customerNameElement = new Element("customerName").
                    setText(customer.getCustomerName());
            Element customerSurNameElement = new Element("customerSurName").
                    setText(customer.getCustomerSurName());
            Element customerAddressElement = new Element("customerAddress").
                    setText(""+ customer.getCustomerAddress());
            Element customerZipCodeElement = new Element("customerZipCode").
                    setText(customer.getCustomerZipCode());
            Element customerNationalIdElement = new Element("customerNationalId").
                    setText(customer.getCustomerNationalId());
            Element customerBirthDateElement = new Element("customerBirthDate").
                    setText(customer.getCustomerBirthDate());

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

            xmlOutput.output(doc, new FileWriter("register.xml"));
            logger.debug("writeCustomerInXml method completed !");
    }





}
