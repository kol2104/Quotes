package com.kameleoon.quotes.persistence;

import com.kameleoon.quotes.model.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuoteRepository extends CrudRepository<Quote, String> {

    List<Quote> findAllByUserId(String userId);
}
