package utility;

import business.DataProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import model.AccountDTO;
import model.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author M.nia
 * this is a class for writing accounts and customers which are not valid
 * @see com.fasterxml.jackson.core
 */

public class WriteInJson {
    private static final Logger logger = LoggerFactory.getLogger(WriteInJson.class);

    /**
     * this is the method to write accounts and customers which are not valid in a json file
     * @param wrongAccountList a list of invalid accounts
     * @param wrongCustomerList a list of invalid customers
     */

    public static void writeInJson(List<AccountDTO> wrongAccountList, List<CustomerDTO> wrongCustomerList) {
        logger.debug("start writeInJson method");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("error.json");

            ObjectNode rootNode = objectMapper.createObjectNode();
            rootNode.putPOJO("wrongAccountList", wrongAccountList);
            rootNode.putPOJO("wrongCustomerList", wrongCustomerList);
        try {

            objectMapper.writeValue(file, rootNode);
            logger.debug("JSON file created successfully: " + file.getAbsolutePath());


        } catch (IOException e) {

            logger.error("Failed to create JSON file: " + e.getMessage());
        }
    }
}