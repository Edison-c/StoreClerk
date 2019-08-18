package com.example.storeclerk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;


public class BlankFragment extends Fragment implements View.OnClickListener {
    BlankFragment.resultInterface4 callback;
    private Button refresh;
    private  View v;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_blank, container, false);
        refresh = v.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view){
        if (view.getId()==R.id.refresh) {
                callback.onReturnResults4("refresh");
        }
    }

    public void setCallback(BlankFragment.resultInterface4 callback) {
        this.callback = callback;
    }

    public interface resultInterface4 {
        void onReturnResults4(String refresh);
    }
}


