package com.stleee.tts.staff_service.repository;

import com.stleee.tts.staff_service.model.Token;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {
}
