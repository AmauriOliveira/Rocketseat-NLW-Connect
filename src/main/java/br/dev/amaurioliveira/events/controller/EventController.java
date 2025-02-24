package br.dev.amaurioliveira.events.controller;

import br.dev.amaurioliveira.events.model.Event;
import br.dev.amaurioliveira.events.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event event) {
        return eventService.addNewEvent(event);
    }

    @GetMapping("/events")
    public List<Event> getAllEvent() {
        return eventService.getAllEvents();
    }

    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName) {
        Event event = eventService.getByPrettyName(prettyName);

        if (event != null) {
            return ResponseEntity.ok().body(event);
        }
        return ResponseEntity.notFound().build();
    }
}
