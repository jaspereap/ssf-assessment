package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
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
    public ModelAndView processRegistration(@Valid @ModelAttribute Registration registration, BindingResult result, @RequestBody MultiValueMap<String, String> form) {
        String eventId = form.getFirst("eventId");
        // System.out.println("Event Id is: " + eventId);
        Event retrievedEvent = dataSvc.getRecord(eventId);
        ModelAndView mav = new ModelAndView();
        mav.addObject("event", retrievedEvent);

        if (result.hasErrors()) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.setViewName("eventregister");
            
            return mav;
        }

        if (!dataSvc.validMobile(registration.getMobile())) {
            FieldError err = new FieldError("registration", "mobile", "Must start with 8 or 9!");
            result.addError(err);
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.setViewName("eventregister");

            return mav;
        }

        if (!dataSvc.validAge(registration.getDob())) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("message", "Must be 21 or older!");
            mav.setViewName("ErrorRegistration");

            return mav;
        }

        if (!dataSvc.validParticipants(eventId, registration.getTickets())) {
            mav.setStatus(HttpStatus.BAD_REQUEST);
            mav.addObject("message", "Your request for tickets exceeded the event size.");
            mav.setViewName("ErrorRegistration");

            return mav;
        }

        dataSvc.incrParticipantsBy(eventId, registration.getTickets());
        mav.setStatus(HttpStatus.ACCEPTED);
        mav.setViewName("SuccessRegistration");
        
        return mav;
    }
}
