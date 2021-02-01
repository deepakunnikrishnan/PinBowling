package com.androidnerds.bowling.game.components.scoreboard.diffutil;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.androidnerds.bowling.game.domain.model.Frame;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.List;

public class ScoreboardDiffUtil extends DiffUtil.Callback {

    private List<Frame> newFrames;
    private List<Frame> oldFrames;


    public ScoreboardDiffUtil(List<Frame> oldFrames, List<Frame> newFrames) {
        this.newFrames = newFrames;
        this.oldFrames = oldFrames;
    }


    @Override
    public int getOldListSize() {
        return null != oldFrames ? oldFrames.size(): 0;
    }

    @Override
    public int getNewListSize() {
        return null != newFrames  ? newFrames.size(): 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFrames.get(oldItemPosition).getFrameNumber() == newFrames.get(newItemPosition).getFrameNumber();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFrames.get(oldItemPosition).equals(newFrames.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {

        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
