package com.example.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class BlankFragment3 extends Fragment {

    GridView gridView;

    ArrayList<NameModel> names = new ArrayList<NameModel>();

    String[] tempName ={"Alex", "Jango" , "Js" , "Java"};
    public BlankFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        for (String item : tempName) {
            names.add(new NameModel(item,"Test"+item,""));
        }
        gridView.setAdapter(new ArrayAdapter<NameModel>(requireContext(), android.R.layout.simple_selectable_list_item , names){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position,convertView,parent);
                TextView tv = (TextView) view;
                RelativeLayout.LayoutParams lp =  new RelativeLayout.LayoutParams(
                        GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT
                );
                tv.setLayoutParams(lp);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();
                tv.setLayoutParams(params);
                tv.setHeight(150);
                tv.setGravity(Gravity.CENTER);
                tv.setText(names.get(position).getName());
                tv.setBackgroundColor(Color.parseColor("#FF82DE81"));
                return tv;

            }


        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.frameLayout, BlankFragment2.newInstance(names.get(position).getName(),names.get(position).getNameDate(),names.get(position).getMeaning()), null)
                        .commit();

            }
        });
        return view;
    }

}