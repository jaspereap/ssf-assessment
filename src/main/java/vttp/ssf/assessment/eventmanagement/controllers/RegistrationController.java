package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.models.Registration;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping
public class RegistrationController {
    @Autowired
    private DatabaseService dataSvc;

    // TODO: Task 6
	@GetMapping(path = "/events/register/{eventId}")
	public String getRegisteration(@PathVariable String eventId, Model model) {
        Event retrievedEvent = dataSvc.getRecord(eventId);
        model.addAttribute("event", retrievedEvent);

        model.addAttribute("registration", new Registration());
		return "eventregister";
	}

    // TODO: Task 7
    @PostMapping(path = "/registration/register")
    public String processRegistration() {

        return "SuccessRegistration";
    }
}
