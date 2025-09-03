package practicing.jarvisproject.Alarm.Controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import practicing.jarvisproject.Alarm.Entity.Entity;
import practicing.jarvisproject.Alarm.Service.Service;

import java.util.List;

@RestController
@RequestMapping("/alarm")
public class Controller {

    @Autowired
    private Service service;

    //get
    @GetMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public List<Entity> getAll() {
        return service.getData();
    }
    @GetMapping("/get/id/{id}")
    public Entity getByIds(@PathVariable("id") Long  id) {
        return service.getDataById(id).orElse(null);
    }
    //post
    @PostMapping("/post")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public boolean postData(@RequestBody Entity entity){
        service.saveData(entity);
        return true;
    }

    //delete
    @DeleteMapping("/id/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public boolean deleteData(@PathVariable Long  id) {
        service.deleteData(id);
        return true;
    }

    //update
    @PutMapping("/put/id/{id}")
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public boolean putMapping(@PathVariable Long id,@RequestBody Entity entity) {
        service.updateData(entity, id);
        return true;
    }


}
