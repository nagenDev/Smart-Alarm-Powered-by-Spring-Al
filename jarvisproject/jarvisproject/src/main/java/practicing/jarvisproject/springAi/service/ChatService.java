package practicing.jarvisproject.springAi.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    private ChatModel chatModel;
    String prompt ="Give a clear and helpful response in simple, short form. Avoid long sentences or extra details. Sound like a voice assistant.";

    public String generateResponce(String inputText) {
        String responce = chatModel.call(inputText+" "+prompt);
        return responce;
    }
}
