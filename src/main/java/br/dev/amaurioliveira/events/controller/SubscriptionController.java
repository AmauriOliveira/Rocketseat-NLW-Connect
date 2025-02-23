package br.dev.amaurioliveira.events.controller;

import br.dev.amaurioliveira.events.dto.ErrorMessage;
import br.dev.amaurioliveira.events.dto.SubscriptionResponse;
import br.dev.amaurioliveira.events.exception.EventNotFoundException;
import br.dev.amaurioliveira.events.exception.SubscriptionConflictException;
import br.dev.amaurioliveira.events.exception.UserIndicatorNotFoundException;
import br.dev.amaurioliveira.events.model.User;
import br.dev.amaurioliveira.events.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName, @RequestBody User subscriber, @PathVariable(required = false) Integer userId) {
        try {
            SubscriptionResponse subscription = subscriptionService.createNewSubscription(prettyName, subscriber, userId);
            if (subscription != null) {
                return ResponseEntity.ok().body(subscription);
            }
        } catch (EventNotFoundException | UserIndicatorNotFoundException exception) {
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
        catch (SubscriptionConflictException exception) {
            return ResponseEntity.status(409).body(new ErrorMessage(exception.getMessage()));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?> generateRankingByEvent(@PathVariable String prettyName) {
        try {
            return ResponseEntity.ok(subscriptionService.getSubscriptionRankingItems(prettyName).subList(0, 3));
        } catch (EventNotFoundException exception) {
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
    }

    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> generateRankingByEventAndUser(@PathVariable String prettyName, @PathVariable Integer userId) {
        try {
            return ResponseEntity.ok( subscriptionService.getSubscriptionRankingByUser(prettyName, userId));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
        }
    }
}
