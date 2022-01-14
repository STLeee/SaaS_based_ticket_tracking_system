package com.stleee.tts.staff_service.controller;

import java.util.Optional;

import com.stleee.tts.staff_service.model.Token;
import com.stleee.tts.staff_service.repository.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private TokenRepository tokenRepository;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseTransfer Get(@PathVariable("id") String id) {
        Optional<Token> tokenOptional = tokenRepository.findById(id);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            return new ResponseTransfer(token);
        }
        return new ResponseTransfer(404, "token " + id + " not found");
    }
}
