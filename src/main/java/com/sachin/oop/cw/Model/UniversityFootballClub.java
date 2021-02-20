package com.sachin.oop.cw.Model;

public class UniversityFootballClub extends FootballClub {
    private String universityName;
    private String division;

    public UniversityFootballClub(String name, String universityName, String coachName, String division) {
        super(name, coachName);
        this.universityName = universityName;
        this.division = division;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "Type = UniversityFootballClub - " +
                "Name = "+ super.getName() +
                ", University Name = " + universityName +
                ", Coach Name = " + super.getCoachName() +
                ", Division = " + division +
                ", Ranking = " + super.getRanking() +
                ", Points = " + super.getPoints() +
                ", Joined Date= " + super.getDateJoined() +
                ", Wins = " + super.getWins() +
                ", Draws = " + super.getDraws() +
                ", Defeats = " + super.getDefeats() +
                ", Scored Total Goals = " + super.getTotalScoredGoals() +
                ", Received Total Goals = " + super.getTotalReceivedGoals() +
                ", Number Of Matches Played = " + super.getNumOfMatchesPlayed();

    }
}
