package vttp.ssf.assessment.eventmanagement.services;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {
    @Autowired
    private RedisRepository redisRepo;
    // TODO: Task 1

    public List<Event> readFile(String fileName) {
        List<Event> eventList = new ArrayList<>();
        try (JsonReader reader = Json.createReader(new FileReader(fileName))) {
            JsonArray arr = reader.readArray();
            for (JsonValue val : arr) {
                JsonObject obj = val.asJsonObject();
                Integer eventId = obj.getInt("eventId");
                String eventName = obj.getString("eventName", "Empty");
                Integer eventSize = obj.getInt("eventSize");
                Long eventDate = Long.parseLong(obj.getJsonNumber("eventDate").toString());
                Integer participants = obj.getInt("partcipants", 0);
                Event event = new Event(eventId, eventName, eventSize, eventDate, participants);
                eventList.add(event);
            }
        } catch (Exception e) {}
        return eventList;
    }

    public void saveRecord(Event event) {
        redisRepo.saveRecord(event);
    }

    public Event getRecord(String eventId) {
        return redisRepo.getEvent(Integer.parseInt(eventId));
    }
}
