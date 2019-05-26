package com.devguide.jfx.model.beans;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 255, nullable = false)
    private String name;

    @OneToOne
    private Skills skills;


}
