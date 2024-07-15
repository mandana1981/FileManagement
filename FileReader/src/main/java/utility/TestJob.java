package utility;

import business.DataProcessor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A job implementation for the Quartz Scheduler framework.
 */


public class TestJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(TestJob.class);

    /**
     *  Executes the job, which involves logging a message and triggering the data processing workflow.
     * @param jExeCtx the job execution context provided by the Quartz Scheduler framework.
     * @throws JobExecutionException if there is an exception during job execution.
     */
    public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
        System.out.println("TestJob run successfully...");
        logger.info("!!!! Start Test Job !!!!");

        DataProcessor dataProcessor=new DataProcessor();
        dataProcessor.getData();
    }
}
