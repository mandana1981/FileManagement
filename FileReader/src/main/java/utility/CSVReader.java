package utility;

import model.AccountDTO;
import model.CustomerDTO;
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
 * @author is M.nia
 * CSVReader is a class to raed data from two CSV files
 * @see CSVParser
 */

public class CSVReader {
    private static final String ACCOUNT_CSV_FILE_PATH = "src/main/resources/CSVFiles/account.csv";
    private static final Logger logger = LoggerFactory.getLogger(WriteInJson.class);

    /**
     * accountCSVReader reads accounts from the CSV file and map it to an ArrayList

     *@return a list called AccountDTO
     */

    public static List<AccountDTO> accountCSVReader() {


        List<AccountDTO> accountDTOList = new ArrayList<>();
        logger.info("reading accountCSV file started");
        try (Reader reader = Files.newBufferedReader(Paths.get(ACCOUNT_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());) {
            for (CSVRecord csvRecord : csvParser) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setRecordNumber(Integer.parseInt(csvRecord.get(0)));
                accountDTO.setAccountNumber(csvRecord.get(1));
                accountDTO.setAccountType(csvRecord.get(2));
                accountDTO.setAccountCustomerId(Integer.parseInt(csvRecord.get(3)));
                accountDTO.setAccountLimit(csvRecord.get(4));
                accountDTO.setAccountOpenDate(csvRecord.get(5));
                accountDTO.setAccountBalance(csvRecord.get(6));
                accountDTOList.add(accountDTO);
                logger.debug("record is read : {} ", accountDTO);

            }
        } catch (IOException e) {
            logger.error("error reading accountCSV file {} ",ACCOUNT_CSV_FILE_PATH);
            throw new RuntimeException(e);
        }
        logger.info("reading accountCSV  completed");
        return accountDTOList;

    }

    private static final String CUSTOMER_CSV_FILE_PATH = "src/main/resources/CSVFiles/customer.csv";

    /**
     * customerCSVReader reads customers from the CSV file and map it to an ArrayList
     * @return a list called CustomerDTO
     */


    public static List<CustomerDTO> customerCSVReader() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        logger.info("reading customerCSV file started ");
        try (Reader reader = Files.newBufferedReader(Paths.get(CUSTOMER_CSV_FILE_PATH));

             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim());) {
            for (CSVRecord csvRecord : csvParser) {

                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setCustomerId(Integer.parseInt(csvRecord.get(1)));
                customerDTO.setCustomerName(csvRecord.get(2));
                customerDTO.setCustomerSurName(csvRecord.get(3));
                customerDTO.setCustomerAddress(csvRecord.get(4));
                customerDTO.setCustomerZipCode(csvRecord.get(5));
                customerDTO.setCustomerNationalId(csvRecord.get(6));
                customerDTO.setCustomerBirthDate(csvRecord.get(7));
                customerDTOList.add(customerDTO);
                logger.debug("record is read {}",customerDTO);
            }
        } catch (IOException e) {
            logger.debug("error reading customerCSV file {} ",CUSTOMER_CSV_FILE_PATH);
            throw new RuntimeException(e);
        }
        logger.info("reading customerCSV file completed");
        return customerDTOList;

    }
}


