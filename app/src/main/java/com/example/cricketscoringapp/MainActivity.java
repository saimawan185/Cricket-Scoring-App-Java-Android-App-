package com.example.cricketscoringapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private  Button firstTeam, secondTeam, nextBtn;
    private  EditText numberOfOvers;

    private  TextView latitude, longitude, altitude;

    private TrackGPS gps;

    private static final int REQUEST_CODE_PERMISSION = 1;
    String[] mPermission = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        altitude = findViewById(R.id.altitude);

        firstTeam = (Button) findViewById(R.id.firstTeamBtn);
        secondTeam =(Button) findViewById(R.id.secondTeamBtn);
        nextBtn =(Button) findViewById(R.id.nextBtn);
        numberOfOvers =(EditText) findViewById(R.id.numberOfOvers);

        firstTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterFirstTeam.class);
                startActivity(intent);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = numberOfOvers.getText().toString().trim();

                if(TextUtils.isEmpty(input))
                {
                    showDialogBox("Error", "Overs cannot be empty!");
                }
                else
                {
                    int overs = Integer.parseInt(input);

                    if (overs < 1 || overs > 20) {
                        showDialogBox("Error", "Number of overs must be between 1 and 20.");
                    } else {
                        showDialogBox("Success", "Number of overs is valid!");
                    }
                }
            }
        });

        if(Build.VERSION.SDK_INT>= 23) {

            if (checkSelfPermission(mPermission[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, mPermission, REQUEST_CODE_PERMISSION);
                return;
            }
            else {
                initializeGps();
            }
        }
        else {
            initializeGps();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        gps.stopGPS();
    }

    private void initializeGps()
    {
        gps=new TrackGPS(MainActivity.this);
        if (gps.canGetLocation()){
            latitude.setText("Latitude: "+gps.getLatitude());
            longitude.setText("Longitude: "+gps.getLongitude());
            altitude.setText("Altitude: "+gps.getAltitude());
        }
        else{
            gps.showAlert();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeGps();
            } else {
                Toast.makeText(this, "Permission denied, cannot get location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showDialogBox(String title, String description) {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title); // Set the dialog title
        builder.setMessage(description); // Set the dialog message

        // Add the OK button
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}