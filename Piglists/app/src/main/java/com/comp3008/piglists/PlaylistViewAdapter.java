package com.comp3008.piglists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlayList} and makes a call to the
 * specified {@link PlaylistFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PlaylistViewAdapter
        extends RecyclerView.Adapter<PlaylistViewAdapter.PlayListViewHolder> {

    private final List<PlayList> mValues;
    private final PlaylistFragment.OnListFragmentInteractionListener mListener;

    public PlaylistViewAdapter(List<PlayList> items, PlaylistFragment.OnListFragmentInteractionListener listener) {
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
        public final ListView mContentView;
        public PlayList mItem;
        private final Context mContext;

        public PlayListViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (ListView) view.findViewById(R.id.content);
            mContext = view.getContext();
        }

        public void setPlayList(PlayList pl) {
            mItem = pl;
            mIdView.setText(String.valueOf(pl.id));
            mContentView.setAdapter(new PlayListAdapter(mContext, pl.songs));
        }
    }

    public class PlayListAdapter extends ArrayAdapter<PlayList.SongWrapper> {
        private final Context context;
        private final List<PlayList.SongWrapper> values;

        public PlayListAdapter(Context context, List<PlayList.SongWrapper> values) {
            super(context, R.layout.view_song_wrapper, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.view_song_wrapper, parent, false);
            SongWrapperViewHolder songView =
                    new SongWrapperViewHolder(rowView);
            songView.setSongWrapper(values.get(position));
            return rowView;
        }
    }

}
