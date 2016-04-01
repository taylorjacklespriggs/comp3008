package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.comp3008.piglists.model.Song;

/**
 * Created by taylor on 4/1/16.
 */
public class SongViewHolder extends RecyclerView.ViewHolder {
    Song song;
    public SongViewHolder(View itemView, Song s) {
        super(itemView);
        song = s;
    }
}
