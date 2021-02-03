package com.androidnerds.bowling.game.components.scoreboard;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.game.domain.GameEngine;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

public class ScoreboardView extends RecyclerView implements GameEngine.OnScoreChangeListener {

    private final Context context;
    private ScoreboardAdapter scoreboardAdapter;

    public ScoreboardView(@NonNull Context context) {
        this(context, null);
    }

    public ScoreboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initWithScoreboard();
    }

    private void init(@NonNull ScoreboardAdapter scoreboardAdapter) {
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.setLayoutManager(new GridLayoutManager(this.context, GameEngine.MAX_FRAMES/2));
        }else {
            this.setLayoutManager(new GridLayoutManager(this.context, GameEngine.MAX_FRAMES));
        }
        setAdapter(scoreboardAdapter);
    }

    private void initWithScoreboard() {
        scoreboardAdapter = new ScoreboardAdapter();
        init(scoreboardAdapter);
    }

    private void updateScoreboard(ScoreboardAdapter adapter, Scoreboard scoreboard) {
        adapter.updateScoreboard(scoreboard);
    }

    @Override
    public void onScoreboardUpdated(Scoreboard scoreboard) {
        updateScoreboard(scoreboardAdapter, scoreboard);
    }
}
