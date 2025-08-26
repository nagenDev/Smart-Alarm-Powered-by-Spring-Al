//package practicing.jarvisproject.voiceConverter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import practicing.jarvisproject.voiceConverter.TextToSpeechService;
//import practicing.jarvisproject.voiceConverter.VoiceToTextService;
//
//import java.io.File;
//
//@RestController
//@RequestMapping("/api/voiceToVoice")
//public class VoiceToVoiceController {
//
//    @Autowired
//    private VoiceToTextService voiceToTextService;
//
//    @Autowired
//    private TextToSpeechService textToSpeechService;
//
//    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<byte[]> voiceToVoice(@RequestParam("filePath") String filePath) {
//        try {
//            // 1. Voice → Text
//            String transcribedText = voiceToTextService.convertVoiceToText(new File(filePath));
//            System.out.println("🎤 Transcribed: " + transcribedText);
//
//            // 2. Text → AI → Voice
//            byte[] voiceResponse = textToSpeechService.convertTextToVoice(transcribedText);
//
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=response.mp3")
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(voiceResponse);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(("Error: " + e.getMessage()).getBytes());
//        }
//    }
//}


package practicing.jarvisproject.springAi.voiceConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/voiceToVoice")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class VoiceToVoiceController {

    @Autowired
    private VoiceToTextService voiceToText;

    @Autowired
    private TextToSpeechController textToSpeech;

    @PostMapping(value = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponseEntity<byte[]> convertVoiceToVoice(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Convert uploaded voice → text
            String transcribedText = voiceToText.convertVoiceToText(file);

            // 2. Convert that text → voice (AI response included inside TextToSpeech)
            ResponseEntity<byte[]> voiceResponse = textToSpeech.textToVoiceString(transcribedText);

            // 3. Return voice response back to user
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=response.mp3")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(voiceResponse.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(("Error: " + e.getMessage()).getBytes());
        }
    }
}
