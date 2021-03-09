package com.example.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.myapplication.AlarmActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.os.Build.VERSION_CODES.R;

public class AlarmsAdapter extends ArrayAdapter<AlarmActivity.AlarmType> {
    private Context mContext;
    private List<AlarmActivity.AlarmType> alarmList = new ArrayList<>();

    public AlarmsAdapter(@NonNull Context context,  ArrayList<AlarmActivity.AlarmType> list) {
        super(context,0, list);
        mContext = context;
        alarmList = list;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(com.example.myapplication.R.layout.alarms_list_item,parent,false);

        AlarmActivity.AlarmType currentAlarm = alarmList.get(position);


        TextView name = (TextView) listItem.findViewById(com.example.myapplication.R.id.name);
        name.setText(currentAlarm.getName());

        TextView clock = (TextView) listItem.findViewById(com.example.myapplication.R.id.time);
        clock.setText(currentAlarm.getHour()+":"+currentAlarm.getMinutes());

//        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch active = (Switch) listItem.findViewById(com.example.myapplication.R.id.active);
//        Boolean temp = false;
//        if(currentAlarm.getActive().toString().equals("true")){
//            temp = true;
//        }
//        active.setChecked(temp);
//        active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                String checkedTemp = "false";
//                if (isChecked)checkedTemp = "true";
//                currentAlarm.setActive(checkedTemp);
//            }
//        });
//
//        Button delBtn = (Button) listItem.findViewById(com.example.myapplication.R.id.deleteBtn);
//        delBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alarmList.remove(position);
//            }
//        });

        return listItem;

    }
}
