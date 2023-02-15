package com.kameleoon.quotes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String voteId;
    @Column(nullable = false)
    private Long valueVote;
    @Column(nullable = false)
    private String quoteId;
    private String userId;
    @Column(nullable = false)
    private Long numberVote;

    public Vote(Long valueVote, String quoteId, String userId, Long numberVote) {
        this.valueVote = valueVote;
        this.quoteId = quoteId;
        this.userId = userId;
        this.numberVote = numberVote;
    }
}
