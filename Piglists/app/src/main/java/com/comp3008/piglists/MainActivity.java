package com.comp3008.piglists;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.comp3008.piglists.model.Guest;
import com.comp3008.piglists.model.GuestStructure;
import com.comp3008.piglists.model.PlayList;
import com.comp3008.piglists.model.PlayListStructure;
import com.comp3008.piglists.model.Searchable;
import com.comp3008.piglists.model.Song;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PlayListFragment.OnPlayListInteractionListener,
        GuestFragment.OnGuestSelectListener,SongFragment.OnListFragmentInteractionListener {

    static boolean isConnected = false;
    static boolean inEvent = false;
    static boolean admin = false;
    protected Menu sideMenu;
    boolean pickingPlaylist = false;
    boolean inCurrentlyPlaying = false;
    TextView searchBar;
    Searchable currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);
        sideMenu = navView.getMenu();

        PlayListFragment myPlayListsFragment = new PlayListFragment();

        //add the playlist fragment to start with
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, myPlayListsFragment)
                .commit();
        currentFragment = myPlayListsFragment;

        searchBar = (TextView)findViewById(R.id.txtSearch);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentFragment.search(searchBar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                Searchable f = (Searchable) getSupportFragmentManager().getFragments().get(getSupportFragmentManager().getBackStackEntryCount() - 1);
                currentFragment = f;
            }
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_connect) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            inCurrentlyPlaying = false;
            if (isConnected) {
                //item.setIcon(R.drawable.red_x);
                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.red_x);
                findViewById(R.id.nav_bar_header).setBackgroundResource(R.drawable.side_nav_bar_red);
                ((TextView) findViewById(R.id.nav_bar_textView)).setText(R.string.side_nav_bar_disconnected);
                item.setTitle(R.string.connect);
                sideMenu.findItem(R.id.nav_new_event).setEnabled(false);
                sideMenu.findItem(R.id.nav_join_event).setEnabled(false);
                builder.setMessage(R.string.disconnectingDialog);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isConnected = false;
                        dialog.dismiss();
                    }
                });
                builder.create().show();

            } else {
                final ConnectingToEventTask task = new ConnectingToEventTask();
                builder.setMessage(R.string.connectingDialog);
                builder.setPositiveButton(R.string.connectingDialogCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        task.cancel(true);
                    }
                });
                dialog.show();
                task.execute(new TaskParams(builder, dialog, item));
            }
        } else if (id == R.id.nav_join_event) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            if (inEvent) {
                //item.setIcon(R.drawable.red_x);
                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.red_x);
                findViewById(R.id.nav_bar_header).setBackgroundResource(R.drawable.side_nav_bar_red);
                ((TextView) findViewById(R.id.nav_bar_textView)).setText(R.string.side_nav_bar_disconnected);
                item.setTitle(R.string.joinEvent);
                sideMenu.findItem(R.id.nav_new_event).setEnabled(false);
                sideMenu.findItem(R.id.nav_connect).setEnabled(true);
                sideMenu.findItem(R.id.nav_now_playing).setEnabled(false);
                builder.setMessage(R.string.disconnectingDialog);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inEvent = false;
                        dialog.dismiss();
                        if(inCurrentlyPlaying){
                            PlayListFragment playlists = new PlayListFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playlists)
                                    .commit();
                            inCurrentlyPlaying = false;
                        }
                    }
                });
                builder.create().show();

            }else {

                final JoinEventTask task = new JoinEventTask();
                builder.setMessage(R.string.joiningDialog);
                builder.setPositiveButton(R.string.connectingDialogCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        task.cancel(true);
                    }
                });
                dialog.show();
                task.execute(new TaskParams(builder, dialog, item));

            }
        } else if (id == R.id.nav_manage_guests) {
            inCurrentlyPlaying = false;
            GuestFragment guestFragment = new GuestFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, guestFragment)
                    .addToBackStack("").commit();
            currentFragment = guestFragment;

        } else if (id == R.id.nav_my_playlists) {
            inCurrentlyPlaying = false;
            PlayListFragment playlists = new PlayListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playlists)
                    .addToBackStack("").commit();
            currentFragment = playlists;
        } else if (id == R.id.nav_new_event) {
            inCurrentlyPlaying = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.selectPlaylist);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    pickingPlaylist = true;
                    PlayListFragment playlists = new PlayListFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playlists)
                            .addToBackStack("").commit();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.nav_new_playlist) {
            inCurrentlyPlaying = false;
        } else if (id == R.id.nav_now_playing) {
            SongFragment frag = new SongFragment();
            frag.setPlaylist(PlayListStructure.ITEM_MAP.get("-1"), true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag)
                    .addToBackStack("").commit();
            currentFragment = frag;
            inCurrentlyPlaying = true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onListFragmentInteraction(PlayList item){
        if(!pickingPlaylist) {
            SongFragment songFragment = new SongFragment();
            songFragment.setPlaylist(item, false);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, songFragment)
                    .addToBackStack("").commit();
            currentFragment = songFragment;
        }else{
            PlayList currentlyplaying = new PlayList("-1", "Currently Playing");
            for(Song song : item.getSongs()){
                song.inCurrentlyPlaying(true);
                currentlyplaying.songs.add(song);
            }
            PlayListStructure.ITEM_MAP.put("-1", currentlyplaying);
            SongFragment songFragment = new SongFragment();
            songFragment.setPlaylist(currentlyplaying, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, songFragment)
                    .addToBackStack("").commit();
            currentFragment = songFragment;
            pickingPlaylist = false;
            sideMenu.findItem(R.id.nav_now_playing).setEnabled(true);
            sideMenu.findItem(R.id.nav_new_event).setEnabled(false);
            sideMenu.findItem(R.id.nav_manage_guests).setEnabled(true);
        }
    }

    @Override
    public void onGuestSelected(final Guest item, final GuestViewAdapter adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View guestDialogView = inflater.inflate(R.layout.guest_dialog, null);

        ((TextView) guestDialogView.findViewById(R.id.guestName)).setText(item.id);
        ((TextView) guestDialogView.findViewById(R.id.guestInfo)).setText(item.getDescription());
        ((CheckBox) guestDialogView.findViewById(R.id.enableVote)).setChecked(!item.canVote());
        ((CheckBox) guestDialogView.findViewById(R.id.muteSongSugestion)).setChecked(!item.canSuggest());

        builder.setView(guestDialogView)
                .setNegativeButton(R.string.kickOut, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GuestStructure.removeItem(item);
                        adapter.notifyDataSetChanged();
                    }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (((CheckBox) guestDialogView.findViewById(R.id.muteSongSugestion)).isChecked()) {
                    item.banSuggestions();
                } else {
                    item.enableSuggestions();
                }

                if (((CheckBox) guestDialogView.findViewById(R.id.enableVote)).isChecked()) {
                    item.banVote();
                } else {
                    item.enableVote();
                }

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void onListFragmentInteraction(final Song item){
        Log.i("MainActivity", "song selected: " + item.getTitle());
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        if(inEvent){
            builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setPositiveButton(R.string.requestSong, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setMessage("The song " + item.getTitle() + " by " + item.getAuthor() + " has been requested!");
                    AlertDialog d = builder.create();
                    d.show();
                }
            }).setMessage("Title: " + item.getTitle() + "\n Author: " + item.getAuthor() + "\nGenre: " + item.getGenre());
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setMessage("Title: " + item.getTitle() + "\n Author: " + item.getAuthor() + "\nGenre: " + item.getGenre());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private class ConnectingToEventTask extends AsyncTask<TaskParams, Integer, Boolean> {
        AlertDialog.Builder builder;
        AlertDialog dialog;
        MenuItem item;

        protected Boolean doInBackground(TaskParams... params) {
            this.builder = params[0].builder;
            this.dialog = params[0].dialog;
            this.item = params[0].item;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                return false;
            }
            if(isCancelled()){
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            if(result) {
                isConnected = true;
                //item.setIcon(R.drawable.checkmark_green);
                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.checkmark_green);
                item.setTitle(R.string.disconnect);
                findViewById(R.id.nav_bar_header).setBackgroundResource(R.drawable.side_nav_bar);
                ((TextView) findViewById(R.id.nav_bar_textView)).setText(R.string.side_nav_bar_connected);

                dialog.dismiss();

                builder.setMessage(R.string.connectingDialogConfirm);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
                sideMenu.findItem(R.id.nav_new_event).setEnabled(true);
                sideMenu.findItem(R.id.nav_join_event).setEnabled(false);
            }
        }
    }

    private class TaskParams {
        AlertDialog.Builder builder;
        AlertDialog dialog;
        MenuItem item;

        TaskParams(AlertDialog.Builder b, AlertDialog d, MenuItem i) {
            builder = b;
            dialog = d;
            item = i;
        }
    }
    private class JoinEventTask extends AsyncTask<TaskParams, Integer, Boolean> {
        AlertDialog.Builder builder;
        AlertDialog dialog;
        MenuItem item;

        protected Boolean doInBackground(TaskParams... params) {
            this.builder = params[0].builder;
            this.dialog = params[0].dialog;
            this.item = params[0].item;

            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                return false;
            }
            if(isCancelled()){
                return false;
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            if(result) {
                inEvent = true;
                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.checkmark_green);
                item.setTitle(R.string.unJoin);
                findViewById(R.id.nav_bar_header).setBackgroundResource(R.drawable.side_nav_bar);
                ((TextView) findViewById(R.id.nav_bar_textView)).setText(R.string.side_nav_bar_joined);

                dialog.dismiss();

                builder.setMessage(R.string.joinedDialogConfirm);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                PlayList playlist = new PlayList("-1", "Now Playing");
                playlist.initSongs();
                PlayListStructure.ITEM_MAP.put("-1", playlist);
                builder.create().show();
                sideMenu.findItem(R.id.nav_manage_guests).setEnabled(false);
                sideMenu.findItem(R.id.nav_connect).setEnabled(false);
                sideMenu.findItem(R.id.nav_new_event).setEnabled(false);
                sideMenu.findItem(R.id.nav_now_playing).setEnabled(true);
            }
        }

    }
}
