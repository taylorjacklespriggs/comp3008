package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comp3008.piglists.model.Song;
import com.comp3008.piglists.model.SongStructure;

import java.util.ArrayList;
import java.util.List;



public class SongViewAdapter extends RecyclerView.Adapter<SongViewAdapter.ViewHolder> {

    private final List<Song> mValues;
    private final List<Song> initialItems;
    private final SongFragment.OnListFragmentInteractionListener mListener;
    private boolean currentlyPlaying;
    public SongViewAdapter(List<Song> items, SongFragment.OnListFragmentInteractionListener listener, boolean currentlyPlaying) {
        mValues = items;
        initialItems = new ArrayList<>(items);
        mListener = listener;
        this.currentlyPlaying = currentlyPlaying;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_song2, parent, false);
        return new ViewHolder(view);
    }

    public void search(String query){
        Log.i("adapter", query);
        if(query == null || query.trim().equals("")){
            SongStructure.SEARCHED_ITEMS.clear();
            SongStructure.SEARCHED_ITEMS.addAll(initialItems);
            notifyDataSetChanged();
            return;
        }
        SongStructure.SEARCHED_ITEMS.clear();
        Log.i("adapter", "size after clearing: " + SongStructure.SEARCHED_ITEMS.size());
        for(Song g : initialItems){
            if(g.toString().toLowerCase().contains(query)){
                Log.i("adapter", g.toString().toLowerCase());
                SongStructure.SEARCHED_ITEMS.add(g);
            }
        }
        Log.i("adapter", "notifying change for size: " + SongStructure.SEARCHED_ITEMS.size());
        notifyDataSetChanged();
        Log.i("adapter", "notifying change for mValues: " + mValues.size());
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
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
        public final TextView mTitle;
        public final TextView mAuthor;
        public final TextView mVotes;
        public final LinearLayout voteContainer;
        public Song mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.txtSongName);
            mAuthor = (TextView) view.findViewById(R.id.txtSongAuthor);
            mVotes = (TextView) view.findViewById(R.id.txtVote);
            voteContainer = (LinearLayout) view.findViewById(R.id.voteLayout);

            Button mUp = (Button) view.findViewById(R.id.btnVoteUp);
            Button mDown = (Button) view.findViewById(R.id.btnVoteDown);
            mUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView votes = mVotes;
                    mItem.onUpVote();
                    votes.setText("" + mItem.getVotes());
                }
            });
            mDown.setOnClickListener(new View.OnClickListener() {
                TextView votes = mVotes;
                @Override
                public void onClick(View v) {
                    mItem.onDownVote();
                    votes.setText("" + mItem.getVotes());
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }

        public void setContent() {
            mTitle.setText(mItem.getTitle());
            mAuthor.setText(mItem.getAuthor());
            mVotes.setText(""+mItem.getVotes());
            if(!mItem.isInCurrentlyPlaying()){
                voteContainer.setVisibility(View.GONE);
            }
        }
    }
}
