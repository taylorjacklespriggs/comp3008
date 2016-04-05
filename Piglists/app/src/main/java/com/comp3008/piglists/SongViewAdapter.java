package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comp3008.piglists.model.Song;

import java.util.List;



public class SongViewAdapter extends RecyclerView.Adapter<SongViewAdapter.ViewHolder> {

    private final List<Song> mValues;
    private final SongFragment.OnListFragmentInteractionListener mListener;
    private boolean currentlyPlaying;
    public SongViewAdapter(List<Song> items, SongFragment.OnListFragmentInteractionListener listener, boolean currentlyPlaying) {
        mValues = items;
        mListener = listener;
        this.currentlyPlaying = currentlyPlaying;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_song2, parent, false);
        return new ViewHolder(view);
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
