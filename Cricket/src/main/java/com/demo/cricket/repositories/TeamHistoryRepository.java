package com.demo.cricket.repositories;

import com.demo.cricket.entities.TeamHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamHistoryRepository extends MongoRepository<TeamHistory, String> {
}
