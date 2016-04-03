package com.comp3008.piglists;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.comp3008.piglists.model.Guest;
import com.comp3008.piglists.model.PlayList;
import com.comp3008.piglists.model.PlayListStructure;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DisplayPlayListsFragment.OnDisplayPlayListsFragmentInteractionListener,
        PlayListFragment.OnPlayListFragmentInteractionListener,
        GuestFragment.OnGuestSelectListener {

    public static final String MY_PLAYLISTS_ID = "my-playlists";
    public static final String MANAGE_GUESTS_ID = "manage-guests";

    boolean isConnected;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private DisplayPlayListsFragment myPlayListsFragment;
    private GuestFragment manageGuestsFragment;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        myPlayListsFragment = DisplayPlayListsFragment.newInstance(1);
        manageGuestsFragment = GuestFragment.newInstance(1);

        //add the playlist fragment to start with
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, myPlayListsFragment, MY_PLAYLISTS_ID)
                .commit();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void showMyPlayLists() {
        replaceFragment(myPlayListsFragment);
    }

    private void showManageGuests() {
        replaceFragmentToStack(manageGuestsFragment, MANAGE_GUESTS_ID);
    }

    private void showPlayList(PlayList pl) {
        PlayListFragment plFragment =
                PlayListFragment.newInstance(1, pl);
        replaceFragmentToStack(plFragment, pl.toString());
    }

    private void replaceFragment(Fragment f) {
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).commit();
    }

    private void replaceFragmentToStack(Fragment f, String id) {
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f)
                .addToBackStack(id).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

            if (isConnected) {
                item.setIcon(R.drawable.red_x);
                item.setTitle(R.string.connect);
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
                        if (which == Dialog.BUTTON_NEUTRAL) {
                            dialog.cancel();
                        }
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

        } else if (id == R.id.nav_manage_guests) {
            showManageGuests();
        } else if (id == R.id.nav_my_playlists) {
            showMyPlayLists();
        } else if (id == R.id.nav_new_event) {

        } else if (id == R.id.nav_new_playlist) {

        } else if (id == R.id.nav_share_playlist) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDisplayPlayListsFragmentInteraction(PlayList item) {
        Log.i("MainActivity", "playlist selected: " + item.toString());
        showPlayList(item);
    }

    @Override
    public void onPlayListFragmentInteraction(PlayList.SongWrapper item) {
        Log.i("MainActivity", "playlist selected: " + item.toString());
    }

    @Override
    public void onGuestSelected(Guest item) {
// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.guest_dialog);
        dialog.setTitle(item.id);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.guestName);
        text.setText(item.getDescription());

        Button btnOK = (Button) dialog.findViewById(R.id.btnOk);
        Button btnKick = (Button) dialog.findViewById(R.id.btnKickOut);
        // if button is clicked, close the custom dialog
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.comp3008.piglists/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.comp3008.piglists/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
                // do nothing
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            isConnected = true;
            item.setIcon(R.drawable.checkmark_green);
            item.setTitle(R.string.disconnect);

            dialog.dismiss();

            builder.setMessage(R.string.connectingDialogConfirm);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create().show();
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
}
