package el.ps.nextetrucknewtemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Page2LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText etName, etPassword;
    String url = "http://160.40.60.237:8080/nextetruck.mission/rest/server/login";
    ExecutorService executorService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2_login);

        btnLogin = findViewById(R.id.btnLogin);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);

        btnLogin.setOnClickListener(new LoginClickListener());
    }

    private class LoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //get the name and the password
            String name, password;
            name = etName.getText().toString();
            password = etPassword.getText().toString();
            Log.i("PAVLOS", "Name:"+name + "| Password:"+password);

            // Create a new thread pool with a single thread
            executorService = Executors.newSingleThreadExecutor();

            // Submit a task to the thread pool for making the HTTP request
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    makeHttpRequest(url, name, password);
                }
            });
        }
    }

    private void makeHttpRequest(String urlString, String nameString, String passwordString){
        try {
            // Create a URL object from the URL string
            URL url = new URL(urlString);

            // Open a connection to the URL
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            urlConnection.setRequestMethod("POST");

            // Add Basic Authentication header
            String auth = nameString + ":" + passwordString;
            String encodedAuth = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty("Authorization", "Basic " + encodedAuth);

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Get the input stream of the connection
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                // Read the input stream line by line
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Close the streams
                in.close();

                // Save Name and Password on global variables
                GlobalData globalData = GlobalData.getInstance();
                globalData.setGlobalName(nameString);
                globalData.setGlobalPassword(passwordString);

                // Handle the response (e.g., update UI)
                handleResponse(content.toString());
            }
            else{
                Log.w("Pavlos HttpRequests", "Non-OK response: " + responseCode + " for URL: " + urlString);
                //Alert Check Credentials
            }

            // Close the connection
            urlConnection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions (e.g., show error message)
            handleError(e.getMessage());
        }
    }

    private void handleResponse(String response) {
        // Update UI with the response
        Log.d("HTTP Response:", response);
        try {
            JSONObject jsonObject = new JSONObject(response);

            // Assuming JSON object has key "name" and "age"
            String user_id = jsonObject.getString("user_id");

            //Log.i("PAVLOS", "String user_id:"+user_id);
            int usr_id = Integer.parseInt(user_id);
            Log.i("PAVLOS", "Integer user_id:"+user_id);
            if(usr_id>0 && usr_id<1000){
                Log.i("PAVLOS", "Login User");
                Intent i = new Intent(Page2LoginActivity.this, Page3MissionActivity.class);
                startActivity(i);
            }
            else{
                Log.i("PAVLOS", "Throw Out User");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(String errorMessage) {
        // Show error message to the user
        Log.e("Pavlos Error", "HandleError:"+errorMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor service when the activity is destroyed
        try {
            // Shutdown the executor service when the activity is destroyed
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions during shutdown (optional)
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LoginActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LoginActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LoginActivity", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LoginActivity", "onStop called");
    }
}