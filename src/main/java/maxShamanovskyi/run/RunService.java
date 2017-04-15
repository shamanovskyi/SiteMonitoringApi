package maxShamanovskyi.run;

import maxShamanovskyi.service.WindowsService;
import org.quartz.SchedulerException;

public class RunService {
    public static void main(String[] args) throws SchedulerException {
        WindowsService windowsService = new WindowsService();
        windowsService.startWork();
    }

}
