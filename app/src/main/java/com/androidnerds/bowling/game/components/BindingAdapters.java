package com.androidnerds.bowling.game.components;

import androidx.databinding.BindingAdapter;

import com.androidnerds.bowling.game.components.controls.pointselector.PointSelectorView;
import com.androidnerds.bowling.game.components.scoreboard.ScoreboardView;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter("app:possibleValues")
    public static void displayPoints(PointSelectorView pointSelectorView, List<Integer> possibleValues) {
        if(null == possibleValues) {
            return;
        }
        pointSelectorView.showPoints(possibleValues);
    }

    @BindingAdapter("app:scoreboard")
    public static void displayPoints(ScoreboardView scoreboardView, Scoreboard scoreboard) {
        if(null == scoreboard) {
            return;
        }
        scoreboardView.onScoreboardUpdated(scoreboard);
    }
}
