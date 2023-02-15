package com.kameleoon.quotes.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "quotes")
@Data
public class Quote {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String quoteId;
    private String content;
    private Date dateOfUpdate;
    @Column(nullable = false)
    private String userId;

    @OneToMany(mappedBy = "quoteId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Vote> votes;
}
