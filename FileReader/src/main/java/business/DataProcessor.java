package business;

import model.Account;
import model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.RepositoryActions;
import utility.CSVReader;
import utility.WriteInJson;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mandana Soleimani Nia
 * this class gets data from accountCSV and customerCSV ,validates the data and records correct data in database and
 * writes the incorrect data in json files
 */

public class DataProcessor {
    private static final String ACCOUNT_CSV_FILE_PATH = "E:\\JavaProjects\\semas\\FileManagement\\FileReader\\src\\main\\resources\\CSVFiles\\account.csv";
    private static final String CUSTOMER_CSV_FILE_PATH = "E:\\JavaProjects\\semas\\FileManagement\\FileReader\\src\\main\\resources\\CSVFiles\\customer.csv";
    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor();
        dataProcessor.getData(ACCOUNT_CSV_FILE_PATH, CUSTOMER_CSV_FILE_PATH);
    }


    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    List<Account> wrongAccountList = new ArrayList<>();
    List<Account> correctAccountList = new ArrayList<>();
    List<Customer> wrongCustomerList = new ArrayList<>();
    List<Customer> correctCustomerList = new ArrayList<>();
    List<Account> matchedAccountList = new ArrayList<>();


    /**
     * Processes account and customer data by reading from CSV files, validating the data,
     * matching accounts with customers, and performing database insertions.
     *
     * @param accountFilePath,customerFilePath
     */
    public void getData(String accountFilePath, String customerFilePath) {

        logger.debug("Start getData method !!!");
        //1- Read CSV File


        try {
            List<Account> accountList = CSVReader.accountCSVReader(accountFilePath);
            List<Customer> customerList = CSVReader.customerCSVReader(customerFilePath);
            logger.info("Read two CSV files accountList.size = {}  , customerDtoList.size {}",
                    accountList.size(), customerList.size());

            //2- Validate data
            //Validate account
            logger.debug("Start validate account list !!!");
            if (accountList != null && !accountList.isEmpty()) {
                for (Account account : accountList) {
                    String accountValidationMsg = accountValidation(account);
                    if (!accountValidationMsg.isEmpty()) {
                        account.setMsg(accountValidationMsg + " " + new Date());
                        wrongAccountList.add(account);
                    } else {
                        correctAccountList.add(account);
                    }
                }
            } else {
                logger.error("accountList is empty");
                return;
            }
            logger.debug("wrongAccountList.size is {} , correctAccountList.size{}", wrongAccountList.size()
                    , correctAccountList.size());

            //Validate customer
            logger.debug("Start validate customer list !!!");
            if (customerList != null && !customerList.isEmpty()) {
                for (Customer customer : customerList) {
                    String customerValidationMsg = customerValidation(customer);
                    if (!customerValidationMsg.isEmpty()) {
                        customer.setMsg(customerValidationMsg + " " + new Date());
                        wrongCustomerList.add(customer);
                    } else {
                        correctCustomerList.add(customer);
                    }
                }
            } else {
                logger.error("customerList is empty");
                return;
            }

            logger.debug("wrongCustomerList.size is {} , correctCustomerList.size{}", wrongCustomerList.size()
                    , correctCustomerList.size());

            //Match account with customers
            boolean hasAccount = false;
            for (Customer customer : correctCustomerList) {
                hasAccount = false;
                for (Account account : correctAccountList) {
                    if (customer.getCustomerId() == (account.getAccountCustomerId())) {
                        //customer has valid account
                        matchedAccountList.add(account);
                        hasAccount = true;
                    }
                }
                if (!hasAccount) {
                    customer.setMsg(" this customer does not have account! ");
                    wrongCustomerList.add(customer);
                }
            }
            correctCustomerList.removeAll(wrongCustomerList);

            logger.debug("matchedAccountList size is= {}", matchedAccountList.size());
            logger.debug("correctCustomerList size is= {}", correctCustomerList.size());
            //compare matched and correct accounts
            for (Account account : correctAccountList) {
                if (!matchedAccountList.contains(account)) {
                    account.setMsg(" this account does not have any customer ");
                    wrongAccountList.add(account);
                }

            }


            //3- insert correct into DB
            try {
                RepositoryActions.insertDBCustomerBatch(correctCustomerList);

            } catch (SQLException | ClassNotFoundException e) {
                logger.error("Error inserting customer into db customerList", e.getMessage());
                WriteInJson.writeInJson("correctCustomer", correctCustomerList);
            }


            try {

                RepositoryActions.insertDBAccountBatch(matchedAccountList);
            } catch (SQLException | ClassNotFoundException e) {
                logger.error("Error inserting account into db accountList", e.getMessage());
                WriteInJson.writeInJson("correctAccount", correctAccountList);
            }


            //4- insert incorrect data into json file
            try {
                WriteInJson.writeInJson(wrongAccountList, wrongCustomerList);
                logger.info(" count of wrong account {} / count of wrong customers {}  are saved in \"error.json\" file ",
                        wrongAccountList.size(), wrongCustomerList.size());
            } catch (IOException e) {
                logger.error("Error writing wrong accounts and customers in json file", e.getMessage());

            }

        } catch (IOException e) {
            //logger.error("Error dealing with the file", e.getMessage());
            logger.error("Error while reading CSV file !!! " + e.getMessage());
        }
        // catch(W ex){

        // }


    }

    /**
     * Validates the given account details in the Account object
     * @param account object containing account details to be validated
     * @return a validation message as a String.The message contains error descriptions for all the invalid fields
     */


    public String accountValidation(Account account) {

        String msg = "";
        if (account.getAccountNumber() == null || account.getAccountNumber().isBlank()) {
            msg += " null account number ";
        }
        if (account.getAccountNumber() != null && account.getAccountNumber().length() != 22) {
            msg += " invalid account number " + account.getAccountNumber();
        }


        if (account.getAccountType() == null || account.getAccountType().isBlank()) {
            msg += " null account type ";
        }
        if (account.getAccountType() != null) {
            int accountType = Integer.parseInt(account.getAccountType());
            if (!(accountType == 1 || accountType == 2 || accountType == 3)) {
                msg += " invalid account type " + account.getAccountType();
            }


            if (account.getAccountBalance() == null || account.getAccountBalance().isBlank()) {
                msg += " null account balance ";
            }
            if (account.getAccountLimit() == null || account.getAccountLimit().isBlank()) {
                msg += " null account limit ";
            }
            if (account.getAccountBalance() != null && account.getAccountLimit() != null) {

                int accountBalance = Integer.parseInt(account.getAccountBalance());


                int accountLimit = Integer.parseInt(account.getAccountLimit());


                if (accountBalance > accountLimit) {
                    msg += " invalid account balance " + account.getAccountBalance();
                }
            }

            if (account.getAccountOpenDate() == null || account.getAccountOpenDate().isBlank()) {
                msg += " null account open date ";
            }
            if (account.getAccountCustomerId() == 0) {
                msg += " null account customer id ";
            }
        }
        if (!msg.isEmpty()) {
            logger.trace("Validate msg is: {} ->for accountDto.number is {}", msg, account.getAccountNumber());
        }
        return msg;
    }

    /**
     * Validates the given customer details in the Customer object
     * @param customer object containing customer details to be validated
     * @return a validation message as a String.The message contains error descriptions for all the invalid fields
     */

    public String customerValidation(Customer customer) {

        String message = "";
        if (customer.getCustomerNationalId() == null || customer.getCustomerNationalId().isBlank()) {
            message += " customer national id is null ";
        }
        if (customer.getCustomerNationalId() != null && !customer.getCustomerNationalId().matches("[0-9]{10}")) {
            message += " invalid national id " + customer.getCustomerNationalId();
        }

        if (customer.getCustomerBirthDate() == null || customer.getCustomerBirthDate().isBlank()) {
            message += " customer birth date is null ";
        }
        if (customer.getCustomerBirthDate() != null) {
            int customerBirthDate = Integer.parseInt(customer.getCustomerBirthDate());
            if (customerBirthDate < 1995) {
                message += " invalid birth date! " + customer.getCustomerBirthDate();
            }
        }
        if (customer.getCustomerName() == null || customer.getCustomerName().isBlank()) {
            message += " customer name is null ";
        }
        if (customer.getCustomerSurName() == null || customer.getCustomerSurName().isBlank()) {
            message += " customer surname is null ";
        }
        if (customer.getCustomerAddress() == null || customer.getCustomerAddress().isBlank()) {
            message += " customer address is null ";
        }
        if (customer.getCustomerZipCode() == null || customer.getCustomerZipCode().isBlank()) {
            message += " customer zipcode is null ";
        }
        if (customer.getCustomerId() == 0) {
            message += " customer id is null ";
        }

        return message;
    }


}






