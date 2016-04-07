package com.comp3008.piglists;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comp3008.piglists.model.PlayList;
import com.comp3008.piglists.model.PlayListStructure;
import com.comp3008.piglists.model.Searchable;
import com.comp3008.piglists.model.Song;
import com.comp3008.piglists.model.SongStructure;

import java.util.Random;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SongFragment extends Fragment implements Searchable {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private PlayList playlist;
    private boolean currentlyPlaying = false;
    private SongViewAdapter adapter;
    private TextView txtCurrentTime;
    private ProgressBar progressBar;
    private IncrementPlaying ip;
    private TextView txtPlaylistName;
    private boolean newList;
    private DoneListener donePlaylistListener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongFragment() {
    }
    public interface DoneListener{
       void onDoneMakingPlaylist(PlayList newPlaylist);
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SongFragment newInstance(int columnCount) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    public void search(String query){
        adapter.search(query);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_list2, container, false);
        View rv = view.findViewById(R.id.list);
        txtPlaylistName = (TextView)view.findViewById(R.id.txtPlaylistName);
        Button btnDone = (Button)view.findViewById(R.id.btnAddDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongFragment.this.doneMakingNewPlaylist();
            }
        });

        if(currentlyPlaying){
            txtPlaylistName.setText("Currently Playing");
            Song s = PlayListStructure.ITEM_MAP.get("-1").getTopThree().get(1);
            TextView name = (TextView) view.findViewById(R.id.txtSongName);
            TextView end = (TextView) view.findViewById(R.id.txtEndTime);
            txtCurrentTime = (TextView) view.findViewById(R.id.txtCurrentTime);
            name.setText(s.getTitle() + " by " + s.getAuthor());
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            Random rand = new Random();
            int endMin = rand.nextInt(4-2)+2;
            int endSec = rand.nextInt(60-5)+5;
            int currentMin = rand.nextInt(endMin-1)+1;
            int currentSec = rand.nextInt(endSec-1)+1;
            int prog = ((currentSec + (currentMin*60))*100 / (endSec + (endMin*60)));
            progressBar.setProgress(prog);
            if(endSec < 10){
                end.setText(endMin + ":0" + endSec);
            }else {
                end.setText(endMin + ":" + endSec);
            }
            if(currentSec < 10){
                txtCurrentTime.setText(currentMin + ":0" + currentSec);
            }else{
                txtCurrentTime.setText(currentMin + ":" + currentSec);
            }
            ip = new IncrementPlaying();
            ip.execute(new TaskParams(currentSec, currentMin, progressBar.getProgress(), endSec, endMin));
        }else if(newList){
            txtPlaylistName.setText("New Playlist");
            btnDone.setVisibility(View.VISIBLE);
            view.findViewById(R.id.nowPlayingView).setVisibility(View.GONE);
        }else{
            txtPlaylistName.setText(playlist.title);
            view.findViewById(R.id.nowPlayingView).setVisibility(View.GONE);
        }
        // Set the adapter
        if (rv instanceof RecyclerView) {
            Context context = rv.getContext();
            RecyclerView recyclerView = (RecyclerView) rv;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            if(newList){
                SongStructure.SEARCHED_ITEMS.clear();
                SongStructure.SEARCHED_ITEMS.addAll(SongStructure.ITEMS);
                for(Song s : SongStructure.SEARCHED_ITEMS){
                    s.setChecked(false);
                }
            }else {
                SongStructure.SEARCHED_ITEMS.clear();
                SongStructure.SEARCHED_ITEMS.addAll(playlist.getSongs());
            }
            adapter = new SongViewAdapter(SongStructure.SEARCHED_ITEMS, mListener, currentlyPlaying, newList);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private void doneMakingNewPlaylist() {
        PlayList pl = new PlayList("","");
        pl.songs.clear();
        for(Song s : SongStructure.ITEMS){
            if(s.isChecked()){
                pl.songs.add(s);
            }
        }
        if(donePlaylistListener != null)
            donePlaylistListener.onDoneMakingPlaylist(pl);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(ip != null){
            ip.cancel(true);
        }
    }

    public void setPlaylist(PlayList item, boolean currentlyPlaying, boolean newList, DoneListener listener) {
        this.playlist = item;
        this.currentlyPlaying = currentlyPlaying;
        this.newList = newList;
        this.donePlaylistListener = listener;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Song item);
    }

    private class IncrementPlaying extends AsyncTask<TaskParams, TaskParams, Boolean> {
        int currentSec;
        int currentMin;
        int currentProg;
        ProgressBar progress;
        int endmin;
        int endsec;

        protected Boolean doInBackground(TaskParams... params) {
            this.currentSec = params[0].currentSec;
            this.currentMin = params[0].currentMin;
            this.currentProg = params[0].progress;
            this.endsec = params[0].endSec;
            this.endmin = params[0].endMin;

            while(true) {
                try {
                    Thread.sleep(1000);
                    if(currentSec > 59){
                        currentMin ++;
                        currentSec = 0;
                    }else{
                        currentSec ++;
                    }
                    long prog = ((currentSec + (currentMin*60))*100 / (endsec + (endmin*60)));
                    currentProg = ((int) prog);
                    publishProgress(new TaskParams(currentSec, currentMin, currentProg));

                } catch (InterruptedException ex) {
                    return false;
                }
                if (isCancelled()) {
                    return false;
                }
            }
        }

        @Override
        protected void onProgressUpdate(TaskParams ... params){
            SongFragment.this.update(new TaskParams(currentSec, currentMin, currentProg));
        }
        protected void onPostExecute(Boolean result) {

        }
    }

    private void update(TaskParams taskParams) {
        if(taskParams.currentSec < 10){
            txtCurrentTime.setText(taskParams.currentMin + ":0" + taskParams.currentSec);
        }else{
            txtCurrentTime.setText(taskParams.currentMin + ":" + taskParams.currentSec);
        }
        progressBar.setProgress(taskParams.progress);
    }

    private class TaskParams {
        int currentSec;
        int currentMin;
        int progress;
        int endSec;
        int endMin;
        TaskParams(int currsec, int currmin, int prog, int endsec, int endmin) {
            currentSec = currsec;
            currentMin = currmin;
            progress = prog;
            this.endSec = endsec;
            this.endMin = endmin;
        }
        TaskParams(int currsec, int currmin, int prog) {
            currentSec = currsec;
            currentMin = currmin;
            progress = prog;
        }
    }
}
