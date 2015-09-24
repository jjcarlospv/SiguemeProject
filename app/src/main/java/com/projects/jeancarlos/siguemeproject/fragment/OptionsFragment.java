package com.projects.jeancarlos.siguemeproject.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.projects.jeancarlos.siguemeproject.R;

/**
 * Created by JEANCARLOS on 20/09/2015.
 */
public class OptionsFragment extends Fragment implements View.OnClickListener {

    private Button fragment_option_start;
    private Button fragment_option_list;
    private Button fragment_option_map;
    private Button fragment_option_cancel;
    private InterfaceOptions interfaceOptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_options,container,false);

        fragment_option_start = (Button)view.findViewById(R.id.fragment_option_start);
        fragment_option_list = (Button)view.findViewById(R.id.fragment_option_list);
        fragment_option_cancel = (Button)view.findViewById(R.id.fragment_option_cancel);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragment_option_start.setOnClickListener(this);
        fragment_option_list.setOnClickListener(this);
        fragment_option_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.fragment_option_start:
                interfaceOptions.getOption(0);
                break;

            case R.id.fragment_option_list:
                interfaceOptions.getOption(1);
                break;

            /*case R.id.fragment_option_pause:
                interfaceOptions.getOption(2);
                break;*/

            case R.id.fragment_option_cancel:
                interfaceOptions.getOption(3);
                break;
        }
    }

    /**
     * Interface
     */
    public interface InterfaceOptions{
        void getOption(int i);
    }

    public void setInterfaceOptions(InterfaceOptions interfaceOptions){
        this.interfaceOptions = interfaceOptions;
    }
}
