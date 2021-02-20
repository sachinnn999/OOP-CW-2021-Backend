package com.sachin.oop.cw.Controller;

import com.sachin.oop.cw.Service.LeagueManager;
import com.sachin.oop.cw.Model.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@SpringBootApplication
@ComponentScan({"com.sachin"})
@RestController
@CrossOrigin
@RequestMapping("/leagueManager/UEFA")
public class SysMenu implements CommandLineRunner, Menu {

    @Autowired
    LeagueManager UEFALeagueManager;
    //LeagueManager UEFALeagueManager = new PremierLeagueManager();

    @Value("${UEFA.maxScore-limit}")
    private int maxScore;
    //private static final int maxScore = 15;

    @Value("${UEFA.front-end.url}")
    private String frontendUrl;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\t\t\tUEFA League Manager");
        boolean exit = false;
        while (!exit) {
            exit = runSysMenu();
        }
        System.exit(0);
    }

    @Override
    public boolean runSysMenu() {
        Scanner scanner = new Scanner(System.in);
        char choice = '0';
        boolean exit = false;
        while (!exit) {
            System.out.print("\t...Sys Menu.."
                    + "\nPlease PRESS \n'1' to Add a new Club , \n'2' to Delete a Club, "
                    + "\n'3' to Search a Club, \n'4' to League Table, \n'5' to Add a Match,"
                    + "\n'6' to get Matches Log, \n'7' to Save, \n'8' to Load, \n'q' to Exit: ");
            choice = scanner.next().charAt(0);
            //leagueManager.test();
            switch (choice) {
                case '1':
                    addClubToLeagueCli();
                    restTime(2500);
                    break;
                case '2':
                    deleteClubFromLeagueCli();
                    restTime(2500);
                    break;
                case '3':
                    searchAClubCli();
                    restTime(2500);
                    break;
                case '4':
                    System.out.print("Please PRESS '1' to get Console Based Table , '2' to to get Web Interface Table: ");
                    choice = scanner.next().charAt(0);
                    switch (choice) {
                        case  '1':
                            //console based feature
                            showLeagueTableCli();
                            break;
                        case '2':
                            //calling ng url
                            String url = frontendUrl + "league-table/view";
                            openUrl(url);
                            break;
                        default:
                            System.out.println("\t Invalid Input");
                    }
                    restTime(2500);
                    break;
                case '5':
                    addPlayedMatchToLeagueCli();
                    restTime(2500);
                    break;
                case '6':
                    System.out.print("Please PRESS '1' to get Console Based App , '2' to to get Web Interface: ");
                    choice = scanner.next().charAt(0);
                    switch (choice) {
                        case  '1':
                            //console based feature
                            matchesCli();
                            break;
                        case '2':
                            //calling ng url
                            String url = frontendUrl + "matches/matches-log";
                            openUrl(url);
                            break;
                        default:
                            System.out.println("\t Invalid Input");
                    }
                    restTime(700);
                    break;
                case '7':
                    saveCli();
                    restTime(700);
                    break;
                case '8':
                    loadCli();
                    restTime(400);
                    break;
                case 'q':
                case 'Q':
                    exit = true;
                    break;
            }
        }
        return false;
    }




    @Override
    public void addClubToLeagueCli() {
        Scanner scanner = new Scanner(System.in);
        //instruction message --football club type
        System.out.print("\tPress '1' to Create a Club level Football Club \n" +
                "\tPress '2' to Create a University level Football Club \n" +
                "\tPress '3' to to Create a School level Football Club \n" +
                "Press the relevant key to be continue?: ");
        //getting users inputs
        int teamType;
        //user input exception handling
        try {
            teamType = scanner.nextInt();
        } catch (Exception e) {
            teamType = -99;
        }
        //inputs validation
        if (teamType == 1 || teamType == 2 || teamType == 3) {
            //String clubType = "";
            boolean isValidName;
            //clubType = getClubType(teamType);
            Scanner scanner2 = new Scanner(System.in);
            while (true) {
                try {
                    //Scanner scanner = new Scanner(System.in);
                    String name = "", coachName = "", location = "", manager = "", instituteName = "", division = "";
                    //SportsClub sc = null;
                    //name
                    System.out.println("\t...Please fill the form...");
                    while (true) {
                        System.out.print("Name: ");
                        name = scanner2.nextLine();
                        //name Validation
                        isValidName = UEFALeagueManager.validateClubName(name, teamType);
                        if (!isValidName) {
                            //if name already registered giving an error
                            System.out.println("\tError club already registered.. Try another Name");
                            continue;
                        }
                        break;
                    }
                    //coachName
                    System.out.print("Coach Name: ");
                    coachName = scanner2.nextLine();

                    if (teamType == 1) {
                        //manager
                        System.out.print("Manager: ");
                        manager = scanner2.nextLine();
                        //location
                        System.out.print("Location: ");
                        location = scanner2.nextLine();
                        //sc = new FootballClub(name, location, manager, coachName);
                    } else if (teamType == 2) {
                        //universityName
                        System.out.print("University Name: ");
                        instituteName = scanner2.nextLine();
                        //division
                        System.out.print("Division: ");
                        division = scanner2.nextLine();
                        //sc = new UniversityFootballClub(name, universityName, coachName, division);
                    } else if (teamType == 3) {
                        //schoolName
                        System.out.print("School Name: ");
                        instituteName = scanner2.nextLine();
                        //division
                        System.out.print("Division: ");
                        division = scanner2.nextLine();
                        //sc = new SchoolFootballClub(name, schoolName, coachName, division);
                    }
                    //club creation invoked and getting and response whether its success or not
                    List<Object> result = UEFALeagueManager.createClub(teamType, name, manager, coachName, location, instituteName, division);
                    //display response
                    if ((boolean) result.get(0)) {
                        System.out.println(result.get(1));
                    } else {
                        System.out.println("Something went wrong try again later");
                    }
                    //System.out.println(sc.getClass().getSimpleName());
                    //sportsClubs.add(sc);
                    //System.out.println("\tClub has Successfully Added to the League\n" + sc);
                    break;
                } catch (Exception e) {
                    System.out.println("\tAn Exception Caught... Please Try Again");
                }
            }
        } else {
            System.out.println("\tInvalid input");
        }
    }

    @Override
    public void deleteClubFromLeagueCli() {
        //registered clubs count must be more than 0
        if (UEFALeagueManager.getRegisteredClubsCount() != 0) {
            //initiation
            SportsClub team;

            //getting all registered teams to display
            List<SportsClub> allTeams = getAllTeams(false);

            //getting Opponent1
            team = getTeamFromList(allTeams);
            //calling deletion method
            List<Object> results = UEFALeagueManager.deleteClub(team);
            //displaying response
            System.out.println(results.get(1));
        } else {
            System.out.println("\tLeague is empty");
        }
    }

    @Override
    public void searchAClubCli() {
        //registered clubs count must be more than 0
        if (UEFALeagueManager.getRegisteredClubsCount() != 0) {
            //initiation
            SportsClub sc;

            //getting all registered teams to display
            List<SportsClub> allTeams = getAllTeams(false);

            //getting Opponent1
            sc = getTeamFromList(allTeams);
            System.out.printf("%-10s %-30s %-30s %10s" +
                            " %10s %10s %13s" +
                            " %20s %20s %15s\n",
                    "Ranking", "Name", "Coach Name", "Points",
                    "Wins", "Defeats", "Tot Played",
                    "Tot Received Goals", "Tot Scored Goals", "Joined Date");
            System.out.printf("%2d.%7s %-30s %-30s %10d %10d %10d %13d %20d %20d %15s\n",
                    sc.getRanking(), "", sc.getName(), ((FootballClub) sc).getCoachName(), sc.getPoints(),
                    ((FootballClub) sc).getWins(), ((FootballClub) sc).getDefeats(), ((FootballClub) sc).getNumOfMatchesPlayed(),
                    ((FootballClub) sc).getTotalReceivedGoals(), ((FootballClub) sc).getTotalScoredGoals(),
                    new SimpleDateFormat("dd-MM-yyyy").format(sc.getDateJoined()));
        }
    }

    @Override
    public void showLeagueTableCli() {
        Scanner scanner = new Scanner(System.in);
        //instruction message --football club type
        System.out.print("\tPress '1' to League Table Order by Points  \n" +
                "\tPress '2' to League Table Order by Goals Scored \n" +
                "\tPress '3' to League Table Order by Wins \n" +
                "Press the relevant key to be continue?: ");
        int option;
        //user input exception handling
        try {
            option = scanner.nextInt();
        } catch (Exception e) {
            option = -99;
        }
        String[] leagueCats = {"Club Level", "University Level", "School Level"};
        //inputs validation
        if(option == 1|| option == 2 || option == 3){
            List<ArrayList<SportsClub>> result = UEFALeagueManager.leagueTable(option);
            System.out.println("\tPremier League Table");
            String tableHeader = String.format("%-10s %-30s %-30s %10s" +
                            " %10s %10s %10s %13s" +
                            " %20s %20s %15s\n",
                    "Ranking", "Name", "Coach Name", "Points",
                    "Wins", "Defeats", "Draws","Tot Played",
                    "Tot Received Goals", "Tot Scored Goals", "Joined Date");
            for (int i = 0; i < leagueCats.length; i++) {
                System.out.println("\t\tLeague Category - " + leagueCats[i]);
                if (result.get(i).size() != 0) {
                    System.out.printf(tableHeader);
                    for (SportsClub sc : result.get(i)) {
                        System.out.printf("%2d.%7s %-30s %-30s %10d %10d %10d %10d %13d %20d %20d %15s\n",
                                sc.getRanking(), "", sc.getName(), ((FootballClub) sc).getCoachName(), sc.getPoints(),
                                ((FootballClub) sc).getWins(), ((FootballClub) sc).getDefeats(), ((FootballClub) sc).getDraws(), ((FootballClub) sc).getNumOfMatchesPlayed(),
                                ((FootballClub) sc).getTotalReceivedGoals(), ((FootballClub) sc).getTotalScoredGoals(),
                                new SimpleDateFormat("dd-MM-yyyy").format(sc.getDateJoined()));
                    }
                } else {
                    System.out.printf("%50s\n", "NO RECORDS");
                }
            }
        }else {
            System.out.println("\tInvalid input");
        }


    }


    //this method is used to play a match between two same category teams
    @Override
    public void addPlayedMatchToLeagueCli() {
        //basic validation
        if (UEFALeagueManager.getRegisteredClubsCount() >= 2) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\tPress '1' to Add a Match Manually \n" +
                    "\tPress '2' to Add a Match Automatically \n" +
                    "Press the relevant key to be continue?: ");
            //getting users inputs
            int option;
            //user input exception handling
            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                option = -99;
            }
            //inputs validation
            if(option == 1 || option == 2){
                int team1Score = 0, team2Score = 0;
                Date playedDate = new Date();
                SportsClub team1, team2;
                String strPlayedDate;
                //getting all teams
                //condition - teams categories at least need to have 2 teams
                List<SportsClub> allTeams1 = getAllTeams(true);
                //manual process
                if(option == 1) {
                    //getting Opponent1
                    team1 = getTeamFromList(allTeams1);
                    //removing the selected object temporarily
                    allTeams1.remove(team1);
                    //creating set of teams which opp1 type
                    List<SportsClub> allTeams2 = new ArrayList<>();
                    for (SportsClub sc : allTeams1) {
                        if (sc.getClass().getSimpleName().equals(team1.getClass().getSimpleName())) {
                            allTeams2.add(sc);
                        }
                    }
                    //validation whether result set is null or not to proceed
                    if (allTeams2.size() != 0) {
                        //getting Opponent2
                        team2 = getTeamFromList(allTeams2);
                        while (true) {
                            //getting played Date, input date pattern has to be 'dd-MM-yyyy'
                            System.out.print("Played Date 'dd-MM-yyyy': ");
                            strPlayedDate = scanner.nextLine();
                            //date validation
                            if (Pattern.matches("^([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$", strPlayedDate)) {
                                String[] arrDate = strPlayedDate.split("-");
                                playedDate = createDateObject(Integer.parseInt(arrDate[2]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[0]));
                                break;
                            } else {
                                System.out.println("\tInvalid input! Try Again");
                            }
                        }
                        //getting team1 and team2 scores
                        while (true) {
                            try {
                                System.out.printf("Team 1 (%s) Score: ", team1.getName());
                                team1Score = scanner.nextInt();
                                if(team1Score < 0) {
                                	throw new Exception("Score Cannot be Minus");
                                }
                                System.out.printf("Team 2 (%s) Score: ", team2.getName());
                                team2Score = scanner.nextInt();
                                if(team2Score < 0) {
                                	throw new Exception("Score Cannot be Minus");
                                }
                                break;
                            } catch (Exception e) {
                                System.out.println("\tInvalid input! Try Again");
                            }
                        }

                        List<Object> result = UEFALeagueManager.addPlayedMatch(playedDate, team1, team2, team1Score, team2Score);
                        System.out.println(result.get(1));
                    }else {
                        System.out.println("\t No Matching Teams to Play");
                    }
                //automated process
                }else if (option == 2){
                    System.out.print("Please PRESS \n'1' to get Console Based App , \n'2' to Web Based App: ");
                    char choice = scanner.next().charAt(0);
                    switch (choice){
                        case '1':
                            Map result = automateAddMatch();

                            System.out.println(result.get("responseMsg"));
                            playedDate = (Date) result.get("playedDate");
                            team1 = (SportsClub) result.get("team1");
                            team2 = (SportsClub) result.get("team2");
                            team1Score = (int) result.get("team1Score");
                            team2Score = (int) result.get("team2Score");

                            String columnHeaders = String.format("%-15s %-20s %-30s %-30s %15s %15s","Played Date", "Club Type","Team 01", "Team 02", "Team 01 Score", "Team 02 Score");
                            System.out.println(columnHeaders);
                            String temp = String.format("%-15s %-20s %-30s %-30s %15d %15d", new SimpleDateFormat("dd-MM-yyyy").format(playedDate)
                                    ,team1.getClass().getSimpleName(),team1.getName(),team2.getName(),team1Score,team2Score);
                            System.out.println(temp);
                            break;
                        case '2':
                            //calling URL
                            String url = frontendUrl + "matches/auto-play";
                            openUrl(url);
                            break;
                        default:
                            System.out.println("\t Invalid Input");
                            break;
                    }
                }
            }else {
                System.out.println("\t Invalid Input! Try Again Later!!");
            }
        } else {
            System.out.println("\tRegistered count has to be more than 1 to start a match");
        }
    }

    private Map automateAddMatch() {
        Map response = new LinkedHashMap();

        SportsClub team1, team2;
        Date playedDate;
        int team1Score, team2Score;
        List<SportsClub> allTeams1 = getAllTeams(true);
        Random random = new Random();
        team1 = allTeams1.get(random.nextInt(allTeams1.size()));
        //removing the selected object temporarily
        allTeams1.remove(team1);
        //creating set of teams which opp1 type
        List<SportsClub> allTeams2 = new ArrayList<>();
        for (SportsClub sc : allTeams1) {
            if (sc.getClass().getSimpleName().equals(team1.getClass().getSimpleName())) {
                allTeams2.add(sc);
            }
        }
        //validation whether result set is null or not to proceed
        if (allTeams2.size() != 0) {
            //getting team2
            team2 = allTeams2.get(random.nextInt(allTeams2.size()));
            playedDate = getSysDate();
            team1Score = random.nextInt(maxScore);
            team2Score = random.nextInt(maxScore);
            List<Object> result = UEFALeagueManager.addPlayedMatch(playedDate, team1, team2, team1Score, team2Score);
            response.put("responseMsg",result.get(1));
            response.put("team1",team1);
            response.put("team2",team2);
            response.put("team1Score",team1Score);
            response.put("team2Score",team2Score);
            response.put("playedDate", playedDate);
        }

        return response;
    }

    @Override
    public void matchesCli() {
        if(UEFALeagueManager.getPlayedMatchCount() > 0) {
            Scanner scanner = new Scanner(System.in);
            //instruction message
            System.out.print("\tPress '1' to get Match Log \n" +
                    "\tPress '2' to search  a Match\n" +
                    "Press the relevant key to be continue?: ");
            //getting users inputs
            int option;
            //user input exception handling
            try {
                option = scanner.nextInt();
            } catch (Exception e) {
                option = -99;
            }
            //inputs validation
            if (option == 1 || option == 2) {
                List<Match> result = null;
                String columnHeaders = String.format("%-15s %-30s %-30s %15s %15s", "Played Date", "Team 01", "Team 02", "Team 01 Score", "Team 02 Score");
                if (option == 1) {
                    System.out.println("\t\t Match Log");
                    result = UEFALeagueManager.getAllMatches();
                    System.out.println(columnHeaders);
                } else if (option == 2) {
                    System.out.println("\t\t Search a Match");
                    System.out.print("Date 'dd-MM-yyyy': ");
                    String strDate = scanner.next();
                    if (Pattern.matches("^([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$", strDate)) {
                        String[] arrDate = strDate.split("-");
                        Date date = createDateObject(Integer.parseInt(arrDate[2]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[0]));
                        result = UEFALeagueManager.getAllMatches(date);
                        System.out.println(columnHeaders);
                    } else {
                        System.out.println("\tInvalid input! Try Again");
                    }
                }
                if(!result.isEmpty()) {
                    for (Match m : result) {
                        String temp = String.format("%-15s %-30s %-30s %15d %15d", new SimpleDateFormat("dd-MM-yyyy").format(m.getPlayedDate())
                                , m.getTeam1().getName(), m.getTeam2().getName(), m.getTeam1Score(), m.getTeam2Score());
                        System.out.println(temp);
                    }
                }else {
                    System.out.printf("%100S \n","NO RECORDS");
                }
            } else {
                System.out.println("\t Invalid Input! Try Again Later!!");
            }
        }else {
            System.out.println("\t\t NO MATCH RECORDS FOUND");
        }
    }

    //this method is to save all records to files
    @Override
    public void saveCli() {
        List<Object> response = UEFALeagueManager.saveToFile();
        System.out.println(response.get(1));

    }

    //this method is to load all records from files
    @Override
    public void loadCli() {
        List<Object> response = UEFALeagueManager.loadFile();
        System.out.println(response.get(1));
    }

    //created to get league table data for front end
    //returns a hashmap with club category wise
    @RequestMapping(value = "/leagueTable/{sortingMethod}", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Map getLeagueTable(@PathVariable int sortingMethod){
        Map<String, Object> response = new HashMap<>();

        List<ArrayList<SportsClub>> result = UEFALeagueManager.leagueTable(sortingMethod);
        response.put("clubLevel", result.get(0));
        response.put("universityLevel", result.get(1));
        response.put("schoolLevel", result.get(2));
        return response;
    }

    //created to make a random match through web front end
    //returns a hashmap with components details
    @RequestMapping(value = "match/random-match", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Map  insertRandomMatch(){
        Map response = automateAddMatch();
        response.put("category", response.get("team1").getClass().getSimpleName());
        return response;
    }

    //created to get matches logs for front end
    //parameters - to get all --> '*' for a specific date --> pattern = 'dd-MM-yyyy'
    //returns a hashmap with match details
    @RequestMapping(value = "match/log/{date}", method = RequestMethod.GET)
    @ResponseBody
    @Override
    public Map getMatchLog(@PathVariable String date) {
        Map<String, Object> response = new HashMap<>();
        List<Match> result;
        String status, message;
        if(date.equalsIgnoreCase("*")) {
            result = UEFALeagueManager.getAllMatches();
            status = "success";
            message = "success";
        }else if (Pattern.matches("^([0-2][0-9]|(3)[0-1])(-)(((0)[0-9])|((1)[0-2]))(-)\\d{4}$", date)) {
            String[] arrDate = date.split("-");
            Date playedDate = createDateObject(Integer.parseInt(arrDate[2]), Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[0]));
            result = UEFALeagueManager.getAllMatches(playedDate);
            if(result.isEmpty()) {
                status = "no-data";
                message = "success with no data";
            }else {
                status = "success";
                message = "success";
            }
        }else{
            status =  "error";
            message = "date validation failed pattern 'dd-MM-yyyy'";
            result = null;
        }
        response.put("result",result);
        response.put("status",status);
        response.put("message",message);
        return response;
    }

    //returning all registered teams as a list
    //if valiSameCatCountMoreTwo == true checking whether same cat has atleast 2 teams if not that category wont be added to the returning list
    // *****sorted as teams type wise**********
    private List<SportsClub> getAllTeams(boolean valiSameCatCountMoreTwo) {
        List<ArrayList<SportsClub>> result = UEFALeagueManager.leagueTable(1);
        List<SportsClub> allTeams = new ArrayList<>();
        for (ArrayList<SportsClub> cats : result) {
            if (valiSameCatCountMoreTwo) {
                if (cats.size() >= 2) {
                    allTeams.addAll(cats);
                }
            }else {
                allTeams.addAll(cats);
            }

        }
        return allTeams;
    }

    //this method is to extract team from a given set of teams
    private SportsClub getTeamFromList(List<SportsClub> allTeams) {
        int j = allTeams.size(), oppIndex = -99;
        SportsClub teamOpp;
        //displaying all teams with an index
        searchResults(allTeams);
        //user has to select the team by giving the relevant index
        while (true) {
            try {
                Scanner scanner1 = new Scanner(System.in);
                System.out.print("Please Select\nIndex of Team: ");
                oppIndex = scanner1.nextInt();
                //input index validation
                if (oppIndex > 0 && oppIndex <= j) {
                    break;
                } else {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("\tInvalid input! Try Again");
            }
        }
        //returning the selected object
        teamOpp = allTeams.get(oppIndex - 1);
        return teamOpp;
    }

    //to display returned List values in CLI
    private void searchResults(List<SportsClub> temp) {
        int i = 0;
        System.out.println("\t\t\tSearch Results");
        //System.out.println("No  \tName\t\t\t\tClub Type");
        System.out.printf("%-5s  %-40s %-15s \n", "No", "Name", "Club Type");
        for (SportsClub sc : temp) {
            i++;
            //System.out.println(i + ".\t "+ temp.get(i-1).getName()+ "\t\t\t\t" + temp.get(i-1).getClass().getSimpleName());
            System.out.printf("%2d.%2s  %-40s %-15s \n", i, "", sc.getName(), sc.getClass().getSimpleName());
        }
    }

    //year – the year
    //month – the month between 0-12.
    ///date – the day of the month between 1-31.
    private Date createDateObject(int year, int month, int date) {
        Date result = new Date(year-1900, month-1, date);
        return result;
    }

    private Date getSysDate() {
        Date now = new Date();
        Date result = createDateObject(now.getYear()+1900,now.getMonth()+1,now.getDate());
        return result;
    }

    //this method is to pause the program for a limited time
    // i --> time in milliseconds
    private void restTime(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
            System.out.println("Opps... \n\tAn Unknown Error\n\t\t Restart the Program");
        }
    }

    //cross platform solution to open a url on a web browser
    //ref source - https://mkyong.com/java/open-browser-in-java-windows-or-linux/
    private void openUrl(String url) {
        //System.out.println(url);
        String osType = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        try{
            //for windows
            if (osType.contains("win")) {
                runtime.exec( "rundll32 url.dll,FileProtocolHandler " + url);
            //for mac
            } else if (osType.contains("mac")) {
                runtime.exec( "open " + url);
            //for unix linux
            }else if (osType.contains("nix") || osType.contains("nux")) {
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                        "netscape","opera","links","lynx"};
                StringBuffer command = new StringBuffer();
                for (int i=0; i<browsers.length; i++)
                    command.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");
                runtime.exec(new String[] { "sh", "-c", command.toString() });
            }
        }catch (Exception e){
            System.out.println("An Exception Occurred! Try Again Later");
        }
    }
}

