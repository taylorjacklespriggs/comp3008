package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;
import com.comp3008.piglists.model.Song;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlayList} and makes a call to the
 * specified {@link PlaylistFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SonglistViewAdapter extends RecyclerView.Adapter<SonglistViewAdapter.SongViewHolder> {

    private final List<Song> myList;
    private final PlaylistFragment.OnListFragmentInteractionListener mListener;

    public SonglistViewAdapter(List<Song> pl, PlaylistFragment.OnListFragmentInteractionListener listener) {
        myList = pl;
        mListener = listener;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playlist, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SongViewHolder holder, int position) {
        Song song = myList.get(position);
        holder.mItem = song;
        holder.mTitleView.setText(song.toString());
        //holder.mContentView.setText(myList.get(position).detail());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        //public final TextView mAuthorView;
        //public final TextView mGenreView;
        public Song mItem;

        public SongViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
            //mAuthorView = (TextView) view.findViewById(R.id.author);
            //mGenreView = (TextView) view.findViewById(R.id.genre);
            //mItem = s;
        }
    }
}
