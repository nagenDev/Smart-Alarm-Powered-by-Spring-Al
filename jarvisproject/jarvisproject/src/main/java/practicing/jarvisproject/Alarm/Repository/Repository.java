package practicing.jarvisproject.Alarm.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import practicing.jarvisproject.Alarm.Entity.Entity;
@org.springframework.stereotype.Repository
public interface Repository extends MongoRepository<Entity,Long> {
}
