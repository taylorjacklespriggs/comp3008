package com.comp3008.piglists.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elyas on 3/29/2016.
 */
public class PlayList {
    private String title;
    public int id;
    private ArrayList<Song> songs;

    public PlayList(int id, String title){
        this.title = title;
        this.id = id;
        createSongs();
    }

    private void createSongs() {
        Random rand = new Random();
        songs = new ArrayList<>();
        int max = rand.nextInt((260 - 56) + 1) + 56;
        for(int i = 0; i < max; i++){
            songs.add(new Song("Song " + i, "Author " + i,i ,i %2 == 0? "Rock" : "EDM"));
        }
    }

    public String detail() {

        String detail = "";
        for(int i = 0; i < 5; i++){
            detail += songs.get(i).toString() + "\n";
        }
        return detail;
    }
}
