package com.sachin.oop.cw.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class SportsClub implements Serializable {
    private String name;
    private String location;
    private String manager;
    private int ranking;
    private int points;
    private Date dateJoined = new Date();

    public SportsClub() {
    }

    public SportsClub(String name) {
        this.name = name;
    }

    public SportsClub(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public SportsClub(String name, String location, String manager) {
        this(name,location);
        this.manager = manager;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    @Override
    public String toString() {
        return "SportsClub - " +
                "name = " + name +
                ", location = " + location +
                ", manager = " + manager +
                ", ranking = " + ranking +
                ", points = " + points +
                ", dateJoined = " + new SimpleDateFormat("dd-MM-yyyy").format(dateJoined);
    }

    //validating over the name and club type
    //returns true if comparing object have same name and data type
    public boolean equals(String name, int teamType) {
        if (this.getName().equalsIgnoreCase(name) && this.getClass().getSimpleName().equalsIgnoreCase(getClubType(teamType))) {
            return true;
        }
        return false;
    }

    private String getClubType(int teamType){
        String clubType = "";
        if (teamType == 1) {
            clubType = "FootballClub";
        } else if (teamType == 2) {
            clubType = "UniversityFootballClub";
        } else if (teamType == 3) {
            clubType = "SchoolFootballClub";
        }
        return clubType;
    }
    //validating only the name
    //returns true if comparing objects have same name
    public boolean equals(String name) {
        if (this.getName().equalsIgnoreCase(name)) {
            return true;
        }
        return false;
    }
    //validating only from the class type
    public boolean equals(SportsClub obj) {
        return (super.getClass().getSimpleName()).equals(obj.getClass().getSimpleName());
    }
    //validating name and class type
    public boolean equals(SportsClub obj, boolean nameValidationEnabled){
        if ((super.getClass().getSimpleName()).equals(obj.getClass().getSimpleName())) {
            if (this.getName().equalsIgnoreCase(obj.getName())) {
                //if (this.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}

