package br.dev.amaurioliveira.events.repository;

import br.dev.amaurioliveira.events.model.Event;
import br.dev.amaurioliveira.events.model.Subscription;
import br.dev.amaurioliveira.events.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event event, User user);
}
