package com.comp3008.piglists.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elyas on 3/29/2016.
 */
public class PlayList {
    public String title;
    public String id;
    public List<Song> songs;

    public PlayList(String id, String title){
        this.title = title;
        this.id = id;
        songs = new ArrayList<>();
        if(! id.equals("-1"))
            initSongs();
    }

    public void initSongs() {
        Random rand = new Random();
        int i = rand.nextInt(200-50)+50;
        for(int j = 0; j < i; j ++){
            Song s = SongStructure.ITEMS.get(rand.nextInt(1000));
            if(id.equals("-1"))
                s.inCurrentlyPlaying(true);
            songs.add(s);
        }
    }

    public String detail() {

        String detail = "";
        for(int i = 0; i < 5; i++){
            detail += songs.get(i).toString() + "\n";
        }
        return detail;
    }

    public List<Song> getTopThree() {
        return songs.subList(0,3);
    }

    public List<Song> getSongs(){
        return this.songs;
    }
}
