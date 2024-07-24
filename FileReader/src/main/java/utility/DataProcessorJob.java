package utility;

import business.DataProcessor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Mandana Soleimani Nia
 * A job implementation for the Quartz Scheduler framework.
 */


public class DataProcessorJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(DataProcessorJob.class);
    private static final String ACCOUNT_CSV_FILE_PATH = "src/main/resources/CSVFiles/account.csv";
    private static final String CUSTOMER_CSV_FILE_PATH = "src/main/resources/CSVFiles/customer.csv";
    /**
     *  Executes the job, which involves logging a message and triggering the data processing workflow.
     * @param jExeCtx the job execution context provided by the Quartz Scheduler framework.
     */
    @Override
    public void execute(JobExecutionContext jExeCtx)  {

        logger.info("!!!! Start Test Job !!!!");

        DataProcessor dataProcessor=new DataProcessor();
        dataProcessor.getData(ACCOUNT_CSV_FILE_PATH,CUSTOMER_CSV_FILE_PATH);
    }
}
