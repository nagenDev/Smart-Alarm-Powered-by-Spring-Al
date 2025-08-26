package practicing.jarvisproject.springAi.voiceConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/textToVoice")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class TextToSpeechController {

    @Autowired
    private TextToSpeechService textToSpeechService;

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<byte[]> textToVoiceString(@RequestParam("inputText") String inputText) {
        try {
            byte[] voice = textToSpeechService.convertTextToVoice(inputText);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=response.mp3")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(voice);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(("Error: " + e.getMessage()).getBytes());
        }
    }
}
