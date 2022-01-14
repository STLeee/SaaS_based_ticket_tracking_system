package com.stleee.tts.staff_service.repository;

import java.util.List;

import com.stleee.tts.staff_service.model.Staff;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StaffRepository extends MongoRepository<Staff, String> {
}
