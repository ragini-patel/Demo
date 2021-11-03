package com.demo.cricket.repositories;

import com.demo.cricket.entities.Player;
import com.demo.cricket.entities.TeamStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatRepository extends MongoRepository<TeamStat, String> {
}
