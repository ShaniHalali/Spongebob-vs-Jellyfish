package com.example.spongebobvsjellyfish.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spongebobvsjellyfish.Interfaces.Callback_ListItemClicked;
import com.example.spongebobvsjellyfish.Models.Player;
import com.example.spongebobvsjellyfish.Models.PlayerList;
import com.example.spongebobvsjellyfish.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textview.MaterialTextView;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private static List<Player> players;
    private static Callback_ListItemClicked callbackListItemClicked;

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
        Player player = getPlayer(position);
        holder.name.setText(player.getName());
        holder.score.setText(String.valueOf(player.getScore()));

    }

    @Override
    public int getItemCount() {
        if(players == null){
            return 0;
        }
        return players.size();
    }

    public void setCallbackListItemClicked(Callback_ListItemClicked callbackListItemClicked){
        this.callbackListItemClicked = callbackListItemClicked;
    }
    public static Player getPlayer(int position) {
        return players.get(position);
    }


    public static class PlayerViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView name;
        public MaterialTextView score;
        public ExtendedFloatingActionButton list_BTN_location;

        public PlayerViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.player_LBL_name);
            score = itemView.findViewById(R.id.player_LBL_score);
            list_BTN_location = itemView.findViewById(R.id.list_BTN_location);

            list_BTN_location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callbackListItemClicked != null) {
                        Player currenPlayer = getPlayer(getAdapterPosition());
                        callbackListItemClicked.listItemClicked(currenPlayer); // Replace with actual lat/lon if needed
                    }
                }
            });
        }
    }


}