package vttp.ssf.assessment.eventmanagement.repositories;

import java.io.StringReader;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {
	@Autowired @Qualifier("redisTemplate")
	private RedisTemplate<String, String> template;

	// TODO: Task 2
	public void saveRecord(Event event) {
		Integer eventId = event.getEventId();
		JsonObject body = event.toJson();

		template.opsForValue().set(eventId.toString(), body.toString());
	}

	// TODO: Task 3
	public Integer getNumberOfEvents() {
		Set<String> keys = template.keys("*");
		return keys.size();
	}

	// TODO: Task 4
	public Event getEvent(Integer index) {
		String result = template.opsForValue().get(index.toString());
		JsonReader reader = Json.createReader(new StringReader(result));
		JsonObject object = reader.readObject();

		Integer eventid = object.getInt("eventId");
		String eventName = object.getString("eventName");
		Integer eventSize = object.getInt("eventSize");
		Long eventDate = Long.parseLong(object.getJsonNumber("eventDate").toString());
		Integer participants = object.getInt("participants");

		return new Event(eventid, eventName, eventSize, eventDate, participants);
	}
}
