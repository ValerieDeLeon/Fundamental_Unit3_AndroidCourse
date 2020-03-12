package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Integer, String>
{
    private WeakReference<TextView> mTextView;
    private ProgressBar mProgressBar;
    private Handler mHandler = new Handler();
    SimpleAsyncTask(TextView tv, ProgressBar pb)
    {
        mTextView = new WeakReference<>(tv);
        mProgressBar = pb;
    }

    @Override
    protected void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take long enough that we have
        // time to rotate the phone while it is running
        int s = n * 200;

        // Sleep for the random amount of time
        try {
            publishProgress(s);
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return a String result
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        mProgressBar.setProgress(values[0]);
    }

    protected void onPostExecute(String result)
    {
        mTextView.get().setText(result);
        mProgressBar.setVisibility(View.VISIBLE);
    }
}
