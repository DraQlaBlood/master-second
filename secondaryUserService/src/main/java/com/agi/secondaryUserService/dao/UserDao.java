package com.agi.secondaryUserService.dao;


import com.agi.secondaryUserService.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDao extends MongoRepository<User, UUID> {
}
