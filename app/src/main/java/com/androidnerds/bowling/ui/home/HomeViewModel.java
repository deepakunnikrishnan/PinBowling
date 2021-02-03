package com.androidnerds.bowling.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnerds.bowling.game.domain.GameEngine;
import com.androidnerds.bowling.game.domain.exception.GameNotInitializedException;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.List;

public class HomeViewModel extends ViewModel implements GameEngine.OnPossibleValuesChangeListener,
        GameEngine.OnScoreChangeListener, GameEngine.OnGameStatusChangedListener {

    private final GameEngine gameEngine;

    private final MutableLiveData<List<Integer>> _possibleValues = new MutableLiveData<>();
    public final LiveData<List<Integer>> possibleValues = _possibleValues;

    private final MutableLiveData<Scoreboard> _scoreboardLiveData = new MutableLiveData<>();
    public final LiveData<Scoreboard> scoreboard = _scoreboardLiveData;

    private final MutableLiveData<Boolean> _gameCompletionStatusLiveData = new MutableLiveData<>();
    public final LiveData<Boolean> gameCompletionStatusLiveData = _gameCompletionStatusLiveData;

    private final MutableLiveData<String> _messagesLiveData = new MutableLiveData<>();
    public final LiveData<String> messagesLiveData = _messagesLiveData;


    public HomeViewModel() {
        gameEngine = GameEngine.getInstance();
        gameEngine.setValuesChangeListener(this);
        gameEngine.setScoreChangeListener(this);
        gameEngine.setGameStatusChangedListener(this);
        gameEngine.init();
    }

    @Override
    public void onPossibleValuesChanged(List<Integer> points) {
        _possibleValues.postValue(points);
    }

    @Override
    public void onScoreboardUpdated(Scoreboard scoreboard) {
        _scoreboardLiveData.postValue(scoreboard);
    }

    public void onPointSelected(int points) {
        try {
            gameEngine.roll(points);
        } catch (GameNotInitializedException e) {
            e.printStackTrace();
            _messagesLiveData.postValue("Failed to start the game.");
        }
    }

    public void onResetClicked() {
        gameEngine.init();
    }


    @Override
    public void onGameStatusChanged(GameEngine.GameStatus gameStatus) {
        _gameCompletionStatusLiveData.postValue(gameStatus == GameEngine.GameStatus.GAME_OVER);

    }
}