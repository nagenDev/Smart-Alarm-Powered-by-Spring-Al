//package practicing.jarvisproject.Alarm.Entity;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.Id;
//
//import java.lang.annotation.Documented;
//import java.time.Instant;
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//
//public class Entity {
//
//
//   @Id
//    private Long  id;
//    private String promtString;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private LocalDateTime time;;
//
//
//}
//
//
//
package practicing.jarvisproject.Alarm.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document; // ← ADD THIS
import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection = "alarms") // ← ADD THIS ANNOTATION
public class Entity {
    @Id
    private Long id;
    private String promtString;

  //  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
    private LocalDateTime time;
}