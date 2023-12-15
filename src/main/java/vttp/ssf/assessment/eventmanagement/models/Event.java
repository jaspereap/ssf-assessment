package vttp.ssf.assessment.eventmanagement.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Integer eventId;
    private String eventName;
    private Integer eventSize;
    private Long eventDate;
    private Integer participants;

    
    
    public JsonObject toJson() {
        return Json.createObjectBuilder()
        .add("eventId", this.eventId)
        .add("eventName", this.eventName)
        .add("eventSize", this.eventSize)
        .add("eventDate", this.eventDate)
        .add("participants", this.participants)
        .build();
    }
}
