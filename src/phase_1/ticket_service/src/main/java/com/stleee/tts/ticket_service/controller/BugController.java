package com.stleee.tts.ticket_service.controller;

import java.util.List;

import com.stleee.tts.ticket_service.model.Ticket;
import com.stleee.tts.ticket_service.repository.TicketRepository;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bug")
public class BugController {

    private final TicketRepository ticketRepository;

    BugController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Ticket> Get() {
        return ticketRepository.findByType(Ticket.Type.Bug);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String Post() {
        return "{}";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String Put() {
        return "{}";
    }
    
    @RequestMapping(method = RequestMethod.DELETE)
    public String DELETE() {
        return "{}";
    }
    
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String Get(@PathVariable("id") Integer id) {
        return "{" + id + "}";
    }
}
