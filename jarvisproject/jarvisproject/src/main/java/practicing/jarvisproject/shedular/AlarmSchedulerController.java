package practicing.jarvisproject.shedular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import practicing.jarvisproject.mailSender.MailService;

@RestController
@RequestMapping("/api/scheduler")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AlarmSchedulerController {


    private final AlarmSchedulerService schedulerService;

    @Autowired
    private MailService mailService;

    public AlarmSchedulerController(AlarmSchedulerService schedulerService) {
        this.schedulerService = schedulerService;

    }



    @GetMapping(value = "/alarm/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public DeferredResult<byte[]> waitForAlarmMp3(@PathVariable("id") Long  id) {
        return schedulerService.waitForAlarm(id);
    }
}
