package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;

/**
 * Created by taylor on 4/1/16.
 */
public class SongWrapperViewHolder extends RecyclerView.ViewHolder {

    PlayList.SongWrapper song;
    public final View mView;
    //public final SongViewHolder songView;
    //public final ListView mContentView;
    public PlayList mItem;

    public SongWrapperViewHolder(View view, PlayList.SongWrapper song) {
        super(view);
        mView = view;
        //songView = new SongViewHolder(song.song);
    }
}
