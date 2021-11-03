package com.demo.cricket.repositories;

import com.demo.cricket.entities.Player;
import com.demo.cricket.entities.PlayerStat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatRepository extends MongoRepository<PlayerStat, String> {
}
