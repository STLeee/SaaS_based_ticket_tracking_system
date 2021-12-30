package com.stleee.tts.ticket_service.controller;

import java.time.Instant;
import java.util.List;

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
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseTransfer Post(@RequestHeader("Authorization") String idToken, @RequestBody Ticket newTicket) {
        String stuffType = idToken.split(" ")[1];
        if (stuffType.compareTo("QA") == 0) {
            Ticket ticket = ticketRepository.save(new Ticket(Instant.now().toString(), Ticket.Type.Bug, newTicket.getSummary(), newTicket.getDescription()));
            return new ResponseTransfer(ticket);
        } else {
            return new ResponseTransfer(403, "forbidden");
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseTransfer Put() {
        return new ResponseTransfer(-1, "unknown");
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseTransfer DELETE() {
        return new ResponseTransfer(-1, "unknown");
    }
    
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseTransfer Get(@PathVariable("id") Integer id) {
        return new ResponseTransfer(-1, "unknown");
    }
}
