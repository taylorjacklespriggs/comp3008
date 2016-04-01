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
    //public final ListView songView;
    //public final TextView voteView;
    public PlayList mItem;

    public SongWrapperViewHolder(View view, PlayList.SongWrapper song) {
        super(view);
        mView = view;
        //songView = (ListView) mView.findViewById(R.id.song);
        //voteView = (ListView) mView.findViewById(R.id.vote);
    }
}
