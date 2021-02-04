package com.androidnerds.bowling.game.components.controls.pointselector;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class PointSelectorDiffUtil extends DiffUtil.Callback {

    private List<Integer> oldList;
    List<Integer> newList;

    public PointSelectorDiffUtil(List<Integer> oldList, List<Integer> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

}
