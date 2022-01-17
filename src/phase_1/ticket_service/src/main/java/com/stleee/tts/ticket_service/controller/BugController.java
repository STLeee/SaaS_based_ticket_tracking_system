package com.stleee.tts.ticket_service.controller;


import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.stleee.tts.ticket_service.model.Ticket;
import com.stleee.tts.ticket_service.repository.TicketRepository;
import com.stleee.tts.staff_service.model.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/bug")
public class BugController {

    @Autowired
    private TicketRepository ticketRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Ticket> Get() {
        List<Ticket> tickets = ticketRepository.findByType(Ticket.Type.Bug);
        return tickets;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Ticket Get(@PathVariable("id") String id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        Ticket ticket = ticketOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found"));
        if (ticket.getType() != Ticket.Type.Bug) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found");
        return ticket;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Ticket Post(@RequestHeader("Authorization") String bearerToken, @RequestBody Ticket newTicket) {
        // verify staff
        Optional<Staff> staffOptional = VerifyStaff(bearerToken);
        Staff.Type staffType = staffOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")).getType();
        if (staffType != Staff.Type.QA) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");

        // process
        Ticket ticket = ticketRepository.save(new Ticket(Instant.now().toString(), Ticket.Type.Bug, newTicket.getSummary(), newTicket.getDescription()));
        return ticket;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Ticket Put(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") String id, @RequestBody Ticket newTicket) {
        // verify staff
        Optional<Staff> staffOptional = VerifyStaff(bearerToken);
        Staff.Type staffType = staffOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")).getType();
        if (staffType != Staff.Type.QA) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");

        // process
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        Ticket ticket = ticketOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found"));
        if (ticket.getType() != Ticket.Type.Bug) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found");
        ticket.setSummary(newTicket.getSummary());
        ticket.setDescription(newTicket.getDescription());
        ticket = ticketRepository.save(ticket);
        return ticket;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Ticket DELETE(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") String id) {
        // verify staff
        Optional<Staff> staffOptional = VerifyStaff(bearerToken);
        Staff.Type staffType = staffOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")).getType();
        if (staffType != Staff.Type.QA) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");

        // process
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        Ticket ticket = ticketOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found"));
        if (ticket.getType() != Ticket.Type.Bug) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found");
        ticketRepository.deleteById(id);
        return ticket;
    }
    
    @RequestMapping(value = "/{id}/{var}", method = RequestMethod.PATCH)
    public Ticket PATCH(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") String id, @PathVariable("var") String var, @RequestBody Ticket newTicket) {
        switch (var) {
        case "status":
            // verify staff
            Optional<Staff> staffOptional = VerifyStaff(bearerToken);
            Staff.Type staffType = staffOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")).getType();
            if (staffType != Staff.Type.RD) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");
            
            // process
            Optional<Ticket> ticketOptional = ticketRepository.findById(id);
            Ticket ticket = ticketOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found"));
            if (ticket.getType() != Ticket.Type.Bug) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found");
            ticket.setStatus(newTicket.getStatus());
            ticket = ticketRepository.save(ticket);
            return ticket;
        default:
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not found");
        }
    }

    private Optional<Staff> VerifyStaff(String bearerToken) {
        if (!bearerToken.startsWith("bearer ")) return Optional.empty();
        String token = bearerToken.split(" ")[1];
        RestTemplate restTemplate = new RestTemplate();
        Staff staff = restTemplate.getForObject("http://staff:3000/token/{token}", Staff.class, token);
        return Optional.of(staff);
    }
}
