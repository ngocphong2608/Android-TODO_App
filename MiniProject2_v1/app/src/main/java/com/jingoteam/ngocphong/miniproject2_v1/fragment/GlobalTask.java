package com.jingoteam.ngocphong.miniproject2_v1.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jingoteam.ngocphong.miniproject2_v1.activity.MainActivity;
import com.jingoteam.ngocphong.miniproject2_v1.adapter.ListViewTaskAdapter;
import com.jingoteam.ngocphong.miniproject2_v1.R;
import com.jingoteam.ngocphong.miniproject2_v1.Task;
import com.jingoteam.ngocphong.miniproject2_v1.TaskManager;

import org.joda.time.DateTime;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalTask extends Fragment {
    private DateTime date;
    private ListView myListView = null;
    private ListViewTaskAdapter adapter;

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

        myListView = (ListView)view.findViewById(R.id.global_task_listview);
        adapter = new ListViewTaskAdapter((MainActivity)getActivity(), tasks);
        adapter.notifyDataSetChanged();

        myListView.setAdapter(adapter);

        return view;
    }

}
