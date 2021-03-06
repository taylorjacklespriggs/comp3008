package com.comp3008.piglists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.comp3008.piglists.model.Guest;
import com.comp3008.piglists.model.GuestStructure;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Guest} and makes a call to the
 * specified {@link GuestFragment.OnGuestSelectListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class GuestViewAdapter extends RecyclerView.Adapter<GuestViewAdapter.ViewHolder> {

    private List<Guest> mValues;
    private final GuestFragment.OnGuestSelectListener mListener;

    public GuestViewAdapter(List<Guest> items, GuestFragment.OnGuestSelectListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_guest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).getDescription());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onGuestSelected(holder.mItem, GuestViewAdapter.this);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void search(String query) {
        if(query == null || query.trim().equals("")){
            GuestStructure.SEARCHED_ITEMS.clear();
            GuestStructure.SEARCHED_ITEMS.addAll(GuestStructure.ITEMS);
            notifyDataSetChanged();
            return;
        }
        GuestStructure.SEARCHED_ITEMS.clear();
        for(Guest g : GuestStructure.ITEMS){
            if(g.toString().toLowerCase().contains(query)){
                GuestStructure.SEARCHED_ITEMS.add(g);
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Guest mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
