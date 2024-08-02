package ca.sematec.business;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

/**
 * @author Mandana Soleimani Nia
 * Scheduler – the primary API for interacting with the scheduler of the framework
 * Job – an interface to be implemented by components that we wish to have executed
 * JobDetail – used to define instances of Jobs
 * Trigger – a component that determines the schedule upon which a given Job will be performed
 * JobBuilder – used to build JobDetail instances, which define instances of Jobs
 * TriggerBuilder – used to build Trigger
 * -----------------------------------------------------------------------
 * The JobSchedule class is using Job to run the main task of the project.
 * trigger  and cronTrigger are used to show the time to run the job
 * @see  StdSchedulerFactory
 *
 */


public class JobSchedule {
    private static final Logger logger = LoggerFactory.getLogger(JobSchedule.class);
    private static final String ACCOUNT_CSV_FILE_PATH = "E:\\JavaProjects\\semas\\FileManagement\\FileReader\\src\\main\\resources\\CSVFiles\\account.csv";
  private static final String CUSTOMER_CSV_FILE_PATH = "E:\\JavaProjects\\semas\\FileManagement\\FileReader\\src\\main\\resources\\CSVFiles\\customer.csv";

    public static void main(String[] args) {

        try {


            JobDetail job = JobBuilder.newJob(DataProcessorJob.class)
                    .withIdentity("DataProcessorJob")
                    .build();


            //Trigger specifies start time, end time, running period of the job .In this code trigger schedules
            // a task to execute every 120 seconds


//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInSeconds(120)
//                            .repeatForever())
//                    .build();



            //CronTrigger : specifies start time, end time, running period of the job and with "0 0 10 * * ?" the jub
            //will run every day at 10am


            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("crontrigger", "crontriggergroup1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10 * * ?"))
                    .build();

            logger.info("Scheduling job starts.");
            //schedule the job
            SchedulerFactory schFactory = new StdSchedulerFactory();
            Scheduler sch = schFactory.getScheduler();
            sch.start();
            //sch.scheduleJob(job,trigger);
            sch.scheduleJob(job, cronTrigger);

        } catch (SchedulerException e) {
            logger.error("Error occured while scheduling", e.getMessage());
        } catch (ParseException e) {
            logger.error("Error occured while parsScheduling", e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------------------
    /**
     * A job implementation for the Quartz Scheduler framework.
     */
    public static class DataProcessorJob implements Job {
        /**
         *  Executes the job, which involves logging a message and triggering the data processing workflow.
         * @param jExeCtx the job execution context provided by the Quartz Scheduler framework.
         */
        @Override
        public void execute(JobExecutionContext jExeCtx) {
            logger.info("!!!! Start Test Job !!!!");
            DataProcessor dataProcessor = new DataProcessor();
            dataProcessor.getData(ACCOUNT_CSV_FILE_PATH, CUSTOMER_CSV_FILE_PATH);

        }
    }
}