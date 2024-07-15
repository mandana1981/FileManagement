package business;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import utility.TestJob;

import java.text.ParseException;

/**
 * Scheduler – the primary API for interacting with the scheduler of the framework
 * Job – an interface to be implemented by components that we wish to have executed
 * JobDetail – used to define instances of Jobs
 * Trigger – a component that determines the schedule upon which a given Job will be performed
 * JobBuilder – used to build JobDetail instances, which define instances of Jobs
 * TriggerBuilder – used to build Trigger instances
 */


public class JobSchedule {


    public static void main(String[] args) {

        try {

            // specify the job' s details..
            JobDetail job = JobBuilder.newJob(TestJob.class)
                    .withIdentity("testJob")
                    .build();

            // specify the running period of the job
            //Trigger : specifies start time, end time, running period of the job
//            Trigger trigger = TriggerBuilder.newTrigger()
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                            .withIntervalInSeconds(120)
//                            .repeatForever())
//                    .build();



            // CronTrigger the job to run on the every 20 seconds
            //CronTrigger : specifies start time, end time, running period of the job according to Unix cron expression.

     /*"10" - This represents the minute of the hour when the task should be executed. In this case, it's the 10th minute of every hour.
       "*" (first asterisk) - This represents the hour of the day. The asterisk means "every hour," so the task will be executed at the 10th minute of every hour.
       "*" (second asterisk) - This represents the day of the month. The asterisk means "every day," so the task will be executed at the 10th minute of every hour,
            every day of the month.
       "*" (third asterisk) - This represents the month of the year. The asterisk means "every month," so the task will be executed at the 10th minute of every hour, every day, every month.
       "*" (fourth asterisk) - This represents the day of the week. The asterisk means "every day of the week," so the task will be executed at the 10th minute of every hour,
            every day, every month, regardless of the day of the week.
       In summary, the cron expression "10 * * * *" means that the task will be executed at the 10th minute of every hour, every day, every month, and every day of the week.*/

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("crontrigger","crontriggergroup1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("1 * * * * ?"))
                    .build();


            //schedule the job
            SchedulerFactory schFactory = new StdSchedulerFactory();
            Scheduler sch = schFactory.getScheduler();
            sch.start();
            //sch.scheduleJob(job,trigger);
            sch.scheduleJob(job, cronTrigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
