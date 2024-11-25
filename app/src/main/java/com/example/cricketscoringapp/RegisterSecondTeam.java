package com.example.cricketscoringapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RegisterSecondTeam extends AppCompatActivity {

    private ArrayList<PlayerModel> players;
    private Button secondTeamDoneButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_team);

        Toolbar toolbar = findViewById(R.id.secondTeamToolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable backIcon = getResources().getDrawable(R.drawable.back_button); // Ensure you have a back icon in drawable
        DrawableCompat.setTint(backIcon, getResources().getColor(android.R.color.white));  // Set the color to white
        toolbar.setNavigationIcon(backIcon);

        RecyclerView playersRecyclerView = findViewById(R.id.secondTeamPlayersRecyclerView);
        secondTeamDoneButton = findViewById(R.id.secondTeamDoneBtn);

        if (getIntent().hasExtra("secondTeamPlayers")) {
            players = getIntent().getParcelableArrayListExtra("secondTeamPlayers");
        } else {
            players = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                players.add(new PlayerModel("", false));
            }
        }

        // Set up RecyclerView
        PlayersAdapter adapter = new PlayersAdapter(players);
        playersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playersRecyclerView.setAdapter(adapter);

        secondTeamDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putParcelableArrayListExtra("secondTeamPlayers", players);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button press
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
