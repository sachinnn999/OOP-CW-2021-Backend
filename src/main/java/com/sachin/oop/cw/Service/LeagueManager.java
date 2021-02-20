package com.sachin.oop.cw.Service;

import java.util.*;
import com.sachin.oop.cw.Model.*;

public interface LeagueManager {
    int getRegisteredClubsCount();
    int getPlayedMatchCount();
    List<Object> createClub(int clubType, String name, String manager, String coachName, String location, String instituteName, String division);
    List<Object> deleteClub(SportsClub sportsClub);
    List<Object> updatePoints();
    List<ArrayList<SportsClub>> leagueTable(int option);
    List<Object> addPlayedMatch(Date playedDate, SportsClub team1, SportsClub team2, int team1Score, int team2Score);
    List<Match> getAllMatches();
    List<Match> getAllMatches(Date date);
    List<Object> saveToFile();
    List<Object> loadFile();
    boolean validateClubName(String name, int teamType);
}
