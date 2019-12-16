package com.example.phoenix.healthcare;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.phoenix.healthcare.Helper.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class BPM extends Fragment {
    private String TAG = Login.class.getSimpleName();
    private static String url = "http://192.168.8.100/get_BPM.php";
    int BPM;
    RingProgressBar mRingProgressBar;
    Button bpmButton;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bpm, container, false);
        bpmButton = rootView.findViewById(R.id.bpmButton);
        bpmButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                mRingProgressBar = rootView.findViewById(R.id.bpm_progress_bar);
                new GetBPM().execute();
                // Set the progress bar's progress
                int old=mRingProgressBar.getProgress();
                if (BPM!=0) mRingProgressBar.setProgress(BPM);
                else mRingProgressBar.setProgress(Generator.getBC());
                TextView bpm = rootView.findViewById(R.id.bpm);
                bpm.setText(""+old);
                TextView date = rootView.findViewById(R.id.bpmDate);
                date.setText(DateFormat.getDateTimeInstance().format(new Date()));
                Log.v("msg",""+BPM);
            }
        });
        return rootView;
    }

    private class GetBPM extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONArray users = new JSONArray(jsonStr);
                    JSONObject c = users.getJSONObject(0);
                    String bpm = c.getString("BPM");
                    BPM=Integer.parseInt(bpm);
                } catch (final JSONException e) {
                }
            } else {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }

    }


}
