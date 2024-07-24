package utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.Account;
import model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Mandana Soleimani Nia
 * this is a class for writing a list in a json file
 * @see com.fasterxml.jackson.core
 */

public class WriteInJson {
    private static final Logger logger = LoggerFactory.getLogger(WriteInJson.class);

    /**
     * this is a method to write a list in a json file
     * @param fileName
     * @param list
     * @throws IOException
     */

    public static void writeInJson(String fileName,List list) throws IOException{

        logger.debug("start writeInJson method");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.putPOJO(fileName,list);
        objectMapper.writeValue(file, rootNode);
            logger.debug("JSON file created successfully: " + file.getAbsolutePath());

    }
    /**
     * this is the method to write accounts and customers which are not valid in a json file
     * @param wrongAccountList a list of invalid accounts
     * @param wrongCustomerList a list of invalid customers
     * @throws IOException
     */
    public static void writeInJson(List<Account> wrongAccountList, List<Customer> wrongCustomerList) throws IOException {
        logger.debug("start writeInJson method for wrong account list and wrong customer list ");
        writeInJson("wrongAccount",wrongAccountList);
        writeInJson("wrongCustomer",wrongCustomerList);

            logger.debug("JSON file for wrong accounts and wrong customers is created successfully");



    }
}