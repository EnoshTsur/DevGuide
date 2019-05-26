package com.devguide.jfx.model.beans;

import javax.persistence.*;

@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double apollo;
    private double git;
    private double graphql;
    private double grid;
    private double linux;
    private double react;
    private double springBoot;

    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true)
    private User user;

}
