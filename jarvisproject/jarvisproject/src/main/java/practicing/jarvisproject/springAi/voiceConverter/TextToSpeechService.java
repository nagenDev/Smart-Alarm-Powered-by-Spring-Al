//package practicing.jarvisproject.voiceConverter;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.*;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import practicing.jarvisproject.service.ChatService;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/textToVoice")
//public class TextToSpeech {
//
//    @Autowired
//    private ChatService chatService;
//
//    private static final String VOICE_ID = "JBFqnCBsd6RMkjVDRZzb";
//    private static final String API_KEY = "sk_536ac052d3912a63a9233d3261b5c9fa36a7a756e0eda916";
//
//    @GetMapping(value = "/get", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<byte[]> textToVoiceString(@RequestParam("inputText") String inputText) {
//        try {
//            // 1. Get AI response text
//            String responseTxt = chatService.generateResponce(inputText);
//
//            // 2. Build JSON safely using Map
//            Map<String, Object> requestBody = new HashMap<>();
//            requestBody.put("text", responseTxt);
//            requestBody.put("model_id", "eleven_multilingual_v2");
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonBody = objectMapper.writeValueAsString(requestBody);
//
//            // 3. Call ElevenLabs API
//            RestTemplate restTemplate = new RestTemplate();
//            String url = "https://api.elevenlabs.io/v1/text-to-speech/" + VOICE_ID + "?output_format=mp3_44100_128";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("xi-api-key", API_KEY);
//
//            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
//
//            ResponseEntity<byte[]> apiResponse = restTemplate.exchange(
//                    url,
//                    HttpMethod.POST,
//                    requestEntity,
//                    byte[].class
//            );
//
//            // 4. Return MP3 audio file
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=response.mp3")
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(apiResponse.getBody());
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(("Error: " + e.getMessage()).getBytes());
//        }
//    }
//}


package practicing.jarvisproject.springAi.voiceConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practicing.jarvisproject.springAi.service.ChatService;

import java.util.HashMap;
import java.util.Map;

@Service
public class TextToSpeechService {   // ✅ service, not controller

    @Autowired
    private ChatService chatService;

    private static final String VOICE_ID = "JBFqnCBsd6RMkjVDRZzb";
    private static final String API_KEY = "sk_536ac052d3912a63a9233d3261b5c9fa36a7a756e0eda916";  // hide in env variable later

    public byte[] convertTextToVoice(String inputText) {
        try {
            // 1. Get AI response
            String responseTxt = chatService.generateResponce(inputText);

            // 2. Build JSON safely using Map
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("text", responseTxt);
            requestBody.put("model_id", "eleven_multilingual_v2");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // 3. Call ElevenLabs API
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://api.elevenlabs.io/v1/text-to-speech/" + VOICE_ID + "?output_format=mp3_44100_128";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("xi-api-key", API_KEY);

            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<byte[]> apiResponse = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    byte[].class
            );

            return apiResponse.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error converting text to voice: " + e.getMessage());
        }
    }
}

