package com.sachin.oop.cw.Model;

import java.text.SimpleDateFormat;

public class FootballClub extends SportsClub{
    private String coachName;
    private int wins = 0;
    private int draws = 0;
    private int defeats = 0;
    private int totalReceivedGoals = 0;
    private int totalScoredGoals = 0;
    private int numOfMatchesPlayed = 0;

    public FootballClub() {
        super();
    }

    public FootballClub(String name, String coachName) {
        super(name);
        this.coachName = coachName;
    }

    public FootballClub(String name, String location, String coachName) {
        super(name, location);
        this.coachName = coachName;
    }

    public FootballClub(String name, String location, String manager, String coachName) {
        super(name, location, manager);
        this.coachName = coachName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getDefeats() {
        return defeats;
    }

    public void setDefeats(int defeats) {
        this.defeats = defeats;
    }

    public int getTotalReceivedGoals() {
        return totalReceivedGoals;
    }

    public void setTotalReceivedGoals(int totalReceivedGoals) {
        this.totalReceivedGoals = totalReceivedGoals;
    }

    public int getTotalScoredGoals() {
        return totalScoredGoals;
    }

    public void setTotalScoredGoals(int totalScoredGoals) {
        this.totalScoredGoals = totalScoredGoals;
    }

    public int getNumOfMatchesPlayed() {
        return numOfMatchesPlayed;
    }

    public void setNumOfMatchesPlayed(int numOfMatchesPlayed) {
        this.numOfMatchesPlayed = numOfMatchesPlayed;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public boolean updateStats(String status, int totalReceivedGoals, int totalScoredGoals){
        boolean response = false;
        if (status != null || !status.contentEquals("")) {
            if (status.equalsIgnoreCase("w")) {
                wins++;
            } else if (status.equalsIgnoreCase("L")) {
                defeats++;
            } else if (status.equalsIgnoreCase("D")) {
                draws++;
            }
            this.totalReceivedGoals += totalReceivedGoals;
            this.totalScoredGoals += totalScoredGoals;
            numOfMatchesPlayed++;

            response = true;
        }
        return response;
    }

    @Override
    public String toString() {
        return "Club Type = FootballClub - "  +
                "Name = "+ super.getName() +
                ", Coach Name = " + coachName +
                ", Manager = " + super.getManager() +
                ", Location = " + super.getLocation() +
                ", Ranking = " + super.getRanking() +
                ", Points = " + super.getPoints() +
                ", Joined Date= " + new SimpleDateFormat("dd-MM-yyyy").format(super.getDateJoined()) +
                ", Wins = " + wins +
                ", Draws = " + draws +
                ", Defeats = " + defeats +
                ", Scored Total Goals = " + totalScoredGoals +
                ", Received Total Goals = " + totalReceivedGoals +
                ", Number Of Matches Played = " + numOfMatchesPlayed;
    }
}

