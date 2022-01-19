package com.stleee.tts.ticket_service.controller;


import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.stleee.tts.ticket_service.model.Ticket;
import com.stleee.tts.ticket_service.repository.TicketRepository;
import com.stleee.tts.staff_service.model.Staff;
import com.stleee.tts.staff_service.model.Token;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/bug")
public class BugController {

    @Autowired
    private TicketRepository ticketRepository;

    private static final Logger LOGGER = LogManager.getLogger(BugController.class);
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Ticket> Get() {
        LOGGER.trace("[GET] bug");
        
        // process
        List<Ticket> tickets = ticketRepository.findByType(Ticket.Type.Bug);
        return tickets;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Ticket Get(@PathVariable("id") String id) {
        LOGGER.trace("[GET] bug: " + id);
        
        // process
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        Ticket ticket = ticketOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found"));
        if (ticket.getType() != Ticket.Type.Bug) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "bug " + id + " not found");
        return ticket;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public Ticket POST(@RequestHeader("Authorization") String bearerToken, @RequestBody Ticket newTicket) {
        LOGGER.trace("[POST] bug");
        
        // verify staff
        Optional<Staff> staffOptional = VerifyStaff(bearerToken);
        Staff.Type staffType = staffOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized")).getType();
        if (staffType != Staff.Type.QA) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");

        // process
        Ticket ticket = ticketRepository.save(new Ticket(Instant.now().toString(), Ticket.Type.Bug, newTicket.getSummary(), newTicket.getDescription()));
        return ticket;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Ticket PUT(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") String id, @RequestBody Ticket newTicket) {
        LOGGER.trace("[PUT] bug: " + id);
        
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
        LOGGER.trace("[DELETE] bug: " + id);

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
        LOGGER.trace("[PATCH] bug: " + id + ", var: " + var);

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
        LOGGER.trace("verify staff: " + bearerToken);
        if (!bearerToken.startsWith("Bearer ")) return Optional.empty();
        String tokenID = bearerToken.split(" ")[1];
        RestTemplate restTemplate = new RestTemplate();
        Token token;
        try {
            token = restTemplate.getForObject("http://staff-service:3000/api/token/{id}", Token.class, tokenID);
        } catch(RestClientResponseException e) {
            LOGGER.error("get token error: " + e);
            if (HttpStatus.valueOf(e.getRawStatusCode()).series() == HttpStatus.Series.CLIENT_ERROR) return Optional.empty();
            else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");
        }
        try {
            Staff staff = restTemplate.getForObject("http://staff-service:3000/api/staff/{id}", Staff.class, token.getUID());
            return Optional.of(staff);
        } catch(RestClientResponseException e) {
            LOGGER.error("get staff error: " + e);
            if (HttpStatus.valueOf(e.getRawStatusCode()).series() == HttpStatus.Series.CLIENT_ERROR) return Optional.empty();
            else throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "internal server error");
        }
    }
}
