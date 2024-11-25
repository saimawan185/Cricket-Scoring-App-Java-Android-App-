package com.example.cricketscoringapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder> {

    private List<PlayerModel> players;

    public PlayersAdapter(List<PlayerModel> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_tile, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        PlayerModel player = players.get(position);

        // Set existing data to the views
        holder.playerNumber.setText((position + 1) + ". Name:");
        holder.playerName.setText(player.getName());
        holder.playerIsBaller.setChecked(player.isBaller());

        // Listen for changes in the EditText
        holder.playerName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) { // Save data only when the EditText loses focus
                player.setName(holder.playerName.getText().toString());
            }
        });

        // Listen for changes in the CheckBox
        holder.playerIsBaller.setOnCheckedChangeListener((buttonView, isChecked) -> {
            player.setBaller(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView playerNumber;
        EditText playerName;
        CheckBox playerIsBaller;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerNumber = itemView.findViewById(R.id.playerNumber);
            playerName = itemView.findViewById(R.id.playerName);
            playerIsBaller = itemView.findViewById(R.id.playerIsBaller);
        }
    }
}

