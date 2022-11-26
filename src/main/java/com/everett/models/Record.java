package com.everett.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "records")
public class Record {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "playername")
    private String playerName;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "solvetime", nullable = false)
    private int solveTime;

    @Transient
    private int rank;

    public Record() {
    }

    public Record(String playerName, int score, int solveTime) {
        this.playerName = playerName;
        this.score = score;
        this.solveTime = solveTime;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(int solveTime) {
        this.solveTime = solveTime;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
