package practicing.jarvisproject.mailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    public MailService mailService;

    @PostMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public void testSender(@RequestBody String str) {
        mailService.sendEmail("nagendrayounger@gmail.com","Spring-Ai-project by nagendra",str);
    }
}
