package com.comp3008.piglists.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by elyas on 2016-04-03.
 */
public class PlayListStructure {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PlayList> ITEMS = new ArrayList<PlayList>();
    public static List<PlayList> SEARCHED_ITEMS = new ArrayList<PlayList>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, PlayList> ITEM_MAP = new HashMap<String, PlayList>();

    private static final int COUNT = 25;

    static{
        //create sample guests
        addItem(new PlayList("1", "Top 100"));
        addItem(new PlayList("2", "Recommended"));
        addItem(new PlayList("3", "Guest Favorites"));
        addItem(new PlayList("4", "Rock"));
        addItem(new PlayList("5", "Workout"));
        addItem(new PlayList("6", "Top Weekly"));
        addItem(new PlayList("7", "Pop / Hip Hop"));
        SEARCHED_ITEMS = new ArrayList<>(ITEMS);
    }
    private static void addItem(PlayList item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
