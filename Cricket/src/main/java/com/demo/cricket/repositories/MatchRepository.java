package com.demo.cricket.repositories;

import com.demo.cricket.entities.Match;
import com.demo.cricket.entities.MatchState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatchRepository extends MongoRepository<Match, String> {
    @Query(fields = "{'id':1, 'matchState':1}")
    List<Match> findMatchesByMatchState(MatchState matchState);
}
