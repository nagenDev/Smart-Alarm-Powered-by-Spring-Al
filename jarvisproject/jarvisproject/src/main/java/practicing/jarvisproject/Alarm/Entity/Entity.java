package practicing.jarvisproject.Alarm.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;

@Setter
@Getter

public class Entity {


   @Id
    private Long id;
    private String promtString;
    private LocalDateTime time;


}
