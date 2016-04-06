package com.comp3008.piglists.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elyas on 2016-04-03.
 */
public class SongStructure {
    /**
     * An array of sample (dummy) items.
     */
    public static final List<Song> ITEMS = new ArrayList<Song>();
    public static final List<Song> SEARCHED_ITEMS = new ArrayList<Song>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Song> ITEM_MAP = new HashMap<String, Song>();

    private static final int COUNT = 25;

    static{
        //create sample guests
        for(int i = 0; i < 1000; i ++){
            addItem(new Song(""+i, "Title " + i, "Author " + i, i%2==0?"Rock" : "Pop"));
        }
        SEARCHED_ITEMS.addAll(ITEMS);
    }
    private static void addItem(Song item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
