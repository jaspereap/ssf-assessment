package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping(path = "/events")
public class EventController {
	@Autowired
	private DatabaseService dataSvc;

	//TODO: Task 5
	@GetMapping(path = "/listing")
	public String displayEvents(Model model) {
		List<Event> eventList = dataSvc.readFile("events.json");
		System.out.println("Event List from controller: " + eventList);
		model.addAttribute("eventList", eventList);
		return "listing";
	}

	@PostMapping(path = "/register")
	public String postRegister(@RequestBody MultiValueMap<String, String> body, Model model) {
		System.out.println("Register post body: " + body);
		Integer eventId = Integer.parseInt(body.getFirst("eventId"));
		String eventName = body.getFirst("eventName");
		Integer eventSize = Integer.parseInt(body.getFirst("eventSize"));
		Long eventDate = Long.parseLong(body.getFirst("eventDate"));
		Integer participants = Integer.parseInt(body.getFirst("participants"));
		model.addAttribute("event", new Event(eventId, eventName, eventSize, eventDate, participants));

		return "eventregister";
	}
}
