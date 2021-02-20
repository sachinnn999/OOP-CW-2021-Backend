package com.sachin.oop.cw.Model;

public class SchoolFootballClub extends FootballClub{
    private String schoolName;
    private String division;

    public SchoolFootballClub(String name, String schoolName, String coachName, String division) {
        super(name, coachName);
        this.schoolName = schoolName;
        this.division = division;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "Type = SchoolFootballClubs - " +
                "Name = "+ super.getName() +
                " School Name = " + schoolName  +
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
