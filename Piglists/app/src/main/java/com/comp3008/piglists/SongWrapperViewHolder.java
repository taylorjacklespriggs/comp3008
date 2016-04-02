package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;

/**
 * Created by taylor on 4/1/16.
 */
public class SongWrapperViewHolder extends SongViewHolder {

    private PlayList.SongWrapper wrapper;
    public final TextView mVoteView;

    public SongWrapperViewHolder(View view) {
        super(view);
        mVoteView = (TextView) mView.findViewById(R.id.votes);
    }

    public void setSongWrapper(PlayList.SongWrapper wrap) {
        wrapper = wrap;
        //mVoteView.setText(wrap.votes);
        setSong(wrap.song);
    }
}
