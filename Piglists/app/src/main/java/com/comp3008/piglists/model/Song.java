package com.comp3008.piglists.model;

/**
 * Created by elyas on 3/29/2016.
 */
public class Song {
    private String title;
    private String author;
    private String genre;

    public Song(){

    }

    public Song(String title, String author, String genre){
        this.title = title;
        this.author = author;
        this.genre = genre;
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
}
