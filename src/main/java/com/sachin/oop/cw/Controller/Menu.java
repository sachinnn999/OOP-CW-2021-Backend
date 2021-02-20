package com.sachin.oop.cw.Controller;

import java.util.Map;

public interface Menu {
    boolean runSysMenu();
    void addClubToLeagueCli();
    void deleteClubFromLeagueCli();
    void searchAClubCli();
    void showLeagueTableCli();
    void addPlayedMatchToLeagueCli();
    void matchesCli();
    void saveCli();
    void loadCli();
    Map getLeagueTable(int sortingMethod);
    Map  insertRandomMatch();
    Map getMatchLog(String date);
}
