package business;

import model.AccountDTO;
import model.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.RepositoryActions;
import utility.CSVReader;
import utility.WriteInJson;


import java.util.*;

/**
 * @author  is M.nia
 * this class gets data from files and classifies them into 5 Lists
 */

public class DataProcessor {


    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    List<AccountDTO> wrongAccountList = new ArrayList<>();
    List<AccountDTO> correctAccountList = new ArrayList<>();
    //Set<AccountDTO> correctAccountList = new HashSet<>();
    List<CustomerDTO> wrongCustomerList = new ArrayList<>();
    List<CustomerDTO> correctCustomerList = new ArrayList<>();
    List<AccountDTO> matchedAccountList = new ArrayList<>();




    /**
     * Processes account and customer data by reading from CSV files, validating the data,
     * matching accounts with customers, and performing database insertions.
     */
    public void getData() {

        logger.debug("Start getData method !!!");
        //1- Read CSV File
        List<AccountDTO> accountDTOList = CSVReader.accountCSVReader();
        List<CustomerDTO> customerDTOList = CSVReader.customerCSVReader();

        logger.info("Read two CSV files accountDTOList.size = {}  , customerDtoList.size {}",
                accountDTOList.size(),customerDTOList.size());

        //2- Validate data
        //Validate account
        logger.debug("Start validate account list !!!");

        for (AccountDTO accountDTO : accountDTOList) {
            String accountValidationMsg = accountValidation(accountDTO);
            if (accountValidationMsg.length() > 0) {
                accountDTO.setMsg(accountValidationMsg + " " + new Date());
                wrongAccountList.add(accountDTO);
            } else {
                correctAccountList.add(accountDTO);
            }
        }

        logger.debug("wrongAccountList.size is {} , correctAccountList.size{}",wrongAccountList.size()
                ,correctAccountList.size());

        //Validate customer
        logger.debug("Start validate customer list !!!");

        for (CustomerDTO customerDTO : customerDTOList) {
            String customerValidationMsg = customerValidation(customerDTO);
            if (customerValidationMsg.length() > 0) {
                customerDTO.setMsg(customerValidationMsg + " " + new Date());
                wrongCustomerList.add(customerDTO);
            } else {
                correctCustomerList.add(customerDTO);
            }
        }
        logger.debug("wrongCustomerList.size is {} , correctCustomerList.size{}",wrongCustomerList.size()
                ,correctCustomerList.size());

        //Match account with customers
        boolean hasAccount = false;
        for (CustomerDTO customerDTO : correctCustomerList) {
            hasAccount = false;
            for (AccountDTO accountDTO : correctAccountList) {
                if (customerDTO.getCustomerId()==(accountDTO.getAccountCustomerId())) {
                    //customer has valid account
                    matchedAccountList.add(accountDTO);
                    hasAccount = true;
                }
            }
            if (!hasAccount) {
                customerDTO.setMsg(" this customer does not have account! ");
                wrongCustomerList.add(customerDTO);
            }
        }
        correctCustomerList.removeAll(wrongCustomerList);

        logger.debug("matchedAccountList size is= {}",matchedAccountList.size());
        logger.debug("correctCustomerList size is= {}",correctCustomerList.size());
        //compare matched and correct accounts
        for (AccountDTO accountDTO:correctAccountList){
            if (!matchedAccountList.contains(accountDTO)){
                accountDTO.setMsg(" this account does not have any customer ");
                wrongAccountList.add(accountDTO);
            }

        }



        //3- insert correct into DB

        RepositoryActions.insertDBCustomerBatch(correctCustomerList);



            RepositoryActions.insertDBAccountBatch(matchedAccountList);



        //4- insert incorrect data into json file
       WriteInJson.writeInJson(wrongAccountList,wrongCustomerList);
       logger.info(" count of wrong account {} / count of wrong customers {}  are saved in \"error.json\" file ",
               wrongAccountList.size(),wrongCustomerList.size());
    }

    /**
     * Validates the given account details in the AccountDTO object
     * @param accountDTO object containing account details to be validated
     * @return a validation message as a String.The message contains error descriptions for all the invalid fields
     */


    public String accountValidation(AccountDTO accountDTO) {

        String msg = "";
        if (accountDTO.getAccountNumber() == null || accountDTO.getAccountNumber().isBlank()) {
            msg += " null account number ";
        }
        if (accountDTO.getAccountNumber() != null && accountDTO.getAccountNumber().length() != 22) {
            msg += " invalid account number " + accountDTO.getAccountNumber();
        }


        if (accountDTO.getAccountType() == null || accountDTO.getAccountType().isBlank()) {
            msg += " null account type ";
        }
        if (accountDTO.getAccountType() != null) {
            int accountType = Integer.parseInt(accountDTO.getAccountType());
            if (!(accountType == 1 || accountType == 2 || accountType == 3)) {
                msg += " invalid account type " + accountDTO.getAccountType();
            }


            if (accountDTO.getAccountBalance() == null || accountDTO.getAccountBalance().isBlank()) {
                msg += " null account balance ";
            }
            if (accountDTO.getAccountLimit() == null || accountDTO.getAccountLimit().isBlank()) {
                msg += " null account limit ";
            }
            if (accountDTO.getAccountBalance() != null && accountDTO.getAccountLimit() != null) {

                int accountBalance = Integer.parseInt(accountDTO.getAccountBalance());


                int accountLimit = Integer.parseInt(accountDTO.getAccountLimit());


                if (accountBalance > accountLimit) {
                    msg += " invalid account balance " + accountDTO.getAccountBalance();
                }
            }

            if (accountDTO.getAccountOpenDate() == null || accountDTO.getAccountOpenDate().isBlank()) {
                msg += " null account open date ";
            }
            if (accountDTO.getAccountCustomerId() ==0) {
                msg += " null account customer id ";
            }
        }

        logger.trace("Validate msg is: {} ->for accountDto.number is {}",msg,accountDTO.getAccountNumber());
        return msg;
    }

    /**
     * Validates the given customer details in the CustomerDTO object
     * @param customerDTO object containing customer details to be validated
     * @return a validation message as a String.The message contains error descriptions for all the invalid fields
     */

    public String customerValidation(CustomerDTO customerDTO) {

        String message = "";
        if (customerDTO.getCustomerNationalId() == null || customerDTO.getCustomerNationalId().isBlank()) {
            message += " customer national id is null ";
        }
        if (customerDTO.getCustomerNationalId() != null && !customerDTO.getCustomerNationalId().matches("[0-9]{10}")) {
            message += " invalid national id " + customerDTO.getCustomerNationalId();
        }

        if (customerDTO.getCustomerBirthDate() == null || customerDTO.getCustomerBirthDate().isBlank()){
            message += " customer birth date is null ";
    }
        if (customerDTO.getCustomerBirthDate()!=null){
            int customerBirthDate = Integer.parseInt(customerDTO.getCustomerBirthDate());
            if (customerBirthDate < 1995) {
                message += " invalid birth date! " + customerDTO.getCustomerBirthDate();
            }
        }
        if (customerDTO.getCustomerName()==null || customerDTO.getCustomerName().isBlank()){
            message += " customer name is null ";
        }
        if (customerDTO.getCustomerSurName()==null || customerDTO.getCustomerSurName().isBlank()){
            message += " customer surname is null ";
        }
        if (customerDTO.getCustomerAddress()==null || customerDTO.getCustomerAddress().isBlank()){
            message += " customer address is null ";
        }
        if (customerDTO.getCustomerZipCode()==null || customerDTO.getCustomerZipCode().isBlank()){
            message += " customer zipcode is null ";
        }
        if (customerDTO.getCustomerId()==0){
            message += " customer id is null ";
        }

        return message;
    }



}






