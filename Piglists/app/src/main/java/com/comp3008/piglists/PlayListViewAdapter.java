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

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlayList} and makes a call to the
 * specified {@link PlayListFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PlayListViewAdapter
        extends RecyclerView.Adapter<PlayListViewAdapter.PlayListViewHolder> {

    public final static int SONG_PREVIEW_COUNT = 4;

    private final List<PlayList> mValues;
    private final PlayListFragment.OnListFragmentInteractionListener mListener;

    public PlayListViewAdapter(List<PlayList> items, PlayListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playlist, parent, false);
        return new PlayListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlayListViewHolder holder, int position) {
        PlayList playList = mValues.get(position);
        holder.mItem = playList;
        holder.setPlayList(playList);

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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class PlayListViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final LinearLayout mContentView;
        public PlayList mItem;

        public PlayListViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (LinearLayout) view.findViewById(R.id.song_wrapper_list);
        }

        public void setPlayList(PlayList pl) {
            mItem = pl;
            mIdView.setText(String.valueOf(pl.id));
            List<PlayList.SongWrapper> songs = new ArrayList<PlayList.SongWrapper>(pl.songs);
            Collections.sort(songs, new Comparator<PlayList.SongWrapper>() {
                @Override
                public int compare(PlayList.SongWrapper lhs, PlayList.SongWrapper rhs) {
                    return lhs.votes - rhs.votes;
                }
            });
            songs = songs.subList(0, SONG_PREVIEW_COUNT);
            mContentView.removeAllViews();
            for(PlayList.SongWrapper wrap : songs) {
                SongWrapperView swv = new SongWrapperView(mContentView);
                swv.setSongWrapper(wrap);
                mContentView.addView(swv.mView);
            }
        }
    }

}
