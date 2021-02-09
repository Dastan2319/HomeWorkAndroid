package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class BlankFragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param2";

    private String name;
    private String dates;
    private String meaning;

    public TextView Textname;
    public TextView Textdates;
    public TextView Textmeaning;
    public Button back;
    public static BlankFragment2 newInstance(String param1, String param2, String param3) {
        BlankFragment2 fragment = new BlankFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM2, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment2() {
        super(R.layout.fragment_names_detail);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            dates = getArguments().getString(ARG_PARAM2);
            meaning = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_names_detail, container, false);
        Textname =  view.findViewById(R.id.textName);
        Textdates = view.findViewById(R.id.TextDates);
        Textmeaning = view.findViewById(R.id.TextMeaning);
        back = view.findViewById(R.id.backBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameLayout, new BlankFragment3(), null)
                        .commit();
            }
        });
        Textname.setText(name);
        Textdates.setText(dates);
        Textmeaning.setText(meaning);
        return view;
    }
}