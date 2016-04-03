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
public class PlayListStructure {

    public static final List<PlayList> ITEMS = new ArrayList<PlayList>();
    public static final Map<Integer, PlayList> ITEM_MAP = new HashMap<Integer, PlayList>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(PlayList item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PlayList createDummyItem(int position) {
        return new PlayList(position, String.valueOf(position));
    }
}
