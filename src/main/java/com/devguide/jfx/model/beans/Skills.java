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

    public Skills() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getApollo() {
        return apollo;
    }

    public void setApollo(double apollo) {
        this.apollo = apollo;
    }

    public double getGit() {
        return git;
    }

    public void setGit(double git) {
        this.git = git;
    }

    public double getGraphql() {
        return graphql;
    }

    public void setGraphql(double graphql) {
        this.graphql = graphql;
    }

    public double getGrid() {
        return grid;
    }

    public void setGrid(double grid) {
        this.grid = grid;
    }

    public double getLinux() {
        return linux;
    }

    public void setLinux(double linux) {
        this.linux = linux;
    }

    public double getReact() {
        return react;
    }

    public void setReact(double react) {
        this.react = react;
    }

    public double getSpringBoot() {
        return springBoot;
    }

    public void setSpringBoot(double springBoot) {
        this.springBoot = springBoot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
