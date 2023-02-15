package com.kameleoon.view.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Vote {
    private String voteId;
    private Long valueVote;
    private String quoteId;
    private Long numberVote;
}
