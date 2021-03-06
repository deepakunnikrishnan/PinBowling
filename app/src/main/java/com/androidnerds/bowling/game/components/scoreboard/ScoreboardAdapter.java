package com.androidnerds.bowling.game.components.scoreboard;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.databinding.ItemFrameBinding;
import com.androidnerds.bowling.game.domain.model.Frame;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter class for {@link RecyclerView} by extending {@link RecyclerView.Adapter}
 * This adapter supports only one {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} - {@link FrameViewHolder}
 */
class ScoreboardAdapter extends RecyclerView.Adapter<FrameViewHolder> {

    private final List<Frame> frameList = new ArrayList<>();

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFrameBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_frame, parent, false);
        return new FrameViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder holder, int position) {
        holder.bind(frameList.get(position));
    }

    @Override
    public int getItemCount() {
        return frameList.size();
    }

    /**
     * For updating the scoreboard with the latest data.
     * @param newScoreboard
     */
    public void updateScoreboard(Scoreboard newScoreboard) {
        frameList.clear();
        frameList.addAll(newScoreboard.getFrames());
        notifyDataSetChanged();
    }
}
