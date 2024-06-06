package el.ps.nextetrucknewtemplate;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronousHttpRequests {
    // Main executor for background tasks
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private String sUrl;
    private String sUsername;
    private String sPassword;
    private int responseCode;
    private Boolean loggedInValue = false;

    public Boolean executeLoginRequests(String urlString, String username, String password) {
        sUrl = urlString;
        sUsername = username;
        sPassword = password;
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Dimokas login request
                    //String username = "nextuser1";
                    //String password = "JDM/Om5kUCExITJA";
                    String response = makeHttpLoginRequest(sUrl, sUsername, sPassword);
                    Log.d("HTTP Response:", response);
                } catch (Exception e) {
                    Log.e("SynchronousHttpRequests", "Error during HTTP requests", e);
                    e.printStackTrace();
                }
            }
        });
        return loggedInValue;
    }

    private String makeHttpLoginRequest(String urlString, String username, String password) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestMethod("POST");

            // Add Basic Authentication header
            String auth = username + ":" + password;
            String encodedAuth = Base64.encodeToString(auth.getBytes(), Base64.NO_WRAP);
            urlConnection.setRequestProperty("Authorization", "Basic " + encodedAuth);

            responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                loggedInValue = true;
                return response.toString();
            } else {
                Log.w("SynchronousHttpRequests", "Non-OK response: " + responseCode + " for URL: " + urlString);
                if (responseCode == HttpURLConnection.HTTP_ACCEPTED) {
                    // Handle 202 Accepted specifically if needed
                    loggedInValue = true;
                    return "Request accepted, but processing not complete.";
                } else {
                    loggedInValue = false;
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
