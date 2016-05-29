package com.jingoteam.ngocphong.miniproject2_v1.MyAdapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;

import com.jingoteam.ngocphong.miniproject2_v1.R;
import com.jingoteam.ngocphong.miniproject2_v1.Task;

import java.util.List;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.listview_item, null);
        CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.checkbox_task);

        checkBox.setText(taskList.get(position).getContent());

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
