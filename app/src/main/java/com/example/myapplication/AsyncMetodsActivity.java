package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class AsyncMetodsActivity extends AppCompatActivity {


    TextView process;
    ProgressBar progressBar;
    Button startAsync;
    Button stopAsync;
    MyTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_metods);
        process = findViewById(R.id.process);
        progressBar = findViewById(R.id.progressBar);
        startAsync = findViewById(R.id.startAsync);
        stopAsync = findViewById(R.id.stopAsync);

        startAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new MyTask();
                task.execute();
            }
        });

        stopAsync.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                task.cancel(true);
                task.onCancelled();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class MyTask extends AsyncTask <Void, Integer, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int counter = 0;
                for(int i = 0; i < 100; i++){
                    TimeUnit.SECONDS.sleep(3);
                    publishProgress(++counter);
                    if (isCancelled()) {
                        return null;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            process.setText(task.getStatus().toString());
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            process.setText(task.getStatus().toString()+": " + values[0]+"%");
            progressBar.setProgress((Integer) values[0]);
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPreExecute();
            process.setText(task.getStatus().toString());
            progressBar.setProgress(0);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            process.setText("Stopped");
            progressBar.setProgress(0);
        }
    }
}

