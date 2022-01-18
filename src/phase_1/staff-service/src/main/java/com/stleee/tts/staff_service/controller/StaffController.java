package com.stleee.tts.staff_service.controller;

import java.util.Optional;

import com.stleee.tts.staff_service.model.Staff;
import com.stleee.tts.staff_service.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Staff Get(@PathVariable("id") String id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        return staffOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "staff " + id + " not found"));
    }
}
