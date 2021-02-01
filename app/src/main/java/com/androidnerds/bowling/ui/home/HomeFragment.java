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
import com.androidnerds.bowling.game.components.scoreboard.ScoreboardView;
import com.androidnerds.bowling.game.domain.GameEngine;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel mViewModel;
    private EditText editTextNumber;
    private Button buttonInput;
    private Button buttonReset;
    private ScoreboardView scoreboardView;
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
        this.editTextNumber = view.findViewById(R.id.editTextNumber);
        this.buttonInput = view.findViewById(R.id.buttonInput);
        this.buttonReset = view.findViewById(R.id.buttonReset);
        this.buttonInput.setOnClickListener(this);
        this.buttonReset.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        gameEngine = GameEngine.getInstance();
        gameEngine.init();
        this.scoreboardView.setupGameEngine(gameEngine);

    }

    private void onInputEntered(int points) {
        this.gameEngine.roll(points);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonInput) {
            String number = editTextNumber.getText().toString();
            gameEngine.roll(Integer.parseInt(number));
        } else if(v.getId() == R.id.buttonReset) {
            gameEngine.init();
        }

    }
}