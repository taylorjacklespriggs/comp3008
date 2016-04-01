package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlayList} and makes a call to the
 * specified {@link PlaylistFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PlaylistView extends RecyclerView.Adapter<PlaylistView.ViewHolder> {

    private final List<PlayList> mValues;
    private final PlaylistFragment.OnListFragmentInteractionListener mListener;

    public PlaylistView(List<PlayList> items, PlaylistFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PlayList playList = mValues.get(position);
        holder.mItem = playList;
        holder.mIdView.setText(String.valueOf(playList.id));
        //holder.mContentView.setAdapter();

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ListView mContentView;
        public PlayList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (ListView) view.findViewById(R.id.content);
            //mContentView.setAdapter(new PlayListAdapter());
        }
    }

    public class PlayListAdapter extends RecyclerView.Adapter<SongWrapperViewHolder> {
        PlayList playList;
        public PlayListAdapter() {
            playList = null;
        }

        @Override
        public SongWrapperViewHolder onCreateViewHolder(ViewGroup parent, int index) {
            return new SongWrapperViewHolder(parent, playList.songs.get(index));
        }

        @Override
        public void onBindViewHolder(SongWrapperViewHolder holder, int position) {
            playList = mValues.get(position);
            //holder
        }

        @Override
        public int getItemCount() {
            return playList.songs.size();
        }
    }
}
