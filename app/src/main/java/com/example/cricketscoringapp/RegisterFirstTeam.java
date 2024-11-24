package com.example.cricketscoringapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RegisterFirstTeam extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_team);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable backIcon = getResources().getDrawable(R.drawable.back_button); // Ensure you have a back icon in drawable
        DrawableCompat.setTint(backIcon, getResources().getColor(android.R.color.white));  // Set the color to white
        toolbar.setNavigationIcon(backIcon);

        RecyclerView playersRecyclerView = findViewById(R.id.playersRecyclerView);

        // Initialize the player list
        List<PlayerModel> players = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new PlayerModel("", false));
        }

        // Set up RecyclerView
        PlayersAdapter adapter = new PlayersAdapter(players);
        playersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playersRecyclerView.setAdapter(adapter);
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
