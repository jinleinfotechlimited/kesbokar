package com.kesbokar.kesbokar;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusBusinessFragment extends Fragment {

    RadioGroup rgStatus;
    Button btnBack, btnSubmit;
    String condition;

    public StatusBusinessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_business, container, false);

        rgStatus = view.findViewById(R.id.rgStatus);
        btnBack = view.findViewById(R.id.btnBack);
        btnSubmit= view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        rgStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbActive:condition = "rbActive";
                        break;

                    case R.id.rbDeactive:condition = "rbDeactive";
                }
            }
        });


        return view;
    }

}