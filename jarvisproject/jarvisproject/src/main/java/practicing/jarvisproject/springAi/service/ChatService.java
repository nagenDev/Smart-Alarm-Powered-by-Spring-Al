package practicing.jarvisproject.springAi.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    private ChatModel chatModel;
    String prompt = "(talk to me on this topic of alarm text use 2 sentence only):-";
    public String generateResponce(String inputText) {
        String responce = chatModel.call(prompt+" "+inputText);
        return responce;
    }
}
