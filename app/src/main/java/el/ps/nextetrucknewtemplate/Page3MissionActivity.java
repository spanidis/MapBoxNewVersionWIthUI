package el.ps.nextetrucknewtemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private String jsonArrayData;
    private ArrayAdapter<String> adapter;
    private int selectionPosition = 0;
    Button btnProceed;
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
                Toast.makeText(Page3MissionActivity.this, "Selection: " + selectedItem, Toast.LENGTH_SHORT).show();

            }
        });

        btnProceed = findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the Coords function
                if(selectionPosition !=0)
                {
                    getCoordsFromArray(selectionPosition);
                    //Log.i("PAVLOS ARRAY", jsonArrayData.toString());
                }
                else{
                    Toast.makeText(Page3MissionActivity.this, "Please select a Mission", Toast.LENGTH_SHORT).show();
                }
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
                makeDimokasHttpRequest(url, name, password);
            }
        });

    }

    private void getCoordsFromArray(int itemPosition){
        try {
            JSONArray jsonArray = new JSONArray(jsonArrayData);
            //Log.i("SELECTED MISSION", "String mission_id:"+jsonArray.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(itemPosition);
            //Log.i("SELECTED MISSION", "String mission_id:"+jsonObject.toString());
            String mission_id = jsonObject.getString("mission_id");
            //String m_title = jsonObject.getString("m_title");
            //String m_departure_lat = jsonObject.getString("m_departure_lat");
            //String m_departure_long = jsonObject.getString("m_departure_long");
            //String m_arrival_lat = jsonObject.getString("m_arrival_lat");
            //String m_arrival_long = jsonObject.getString("m_arrival_long");
            Log.i("SELECTED MISSION", "String mission_id:"+mission_id);

            //Call NNG - 3 CALLS
            new SynchronousHttpRequests().executeRequests();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class SynchronousHttpRequests {

        // Main executor for background tasks
        private ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Handler to post results to the main thread
        private Handler handler = new Handler(Looper.getMainLooper());

        public void executeRequests() {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // First HTTP request
                        String response1 = makeHttpRequest("http://nextetruck.nng.com:5001/?ApiKey=WsJhIClUoGhV1ohCNFnAxk5tJT7hAd6X3qkIhbgBNOMMXIpgsgd1WUoFKahck8wW&SetPathFormat=CompressedJSON&Vehicle=Certh_Vehicle1");
                        Log.d("HTTP Response 1", response1);

                        if(response1.equals("Request accepted, but processing not complete."))
                        {
                            // Second HTTP request
                            String response2 = makeHttpRequest("http://nextetruck.nng.com:5001/?ApiKey=WsJhIClUoGhV1ohCNFnAxk5tJT7hAd6X3qkIhbgBNOMMXIpgsgd1WUoFKahck8wW&TargetCoordinate=47.527923,19.034228&Vehicle=Certh_Vehicle1");
                            Log.d("HTTP Response 2", response2);

                            if(response2.equals("Request accepted, but processing not complete."))
                            {
                                // Third HTTP request
                                //StringBuffer stringBuffer = new StringBuffer();
                                //String response3 = makeHttpRequest("http://nextetruck.nng.com:5001/?ApiKey=WsJhIClUoGhV1ohCNFnAxk5tJT7hAd6X3qkIhbgBNOMMXIpgsgd1WUoFKahck8wW&SourceCoordinate=47.679449,19.654151:3.51&Vehicle=Certh_Vehicle1");
                                //stringBuffer.append(makeHttpRequest("http://nextetruck.nng.com:5001/?ApiKey=WsJhIClUoGhV1ohCNFnAxk5tJT7hAd6X3qkIhbgBNOMMXIpgsgd1WUoFKahck8wW&SourceCoordinate=46.401017,20.307006:3.51&Vehicle=Certh_Vehicle1"));
                                //Log.d("HTTP Response 3", response3);
                                JSONObject jsonObject = new JSONObject(makeHttpRequest("http://nextetruck.nng.com:5001/?ApiKey=WsJhIClUoGhV1ohCNFnAxk5tJT7hAd6X3qkIhbgBNOMMXIpgsgd1WUoFKahck8wW&SourceCoordinate=46.401017,20.307006:3.51&Vehicle=Certh_Vehicle1"));
                                //Log.d("HTTP Response 3", stringBuffer.toString());
                                Log.d("HTTP Response 3", jsonObject.get("VehicleName").toString());
                                Log.d("HTTP Response 3", jsonObject.get("RoadSegmentIds").toString());
                                Log.d("HTTP Response 3", jsonObject.get("Length").toString());
                                //Call Zsolt Class
                            }
                        }

                        // Post results to the main thread if necessary
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // Update UI with results here if needed
                            }
                        });
                    } catch (Exception e) {
                        Log.e("SynchronousHttpRequests", "Error during HTTP requests", e);
                        e.printStackTrace();
                    }
                }
            });
        }

        private String makeHttpRequest(String urlString) throws Exception {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setRequestMethod("GET");
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    return response.toString();
                } else {
                    Log.w("SynchronousHttpRequests", "Non-OK response: " + responseCode + " for URL: " + urlString);
                    if (responseCode == HttpURLConnection.HTTP_ACCEPTED) {
                        // Handle 202 Accepted specifically if needed
                        return "Request accepted, but processing not complete.";
                    } else {
                        throw new Exception("Failed to make request: " + responseCode);
                    }
                }
            }
            catch (Exception e) {
                Log.e("SynchronousHttpRequests", "Error in makeHttpRequest: " + urlString, e);
                throw e;
            }
            finally {
                urlConnection.disconnect();
            }
        }
    }

    private void makeDimokasHttpRequest(String urlString, String nameString, String passwordString){
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
        jsonArrayData = response;
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
                    //String m_departure_lat = jsonObject.getString("m_departure_lat");
                    //String m_departure_long = jsonObject.getString("m_departure_long");
                    //String m_arrival_lat = jsonObject.getString("m_arrival_lat");
                    //String m_arrival_long = jsonObject.getString("m_arrival_long");
                    Log.i("PAVLOS MISSION", "String mission_id:"+mission_id);
                    data.add(mission_id + " " + m_title);
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