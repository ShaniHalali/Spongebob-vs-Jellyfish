package com.example.spongebobvsjellyfish.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spongebobvsjellyfish.Interfaces.Callback_ListItemClicked;
import com.example.spongebobvsjellyfish.Models.Player;
import com.example.spongebobvsjellyfish.R;
import com.google.android.material.textview.MaterialTextView;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private List<Player> players;
    private Callback_ListItemClicked callbackListItemClicked;

    public PlayerAdapter(List<Player> players, Callback_ListItemClicked callbackListItemClicked) {
        this.players = players;
        this.callbackListItemClicked = callbackListItemClicked;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_player_item, parent, false);
        return new PlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = players.get(position);
        holder.name.setText(player.getName());
        holder.score.setText(String.valueOf(player.getScore()));
        holder.itemView.setOnClickListener(v -> {
            if (callbackListItemClicked != null) {
                callbackListItemClicked.listItemClicked(0.0, 0.0, position); // Replace with actual lat/lon if needed
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    // New method to update the list of players
    public void updatePlayers(List<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView name;
        public MaterialTextView score;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.player_LBL_name);
            score = itemView.findViewById(R.id.player_LBL_score);
        }
    }
}
