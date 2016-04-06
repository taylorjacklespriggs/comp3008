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
    private boolean currentlyPlaying = false;
    private int voteCount;
    public Song(){

    }

    public Song(String id, String title, String author, String genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
        Random random = new Random();

        this.votes = random.nextInt(50-1) + 50;
        this.voteCount = 0;
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
        if(voteCount < 1) {
            votes++;
            voteCount++;
        }
    }
    public void onDownVote(){
        if(voteCount > -1){
            votes--;
            voteCount --;
        }
    }

    public void inCurrentlyPlaying(boolean b) {
        currentlyPlaying = b;
    }
    public boolean isInCurrentlyPlaying(){
        return currentlyPlaying;
    }

    public String toString(){
        return "Title: " + title + " author: " + author + " genre: " + genre + "votes: " + votes;
    }
}
