package com.kameleoon.quotes.persistence;

import com.kameleoon.quotes.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends CrudRepository<Vote, String> {

    @Query(value = "SELECT v1.quote_id " +
            "FROM votes v1 JOIN (" +
                "SELECT MAX(number_vote) nv, quote_id " +
                "FROM votes GROUP BY quote_id) AS v2 " +
            "ON v1.number_vote = v2.nv AND v1.quote_id = v2.quote_id " +
            "ORDER BY value_vote DESC LIMIT 10", nativeQuery = true)
    List<String> findTopTenVotesByVoteValue();

    @Query(value = "SELECT v1.quote_id " +
            "FROM votes v1 JOIN (" +
                "SELECT MAX(number_vote) nv, quote_id " +
                "FROM votes GROUP BY quote_id) AS v2 " +
            "ON v1.number_vote = v2.nv AND v1.quote_id = v2.quote_id " +
            "ORDER BY value_vote ASC LIMIT 10", nativeQuery = true)
    List<String> findFlopTenVotesByVoteValue();

}
