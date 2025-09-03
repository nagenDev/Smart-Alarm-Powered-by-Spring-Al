package practicing.jarvisproject.shedular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlarmSchedulerTask {


    private final AlarmSchedulerService schedulerService;

    public AlarmSchedulerTask(AlarmSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    // Check once per second whether any waiting request is due to be released
    @Scheduled(fixedRate = 1000)
    public void tick() {
        schedulerService.releaseDueAlarms();
    }
}
