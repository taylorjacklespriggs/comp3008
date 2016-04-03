package com.comp3008.piglists;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;
import com.comp3008.piglists.model.Song;

/**
 * Created by taylor on 4/1/16.
 */
public class SongViewHolder {

    private Song song;
    public final View view;
    public final TextView mTitleView;
    public final TextView mAuthorView;

    public SongViewHolder(View v) {
        view = v;
        mTitleView = (TextView) view.findViewById(R.id.title);
        mAuthorView = (TextView) view.findViewById(R.id.author);
    }
    public void setSong(Song s) {
        song = s;
        mTitleView.setText(song.getTitle());
        mAuthorView.setText(song.getAuthor());
    }
}
