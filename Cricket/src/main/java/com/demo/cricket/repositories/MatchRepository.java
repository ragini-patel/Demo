package com.demo.cricket.repositories;

import com.demo.cricket.entities.Match;
import com.demo.cricket.entities.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
}
