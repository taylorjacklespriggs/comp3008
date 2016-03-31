package com.comp3008.piglists.model;

/**
 * Created by elyas on 3/29/2016.
 */
public class Song {
    String title;
    String author;
    int votes;
    String genre;

    public Song(){

    }

    public Song(String title, String author, int votes, String genre){
        this.title = title;
        this.author = author;
        this.votes = votes;
        this.genre = genre;
    }
}
