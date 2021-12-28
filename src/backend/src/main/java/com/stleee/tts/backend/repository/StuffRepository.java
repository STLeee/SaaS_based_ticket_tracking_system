package com.stleee.tts.backend.repository;

import com.stleee.tts.backend.model.Stuff;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StuffRepository extends MongoRepository<Stuff, String> {
}
