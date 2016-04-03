package com.comp3008.piglists;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.comp3008.piglists.model.Guest;
import com.comp3008.piglists.model.PlayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,PlayListFragment.OnPlayListInteractionListener,
        GuestFragment.OnGuestSelectListener {

    boolean isConnected;

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

        PlayListFragment myPlayListsFragment = new PlayListFragment();

        //add the playlist fragment to start with
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, myPlayListsFragment)
                .commit();
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
                //item.setIcon(R.drawable.red_x);
                ((ImageView) findViewById(R.id.imageView)).setImageResource(R.drawable.red_x);
                findViewById(R.id.nav_bar_header).setBackgroundResource(R.drawable.side_nav_bar_red);
                ((TextView) findViewById(R.id.nav_bar_textView)).setText(R.string.side_nav_bar_disconnected);
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
            GuestFragment guestFragment = new GuestFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, guestFragment)
                    .addToBackStack("").commit();

        } else if (id == R.id.nav_my_playlists) {
            //DisplayPlayListsFragment playlists = new DisplayPlayListsFragment();
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playlists)
                   // .addToBackStack("").commit();
        } else if (id == R.id.nav_new_event) {

        } else if (id == R.id.nav_new_playlist) {

        } else if (id == R.id.nav_share_playlist) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onListFragmentInteraction(PlayList item){

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
