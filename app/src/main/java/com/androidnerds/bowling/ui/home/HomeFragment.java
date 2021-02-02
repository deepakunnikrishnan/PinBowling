package com.androidnerds.bowling.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.game.components.controls.pointselector.PointSelectorView;
import com.androidnerds.bowling.game.components.scoreboard.ScoreboardView;
import com.androidnerds.bowling.game.domain.GameEngine;

import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel mViewModel;
    private Button buttonReset;
    private ScoreboardView scoreboardView;
    private PointSelectorView pointSelectorView;
    private GameEngine gameEngine;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        this.scoreboardView = view.findViewById(R.id.scoreboard);
        this.pointSelectorView = view.findViewById(R.id.pointSelector);
        this.buttonReset = view.findViewById(R.id.buttonReset);
        this.buttonReset.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        this.pointSelectorView.setOnPointSelectedChangeListener(this::onInputEntered);
        gameEngine = GameEngine.getInstance();
        gameEngine.setValuesChangeListener(points -> pointSelectorView.showPoints(points));
        gameEngine.init();
        this.scoreboardView.setupGameEngine(gameEngine);
    }

    private void onInputEntered(int points) {
        this.gameEngine.roll(points);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonReset) {
            gameEngine.init();
        }

    }
}