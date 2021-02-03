package com.androidnerds.bowling.game.domain;

import android.util.Log;

import com.androidnerds.bowling.game.domain.constant.GameConstants;
import com.androidnerds.bowling.game.domain.exception.GameNotInitializedException;
import com.androidnerds.bowling.game.domain.model.Player;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * GameEngine class keeps track of the state of the game and forms the single interface through which the
 * game is played.
 * The UI layer communicates with the GameEngine and this class takes the user through the game.
 * </p>
 *
 * <p>
 *     GameEngine class provides with different Callbacks that can be used to receive the:
 *     1. Change in scoreboard.
 *     2. Change in possible values for the next roll.
 *     3. Status of the game.
 * </p>
 *
 * <p> GameEngine class delegates the scoreboard handling to the {@link ScoreboardHandler} class.</p>
 *
 * <p>
 *     GameEngine class keep tracks of the status of the game.
 *     When the object for the GameEngine is created, the game status would be default to {@link GameStatus#GAME_NOT_INITIALIZED}.
 *     When the game is initialized, the status is changed to {@link GameStatus#GAME_STARTED}.
 *     After each move, the GameEngine checks whether the game has ended or not. If the game has ended, then the status is updated
 *     to {@link GameStatus#GAME_OVER}
 * </p>
 *
 * <p>
 *     GameEngine class also provides option to reset the game via {@link GameEngine#init()}. Once initialized, it would change the
 *     the status is changed to {@link GameStatus#GAME_STARTED}.
 * </p>
 *
 */
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

    public interface OnGameStatusChangedListener {
        void onGameStatusChanged(GameStatus gameStatus);
    }

    public static final String TAG = "GameEngine";
    public static final int MAX_FRAMES = GameConstants.MAX_FRAMES;

    private static GameEngine gameEngine;

    private OnScoreChangeListener scoreChangeListener;
    private OnPossibleValuesChangeListener valuesChangeListener;
    private OnGameStatusChangedListener gameStatusChangedListener;

    private Scoreboard scoreboard;
    private ScoreboardHandler scoreBoardHandler;
    private GameStatus gameStatus = GameStatus.GAME_NOT_INITIALIZED;
    private List<Player> playerList;

    public static GameEngine getInstance() {
        if (null == gameEngine) {
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
        setGameStatus(GameStatus.GAME_STARTED);
        sendScoreChangeListenerCallback();
    }

    /**
     * Method to provide the pins down after rolling the ball.
     * <p>
     *     Validates the current GameStatus.
     *     If the status is {@link GameStatus#GAME_STARTED}, then proceeds to pass it to the {@link ScoreboardHandler}
     *     via {@link ScoreboardHandler#updateScore(int)}.
     *     The {@link ScoreboardHandler} returns whether the point was updated or not.
     * </p>
     *
     * <p>
     *  {@link GameEngine} sends a callback indicating the scoreboard was updated via {@link OnScoreChangeListener#onScoreboardUpdated(Scoreboard)}
     * </p>
     *
     * <p>
     *     After each roll, {@link GameEngine} validates whether the game has ended or not.
     *     If the game has completed, then its updated via {@link OnGameStatusChangedListener#onGameStatusChanged(GameStatus)}
     * </p>
     *
     * @param points - no:of pins down
     * @return
     * @throws GameNotInitializedException
     */
    public boolean roll(int points) throws GameNotInitializedException {
        if(!validateGameStatus()) {
            throw new GameNotInitializedException();
        }
        Log.d(TAG, "roll(" + points + ")");
        boolean updated = this.scoreBoardHandler.updateScore(points);
        if (updated) {
            sendScoreChangeListenerCallback();
        } else {
            Log.d(TAG, "roll(" + points + ")" + " Invalid points");
        }
        if (this.scoreBoardHandler.isGameOver()) {
            setGameStatus(GameStatus.GAME_OVER);
        }
        return updated;
    }

    private boolean validateGameStatus() {
        return gameStatus == GameStatus.GAME_STARTED;
    }

    private void sendScoreChangeListenerCallback() {
        if (null != scoreChangeListener) {
            scoreChangeListener.onScoreboardUpdated(this.scoreboard);
        }
        if (null != valuesChangeListener) {
            valuesChangeListener.onPossibleValuesChanged(this.scoreBoardHandler.getPossiblePoints());
        }
    }

    private void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
        sendGameStatusChangedListenerCallback(gameStatus);
    }

    private void sendGameStatusChangedListenerCallback(GameStatus gameStatus) {
        if(null != gameStatusChangedListener) {
            this.gameStatusChangedListener.onGameStatusChanged(gameStatus);
        }
    }

    public void setScoreChangeListener(OnScoreChangeListener scoreChangeListener) {
        this.scoreChangeListener = scoreChangeListener;
    }

    public void setValuesChangeListener(OnPossibleValuesChangeListener valuesChangeListener) {
        this.valuesChangeListener = valuesChangeListener;
    }

    public void setGameStatusChangedListener(OnGameStatusChangedListener gameCompletionListener) {
        this.gameStatusChangedListener = gameCompletionListener;
    }

}
