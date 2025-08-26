package practicing.jarvisproject.springAi.voiceConverter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

//public class VoiceToTextService {
//    public static void main(String[] args) {
//        String url = "https://api.elevenlabs.io/v1/speech-to-text";
//        String apiKey = "sk_536ac052d3912a63a9233d3261b5c9fa36a7a756e0eda916";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        File file = new File("C:/Users/ngkv2/Downloads/Standard recording 10.mp3"); // your file path
//        FileSystemResource resource = new FileSystemResource(file);
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", resource);
//        body.add("model_id", "scribe_v1");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("xi-api-key", apiKey);
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//
//        System.out.println("Status: " + response.getStatusCode());
//        System.out.println("Response: " + response.getBody());
//    }
//}

//
//@Service
//public class VoiceToTextService {
//
//    private static final String STT_URL = "https://api.elevenlabs.io/v1/speech-to-text";
//    private static final String API_KEY = "sk_536ac052d3912a63a9233d3261b5c9fa36a7a756e0eda916";
//
//    public String convertVoiceToText(File file) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        FileSystemResource resource = new FileSystemResource(file);
//
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("file", resource);
//        body.add("model_id", "scribe_v1");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("xi-api-key", API_KEY);
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(STT_URL, HttpMethod.POST, requestEntity, String.class);
//
//        return response.getBody();
//    }
//}


//package practicing.jarvisproject.voiceConverter;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;

@Service
public class VoiceToTextService {

    private static final String API_KEY = "sk_536ac052d3912a63a9233d3261b5c9fa36a7a756e0eda916";
    private static final String URL = "https://api.elevenlabs.io/v1/speech-to-text";

    public String convertVoiceToText(MultipartFile file) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Wrap the uploaded file into a resource
        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);
        body.add("model_id", "scribe_v1");

        HttpHeaders headers = new HttpHeaders();
        headers.set("xi-api-key", API_KEY);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to transcribe. Status: " + response.getStatusCode());
        }
    }
}
