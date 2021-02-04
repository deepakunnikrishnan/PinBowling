package com.androidnerds.bowling.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnerds.bowling.game.domain.GameEngine;
import com.androidnerds.bowling.game.domain.exception.GameNotInitializedException;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.List;

/**
 * ViewModel class for the Home Screen.
 * Provides core set of data as LiveData for observers.
 * Interacts with the domain layer of the application.
 */
public class HomeViewModel extends ViewModel implements GameEngine.OnPossibleValuesChangeListener,
        GameEngine.OnScoreChangeListener, GameEngine.OnGameStatusChangedListener {

    private final GameEngine gameEngine;
    //LiveData to get the list of possible values during the course of the game.
    private final MutableLiveData<List<Integer>> _possibleValues = new MutableLiveData<>();
    public final LiveData<List<Integer>> possibleValues = _possibleValues;
    //LiveData to get the updated Scoreboard info.
    private final MutableLiveData<Scoreboard> _scoreboardLiveData = new MutableLiveData<>();
    public final LiveData<Scoreboard> scoreboard = _scoreboardLiveData;
    //LiveData for the GameCompletion status
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
            _messagesLiveData.postValue("Please Reset the scoreboard.");
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