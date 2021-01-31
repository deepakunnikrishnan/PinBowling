package com.androidnerds.bowling.game.domain;

import android.util.Log;

import com.androidnerds.bowling.game.domain.constants.GameConstants;
import com.androidnerds.bowling.game.domain.model.Player;
import com.androidnerds.bowling.game.domain.model.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    public static final String TAG = "GameEngine";

    public enum GameStatus {
        GAME_NOT_INITIALIZED,
        GAME_STARTED,
        GAME_OVER
    }

    private static final int MAX_FRAMES = GameConstants.MAX_FRAMES;
    private ScoreBoardHandler scoreBoardHandler;
    //private ScoreBoard scoreBoard;
    private GameStatus gameStatus = GameStatus.GAME_NOT_INITIALIZED;
    private List<Player> playerList;
    private List<Integer> pins;

    public void init() {
        initGame();
    }

    private void initGame() {
        this.playerList = new ArrayList<>();
        this.playerList.add(new Player("Player 1"));
        ScoreBoard scoreBoard = new ScoreBoard(this.playerList.get(0), MAX_FRAMES);
        this.scoreBoardHandler = new ScoreBoardHandler(scoreBoard);
        gameStatus = GameStatus.GAME_STARTED;
    }

    public boolean roll(int points) {
        if (this.scoreBoardHandler.isValidPoint(points)) {
            Log.i(TAG, "roll(" + points + ")");
            this.scoreBoardHandler.updateScore(points);
            return true;
        } else {
            Log.i(TAG, "roll(" + points + ")" + " Invalid points");
        }
        return false;
    }

}
