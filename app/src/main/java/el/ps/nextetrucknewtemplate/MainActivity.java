package el.ps.nextetrucknewtemplate;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnEnter;
    Button btnPrivacyDisclaimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Page2LoginActivity.class);
                startActivity(i);
            }
        });

        btnPrivacyDisclaimer = findViewById(R.id.btnPrivacyDisclaimer);
        btnPrivacyDisclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, PrivacyDisclaimer.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Start tasks or refresh UI
        Log.d("MainActivity", "onStart called");
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Resume tasks or update UI
        Log.d("MainActivity", "onResume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Pause tasks or save data
        Log.d("MainActivity", "onPause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Stop tasks or release resources
        Log.d("MainActivity", "onStop called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cleanup resources
        Log.d("MainActivity", "onDestroy called");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        // Reinitialize resources or restart tasks
        Log.d("MainActivity", "onRestart called");
    }
}