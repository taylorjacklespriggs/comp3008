package com.comp3008.piglists;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.comp3008.piglists.model.PlayList;
import com.comp3008.piglists.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlayList} and makes a call to the
 * specified {@link com.comp3008.piglists.PlayListFragment.OnPlayListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PlayListViewAdapter extends RecyclerView.Adapter<PlayListViewAdapter.ViewHolder> {

    private final List<PlayList> mValues;
    private final PlayListFragment.OnPlayListInteractionListener mListener;

    public PlayListViewAdapter(List<PlayList> items, PlayListFragment.OnPlayListInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playlist2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.setContent();

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
        public final TextView mTitleView;
        public final LinearLayout mContentView;
        public PlayList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.playlistid);
            mTitleView = (TextView) view.findViewById(R.id.playlistTitle);
            mContentView = (LinearLayout) view.findViewById(R.id.playlistContent);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.toString() + "'";
        }

        public void setContent() {
            List<Song> songs = mItem.getTopThree();
            mTitleView.setText(mItem.title);
            for (Song s : songs) {
                Context c = mView.getContext();
                LayoutInflater inflater = (LayoutInflater)c.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.simple_song, null);
                TextView title = (TextView)v.findViewById(R.id.txtTitle);
                title.setText(s.getAuthor() + " - " + s.getTitle());
                mContentView.addView(v);
            }

        }
    }
}