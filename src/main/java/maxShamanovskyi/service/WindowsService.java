package maxShamanovskyi.service;

import maxShamanovskyi.job.CheckStatusJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class WindowsService {
    private final String google = "https://www.google.com";
    private final String microsoft = "https://www.microsoft.com";
    private final String apple = "https://www.apple.com";
    private  JobDetail gJob;
    private  JobDetail mJob;
    private  JobDetail aJob;
    private  Trigger gTrigget;
    private  Trigger mTrigget;
    private  Trigger aTrigget;
    private boolean isWorking;
    private Scheduler gSheduler;
    private Scheduler mSheduler;
    private Scheduler aSheduler;

    private void init() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        gSheduler = schedulerFactory.getScheduler();
        mSheduler = schedulerFactory.getScheduler();
        aSheduler = schedulerFactory.getScheduler();

        gJob = JobBuilder.newJob(CheckStatusJob.class)
                .withIdentity("Google", "g")
                .usingJobData("url", google)
                .build();

        mJob = JobBuilder.newJob(CheckStatusJob.class)
                .withIdentity("Microsoft", "m")
                .usingJobData("url", microsoft)
                .build();

        aJob = JobBuilder.newJob(CheckStatusJob.class)
                .withIdentity("Apple", "a")
                .usingJobData("url", apple)
                .build();

        gTrigget = TriggerBuilder.newTrigger().withIdentity("GoogleTrigger", "g")
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInMinutes(2)
                                .repeatForever())
                .usingJobData("url", google)
                .build();

        mTrigget = TriggerBuilder.newTrigger().withIdentity("MicrosoftTrigger", "m")
                .withSchedule(
                        CronScheduleBuilder
                                .cronSchedule("0 15 22 1/2 * ? *"))
                .usingJobData("url", microsoft)
                .build();

        aTrigget = TriggerBuilder.newTrigger().withIdentity("AppleTrigger", "a")
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withIntervalInMinutes(5)
                                .repeatForever())
                .usingJobData("url", apple)
                .build();
    }


    public void startWork() throws SchedulerException {
        isWorking = true;
        init();

        gSheduler.start();
        gSheduler.scheduleJob(gJob, gTrigget);

        mSheduler.start();
        mSheduler.scheduleJob(mJob, mTrigget);

        aSheduler.start();
        aSheduler.scheduleJob(aJob, aTrigget);
    }

    public boolean isWorking() {
        try {
            if(gSheduler.isShutdown() && mSheduler.isShutdown() && aSheduler.isShutdown()){
                isWorking = false;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return isWorking;
    }
}
