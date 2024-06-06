package el.ps.nextetrucknewtemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Page3MissionActivity extends AppCompatActivity {
    private ListView listView;
    ExecutorService executorService;
    String url = "http://160.40.60.237:8080/nextetruck.mission/rest/server/getMissions";
    private List<String> data;
    private JSONArray jsonArrayData = new JSONArray();

    private ArrayAdapter<String> adapter;
    private int selectionPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3_mission);

        // Create an ArrayList
        data = new ArrayList<>();
        // Initialize ListView
        listView = findViewById(R.id.lvMissionSelect);
        // Create ArrayAdapter to populate ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        // Set the ArrayAdapter as the ListView's adapter
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectionPosition = i;
                // Get the selected item text from ListView
                String selectedItem = (String) adapterView.getItemAtPosition(i);

                // Display a toast message with the selected item
                Toast.makeText(Page3MissionActivity.this, "Pavlos Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        //Dimokas call for missions
        GlobalData globalData = GlobalData.getInstance();
        String name = globalData.getGlobalName();
        String password = globalData.getGlobalPassword();
        Log.i("PAVLOS mission", "Name:"+name + "| Password:"+password);

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

    private void makeHttpRequest(String urlString, String nameString, String passwordString){
        try {
            // Create a URL object from the URL string
            URL url = new URL(urlString);

            // Open a connection to the URL
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Set the request method to GET
            urlConnection.setRequestMethod("GET");

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
        runOnUiThread(() -> {
            try {
                JSONArray jsonArray = new JSONArray(response);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String mission_id = jsonObject.getString("mission_id");
                    String m_title = jsonObject.getString("m_title");
                    //String m_departure_address = jsonObject.getString("m_departure_address");
                    //String m_arrival_address = jsonObject.getString("m_arrival_address");
                    String m_departure_lat = jsonObject.getString("m_departure_lat");
                    String m_departure_long = jsonObject.getString("m_departure_long");
                    String m_arrival_lat = jsonObject.getString("m_arrival_lat");
                    String m_arrival_long = jsonObject.getString("m_arrival_long");
                    Log.i("PAVLOS MISSION", "String mission_id:"+mission_id);
                    data.add(mission_id + " " + m_title);
                    JSONObject jsonObjectData = new JSONObject();
                    jsonObjectData.put("mission_id", mission_id);
                    jsonObjectData.put("m_title", m_title);
                    jsonObjectData.put("m_departure_lat", m_departure_lat);
                    jsonObjectData.put("m_departure_long", m_departure_long);
                    jsonObjectData.put("m_arrival_lat", m_arrival_lat);
                    jsonObjectData.put("m_arrival_long", m_arrival_long);
                    jsonArrayData.put(jsonObjectData);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleError(String errorMessage) {
        // Show error message to the user
        Log.e("Pavlos Error", "HandleError:"+errorMessage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MissionActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MissionActivity", "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MissionActivity", "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MissionActivity", "onStop called");
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
        Log.d("MissionActivity", "onDestroy called");
    }
}