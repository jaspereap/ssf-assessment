package vttp.ssf.assessment.eventmanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@SpringBootApplication
public class EventmanagementApplication implements CommandLineRunner {

	@Autowired @Qualifier("redisTemplate")
	RedisTemplate<String, String> template;
	@Value("${spring.redis.host}")
	String redisHost;
	@Value("${spring.redis.port}")
	String redisPort;

	@Autowired
	DatabaseService dataSvc;


	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}

	// TODO: Task 1
	@Override
	public void run(String... args) throws Exception {
		System.out.printf("\tRedis running on %s@%s\n", redisHost, redisPort);
		System.out.println("\tRedis template instance: " + template);

		System.out.println("\tTesting Task 1");
		List<Event> events = dataSvc.readFile("events.json");

		for (Event event : events) {
			dataSvc.saveRecord(event);
		}

	}
	

}
