package business;

import model.AccountDTO;
import model.CustomerDTO;
import utility.MySQLToFiles;

import java.util.List;

public class Processor {
    public static void main(String[] args) {
        MySQLToFiles mySQLToFiles=new MySQLToFiles();
        List<CustomerDTO> customerDTOList=mySQLToFiles.getCustomers();
        Thread thread1= new Thread(){
            @Override
            public void run() {

                mySQLToFiles.writeCustomerInJson(customerDTOList);
            }
        };
        Thread thread2= new Thread() {
            @Override
            public void run() {

                mySQLToFiles.writeCustomerInXml(customerDTOList);
            }
        };
        thread1.start();
        thread2.start();
    }
}
