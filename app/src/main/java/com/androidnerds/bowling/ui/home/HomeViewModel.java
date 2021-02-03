package com.androidnerds.bowling.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnerds.bowling.game.domain.GameEngine;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.List;

public class HomeViewModel extends ViewModel implements GameEngine.OnPossibleValuesChangeListener,
        GameEngine.OnScoreChangeListener, GameEngine.OnGameCompletionListener {
    // TODO: Implement the ViewModel

    GameEngine gameEngine;

    private final MutableLiveData<List<Integer>> _possibleValue = new MutableLiveData<>();
    public final LiveData<List<Integer>> possibleValues = _possibleValue;

    private final MutableLiveData<Scoreboard> _scoreboardLiveData = new MutableLiveData<>();
    public final LiveData<Scoreboard> scoreboardMutableLiveData = _scoreboardLiveData;

    private final MutableLiveData<Boolean> _gameCompletionStatusLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> gameCompletionStatusLiveData = _gameCompletionStatusLiveData;




    public HomeViewModel() {
        gameEngine = GameEngine.getInstance();
        gameEngine.setValuesChangeListener(this);
        gameEngine.setScoreChangeListener(this);
        gameEngine.setGameCompletionListener(this);
        gameEngine.init();
    }

    @Override
    public void onPossibleValuesChanged(List<Integer> points) {
        _possibleValue.postValue(points);
    }

    @Override
    public void onScoreboardUpdated(Scoreboard scoreboard) {
        _scoreboardLiveData.postValue(scoreboard);
    }

    public void onPointSelected(int points) {
        gameEngine.roll(points);
    }

    public void onResetClicked() {
        gameEngine.init();
    }

    @Override
    public void onGameCompleted() {
        _gameCompletionStatusLiveData.postValue(true);
    }
}