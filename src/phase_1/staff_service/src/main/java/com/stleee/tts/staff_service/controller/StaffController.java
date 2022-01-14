package com.stleee.tts.staff_service.controller;

import java.util.Optional;

import com.stleee.tts.staff_service.model.Staff;
import com.stleee.tts.staff_service.repository.StaffRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseTransfer Get(@PathVariable("id") String id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (staffOptional.isPresent()) {
            Staff staff = staffOptional.get();
            return new ResponseTransfer(staff);
        }
        return new ResponseTransfer(404, "staff " + id + " not found");
    }
}
