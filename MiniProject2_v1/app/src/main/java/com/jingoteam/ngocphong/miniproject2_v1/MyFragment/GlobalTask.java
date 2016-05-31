package com.jingoteam.ngocphong.miniproject2_v1.MyFragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jingoteam.ngocphong.miniproject2_v1.MyAdapter.ListViewTaskAdapter;
import com.jingoteam.ngocphong.miniproject2_v1.R;
import com.jingoteam.ngocphong.miniproject2_v1.Task;
import com.jingoteam.ngocphong.miniproject2_v1.TaskManager;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalTask extends Fragment {
    private DateTime date;

    public void setDate(DateTime d){
        date = d;
    }

    public GlobalTask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_global_task, container, false);

        List<Task> tasks = TaskManager.getTaskList(date);

        ListView listView = (ListView)view.findViewById(R.id.global_task_listview);
        ListViewTaskAdapter listViewTaskAdapter = new ListViewTaskAdapter(getContext(), tasks);
        listView.setAdapter(listViewTaskAdapter);


        return view;
    }

}
