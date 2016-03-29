package com.comp3008.piglists.dummy;

/**
 * Created by elyas on 3/29/2016.
 */
public class PlayList {
    private String title;
    public int id;
    public String content;
    public PlayList(int id, String title, String detail){
        this.title = title;
        content = detail;
        this.id = id;
    }
}
