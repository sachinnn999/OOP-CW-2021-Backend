package com.sachin.oop.cw.Service;

import java.io.*;
import java.util.*;
import java.util.Date;

import com.sachin.oop.cw.Model.*;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PremierLeagueManager implements LeagueManager {

    @Value("${UEFA.filesPath:./src/main/resources/static/}")
    private String filesPath;

    private List<SportsClub> sportsClubs = new ArrayList<>();
    private List<Match> matches = new ArrayList<>();

    @Override
    public int getRegisteredClubsCount(){
        return sportsClubs.size();
    }

    @Override
    public int getPlayedMatchCount() {
        return matches.size();
    }

    //this method will create clubs and add it to the list
    //returning status and messages as an object list
    @Override
    public List<Object> createClub(int clubType, String name, String manager, String coachName, String location, String instituteName, String division) {
        List<Object> response = new ArrayList<>();
        SportsClub sc = null;
        boolean result = false;
        String msg = "";
        if(clubType == 1){
            sc = new FootballClub(name, location, manager, coachName);
        }else if(clubType == 2){
            sc = new UniversityFootballClub(name, instituteName, coachName, division);
        }else if(clubType == 3){
            sc = new SchoolFootballClub(name, instituteName, coachName, division);
        }
        if(sc != null) {
            sportsClubs.add(sc);
            result = true;
            msg = String.format("%s %s Club has Created Successfully!", sc.getName(), sc.getClass().getSimpleName());
        }
        response.add(0,result);
        response.add(1,msg);
        return response;
    }

    //this method will remove the given club from the list
    //returning status and messages as an object list
    @Override
    public List<Object> deleteClub(SportsClub sportsClub) {
        boolean result = false;
        String msg = "";
        List<Object> results = new ArrayList<>();
        try {
            sportsClubs.remove(sportsClub);
            msg = "Deletion Successful";
            result = true;
        }catch (Exception e){
            msg = "Exception occurred while deleting ";
            result =false;
        }finally {
            results.add(0,result);
            results.add(1,msg);
            return results;
        }
    }

    @Override
    public List<Object> updatePoints() {
        List<Object> response = new ArrayList<>();
        response.add(0, false); //status
        response.add(1,null); //result set
        List<ArrayList<SportsClub>> sortedClubs = new ArrayList<>();
        ArrayList<SportsClub> clubLevel = new ArrayList<>();
        ArrayList<SportsClub> universityLevel = new ArrayList<>();
        ArrayList<SportsClub> schoolLevel = new ArrayList<>();
        for (SportsClub sc:sportsClubs) {
            if (sc instanceof UniversityFootballClub){
                universityLevel.add(sc);
            }else if(sc instanceof SchoolFootballClub){
                schoolLevel.add(sc);
            }else if(sc instanceof FootballClub){
                clubLevel.add(sc);
            }
        }

        Collections.sort(clubLevel, Collections.reverseOrder(new SortByPoints()));
        updateClubRanking(clubLevel);
        Collections.sort(universityLevel, Collections.reverseOrder(new SortByPoints()));
        updateClubRanking(universityLevel);
        Collections.sort(schoolLevel, Collections.reverseOrder(new SortByPoints()));
        updateClubRanking(schoolLevel);
        sortedClubs.add(0,clubLevel);
        sortedClubs.add(1,universityLevel);
        sortedClubs.add(2,schoolLevel);
        response.set(0,true);
        response.set(1,sortedClubs);
        return response;
    }

    @Override
    //process the league table  club type wise and returning lists type wise too
    public List<ArrayList<SportsClub>> leagueTable(int option) {
        List<ArrayList<SportsClub>> results = new ArrayList<>();
        ArrayList<SportsClub> clubLevel = new ArrayList<>();
        ArrayList<SportsClub> universityLevel = new ArrayList<>();
        ArrayList<SportsClub> schoolLevel = new ArrayList<>();
        for (SportsClub sc:sportsClubs) {
            if (sc instanceof UniversityFootballClub){
                universityLevel.add(sc);
            }else if(sc instanceof SchoolFootballClub){
                schoolLevel.add(sc);
            }else if(sc instanceof FootballClub){
                clubLevel.add(sc);
            }
        }
        if(option == 1){
            Collections.sort(clubLevel, Collections.reverseOrder(new SortByPoints()));
            //updateClubRanking(clubLevel);
            Collections.sort(universityLevel, Collections.reverseOrder(new SortByPoints()));
            //updateClubRanking(universityLevel);
            Collections.sort(schoolLevel, Collections.reverseOrder(new SortByPoints()));
            //updateClubRanking(schoolLevel);
        }else if (option == 2){
            Collections.sort(clubLevel, Collections.reverseOrder(new SortByGoals()));
            Collections.sort(universityLevel, Collections.reverseOrder(new SortByGoals()));
            Collections.sort(schoolLevel, Collections.reverseOrder(new SortByGoals()));
        }else if (option == 3){
            Collections.sort(clubLevel, Collections.reverseOrder(new SortByWins()));
            Collections.sort(universityLevel, Collections.reverseOrder(new SortByWins()));
            Collections.sort(schoolLevel, Collections.reverseOrder(new SortByWins()));
        }
        results.add(0,clubLevel);
        results.add(1,universityLevel);
        results.add(2,schoolLevel);
        return results;
    }
    
    //this method is to create an match and adding to the matches list keep a log of played matches all the time
    //need to pass match playedDate,team1,team2 (as SportsClub objects), team1 and team2 scores
    @Override
    public List<Object> addPlayedMatch(Date playedDate, SportsClub team1, SportsClub team2, int team1Score, int team2Score) {
//        System.out.println(team1.toString());
//        System.out.println(team2.toString());
        List<Object> response = new ArrayList<>();
        boolean result = false;
        String msg = "";

        //getting indexes of teams by looping
        int i=0, team1Ind = -99,team2Ind = -99;
        for (SportsClub sc: sportsClubs) {
            if(sc.equals(team1,true)){
                team1Ind = i;
            }else if (sc.equals(team2,true)){
                team2Ind = i;
            }
            i++;
        }
//        System.out.println(sportsClubs.get(team1Ind));
//        System.out.println(sportsClubs.get(team2Ind));
        String statusT1 = "", statusT2 = "";
        //indexes validation
        if(team1Ind != -99 && team2Ind != -99){
            SportsClub t1 = sportsClubs.get(team1Ind);
            SportsClub t2 = sportsClubs.get(team2Ind);
            //Club Points updating Process
            if (team1Score > team2Score){
                t1.setPoints(t1.getPoints() + 3);
                statusT1 = "w";
                statusT2 = "l";
            }else if(team1Score < team2Score){
                t2.setPoints(t2.getPoints() + 3);
                statusT1 = "l";
                statusT2 = "w";
            }else if(team1Score == team2Score){
                t1.setPoints(t1.getPoints() +1);
                t2.setPoints(t2.getPoints() +1);
                statusT1 = "D";
                statusT2 = "d";
            }
            //teams stats updating process
            boolean boolT1 = ((FootballClub) t1).updateStats(statusT1,team2Score,team1Score);
            boolean boolT2 = ((FootballClub) t2).updateStats(statusT2,team1Score,team2Score);
            //team stats validation
            if(boolT1 && boolT2){
                //replacing the updated sportsclubs objects with old objects
                sportsClubs.set(team1Ind,t1);
                sportsClubs.set(team2Ind,t2);
                //creating a match object and adding to the matches list to keep a log
                Match match = new Match(playedDate,team1,team2,team1Score,team2Score);
                matches.add(match);
                //method response creation
                result = true;
                List<Object> res = updatePoints();
                msg = "Match Details Successfully Added";
            }else {
                result = false;
                msg = "An Exception Occurred While Updating Teams Stats! Try Again Later";
            }
        }else {
            result = false;
            msg = "An Exception Occurred! Pls contact Admin!";

        }
        response.add(0,result);
        response.add(1,msg);
        return response;
    }

    @Override
    public List<Match> getAllMatches() {
        List<Match> response = matches;
        Collections.sort(response, Collections.reverseOrder(new SortByDate()));
        return response;
    }

    @Override
    public List<Match> getAllMatches(Date date) {
        List<Match> result = new ArrayList<>();
        for (Match m: matches) {
            if(m.getPlayedDate().compareTo(date) == 0){
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public List<Object> saveToFile() {
        List<Object> response = new ArrayList<>();
        response.add(0, false);
        response.add(1,"");
        try {
            //File file = new File("./src/main/resources/static/Clubs.txt");
            File file = new File(filesPath+"Clubs.txt");
            if(!file.exists()){
                if (file.createNewFile()) {
                    response.set(0,false);
                    response.set(1,"File created: " + file.getName());
                    //System.out.println("File created: " + file.getName());
                }else{
                    response.set(0,false);
                    response.set(1,response.get(1) + "\n\tAn Exception Occurred. Try Again Later");
                    return response;
                }
            }
            FileOutputStream fileOS = new FileOutputStream(file);
            ObjectOutputStream objOS = new ObjectOutputStream(fileOS);
            objOS.writeObject(sportsClubs);
            objOS.flush();
            objOS.close();
            response.set(0,true);
            response.set(1,response.get(1) + "\n\tFile Saved Successfully");
            try {
                //file = new File("./src/main/resources/static/Matches.txt");
                file = new File(filesPath + "Matches.txt");
                if (file.exists()) {
                    fileOS = new FileOutputStream(file);
                    objOS = new ObjectOutputStream(fileOS);
                    objOS.writeObject(matches);
                    objOS.flush();
                    objOS.close();
                }
            }catch (Exception m){

            }
        } catch (Exception e) {
            response.set(0,false);
            response.set(1,response.get(1) + "\n\tAn Exception Occurred. Try Again Later " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<Object> loadFile() {
        List<Object> response = new ArrayList<>();
        response.add(0, false);
        response.add(1,"");
        try {
            //File file = new File("./src/main/resources/static/Clubs.txt");
            File file = new File(filesPath + "Clubs.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            sportsClubs = (ArrayList<SportsClub>) objectInputStream.readObject();
            response.set(0, true);
            response.set(1, "\tFile Successfully Loaded");
            try{
                //file = new File("./src/main/resources/static/Matches.txt");
                file = new File(filesPath + "Matches.txt");
                fileInputStream = new FileInputStream(file);
                objectInputStream = new ObjectInputStream(fileInputStream);
                matches = (ArrayList<Match>) objectInputStream.readObject();
            }catch (Exception m){

            }
        }catch (FileNotFoundException f) {
            response.set(0, false);
            response.set(1, response.get(1) + "\n\tAn Exception Occurred. Source File Not Found");
        }catch(ClassNotFoundException c){
            response.set(0, false);
            response.set(1, response.get(1) + "\n\tAn Exception Occurred. Contact Admin " + c.getMessage());
        }catch (Exception e){
            response.set(0,false);
            response.set(1,response.get(1) + "\n\tAn Exception Occurred. Try Again Later " + e.getMessage());
        }
        return response;
    }

    //this is for clubName and type validation
    //need to pass clubName and type --> clubLevel = 1, uniLevel = 2, schoolLevel = 3
    @Override
    public boolean validateClubName (String name, int teamType){
    	if(name == null || name.contentEquals("")) {
    		return false;
    	}
        for (SportsClub sportsClub : sportsClubs) {
            if(sportsClub.equals(name,teamType)){
                return false;
            }
        }
        return true;
    }

    //this method will be invoked inside leagueTable method
    //using this to update club ranking need to pass descending ordered teams list
    //club type wise ranking will be happening check leagueTable method
    private void updateClubRanking(ArrayList<SportsClub> leagueCatArray) {
        int i = 1;
        for (SportsClub sc: leagueCatArray) {
            sc.setRanking(i);
            i++;
        }
    }
}

class SortByPoints implements Comparator<SportsClub> {

    public int compare(SportsClub a, SportsClub b) {
        double x = a.getPoints(), y = b.getPoints();
        if (x == y) {
            int aDiff = ((FootballClub) a).getTotalScoredGoals() - ((FootballClub) b).getTotalScoredGoals();
            return aDiff;
        } else if (x > y) {
            return 1;
        } else {
            return -1;
        }
    }
}

class SortByGoals implements Comparator<SportsClub> {

    public int compare(SportsClub a, SportsClub b) {
        double x = ((FootballClub) a).getTotalScoredGoals(), y = ((FootballClub) b).getTotalScoredGoals();
        if (x >= y) {
            return 1;
        } else {
            return -1;
        }
    }
}

class SortByWins implements Comparator<SportsClub> {

    public int compare(SportsClub a, SportsClub b) {
        double x = ((FootballClub) a).getWins(), y = ((FootballClub) b).getWins();
        if (x >= y) {
            return 1;
        } else {
            return -1;
        }
    }
}

class SortByDate implements Comparator<Match> {

    public int compare(Match a, Match b) {
        Date x = a.getPlayedDate(), y = b.getPlayedDate();
        if (x.compareTo(y) == 0) {
            return 0;
        } else if (x.compareTo(y) > 0) {
            return 1;
        }else {
            return -1;
        }
    }
}