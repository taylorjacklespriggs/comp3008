package com.comp3008.piglists;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.GetChars;
import android.util.Log;
import android.view.MenuItem;

import com.comp3008.piglists.model.PlayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PlaylistFragment.OnListFragmentInteractionListener {

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

        //add the playlist fragment to start with
        PlaylistFragment playlistFragment = new PlaylistFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, playlistFragment).commit();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

            if (isConnected){
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
            }else {
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, guestFragment).addToBackStack("").commit();
        } else if (id == R.id.nav_my_playlists) {

        } else if (id == R.id.nav_new_event) {

        } else if (id == R.id.nav_new_playlist) {

        }else if (id == R.id.nav_share_playlist){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(PlayList item) {
        Log.i("MainActivity", "playlist selected: " + item.toString());
    }

    private class ConnectingToEventTask extends AsyncTask<TaskParams, Integer, Boolean> {
        AlertDialog.Builder builder;
        AlertDialog dialog;
        MenuItem item;

        protected Boolean doInBackground(TaskParams... params){
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

        protected void onPostExecute(Boolean result){
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
