package com.jingoteam.ngocphong.miniproject2_v1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jingoteam.ngocphong.miniproject2_v1.MyAdapter.ListViewTaskAdapter;
import com.jingoteam.ngocphong.miniproject2_v1.MyFragment.GlobalTask;

import org.joda.time.DateTime;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    Toolbar toolbar;
    FloatingActionButton fab;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // main page
        GlobalTask fragment = new GlobalTask();
        fragment.setDate(DateTime.now());
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dlg = new Dialog(context);
                dlg.setContentView(R.layout.new_task);
                dlg.setTitle("New Task");

                Button add = (Button)dlg.findViewById(R.id.add_task);
                final EditText etContent = (EditText)dlg.findViewById(R.id.task_content);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String content = etContent.getText().toString();
                        DateTime d = DateTime.now();

                        if (ConfigManager.selectedItem == ConfigManager.MENU_ITEM.YESTERDAY) {
                            d = d.minusDays(1);
                        } else if (ConfigManager.selectedItem == ConfigManager.MENU_ITEM.TOMORROW){
                            d = d.plusDays(1);
                        }

                        if (!content.contentEquals("")){
                            Task task = new Task(content, d);
                            TaskManager.addTask(context, task);

                            // refresh list view
                            refreshListViewTask();
                        }

                        dlg.dismiss();
                    }
                });

                dlg.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TaskManager.loadAllTask(this);
    }

    public void refreshListViewTask() {
        ListView listView = (ListView)findViewById(R.id.global_task_listview);
        DateTime now = DateTime.now();

        if (ConfigManager.selectedItem == ConfigManager.MENU_ITEM.YESTERDAY){
            now = now.minusDays(1);
        } else if (ConfigManager.selectedItem == ConfigManager.MENU_ITEM.TOMORROW){
            now = now.plusDays(1);
        }

        listView.setAdapter(new ListViewTaskAdapter(this, TaskManager.getTaskList(now)));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        GlobalTask fragment = new GlobalTask();
        DateTime now = DateTime.now();

        if (id == R.id.today_task) {
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.TODAY;
            fragment.setDate(now);
        } else if (id == R.id.yesterday_task){
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.YESTERDAY;
            fragment.setDate(now.minusDays(1));
        } else if (id == R.id.tomorrow_task){
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.TOMORROW;
            fragment.setDate(now.plusDays(1));
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
