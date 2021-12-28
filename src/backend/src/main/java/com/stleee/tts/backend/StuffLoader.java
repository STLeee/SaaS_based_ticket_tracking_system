package com.stleee.tts.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.stleee.tts.backend.model.Stuff;
import com.stleee.tts.backend.repository.StuffRepository;

@SpringBootApplication
@EnableMongoRepositories
public class StuffLoader implements CommandLineRunner {

    @Autowired
    private StuffRepository stuffRepository;

	public static void main(String[] args) {
		SpringApplication.run(StuffLoader.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Stuff creation started...");
        stuffRepository.save(new Stuff(Stuff.Type.QA));
        stuffRepository.save(new Stuff(Stuff.Type.RD));
        System.out.println("Stuff creation complete...");
    }
}
