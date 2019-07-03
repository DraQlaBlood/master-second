package com.agi.masterUserService.dao;


import com.agi.masterUserService.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GroupDao extends MongoRepository<Group, UUID> {
}
