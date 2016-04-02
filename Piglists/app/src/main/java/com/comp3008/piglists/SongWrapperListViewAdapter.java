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
public class SongWrapperListViewAdapter
        extends RecyclerView.Adapter<SongWrapperViewHolder> {

    private final List<PlayList.SongWrapper> myList;
    private final PlaylistFragment.OnListFragmentInteractionListener mListener;

    public SongWrapperListViewAdapter(List<PlayList.SongWrapper> pl,
                                      PlaylistFragment.OnListFragmentInteractionListener listener) {
        myList = pl;
        mListener = listener;
    }

    @Override
    public SongWrapperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playlist, parent, false);
        return new SongWrapperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SongWrapperViewHolder holder, int position) {
        PlayList.SongWrapper song = myList.get(position);
        holder.setSongWrapper(song);

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
}
