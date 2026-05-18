package com.classrep.model;

public class Candidate {
    private final int id;
    private final String name;
    private final String slogan;
    private int voteCount;

    public Candidate(int id, String name, String slogan) {
        this.id = id;
        this.name = name;
        this.slogan = slogan;
        this.voteCount = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlogan() {
        return slogan;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void addVote() {
        voteCount++;
    }
}
