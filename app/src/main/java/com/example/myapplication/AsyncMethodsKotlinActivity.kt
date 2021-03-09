//package com.example.myapplication
//
//import android.annotation.SuppressLint
//import android.os.AsyncTask
//import android.os.Bundle
//import android.view.View
//import android.widget.Button
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.loader.content.AsyncTaskLoader
//import java.util.concurrent.TimeUnit
////coroutine kotlin
//class AsyncMethodsKotlinActivity : AppCompatActivity() {
//    var process: TextView? = null
//    var progressBar: ProgressBar? = null
//    var startAsync: Button? = null
//    var stopAsync: Button? = null
//    var task: AsyncMetodsActivity.MyTask? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_async_methods_kotlin)
//        process = findViewById(R.id.process)
//        progressBar = findViewById(R.id.progressBar)
//        startAsync = findViewById(R.id.startAsync)
//        stopAsync = findViewById(R.id.stopAsync)
//
//        startAsync?.setOnClickListener(View.OnClickListener {
//            task = MyTaskKotlin();
//            task!!.execute()
//        })
//
//        stopAsync?.setOnClickListener(View.OnClickListener {
//            task!!.cancel(true)
//            task!!.onCancelled()
//        })
//    }
//
//    fun onProgres(vararg values: Int){
//        process!!.text = task!!.status.toString() + ": " + values[0] + "%";
//        progressBar!!.progress = values[0];
//    }
//
//
//    class MyTaskKotlin() : AsyncTask<Void?, Integer?, Void?>(){
//        override fun doInBackground(vararg params: Void?): Void? {
//            try {
//                var counter = 0
//                for (i in 0..99) {
//                    TimeUnit.SECONDS.sleep(3)
//                    publishProgress(++counter)
//                    if (isCancelled()) {
//                        return null;
//                    }
//                }
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//            return null
//        }
//
//        override fun onPreExecute() {
//            super.onPreExecute();
//            this.process!!.text = this.task!!.status.toString()
//        }
//
//        fun onProgressUpdate(vararg values: Int) {
//            this.onProgres(values);
//            this.process!!.text = this.task!!.status.toString() + ": " + values[0] + "%"
//            this.progressBar!!.progress = values[0]
//        }
//
//
//        override fun onPostExecute(result: Void?) {
//            this.process!!.text = this.task!!.status.toString()
//            this.progressBar!!.progress = 0
//        }
//
//        override fun onCancelled() {
//            this.process!!.text = "Stopped"
//            this.progressBar!!.progress = 0
//        }
//    }
//}