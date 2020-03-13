package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Integer, String>
{
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;
    private static final int CHUNK_SIZE = 5;

    SimpleAsyncTask(TextView tv, ProgressBar pb)
    {
        mTextView = new WeakReference<>(tv);
        mProgressBar = new WeakReference<>(pb);
    }

    @Override
    protected String doInBackground(Void... voids)
    {
        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);
        int p = n * 400;
        int chunkSize = p / CHUNK_SIZE;

        // Make the task take long enough that we have
        // time to rotate the phone while it is running

        // Sleep for the random amount of time
        for (int i = 0; i < CHUNK_SIZE; i++) {
            try {
                Thread.sleep(chunkSize);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(((i + 1) * 100) / CHUNK_SIZE);
        }

        // Return a String result
        return "Awake at last after sleeping for " + p + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        mProgressBar.get().setProgress(values[0]);
    }

    protected void onPostExecute(String result)
    {
        mTextView.get().setText(result);
    }
}
