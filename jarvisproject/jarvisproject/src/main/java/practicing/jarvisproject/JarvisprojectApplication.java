package practicing.jarvisproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class JarvisprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JarvisprojectApplication.class, args);
	}

}
