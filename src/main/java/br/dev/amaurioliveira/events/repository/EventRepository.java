package br.dev.amaurioliveira.events.repository;

import br.dev.amaurioliveira.events.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}
