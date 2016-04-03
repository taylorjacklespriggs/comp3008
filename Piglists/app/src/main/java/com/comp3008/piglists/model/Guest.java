package com.comp3008.piglists.model;

import java.util.Random;

/**
 * Created by elyas on 2016-04-01.
 */
public class Guest {
    public String id = "Fred";
    private String description = "Joined 25 min ago.";
    private boolean isAbleToMakeSuggestions = true;
    private boolean isAbleToVote = true;


    public Guest(String name){
        this.id = name;
        int min = (new Random()).nextInt(60);
        description = "Joined " + min + " min ago.";
    }
    public String getDescription(){
        return description;
    }

    public boolean canVote() {
        return isAbleToVote;
    }

    public boolean canSuggest() {
        return isAbleToMakeSuggestions;
    }

    public void banVote() {
        isAbleToVote = false;
    }

    public void banSuggestions() {
        isAbleToMakeSuggestions = false;
    }

    public void enableVote() {
        isAbleToVote = true;
    }

    public void enableSuggestions() {
        isAbleToMakeSuggestions = true;
    }
}
