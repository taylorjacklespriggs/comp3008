package com.comp3008.piglists.model;

import java.util.Random;

/**
 * Created by elyas on 2016-04-01.
 */
public class Guest {
    public String id = "Fred";
    private String description = "Joined 25 min ago.";

    public Guest(String name){
        this.id = name;
    }
    public String getDescription(){
        int min = (new Random()).nextInt(60);
        return "Joined " + min + " min ago.";
    }
}
