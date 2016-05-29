package com.jingoteam.ngocphong.miniproject2_v1.MyFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingoteam.ngocphong.miniproject2_v1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalTask extends Fragment {


    public GlobalTask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global_task, container, false);
    }

}
