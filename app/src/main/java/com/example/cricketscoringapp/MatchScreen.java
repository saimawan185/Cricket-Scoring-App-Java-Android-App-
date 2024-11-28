package com.example.cricketscoringapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MatchScreen extends AppCompatActivity {

    private TextView latitude, longitude, altitude, targetScoreText;
    private ImageView muteUnmuteIcon;
    private ArrayList<PlayerModel> firstTeamPlayers, secondTeamPlayers, team1Ballers, team2Ballers;

    private Button one, two, three, four, five, six, zero, noBall, wicket, wide, bies;

    private TextView playerOneNameText, playerTwoNameText, playerOneRunsText, playerTwoRunsText, ballerNameText, ballerOverviewText,
            totalRunsText, totalWicketsText, totalOversText;
    private int totalRuns = 0, totalWickets = 0, team1TotalRuns = 0, team2TotalRuns = 0, targetScore;
    private double overs = 0.0, ballerOvers = 0.0;

    private String playerOneName, playerTwoName, ballerName;
    private int playerOneRuns = 0, playerTwoRuns = 0, ballerWickets = 0, ballerRuns = 0;

    private TrackGPS gps;
    private boolean firstTeamPlayed = false;
    private int playingTeam, currentBallerIndex = 0, currentPlayerIndex = 1, numberOfOvers;

    private static final int REQUEST_CODE_PERMISSION = 1;
    String[] mPermission = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_screen);

        firstTeamPlayers = getIntent().getParcelableArrayListExtra("firstTeamPlayers");
        secondTeamPlayers = getIntent().getParcelableArrayListExtra("secondTeamPlayers");
        numberOfOvers = Integer.valueOf(getIntent().getStringExtra("numberOfOvers"));
        playingTeam = getIntent().getIntExtra("teamWon", 1);

        team1Ballers = new ArrayList<>();
        team2Ballers = new ArrayList<>();

        for (var player : firstTeamPlayers) {
            if (player.isBaller()) {
                team1Ballers.add(player);
            }
        }

        for (var player : secondTeamPlayers) {
            if (player.isBaller()) {
                team2Ballers.add(player);
            }
        }

        initAll();
        setTeams();
        setFields();

        MusicManager.startMusic(this);

        muteUnmuteIcon.setOnClickListener(v -> {
            if (MusicManager.isMusicPlaying()) {
                MusicManager.pauseMusic();
                muteUnmuteIcon.setImageResource(R.drawable.mute);
            } else {
                MusicManager.startMusic(MatchScreen.this);
                muteUnmuteIcon.setImageResource(R.drawable.unmute);
            }
        });

        setupToolbar();

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

    private void initAll() {
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
        targetScoreText = findViewById(R.id.targetScoreText);

        oneClick();
        twoClick();
        threeClick();
        fourClick();
        fiveClick();
        sixClick();
        zeroClick();
        noBallClick();
        wicketClick();
        wideClick();
        biesClick();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable backIcon = getResources().getDrawable(R.drawable.back_button);
        DrawableCompat.setTint(backIcon, getResources().getColor(android.R.color.white));
        toolbar.setNavigationIcon(backIcon);
    }

    private void oneClick() {
        one.setOnClickListener(v -> addRuns(1));
    }

    private void twoClick() {
        two.setOnClickListener(v -> addRuns(2));
    }

    private void threeClick() {
        three.setOnClickListener(v -> addRuns(3));
    }

    private void fourClick() {
        four.setOnClickListener(v -> addRuns(4));
    }

    private void fiveClick() {
        five.setOnClickListener(v -> addRuns(5));
    }

    private void sixClick() {
        six.setOnClickListener(v -> addRuns(6));
    }

    private void zeroClick() {
        zero.setOnClickListener(v -> {
            ballPlay();
            checkOverComplete();
            setFields();
        });
    }

    private void noBallClick() {
        noBall.setOnClickListener(v -> {
            totalRuns++;
            ballerRuns++;
            checkTargetAchieved();
            setFields();
        });
    }

    private void wicketClick() {
        wicket.setOnClickListener(v -> {
            totalWickets++;
            ballerWickets++;
            ballPlay();
            currentPlayerIndex++;
            if (currentPlayerIndex >= firstTeamPlayers.size()) {
                showDialogBox("Innings Over", "All players are out!");
            } else {
                playerOneName = firstTeamPlayers.get(currentPlayerIndex).getName();
            }
            checkOverComplete();
            setFields();
        });
    }

    private void wideClick() {
        wide.setOnClickListener(v -> {
            totalRuns++;
            ballerRuns++;
            checkTargetAchieved();
            setFields();
        });
    }

    private void biesClick() {
        bies.setOnClickListener(v -> {
            totalRuns++;
            ballPlay();
            setFields();
        });
    }

    private void addRuns(int runs) {
        playerOneRuns += runs;
        totalRuns += runs;
        ballerRuns += runs;
        ballPlay();
        if (runs % 2 != 0) {
            swapPlayers();
        }
        checkTargetAchieved();
        checkOverComplete();
        setFields();
    }

    private void ballPlay() {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        ballerOvers += 0.1;
        overs += 0.1;
        ballerOvers = Double.parseDouble(decimalFormat.format(ballerOvers));
        overs = Double.parseDouble(decimalFormat.format(overs));
        if (String.valueOf(ballerOvers).contains(".6")) {
            ballerOvers = Math.ceil(ballerOvers);
            overs = Math.ceil(overs);
        }
    }

    private void checkOverComplete() {
        if (overs == numberOfOvers || totalWickets >= firstTeamPlayers.size()) {
            if (!firstTeamPlayed) {
                firstTeamPlayed = true;
                saveFirstInningsData();

                new AlertDialog.Builder(MatchScreen.this)
                        .setTitle("First Innings Over")
                        .setMessage("Team " + playingTeam + " scored " + totalRuns + " runs!")
                        .setPositiveButton("OK", (dialog, which) -> {
                            dialog.dismiss();
                            resetForSecondInnings();
                        })
                        .show();
            } else {
                showMatchResult();
            }
        }
    }

    private void saveFirstInningsData() {

        if (playingTeam == 1) {
            team1TotalRuns = totalRuns;
            targetScore = team1TotalRuns + 1;
            targetScoreText.setText("Target: " + targetScore);
        } else {
            team2TotalRuns = totalRuns;
            targetScore = team2TotalRuns + 1;
            targetScoreText.setText("Target: " + targetScore);
        }
    }

    private void checkTargetAchieved() {
        if (firstTeamPlayed) {
            if (totalRuns >= targetScore) {
                showDialogBox("Match Over", "Team 2 Won!");
            }
        } else {
            if (playingTeam == 2 && totalRuns >= targetScore) {
                showDialogBox("Match Over", "Team 1 Won!");
            }
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(MatchScreen.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showMatchResult() {
        String resultMessage;

        if (team1TotalRuns > team2TotalRuns) {
            resultMessage = "Team 1 wins by " + (team1TotalRuns - team2TotalRuns) + " runs!";
        } else if (team2TotalRuns > team1TotalRuns) {
            resultMessage = "Team 2 wins by " + (10 - totalWickets) + " wickets!";
        } else {
            resultMessage = "Match tied!";
        }

        new AlertDialog.Builder(MatchScreen.this)
                .setTitle("Match Result")
                .setMessage(resultMessage)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    goToMainActivity();
                })
                .show();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(MatchScreen.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void resetForSecondInnings() {
        totalRuns = 0;
        totalWickets = 0;
        ballerWickets=0;
        ballerRuns=0;
        playerOneRuns=0;
        playerTwoRuns=0;
        overs = 0.0;
        ballerOvers = 0.0;
        currentBallerIndex = 0;
        currentPlayerIndex = 1;

        playingTeam = (playingTeam == 1) ? 2 : 1;

        setTeams();
        setFields();
    }

    private void setTeams() {
        if (playingTeam == 1) {
            playerOneName = firstTeamPlayers.get(0).getName();
            playerTwoName = firstTeamPlayers.get(currentPlayerIndex).getName();
            ballerName = team2Ballers.get(currentBallerIndex).getName();
        } else {
            playerOneName = secondTeamPlayers.get(0).getName();
            playerTwoName = secondTeamPlayers.get(currentPlayerIndex).getName();
            ballerName = team1Ballers.get(currentBallerIndex).getName();
        }
    }
    private void setFields() {
        playerOneNameText.setText(playerOneName);
        playerOneRunsText.setText(String.valueOf(playerOneRuns));
        playerTwoNameText.setText(playerTwoName);
        playerTwoRunsText.setText(String.valueOf(playerTwoRuns));
        ballerNameText.setText(ballerName);
        ballerOverviewText.setText(ballerOvers + " Ovs, " + ballerRuns + " Runs, " + ballerWickets + " Wkts");
        totalRunsText.setText(String.valueOf(totalRuns));
        totalWicketsText.setText(String.valueOf(totalWickets));
        totalOversText.setText(String.valueOf(overs));
    }

    private void swapPlayers() {
        String temp = playerOneName;
        playerOneName = playerTwoName;
        playerTwoName = temp;
        int tempRuns = playerOneRuns;
        playerOneRuns = playerTwoRuns;
        playerTwoRuns = tempRuns;
    }

    private void showDialogBox(String title, String message) {
        new AlertDialog.Builder(MatchScreen.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

        @Override
    protected void onDestroy(){
        super.onDestroy();
        gps.stopGPS();
    }
}
