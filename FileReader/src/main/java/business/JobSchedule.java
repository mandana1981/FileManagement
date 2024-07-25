package business;

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
 * The trigger  and cronTrigger are used to show the time to run the job
 * @see  StdSchedulerFactory
 *
 */


public class JobSchedule {
    private static final Logger logger = LoggerFactory.getLogger(JobSchedule.class);
    private static final String ACCOUNT_CSV_FILE_PATH = "E:\\JavaProjects\\semas\\FileManagement\\FileReader\\src\\main\\resources\\CSVFiles\\account.csv";
  private static final String CUSTOMER_CSV_FILE_PATH = "E:\\JavaProjects\\semas\\FileManagement\\FileReader\\src\\main\\resources\\CSVFiles\\customer.csv";

    public static void main(String[] args) {

        try {

            // specify the job' s details..
            JobDetail job = JobBuilder.newJob(DataProcessorJob.class)
                    .withIdentity("DataProcessorJob")
                    .build();

            // specify the running period of the job
            //Trigger : specifies start time, end time, running period of the job schedules a task to execute every 120
            // seconds indefinitely.

//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInSeconds(120)
//                            .repeatForever())
//                    .build();


            // CronTrigger the job to run on the every 20 seconds
            //CronTrigger : specifies start time, end time, running period of the job according to Unix cron expression.
            /*"10" - This represents the minute of the hour when the task should be executed. In this case, it's the 10th
            minute of every hour.
            "*" (first asterisk) - This represents the hour of the day. The asterisk means "every hour," so the task will
            be executed at the 10th minute of every hour.
            "*" (second asterisk) - This represents the day of the month. The asterisk means "every day," so the task will
            be executed at the 10th minute of every hour,
            every day of the month.
            "*" (third asterisk) - This represents the month of the year. The asterisk means "every month," so the task will
            be executed at the 10th minute of every hour, every day, every month.
            "*" (fourth asterisk) - This represents the day of the week. The asterisk means "every day of the week," so the
            task will be executed at the 10th minute of every hour,
            every day, every month, regardless of the day of the week.
            In summary, the cron expression "10 * * * *" means that the task will be executed at the 10th minute of every
            hour,every day, every month, and every day of the week.*/

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("crontrigger", "crontriggergroup1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10,22 * * ?"))
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