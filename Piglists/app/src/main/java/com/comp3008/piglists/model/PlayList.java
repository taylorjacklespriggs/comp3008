package com.comp3008.piglists.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elyas on 3/29/2016.
 */
public class PlayList {

    public static class SongWrapper {
        public int votes;
        public Song song;
        public SongWrapper(Song s) {
            song = s;
            votes = 0;
        }
        public SongWrapper(Song s, int v) {
            song = s;
            votes = v;
        }
    }
    private String title;
    public int id;
    public List<SongWrapper> songs;

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
            songs.add(new SongWrapper(
                    new Song("Song " + i, "Author " + i,i ,i %2 == 0? "Rock" : "EDM"),
                    (int)(Math.random()*10)));
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
