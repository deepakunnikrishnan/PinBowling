package com.androidnerds.bowling.game.domain;

import android.util.Log;

import com.androidnerds.bowling.game.domain.constant.GameConstants;
import com.androidnerds.bowling.game.domain.model.Player;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    public enum GameStatus {
        GAME_NOT_INITIALIZED,
        GAME_STARTED,
        GAME_OVER
    }

    public interface OnScoreChangeListener {
        void onScoreboardUpdated(Scoreboard scoreboard);
    }

    public interface OnPossibleValuesChangeListener {
        void onPossibleValuesChanged(List<Integer> points);
    }

    public static final String TAG = "GameEngine";
    public static final int MAX_FRAMES = GameConstants.MAX_FRAMES;

    private static GameEngine gameEngine;

    private OnScoreChangeListener scoreChangeListener;
    private OnPossibleValuesChangeListener valuesChangeListener;

    private Scoreboard scoreboard;
    private ScoreboardHandler scoreBoardHandler;
    private GameStatus gameStatus = GameStatus.GAME_NOT_INITIALIZED;
    private List<Player> playerList;
    private List<Integer> pins;

    public static GameEngine getInstance() {
        if(null == gameEngine) {
            gameEngine = new GameEngine();
        }
        return gameEngine;
    }

    private GameEngine() {
        //empty constructor
    }

    public void init() {
        initGame();
    }

    private void initGame() {
        this.playerList = new ArrayList<>();
        this.playerList.add(new Player("Player 1"));
        this.scoreboard = new Scoreboard(this.playerList.get(0), MAX_FRAMES);
        this.scoreBoardHandler = new ScoreboardHandler(scoreboard);
        gameStatus = GameStatus.GAME_STARTED;
        sendScoreChangeListenerCallback();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public boolean roll(int points) {
        Log.i(TAG, "roll(" + points + ")");
        boolean updated = this.scoreBoardHandler.updateScore(points);
        if(updated) {
            sendScoreChangeListenerCallback();
        }else {
            Log.i(TAG, "roll(" + points + ")" + " Invalid points");
        }
        return updated;
    }

    private void sendScoreChangeListenerCallback() {
        if(null != scoreChangeListener) {
            scoreChangeListener.onScoreboardUpdated(this.scoreboard);
        }
        if(null != valuesChangeListener) {
            valuesChangeListener.onPossibleValuesChanged(this.scoreBoardHandler.getPossiblePoints());
        }
    }

    public void setScoreChangeListener(OnScoreChangeListener scoreChangeListener) {
        this.scoreChangeListener = scoreChangeListener;
    }

    public void setValuesChangeListener(OnPossibleValuesChangeListener valuesChangeListener) {
        this.valuesChangeListener = valuesChangeListener;
    }
}
