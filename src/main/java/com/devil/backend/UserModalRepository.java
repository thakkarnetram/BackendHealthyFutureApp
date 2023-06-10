package com.devil.backend;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserModalRepository extends MongoRepository<UserModal,String> {
    List<UserModal> findAllByEmailId(String emailId);
}
