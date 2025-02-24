package br.dev.amaurioliveira.events.service;

import br.dev.amaurioliveira.events.model.Event;
import br.dev.amaurioliveira.events.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event addNewEvent(Event event) {
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));

        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();
    }

    public Event getByPrettyName(String prettyName) {
        return eventRepository.findByPrettyName(prettyName);
    }
}
