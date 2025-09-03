package practicing.jarvisproject.springAi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import practicing.jarvisproject.springAi.service.ChatService;

@Controller
@RequestMapping("/api/n1/chat")
public class ChatController {


    @Autowired
    private ChatService chatService;
    @GetMapping
    public ResponseEntity<String> generateResponce(@RequestParam(value ="inputText") String inputText) {
     String responceText = chatService.generateResponce(inputText);
        return  ResponseEntity.ok(responceText);
    }

}
