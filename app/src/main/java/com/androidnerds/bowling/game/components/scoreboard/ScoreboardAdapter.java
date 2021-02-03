package com.androidnerds.bowling.game.components.scoreboard;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.game.components.scoreboard.diffutil.ScoreboardDiffUtil;
import com.androidnerds.bowling.game.domain.model.Frame;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ScoreboardAdapter extends RecyclerView.Adapter<FrameViewHolder> {

    private final List<Frame> frameList = new ArrayList<>();

    @NonNull
    @Override
    public FrameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FrameViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frame, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FrameViewHolder holder, int position) {
        holder.bind(frameList.get(position));
    }

    @Override
    public int getItemCount() {
        return frameList.size();
    }

    public void updateScoreboard(Scoreboard newScoreboard) {
        frameList.clear();
        frameList.addAll(newScoreboard.getFrames());
        notifyDataSetChanged();
    }
}
