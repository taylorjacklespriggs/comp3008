package com.comp3008.piglists.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class GuestStructure {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Guest> ITEMS = new ArrayList<Guest>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Guest> ITEM_MAP = new HashMap<String, Guest>();

    private static final int COUNT = 25;

    static{
        //create sample guests
        addItem(new Guest("Elyas Syoufi"));
        addItem(new Guest("Harry Potter"));
        addItem(new Guest("Taylor J Spriggs"));
        addItem(new Guest("Walter Mellon"));
        addItem(new Guest("Christopher Charron"));
        addItem(new Guest("Derek Roast"));
        addItem(new Guest("Clark Kent"));
    }
    private static void addItem(Guest item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(Guest item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(item.id);
    }
}
