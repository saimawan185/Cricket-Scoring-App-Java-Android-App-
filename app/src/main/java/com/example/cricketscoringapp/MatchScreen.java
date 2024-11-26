package com.example.cricketscoringapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;

public class MatchScreen extends AppCompatActivity {

    private TextView latitude, longitude, altitude;
    private ImageView muteUnmuteIcon;
    private ArrayList<PlayerModel> firstTeamPlayers, secondTeamPlayers;

    private Button one, two, three, four, five, six, zero, noBall, wicket, wide, bies;

    private TextView playerOneNameText, playerTwoNameText, playerOneRunsText, playerTwoRunsText, ballerNameText, ballerOverviewText,
            totalRunsText, totalWicketsText, totalOversText;
    private int totalRuns, totalWickets;
    private double overs, ballerOvers;

    private String playerOneName,playerTwoName, ballerName;
    private int playerOneRuns, playerTwoRuns, ballerWickets, ballerRuns;

    private TrackGPS gps;

    private static final int REQUEST_CODE_PERMISSION = 1;
    String[] mPermission = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_screen);

        firstTeamPlayers = getIntent().getParcelableArrayListExtra("firstTeamPlayers");
        secondTeamPlayers = getIntent().getParcelableArrayListExtra("secondTeamPlayers");

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        altitude = findViewById(R.id.altitude);
        muteUnmuteIcon = findViewById(R.id.muteUnmuteIcon);

        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        zero = findViewById(R.id.zero);
        noBall = findViewById(R.id.noBall);
        wicket = findViewById(R.id.wicket);
        wide = findViewById(R.id.wide);
        bies = findViewById(R.id.bies);
        playerOneNameText = findViewById(R.id.player1Name);
        playerOneRunsText = findViewById(R.id.player1Runs);
        playerTwoNameText = findViewById(R.id.player2Name);
        playerTwoRunsText = findViewById(R.id.player2Runs);
        ballerNameText = findViewById(R.id.ballerName);
        ballerOverviewText = findViewById(R.id.ballerOverview);
        totalRunsText = findViewById(R.id.runs);
        totalWicketsText = findViewById(R.id.wickets);
        totalOversText = findViewById(R.id.overs);


        MusicManager.startMusic(this);

        muteUnmuteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MusicManager.isMusicPlaying()) {
                    MusicManager.pauseMusic();
                    muteUnmuteIcon.setImageResource(R.drawable.mute);
                } else {
                    MusicManager.startMusic(MatchScreen.this);
                    muteUnmuteIcon.setImageResource(R.drawable.unmute);
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable backIcon = getResources().getDrawable(R.drawable.back_button); // Ensure you have a back icon in drawable
        DrawableCompat.setTint(backIcon, getResources().getColor(android.R.color.white));  // Set the color to white
        toolbar.setNavigationIcon(backIcon);

        if(Build.VERSION.SDK_INT>= 23) {

            if (checkSelfPermission(mPermission[0]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MatchScreen.this, mPermission, REQUEST_CODE_PERMISSION);
                return;
            }
            else {
                initializeGps();
            }
        }
        else {
            initializeGps();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        gps.stopGPS();
    }

    private void initializeGps()
    {
        gps=new TrackGPS(MatchScreen.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
