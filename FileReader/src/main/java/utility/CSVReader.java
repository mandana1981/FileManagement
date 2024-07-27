package utility;

import model.Account;
import model.Customer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mandana Soleimani Nia
 * CSVReader is a class to raed data from two CSV files
 * @see CSVParser
 */

public class CSVReader {

    private static final Logger logger = LoggerFactory.getLogger(CSVReader.class);


    /**
     * accountCSVReader reads accounts from the CSV file and map it to an ArrayList
     *@return a list called Account
     * @throws IOException
     */

    public static List<Account> accountCSVReader(String filePath) throws IOException {
        logger.debug("start accountCSVReader method");
        List<Account> accountList = new ArrayList<>();

                Reader reader = Files.newBufferedReader(Paths.get(filePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
            for (CSVRecord csvRecord : csvParser) {
                Account account = new Account();
                account.setRecordNumber(Integer.parseInt(csvRecord.get(0)));
                account.setAccountNumber(csvRecord.get(1));
                account.setAccountType(csvRecord.get(2));
                account.setAccountCustomerId(Integer.parseInt(csvRecord.get(3)));
                account.setAccountLimit(csvRecord.get(4));
                account.setAccountOpenDate(csvRecord.get(5));
                account.setAccountBalance(csvRecord.get(6));
                accountList.add(account);
            }
            logger.debug("accountCSVReader {}  accounts is read " , accountList.size());



        return accountList;
    }



    /**
     * customerCSVReader reads customers from the CSV file and map it to an ArrayList
     * @return a list called Customer
     * @throws IOException
     */


    public static List<Customer> customerCSVReader(String filePath) throws IOException {
        logger.debug("start customerCSVReader method");
        List<Customer> customerList = new ArrayList<>();
        Reader reader = Files.newBufferedReader(Paths.get(filePath));

             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim());
            for (CSVRecord csvRecord : csvParser) {

                Customer customer = new Customer();
                customer.setCustomerId(Integer.parseInt(csvRecord.get(1)));
                customer.setCustomerName(csvRecord.get(2));
                customer.setCustomerSurName(csvRecord.get(3));
                customer.setCustomerAddress(csvRecord.get(4));
                customer.setCustomerZipCode(csvRecord.get(5));
                customer.setCustomerNationalId(csvRecord.get(6));
                customer.setCustomerBirthDate(csvRecord.get(7));
                customerList.add(customer);
            }
            logger.debug("customerCSVReader {}  customers is raed ", customerList.size());


        return customerList;


    }
}


