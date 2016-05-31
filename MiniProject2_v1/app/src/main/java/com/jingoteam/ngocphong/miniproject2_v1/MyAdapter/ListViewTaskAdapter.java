package com.jingoteam.ngocphong.miniproject2_v1.MyAdapter;

import android.app.Dialog;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.jingoteam.ngocphong.miniproject2_v1.DateManager;
import com.jingoteam.ngocphong.miniproject2_v1.R;
import com.jingoteam.ngocphong.miniproject2_v1.Task;
import com.jingoteam.ngocphong.miniproject2_v1.TaskManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import java.util.zip.CheckedInputStream;

/**
 * Created by NgocPhong on 29/05/2016.
 */
public class ListViewTaskAdapter extends BaseAdapter {
    List<Task> taskList;
    LayoutInflater inflater;
    Context context;

    public ListViewTaskAdapter(Context context, List<Task> tasks){
        this.context = context;
        this.taskList = tasks;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
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

        textView.setText(taskList.get(position).getContent());
        final Task task = taskList.get(position);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dlg = new Dialog(context);
                dlg.setContentView(R.layout.task_detail);
                dlg.setTitle("Task Details");

                TextView content = (TextView)dlg.findViewById(R.id.task_content);
                TextView date = (TextView)dlg.findViewById(R.id.task_date);
                Button remove = (Button)dlg.findViewById(R.id.btn_remove_task);

                content.setText(task.getContent());
                DateTime dateTime = task.getCreatedDate();
                dateTime = dateTime.withZone(DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT+7")));

                date.setText(dateTime.toString());
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TaskManager.removeTask(context, task);
                        dlg.dismiss();
                    }
                });

                dlg.show();
            }
        });

        return rowView;
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
        return false;
    }
}
