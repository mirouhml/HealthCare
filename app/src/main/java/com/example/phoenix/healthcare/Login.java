package com.example.phoenix.healthcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phoenix.healthcare.Helper.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Phoenix on 3/31/2018.
 */

public class Login extends AppCompatActivity {
    private String TAG = Login.class.getSimpleName();
    EditText emailET,passwordET;
    TextView headerEmail;
    private ProgressDialog pDialog;
    //10.0.2.2
    private static String url = "http://192.168.8.100/get_data.php";
    ArrayList<HashMap<String, String>> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        contactList = new ArrayList<>();
        emailET=findViewById(R.id.email);
        passwordET=findViewById(R.id.password);
        headerEmail=findViewById(R.id.header_email);
        new GetContacts().execute();
    }
    public void logIn(View view) {
        String username = emailET.getText().toString();
        String password = passwordET.getText().toString();
        for(int i=0;i<contactList.size();i++){
            String Email=contactList.get(i).get("email");
            String Password=contactList.get(i).get("password");
            if (username.equals(Email) && password.equals(Password) ){
                Intent intent = new Intent(Login.this,SlidePane.class);
                startActivity(intent);
                finish();
                break;
            }

        }
        if (username.equals("admin") && password.equals("admin") ){
            Intent intent = new Intent(Login.this,SlidePane.class);
            startActivity(intent);
            finish();
        }
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

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

                    // looping through All Contacts
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);

                        String email = c.getString("Email");
                        String password = c.getString("Password");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("email", email);
                        contact.put("password", password);


                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

        }

    }


}
