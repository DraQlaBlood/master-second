package com.agi.masterUserService.dao;

import com.agi.masterUserService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDao extends MongoRepository<User, UUID> {
    User findByEmail(String email);

}
