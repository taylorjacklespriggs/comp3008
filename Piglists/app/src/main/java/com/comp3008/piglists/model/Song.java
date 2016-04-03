package com.comp3008.piglists.model;

import android.widget.TextView;

import java.util.Random;

/**
 * Created by elyas on 3/29/2016.
 */
public class Song {
    public String id;
    private String title;
    private String author;
    private String genre;
    private int votes;
    private TextView voteView;

    public Song(){

    }

    public Song(String id, String title, String author, String genre){
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

    public void onUpVote() {
        votes++;
    }
    public void onDownVote(){
        votes--;
    }
}
