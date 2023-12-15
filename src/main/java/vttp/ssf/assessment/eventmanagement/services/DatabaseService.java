package vttp.ssf.assessment.eventmanagement.services;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
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
    public Long getNumberOfEvents() {
        return redisRepo.getNumberOfEvents();
    }
    public void saveRecord(Event event) {
        redisRepo.saveRecord(event);
    }

    public Event getRecord(String eventId) {
        return redisRepo.getEvent(Integer.parseInt(eventId));
    }

    public boolean validMobile(String mobile) {
        String startDigit = mobile.substring(0, 1);
        if ("8".equals(startDigit) | "9".equals(startDigit)) {
            return true;
        }
        return false;
    }

    public boolean validAge(LocalDate dob) {
        // Calculate age
        Integer age = LocalDate.now().getYear() - dob.getYear();
        if (age >= 21) {
            return true;
        }
        return false;
    }

    public boolean validParticipants(String eventId, Integer tickets) {
        Event event = getRecord(eventId);
        if (event.getParticipants() + tickets <= event.getEventSize()) {
            return true;
        }
        return false;
    }

    public void incrParticipantsBy(String eventId, Integer tickets) {
        Event event = getRecord(eventId);
        Integer currParticipants = event.getParticipants();
        event.setParticipants(currParticipants + tickets);
        // saveRecord(event);
        redisRepo.updateRecord(event.getEventId(), event);
    }
}
