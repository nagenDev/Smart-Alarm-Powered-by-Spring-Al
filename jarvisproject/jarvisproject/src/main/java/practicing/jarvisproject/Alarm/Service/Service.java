package practicing.jarvisproject.Alarm.Service;

import org.springframework.beans.factory.annotation.Autowired;
import practicing.jarvisproject.Alarm.Entity.Entity;
import practicing.jarvisproject.Alarm.Repository.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.time.ZoneId;
@org.springframework.stereotype.Service
public class Service {



//    Instant now = LocalDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant(ZoneOffset.ofHoursMinutes(5,30));
//

    @Autowired
    private Repository repository;

    //get
    public List<Entity> getData() {
        return repository.findAll();
    }

    public Optional<Entity> getDataById(Long id) {
        return repository.findById(id);
    }

    //put

    public boolean updateData(Entity entity, Long id) {
        if (repository.existsById(id)) {
            entity.setId(id);
            repository.save(entity);
            return true;
        } else {
            return false;
        }
    }

    //post
    public boolean saveData(Entity entity) {
        repository.save(entity);
        return true;

    }
    //delete
    public boolean deleteData(Long id) {
        repository.deleteById(id);
        return true;
    }


}
