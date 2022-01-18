package com.stleee.tts.staff_service.controller;

import java.util.Optional;

import com.stleee.tts.staff_service.model.Token;
import com.stleee.tts.staff_service.repository.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Token Get(@PathVariable("id") String id) {
        Optional<Token> tokenOptional = tokenRepository.findById(id);
        return tokenOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "token " + id + " not found"));
    }
}
