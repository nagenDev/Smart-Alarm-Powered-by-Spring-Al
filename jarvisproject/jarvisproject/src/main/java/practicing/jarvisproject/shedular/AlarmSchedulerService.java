//package practicing.jarvisproject.shedular;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.request.async.DeferredResult;
//import practicing.jarvisproject.Alarm.Entity.Entity;
//import practicing.jarvisproject.Alarm.Repository.Repository;
//import practicing.jarvisproject.springAi.voiceConverter.TextToSpeechService;
//
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.util.Map;
//import java.util.Optional;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class AlarmSchedulerService {
//
//
//    private final Repository repository;
//
//    private final TextToSpeechService textToSpeechService;
//
//    // Holds waiting HTTP requests until alarm time arrives
//    private final Map<Long, DeferredResult<byte[]>> waitingRequests = new ConcurrentHashMap<>();
//
//    @Autowired
//    public AlarmSchedulerService(Repository repository,
//                                 TextToSpeechService textToSpeechService) {
//        this.repository = repository;
//        this.textToSpeechService = textToSpeechService;
//    }
//
//    /** Called by controller when frontend wants to wait until alarm time. */
////    public DeferredResult<byte[]> waitForAlarm(Long alarmId) {
////        // Keep connection open max 15 minutes (to avoid 30s timeout)
////        DeferredResult<byte[]> result = new DeferredResult<>(15 * 60 * 1000L);
////
////        Optional<Entity> optional = repository.findById(alarmId);
////        if (optional.isEmpty()) {
////            result.setErrorResult(("Alarm not found: " + alarmId).getBytes());
////            return result;
////        }
////
////        Entity alarm = optional.get();
////        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata")); // IST time
////
////        if (!alarm.getTime().isAfter(now)) {
////            // Time already reached → return MP3 immediately
////            result.setResult(generateMp3(bytesTextFor(alarm)));
////        } else {
////            // Hold the request; Task will complete it when time arrives
////            waitingRequests.put(alarmId, result);
////        }
////        return result;
////    }
//    public DeferredResult<byte[]> waitForAlarm(Long alarmId) {
//        DeferredResult<byte[]> result = new DeferredResult<>(15 * 60 * 1000L);
//
//        Optional<Entity> optional = repository.findById(alarmId);
//        if (optional.isEmpty()) {
//            result.setErrorResult(("Alarm not found: " + alarmId).getBytes());
//            return result;
//        }
//
//        Entity alarm = optional.get();
//        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
//
//        // PROPER TIME ZONE CONVERSION
//        ZonedDateTime utcTime = alarm.getTime().atZone(ZoneId.of("UTC"));
//        ZonedDateTime istTime = utcTime.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
//
//        LocalDateTime alarmTimeIst = istTime.toLocalDateTime();
//
//        long secondsUntilAlarm = java.time.Duration.between(now, alarmTimeIst).getSeconds();
//
//        System.out.println("DEBUG: UTC: " + alarm.getTime() +
//                " → IST: " + alarmTimeIst +
//                " | Now: " + now +
//                " | Diff: " + secondsUntilAlarm + "s");
//
//        if (secondsUntilAlarm <= 2 && secondsUntilAlarm >= -2) {
//            result.setResult(generateMp3(bytesTextFor(alarm)));
//        } else if (secondsUntilAlarm < -2) {
//            result.setErrorResult(("Alarm time has already passed: " + alarmTimeIst).getBytes());
//        } else {
//            waitingRequests.put(alarmId, result);
//        }
//        return result;
//    }
//
//
//    /** Invoked by the scheduled task every second to release due alarms. */
////    public void releaseDueAlarms() {
////        if (waitingRequests.isEmpty()) return;
////
////        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata")); // IST time
////
////        waitingRequests.forEach((alarmId, deferred) -> {
////            repository.findById(alarmId).ifPresent(alarm -> {
////                if (!alarm.getTime().isAfter(now)) {
////                    deferred.setResult(generateMp3(bytesTextFor(alarm)));
////                    waitingRequests.remove(alarmId);
////                    System.out.println("✅ Alarm fired: id=" + alarmId + " at " + alarm.getTime());
////                }
////            });
////        });
////    }
//
//    public void releaseDueAlarms() {
//        if (waitingRequests.isEmpty()) return;
//
//        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
//
//        waitingRequests.forEach((alarmId, deferred) -> {
//            repository.findById(alarmId).ifPresent(alarm -> {
//                // CONVERT database UTC time to IST
//                LocalDateTime alarmTimeUtc = alarm.getTime();
//                LocalDateTime alarmTimeIst = alarmTimeUtc.plusHours(5).plusMinutes(30);
//
//                long secondsUntilAlarm = java.time.Duration.between(now, alarmTimeIst).getSeconds();
//
//                System.out.println("DEBUG: UTC: " + alarmTimeUtc + " → IST: " + alarmTimeIst + " | Now: " + now + " | Diff: " + secondsUntilAlarm + "s");
//
//                if (secondsUntilAlarm <= 2 && secondsUntilAlarm >= -2) {
//                    deferred.setResult(generateMp3(bytesTextFor(alarm)));
//                    waitingRequests.remove(alarmId);
//                    System.out.println("✅ Alarm fired: id=" + alarmId);
//                } else if (secondsUntilAlarm < -2) {
//                    waitingRequests.remove(alarmId);
//                    System.out.println("❌ Skipping PAST alarm: id=" + alarmId);
//                }
//            });
//        });
//    }
//
//
//    private String bytesTextFor(Entity alarm) {
//        String text = alarm.getPromtString();
//        if (text == null || text.isBlank()) {
//            text = "This is your reminder scheduled at " + alarm.getTime();
//        }
//        return text;
//    }
//
//    private byte[] generateMp3(String text) {
//        byte[] body = textToSpeechService.convertTextToVoice(text);
//        if (body == null) {
//            throw new RuntimeException("TTS returned empty audio.");
//        }
//        return body;
//    }
//}




package practicing.jarvisproject.shedular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import practicing.jarvisproject.Alarm.Entity.Entity;
import practicing.jarvisproject.Alarm.Repository.Repository;
import practicing.jarvisproject.mailSender.MailService;
import practicing.jarvisproject.springAi.voiceConverter.TextToSpeechService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AlarmSchedulerService {

    @Autowired
    private MailService mailService;

    private final Repository repository;
    private final TextToSpeechService textToSpeechService;

    // Holds waiting HTTP requests until alarm time arrives
    private final Map<Long, DeferredResult<byte[]>> waitingRequests = new ConcurrentHashMap<>();

    @Autowired
    public AlarmSchedulerService(Repository repository,
                                 TextToSpeechService textToSpeechService) {
        this.repository = repository;
        this.textToSpeechService = textToSpeechService;
    }

    /**
     * Called by controller when frontend wants to wait until alarm time.
     */
    public DeferredResult<byte[]> waitForAlarm(Long alarmId) {
        DeferredResult<byte[]> result = new DeferredResult<>(15 * 60 * 1000L); // 15 min timeout

        Optional<Entity> optional = repository.findById(alarmId);
        if (optional.isEmpty()) {
            result.setErrorResult(("Alarm not found: " + alarmId).getBytes());
            return result;
        }

        Entity alarm = optional.get();

        // Convert stored UTC time to IST
        ZonedDateTime alarmUtc = alarm.getTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime alarmIst = alarmUtc.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

        ZonedDateTime nowIst = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        long secondsUntilAlarm = Duration.between(nowIst, alarmIst).getSeconds();

        System.out.println("DEBUG: UTC: " + alarm.getTime() +
                " → IST: " + alarmIst.toLocalDateTime() +
                " | Now IST: " + nowIst.toLocalDateTime() +
                " | Diff: " + secondsUntilAlarm + "s");

        // Send confirmation mail when alarm is scheduled
        mailService.sendEmail(
                "nagendrayounger@gmail.com",
                "Alarm Scheduled",
                "Your alarm is scheduled at " + alarmIst + " (" + alarm.getPromtString() + ")"
        );


        if (secondsUntilAlarm <= 15 && secondsUntilAlarm >= -15) {
            // Time reached → return MP3 immediately
            result.setResult(generateMp3(bytesTextFor(alarm)));
        } else if (secondsUntilAlarm < -15) {
            // Already passed
            result.setErrorResult(("Alarm time has already passed: " + alarmIst.toLocalDateTime()).getBytes());
        } else {
            // Hold the request until scheduled task triggers
            waitingRequests.put(alarmId, result);
        }

        return result;
    }

    /**
     * Invoked by the scheduled task every second to release due alarms.
     */
    public void releaseDueAlarms() {
        if (waitingRequests.isEmpty()) return;

        ZonedDateTime nowIst = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));

        waitingRequests.forEach((alarmId, deferred) -> {
            repository.findById(alarmId).ifPresent(alarm -> {
                ZonedDateTime alarmUtc = alarm.getTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime alarmIst = alarmUtc.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));

                long secondsUntilAlarm = Duration.between(nowIst, alarmIst).getSeconds();

                System.out.println("DEBUG: Checking alarm id=" + alarmId +
                        " | UTC: " + alarm.getTime() +
                        " | IST: " + alarmIst.toLocalDateTime() +
                        " | Now IST: " + nowIst.toLocalDateTime() +
                        " | Diff: " + secondsUntilAlarm + "s");

                if (secondsUntilAlarm <= 15 && secondsUntilAlarm >= -15) {
                    deferred.setResult(generateMp3(bytesTextFor(alarm)));
                    // Send email at alarm time
                    mailService.sendEmail(
                            "nagendrayounger@gmail.com",
                            "Your alarm is ringing!",
                            "This is a reminder for your scheduled alarm at " + alarmIst +" "+ alarm.getPromtString()
                    );
                    waitingRequests.remove(alarmId);
                    System.out.println("✅ Alarm fired: id=" + alarmId);
                } else if (secondsUntilAlarm < -15) {
                    waitingRequests.remove(alarmId);
                    System.out.println("❌ Skipping past alarm: id=" + alarmId);
                }
            });
        });
    }

    private String bytesTextFor(Entity alarm) {
        String text = alarm.getPromtString();
        if (text == null || text.isBlank()) {
            text = "This is your reminder scheduled at " + alarm.getTime();
        }
        return text;
    }

    private byte[] generateMp3(String text) {
        byte[] body = textToSpeechService.convertTextToVoice(text);
        if (body == null) {
            throw new RuntimeException("TTS returned empty audio.");
        }
        return body;
    }
}
