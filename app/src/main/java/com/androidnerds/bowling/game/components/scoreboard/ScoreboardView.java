package com.androidnerds.bowling.game.components.scoreboard;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.databinding.LayoutScoreboardBinding;
import com.androidnerds.bowling.game.domain.GameEngine;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

public class ScoreboardView extends CardView implements GameEngine.OnScoreChangeListener {

    private final Context context;
    private ScoreboardAdapter scoreboardAdapter;

    public ScoreboardView(@NonNull Context context) {
        this(context, null);
    }

    public ScoreboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    private void init(LayoutScoreboardBinding binding, @NonNull ScoreboardAdapter scoreboardAdapter) {
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerViewScoreboard.setLayoutManager(new GridLayoutManager(this.context, GameEngine.MAX_FRAMES/2));
        }else {
            binding.recyclerViewScoreboard.setLayoutManager(new GridLayoutManager(this.context, GameEngine.MAX_FRAMES));
        }
        binding.recyclerViewScoreboard.setAdapter(scoreboardAdapter);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutScoreboardBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_scoreboard,this, true);
        setCardViewStyles(context);
        scoreboardAdapter = new ScoreboardAdapter();
        init(binding, scoreboardAdapter);
    }

    private void setCardViewStyles(Context context) {
        setRadius(context.getResources().getDimension(R.dimen.card_radius));
        setCardElevation(context.getResources().getDimension(R.dimen.card_elevation));
        int padding = (int) context.getResources().getDimension(R.dimen.default_padding);
        setContentPadding(padding,padding,padding,padding);
    }

    private void updateScoreboard(ScoreboardAdapter adapter, Scoreboard scoreboard) {
        adapter.updateScoreboard(scoreboard);
    }

    @Override
    public void onScoreboardUpdated(Scoreboard scoreboard) {
        updateScoreboard(scoreboardAdapter, scoreboard);
    }
}
