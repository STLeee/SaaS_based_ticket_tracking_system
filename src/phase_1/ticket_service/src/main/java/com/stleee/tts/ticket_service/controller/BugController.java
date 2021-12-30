package com.stleee.tts.ticket_service.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.stleee.tts.ticket_service.model.Ticket;
import com.stleee.tts.ticket_service.repository.TicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bug")
public class BugController {

    @Autowired
    private TicketRepository ticketRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseTransfer Get() {
        List<Ticket> tickets = ticketRepository.findByType(Ticket.Type.Bug);
        return new ResponseTransfer(tickets);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseTransfer Get(@PathVariable("id") String id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            if (ticket.getType() == Ticket.Type.Bug) {
                return new ResponseTransfer(ticket);
            }
        }
        return new ResponseTransfer(404, "bug " + id + " not found");
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseTransfer Post(@RequestHeader("Authorization") String idToken, @RequestBody Ticket newTicket) {
        String stuffType = idToken.split(" ")[1];
        if (stuffType.compareTo("QA") == 0) {
            Ticket ticket = ticketRepository.save(new Ticket(Instant.now().toString(), Ticket.Type.Bug, newTicket.getSummary(), newTicket.getDescription()));
            return new ResponseTransfer(ticket);
        }
        return new ResponseTransfer(403, "forbidden");
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseTransfer Put(@RequestHeader("Authorization") String idToken, @PathVariable("id") String id, @RequestBody Ticket newTicket) {
        String stuffType = idToken.split(" ")[1];
        if (stuffType.compareTo("QA") == 0) {
            Optional<Ticket> ticketOptional = ticketRepository.findById(id);
            if (ticketOptional.isPresent()) {
                Ticket ticket = ticketOptional.get();
                if (ticket.getType() == Ticket.Type.Bug) {
                    ticket.setSummary(newTicket.getSummary());
                    ticket.setDescription(newTicket.getDescription());
                    ticket = ticketRepository.save(ticket);
                    return new ResponseTransfer(ticket);
                }
            }
            return new ResponseTransfer(404, "bug " + id + " not found");
        }
        return new ResponseTransfer(403, "forbidden");
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseTransfer DELETE(@RequestHeader("Authorization") String idToken, @PathVariable("id") String id) {
        String stuffType = idToken.split(" ")[1];
        if (stuffType.compareTo("QA") == 0) {
            Optional<Ticket> ticketOptional = ticketRepository.findById(id);
            if (ticketOptional.isPresent()) {
                ticketRepository.deleteById(id);
                return new ResponseTransfer("delete bug success");
            }
            return new ResponseTransfer(404, "bug " + id + " not found");
        }
        return new ResponseTransfer(403, "forbidden");
    }
}
