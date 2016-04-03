package com.comp3008.piglists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3008.piglists.model.Song;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by taylor on 4/1/16.
 */
public class SongView {

    public final View mView;
    public final TextView mTitleView;
    public final TextView mAuthorView;

    public SongView(ViewGroup parent) {
        LayoutInflater lf;
        lf = (LayoutInflater) parent.getContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        mView = lf.inflate(R.layout.view_songwrapper, parent, false);
        mTitleView = (TextView) mView.findViewById(R.id.title);
        mAuthorView = (TextView) mView.findViewById(R.id.author);
    }

    public void setSong(Song song) {
        mTitleView.setText(song.getTitle());
        mAuthorView.setText(song.getAuthor());
    }
}
