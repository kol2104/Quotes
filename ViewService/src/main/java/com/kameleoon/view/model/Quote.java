package com.kameleoon.view.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class Quote {
    private String quoteId;
    private String content;
    private Date dateOfUpdate;
    private String userId;

    private Set<Vote> votes;
}
