package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping
public class EventController {
	@Autowired
	private DatabaseService dataSvc;

	//TODO: Task 5
	@GetMapping(path = "/events/listing")
	public String displayEvents(Model model) {
		// List<Event> eventList = dataSvc.readFile("events.json");
		List<Event> eventList = new LinkedList<>();
		for (int i = 0; i < dataSvc.getNumberOfEvents(); i ++) {
			eventList.add(dataSvc.getRecord(String.valueOf(i)));
		}
		model.addAttribute("eventList", eventList);
		return "listing";
	}

}
