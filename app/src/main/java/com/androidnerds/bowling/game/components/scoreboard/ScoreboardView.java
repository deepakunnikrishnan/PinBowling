package com.androidnerds.bowling.game.components.scoreboard;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.game.domain.GameEngine;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

public class ScoreboardView extends RecyclerView implements GameEngine.OnScoreChangeListener {

    private Context context;
    private GameEngine gameEngine;
    private ScoreboardAdapter scoreboardAdapter;

    public ScoreboardView(@NonNull Context context) {
        this(context, null);
    }

    public ScoreboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private void init(@NonNull ScoreboardAdapter scoreboardAdapter) {
        this.setLayoutManager(new GridLayoutManager(this.context, GameEngine.MAX_FRAMES));
        setAdapter(scoreboardAdapter);
    }

    public void setupGameEngine(@NonNull GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        initWithScoreboard(this.gameEngine);
    }

    private void initWithScoreboard(@NonNull GameEngine gameEngine) {
        gameEngine.setScoreChangeListener(this);
        scoreboardAdapter = new ScoreboardAdapter(gameEngine.getScoreboard());
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
