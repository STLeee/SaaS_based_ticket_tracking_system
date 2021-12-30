package com.stleee.tts.ticket_service;

import com.stleee.tts.ticket_service.repository.TicketRepository;
import com.stleee.tts.ticket_service.model.Bug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class BugLoader implements CommandLineRunner {

    @Autowired
    private TicketRepository ticketRepository;

	public static void main(String[] args) {
		SpringApplication.run(BugLoader.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Bug creation started...");
        ticketRepository.save(new Bug("bug1", "this is bug 1!"));
        ticketRepository.save(new Bug("bug2", "this is bug 2!"));
        System.out.println("Bug creation complete...");
    }
}
