package com.stleee.tts.staff_service;

import com.stleee.tts.staff_service.repository.StaffRepository;
import com.stleee.tts.staff_service.repository.TokenRepository;
import com.stleee.tts.staff_service.model.Staff;
import com.stleee.tts.staff_service.model.Token;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class StaffLoader implements CommandLineRunner {

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private TokenRepository tokenRepository;

	public static void main(String[] args) {
		SpringApplication.run(StaffLoader.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Staff creation started...");
        staffRepository.save(new Staff(UUID.randomUUID().toString().replaceAll("-", ""), Staff.Type.QA));
        staffRepository.save(new Staff(UUID.randomUUID().toString().replaceAll("-", ""), Staff.Type.RD));
        System.out.println("Staff creation complete...");
        System.out.println("Staff search started...");
        List<Staff> staffs = staffRepository.findAll();
        for (Staff staff:staffs) {
            System.out.println(staff);
        }
        System.out.println("Staff search complete...");
        System.out.println("Token creation started...");
        for (Staff staff:staffs) {
            String token = UUID.randomUUID().toString().replaceAll("-", "") + UUID.randomUUID().toString().replaceAll("-", "");
            tokenRepository.save(new Token(token, staff.getID()));
        }
        System.out.println("Token creation complete...");
        System.out.println("Token search started...");
        List<Token> tokens = tokenRepository.findAll();
        for (Token token:tokens) {
            System.out.println(token);
        }
        System.out.println("Token search complete...");
    }
}