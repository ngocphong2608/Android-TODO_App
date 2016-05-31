package com.jingoteam.ngocphong.miniproject2_v1;

import android.app.ActionBar;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jingoteam.ngocphong.miniproject2_v1.MyAdapter.ListViewTaskAdapter;
import com.jingoteam.ngocphong.miniproject2_v1.MyFragment.AllTask;
import com.jingoteam.ngocphong.miniproject2_v1.MyFragment.GlobalTask;

import org.joda.time.DateTime;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final Context context = this;
    Toolbar toolbar;
    FloatingActionButton fab;
    NavigationView navigationView;

    protected void onPause(){
        super.onPause();

        TaskManager.saveAllTask(this);
        ConfigManager.saveAllConfig(this);
    }

    protected void onResume(){
        super.onResume();

        TaskManager.loadAllTask(this);
    }

    protected void onDestroy(){
        super.onDestroy();

        TaskManager.saveAllTask(this);
        ConfigManager.saveAllConfig(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConfigManager.loadAllConfig(this);

        if (ConfigManager.isFirstUse){
            setContentView(R.layout.setting_dialog);
            Button btn = (Button)findViewById(R.id.btn_setting_ok);

            RadioGroup group = (RadioGroup)findViewById(R.id.rg_task_per_day);

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.task_per_day_3) {
                        ConfigManager.taskPerDay = 3;
                    } else if (checkedId == R.id.task_per_day_5) {
                        ConfigManager.taskPerDay = 5;
                    } else if (checkedId == R.id.task_per_day_7) {
                        ConfigManager.taskPerDay = 7;
                    }
                }
            });

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startMainActivity();
                }
            });
        } else {
            startMainActivity();
        }


    }

    public void startMainActivity(){
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
                            boolean succ = TaskManager.addTask(context, task);

                            if (succ) {
                                // refresh list view
                                refreshListViewTask();
                            } else {
                                Toast.makeText(context, "You have enough " + ConfigManager.taskPerDay + " Tasks", Toast.LENGTH_SHORT).show();
                            }
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
            Dialog dlg = new Dialog(this);
            dlg.setTitle("Setting");
            dlg.setContentView(R.layout.setting_dialog);

            RadioButton button;
            RadioGroup group = (RadioGroup) dlg.findViewById(R.id.rg_task_per_day);

            if (ConfigManager.taskPerDay == 3) {
                button = (RadioButton) dlg.findViewById(R.id.task_per_day_3);
                button.setChecked(true);
            } else if (ConfigManager.taskPerDay == 5){
                button = (RadioButton)dlg.findViewById(R.id.task_per_day_5);
                button.setChecked(true);
            } else if (ConfigManager.taskPerDay == 7){
                button = (RadioButton)dlg.findViewById(R.id.task_per_day_7);
                button.setChecked(true);
            }

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.task_per_day_3) {
                        ConfigManager.taskPerDay = 3;
                    } else if (checkedId == R.id.task_per_day_5) {
                        ConfigManager.taskPerDay = 5;
                    } else if (checkedId == R.id.task_per_day_7) {
                        ConfigManager.taskPerDay = 7;
                    }
                }
            });

            dlg.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DateTime now = DateTime.now();
        boolean isFinished = false;

        if (id == R.id.today_task) {
            setTitle("Today Tasks");
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.TODAY;
        } else if (id == R.id.yesterday_task){
            setTitle("Yesterday Tasks");
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.YESTERDAY;
            now = now.minusDays(1);
        } else if (id == R.id.tomorrow_task){
            setTitle("Tomorrow Tasks");
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.TOMORROW;
            now = now.plusDays(1);
        } else if (id == R.id.finished_task){
            setTitle("Finished Tasks");
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.FINISHED;
            isFinished = true;
        } else if (id == R.id.unfinished_task){
            setTitle("UnFinished Tasks");
            ConfigManager.selectedItem = ConfigManager.MENU_ITEM.UNFINISHED;
            isFinished = false;
        }

        switch (id) {
            case R.id.today_task:
            case R.id.tomorrow_task:
            case R.id.yesterday_task: {
                fab.setVisibility(View.VISIBLE);

                GlobalTask fragment = new GlobalTask();
                fragment.setDate(now);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                break;
            }
            case R.id.finished_task:
            case R.id.unfinished_task:{
                fab.setVisibility(View.INVISIBLE);

                AllTask allTask = new AllTask();
                allTask.setFinished(isFinished);

                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, allTask);
                fragmentTransaction.commit();
                break;
            }

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
