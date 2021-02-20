package com.sachin.oop.cw.Model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Match implements Serializable {
    private Date playedDate;
    private SportsClub team1;
    private SportsClub team2;
    private  int team1Score;
    private int team2Score;
    private SportsClub winner;

    public Match() {
    }

    public Match(Date playedDate, SportsClub team1, SportsClub team2, int team1Score, int team2Score) {
        this.playedDate = playedDate;
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        setWinner();
    }

    public Date getPlayedDate() {
        return playedDate;
    }

    public void setPlayedDate(Date playedDate) {
        this.playedDate = playedDate;
    }

    public SportsClub getTeam1() {
        return team1;
    }

    public void setTeam1(SportsClub team1) {
        this.team1 = team1;
    }

    public SportsClub getTeam2() {
        return team2;
    }

    public void setTeam2(SportsClub team2) {
        this.team2 = team2;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public SportsClub getWinner() {
        return winner;
    }

    public void setWinner() {
        if(team1Score > team2Score) {
            this.winner = team1;
        }else if(team1Score < team2Score){
            this.winner = team2;
        }else{
            winner = null;
        }
    }

    @Override
    public String toString() {
        return "Match{" +
                "playedDate=" + new SimpleDateFormat("dd-MM-yyyy").format(playedDate) +
                ", team1=" + team1 +
                ", team2=" + team2 +
                ", team1Score=" + team1Score +
                ", team2Score=" + team2Score +
                ", winner=" + winner +
                '}';
    }
}

