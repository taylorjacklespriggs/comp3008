package com.comp3008.piglists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by taylor on 4/1/16.
 */
public class SongWrapperView {

    public final View mView;
    public final TextView mTitleView;
    public final TextView mAuthorView;
    public final TextView mVoteView;

    public SongWrapperView(ViewGroup parent) {
        LayoutInflater lf;
        lf = (LayoutInflater) parent.getContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        mView = lf.inflate(R.layout.view_songwrapper, parent, false);
        mTitleView = (TextView) mView.findViewById(R.id.title);
        mAuthorView = (TextView) mView.findViewById(R.id.author);
        mVoteView = (TextView) mView.findViewById(R.id.votes);
    }

    public void setSongWrapper(PlayList.SongWrapper wrap) {
        mTitleView.setText(wrap.song.getTitle());
        mAuthorView.setText(wrap.song.getAuthor());
        mVoteView.setText(Integer.toString(wrap.votes));
    }
}
