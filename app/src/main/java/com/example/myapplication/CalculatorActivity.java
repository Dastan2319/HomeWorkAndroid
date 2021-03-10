package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    TextView plus;
    TextView minus;
    TextView multiply;
    TextView division;

    TextView clear;
    TextView ea;

    TextView num1;
    TextView num2;
    TextView num3;

    TextView num4;
    TextView num5;
    TextView num6;

    TextView num7;
    TextView num8;
    TextView num9;

    TextView num0;
    TextView mainBody;

    String body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mainBody = findViewById(R.id.mainBody);

        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);
        multiply=findViewById(R.id.multiply);
        division=findViewById(R.id.division);
        clear=findViewById(R.id.clearMainBody);
        ea=findViewById(R.id.ea);

        num1=findViewById(R.id.num1);
        num2=findViewById(R.id.num2);
        num3=findViewById(R.id.num3);
        num4=findViewById(R.id.num4);
        num5=findViewById(R.id.num5);
        num6=findViewById(R.id.num6);
        num7=findViewById(R.id.num7);
        num8=findViewById(R.id.num8);
        num9=findViewById(R.id.num9);
        num0=findViewById(R.id.num0);


    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onButtonClick(View v){
        switch(v.getId()) {

            case R.id.num0:
                mainBody.setText(mainBody.getText()+"0");
                break;

            case R.id.num1:
                mainBody.setText(mainBody.getText()+"1");
                break;

            case R.id.num2:
                mainBody.setText(mainBody.getText()+"2");
                break;

            case R.id.num3:
                mainBody.setText(mainBody.getText()+"3");
                break;

            case R.id.num4:
                mainBody.setText(mainBody.getText()+"4");
                break;

            case R.id.num5:
                mainBody.setText(mainBody.getText()+"5");
                break;

            case R.id.num6:
                mainBody.setText(mainBody.getText()+"6");
                break;

            case R.id.num7:
                mainBody.setText(mainBody.getText()+"7");
                break;

            case R.id.num8:
                mainBody.setText(mainBody.getText()+"8");
                break;

            case R.id.num9:
                mainBody.setText(mainBody.getText()+"9");
                break;


            case R.id.clearMainBody:
                mainBody.setText("");
                break;

            case R.id.ea:
//                String get = mainBody.getText().toString().
                String body =mainBody.getText().toString();
                String[] temp;
                int answer = 0;
                if(body.contains("+")){
                    temp = mainBody.getText().toString().split("\\+");
                    answer = Integer.parseInt(temp[0]) + Integer.parseInt(temp[1]);
                }
                if(body.contains("/")){
                    temp = mainBody.getText().toString().split("/");
                    answer = Integer.parseInt(temp[0]) / Integer.parseInt(temp[1]);
                }

                if(body.contains("-")){
                    temp = mainBody.getText().toString().split("-");
                    answer = Integer.parseInt(temp[0]) - Integer.parseInt(temp[1]);
                }
                if(body.contains("X")){
                    temp = mainBody.getText().toString().split("X");
                    answer = Integer.parseInt(temp[0]) * Integer.parseInt(temp[1]);
                }
                mainBody.setText(String.valueOf(answer));
                break;

            case R.id.division:
                mainBody.setText(mainBody.getText()+"/");
                break;
            case R.id.multiply:
                mainBody.setText(mainBody.getText()+"X");
                break;
            case R.id.minus:
                mainBody.setText(mainBody.getText()+"-");
                break;
            case R.id.plus:
                mainBody.setText(mainBody.getText()+"+");
                break;


        }
    }
}