package com.stleee.tts.ticket_service.repository;

import java.util.List;

import com.stleee.tts.ticket_service.model.Ticket;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByType(Ticket.Type type);
}
