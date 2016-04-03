package com.comp3008.piglists.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by elyas on 3/29/2016.
 */
public class PlayList {
    private String title;
    public int id;
    public List<Song> songs;

    public PlayList(int id, String title){
        this.title = title;
        this.id = id;
    }

    public String detail() {

        String detail = "";
        for(int i = 0; i < 5; i++){
            detail += songs.get(i).toString() + "\n";
        }
        return detail;
    }
}
