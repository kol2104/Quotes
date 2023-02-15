package com.kameleoon.quotes.service;

import com.kameleoon.quotes.exception.EmptyCollectionQuotesException;
import com.kameleoon.quotes.exception.QuoteNotFoundException;
import com.kameleoon.quotes.exception.UserNotFoundException;
import com.kameleoon.quotes.model.Quote;
import com.kameleoon.quotes.model.Vote;
import com.kameleoon.quotes.persistence.QuoteRepository;
import com.kameleoon.quotes.persistence.VoteRepository;
import com.kameleoon.quotes.rest.UserServiceRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserServiceRestTemplate userServiceRestTemplate;

    public Optional<Quote> getQuoteById(String quoteId) {
        return quoteRepository.findById(quoteId);
    }

    public Quote saveQuote(Quote quote) {
        final long START_VALUE = 0L, START_NUMBER_VOTE = 1L;
        quote.setDateOfUpdate(new Date());
        userServiceRestTemplate.getUserById(quote.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + quote.getUserId()));
        Quote savedQuote = quoteRepository.save(quote);
        voteRepository.save(new Vote(START_VALUE, savedQuote.getQuoteId(), null, START_NUMBER_VOTE));
        return savedQuote;
    }

    public Quote updateQuote(Quote quote) {
        Quote quoteToUpdate = findQuoteById(quote.getQuoteId());

        if (quote.getContent() != null) {
            quoteToUpdate.setContent(quote.getContent());
            quoteToUpdate.setDateOfUpdate(new Date());
        }
        if (quote.getUserId() != null) {
            quoteToUpdate.setUserId(quote.getUserId());
            quoteToUpdate.setDateOfUpdate(new Date());
        }
        return quoteRepository.save(quoteToUpdate);
    }

    public void deleteQuote(String quoteId) {
        quoteRepository.deleteById(quoteId);
    }

    public boolean incrementVote(String quoteId, String userId) {
        Quote quoteToIncrement = findQuoteById(quoteId);
        userServiceRestTemplate.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
        if (isUserVotedEarlier(quoteToIncrement, userId)) {
            return false;
        }
        quoteToIncrement.getVotes()
                .stream()
                .max((vote1, vote2) -> vote1.getNumberVote().compareTo(vote2.getNumberVote()))
                .ifPresent(vote -> voteRepository
                        .save(new Vote(vote.getValueVote() + 1, vote.getQuoteId(),
                                userId,  vote.getNumberVote() + 1)));
        return true;
    }

    private boolean isUserVotedEarlier(Quote quoteToIncrement, String userId) {
        Optional<Vote> optionalVote = quoteToIncrement.getVotes().stream()
                .filter((v) -> userId.equals(v.getUserId())).findFirst();
        if (optionalVote.isPresent()) {
            return true;
        }
        return false;
    }

    public boolean decrementVote(String quoteId, String userId) {
        Quote quoteToDecrement = findQuoteById(quoteId);
        userServiceRestTemplate.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));
        if (isUserVotedEarlier(quoteToDecrement, userId)) {
            return false;
        }
        quoteToDecrement.getVotes()
                .stream()
                .max((vote1, vote2) -> vote1.getNumberVote().compareTo(vote2.getNumberVote()))
                .ifPresent(vote -> voteRepository
                        .save(new Vote(vote.getValueVote() - 1, vote.getQuoteId(),
                                userId,  vote.getNumberVote() + 1)));
        return true;
    }

    public Quote getRandomQuotes() {
        List<Quote> quotes = (List<Quote>) quoteRepository.findAll();
        Random random = new Random();
        if (quotes.size() == 0) {
            return null;
        }
        return quotes.get(random.nextInt(quotes.size()));
    }

    public List<Quote> getTopTenQuotes() {
        List<String> quotesId = voteRepository.findTopTenVotesByVoteValue();
        return findQuoteListById(quotesId);
    }

    public List<Quote> getFlopTenQuotes() {
        List<String> quotesId = voteRepository.findFlopTenVotesByVoteValue();
        return findQuoteListById(quotesId);
    }

    private List<Quote> findQuoteListById(List<String> quotesId) {
        List<Quote> resultList = new ArrayList<>();
        for (String id : quotesId) {
            resultList.add(quoteRepository.findById(id).orElseThrow(() -> new QuoteNotFoundException(id)));
        }
        return resultList;
    }

    public List<Quote> getAllQuotesByUser(String userId) {
        return quoteRepository.findAllByUserId(userId);
    }

    private Quote findQuoteById(String quoteId) {
        String idToFind = (quoteId == null) ? "" : quoteId;
        return quoteRepository
                .findById(idToFind)
                .orElseThrow(() -> new QuoteNotFoundException(idToFind));
    }
}
