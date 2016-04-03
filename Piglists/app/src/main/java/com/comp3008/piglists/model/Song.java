package com.comp3008.piglists.model;

import java.util.Random;

/**
 * Created by elyas on 3/29/2016.
 */
public class Song {
    private String title;
    private String author;
    private String genre;
    private int votes;

    public Song(){

    }

    public Song(String title, String author, String genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
        Random random = new Random();

        this.votes = random.nextInt(2000-500) + 500;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getVotes(){
        return this.votes;
    }
}
