package com.jingoteam.ngocphong.miniproject2_v1.adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.jingoteam.ngocphong.miniproject2_v1.activity.MainActivity;
import com.jingoteam.ngocphong.miniproject2_v1.R;
import com.jingoteam.ngocphong.miniproject2_v1.Task;
import com.jingoteam.ngocphong.miniproject2_v1.TaskManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;
import java.util.TimeZone;

/**
 * Created by NgocPhong on 29/05/2016.
 */
public class ListViewTaskAdapter extends BaseAdapter {
    List<Task> taskList;

    LayoutInflater inflater;
//    Context context;
    MainActivity activity;

    public ListViewTaskAdapter(MainActivity activity, List<Task> tasks){
//        this.context = context;
        this.taskList = tasks;
        this.activity = activity;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.listview_item, null);
        CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.checkbox_task);
        TextView textView = (TextView)rowView.findViewById(R.id.et_task_content);

//        DateFormat date = new SimpleDateFormat("dd-MM-yyy HH:mm:ss z");
//        date.setTimeZone(TimeZone.getTimeZone("GMT+7"));
//        String localTime = date.format(taskList.get(position).getCreatedDate());


        final Task task = taskList.get(position);
        textView.setText(task.getContent());
        checkBox.setChecked(task.isFinished());


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dlg = new Dialog(activity);

                dlg.setContentView(R.layout.task_detail);
                dlg.setTitle("Task Details");

                final EditText content = (EditText)dlg.findViewById(R.id.task_content);
                TextView date = (TextView)dlg.findViewById(R.id.task_date);
                Button remove = (Button)dlg.findViewById(R.id.btn_remove_task);
                Button save = (Button)dlg.findViewById(R.id.btn_save_task);

                content.setText(task.getContent());

                DateTime dateTime = task.getCreatedDate();
                dateTime = dateTime.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));
                date.setText("Created date: " + dateTime.toLocalDate().toString());

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TaskManager.removeTask(task);
                        taskList.remove(task);

                        dlg.dismiss();
                        refreshListView();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        task.setContent(content.getText().toString());
                        dlg.dismiss();
                        refreshListView();
                    }
                });

                dlg.show();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                task.setFinished(isChecked);

            }
        });

        return rowView;
    }

    public void refreshListView(){
        activity.refreshListViewTask();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return taskList.size() == 0;
    }
}
