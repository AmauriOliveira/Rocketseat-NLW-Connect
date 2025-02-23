package br.dev.amaurioliveira.events.service;

import br.dev.amaurioliveira.events.dto.SubscriptionResponse;
import br.dev.amaurioliveira.events.exception.EventNotFoundException;
import br.dev.amaurioliveira.events.exception.SubscriptionConflictException;
import br.dev.amaurioliveira.events.exception.UserIndicatorNotFoundException;
import br.dev.amaurioliveira.events.model.Event;
import br.dev.amaurioliveira.events.model.Subscription;
import br.dev.amaurioliveira.events.model.User;
import br.dev.amaurioliveira.events.repository.EventRepository;
import br.dev.amaurioliveira.events.repository.SubscriptionRepository;
import br.dev.amaurioliveira.events.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {
        Event event = eventRepository.findByPrettyName(eventName);

        if (event == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe.");
        }

        User recoveredUser = userRepository.findByEmail(user.getEmail());

        if (recoveredUser == null) {
            recoveredUser = userRepository.save(user);
        }

        User userIndicator = userRepository.findById(userId).orElse(null);

        if (userIndicator == null) {
            throw new UserIndicatorNotFoundException("Usuario " + userId + " indicador não existe.");
        }

        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setSubscriber(recoveredUser);
        subscription.setIndication(userIndicator);

        Subscription temporarySubscription = subscriptionRepository.findByEventAndSubscriber(event, recoveredUser);

        if (temporarySubscription != null) {
            throw new SubscriptionConflictException("Já existe uma inscrição para " + recoveredUser.getName() + " no evento " + event.getTitle());
        }
        Subscription response = subscriptionRepository.save(subscription);
        return new SubscriptionResponse(response.getSubscriptionNumber(), "http://codecraft.com/" + response.getEvent().getPrettyName() + "/" + response.getSubscriber().getId());

    }
}
