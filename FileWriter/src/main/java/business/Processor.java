package business;

import model.CustomerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.MySQLToFiles;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Mandana Soleimani Nia
 * Processor class is for calling methods to get data from database and write them via two threads in a xml
 * file and a json file.
 */

public class Processor {
    private static final Logger logger = LoggerFactory.getLogger(Processor.class);

    public static void main(String[] args) {
        MySQLToFiles mySQLToFiles = new MySQLToFiles();
        try {
            List<CustomerDTO> customerDTOList = mySQLToFiles.getCustomers();
            Thread thread1 = new Thread() {
                @Override
                public void run() {

                    try {
                        mySQLToFiles.writeCustomerInJson(customerDTOList);
                    } catch (IOException e) {
                        logger.error("could not write in json file " + e.getMessage());
                    }
                }
            };
            thread1.start();
            logger.info("thtead1 started!");
            Thread thread2 = new Thread() {
                @Override
                public void run() {

                    try {
                        mySQLToFiles.writeCustomerInXml(customerDTOList);
                    } catch (IOException e) {
                        logger.error("could not write in xml file " + e.getMessage());
                    }
                }
            };

            thread2.start();
            logger.info("thtead2 started!");


        } catch (SQLException | ClassNotFoundException e) {
            logger.error("could not connect to database " + e.getMessage());

        }

    }
}

