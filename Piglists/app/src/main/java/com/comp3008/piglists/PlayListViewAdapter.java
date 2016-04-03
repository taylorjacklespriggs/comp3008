package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayListViewAdapter
        extends RecyclerView.Adapter<PlayListViewAdapter.SongWrapperViewHolder> {

    public final static int SONG_PREVIEW_COUNT = 4;

    private final PlayList mPlayList;
    private final PlayListFragment.OnPlayListFragmentInteractionListener mListener;

    public PlayListViewAdapter(PlayList pl,
                               PlayListFragment.OnPlayListFragmentInteractionListener listener) {
        mPlayList = pl;
        mListener = listener;
    }

    @Override
    public SongWrapperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_songwrapper, parent, false);
        return new SongWrapperViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final SongWrapperViewHolder holder, int position) {
        PlayList.SongWrapper wrap = mPlayList.songs.get(position);
        holder.setSongWrapper(wrap);

        /*
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mPlayList.songs.size();
    }

    public class SongWrapperViewHolder extends RecyclerView.ViewHolder {
        public final SongWrapperView mView;
        public PlayList.SongWrapper mItem;

        public SongWrapperViewHolder(ViewGroup view) {
            super(view);
            mView = new SongWrapperView(view);
        }

        public void setSongWrapper(PlayList.SongWrapper sw) {
            mView.setSongWrapper(sw);
        }
    }

}
