package com.androidnerds.bowling.game.domain;

import com.androidnerds.bowling.game.domain.constant.GameConstants;
import com.androidnerds.bowling.game.domain.model.Player;
import com.androidnerds.bowling.game.domain.model.Scoreboard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ScoreboardHandlerTest {

    @Test
    public void getPossiblePoints() {
        ScoreboardHandler scoreBoardHandler = getScoreBoardHandler();
        List<Integer> possiblePoints = scoreBoardHandler.getPossiblePoints();
        assertEquals(11, possiblePoints.size());

        int[] inputs = {
                5, 2,//Frame 1
                8, 1,//Frame 2
                7, 3,//Frame 3
                6, 3,//Frame 4
                6, 3,//Frame 5
                6, 3,//Frame 6
                6, 3,//Frame 7
                6, 3,//Frame 8
                6, 3,//Frame 9
                10//Frame 10
        };

        scoreBoardHandler.updateScore(inputs[0]);

        Integer[] expected = {0, 1, 2, 3, 4, 5};
        possiblePoints = scoreBoardHandler.getPossiblePoints();
        Integer[] actual = new Integer[possiblePoints.size()];
        possiblePoints.toArray(actual);
        assertEquals(6, possiblePoints.size());
        assertEquals(0, possiblePoints.get(0).intValue());
        assertEquals(5, possiblePoints.get(possiblePoints.size() - 1).intValue());
        assertArrayEquals(expected, actual);


        for (int i = 1; i < inputs.length; i++) {
            scoreBoardHandler.updateScore(inputs[i]);
        }

        expected = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        possiblePoints = scoreBoardHandler.getPossiblePoints();
        actual = new Integer[possiblePoints.size()];
        possiblePoints.toArray(actual);

        assertEquals(11, possiblePoints.size());
        assertArrayEquals(expected, actual);

        scoreBoardHandler.updateScore(7);

        expected = new Integer[]{0, 1, 2, 3};
        possiblePoints = scoreBoardHandler.getPossiblePoints();
        actual = new Integer[possiblePoints.size()];
        possiblePoints.toArray(actual);

        assertEquals(4, possiblePoints.size());
        assertEquals(3, possiblePoints.get(possiblePoints.size() - 1).intValue());
        assertArrayEquals(expected, actual);
    }

    @Test
    public void getPossiblePointsWithoutStrikeInFirstRollForLastFrame() {

        ScoreboardHandler scoreBoardHandler = getScoreBoardHandler();
        List<Integer> possiblePoints = scoreBoardHandler.getPossiblePoints();
        assertEquals(11, possiblePoints.size());
        int[] inputs = {
                5, 2,//Frame 1
                8, 1,//Frame 2
                7, 3,//Frame 3
                6, 3,//Frame 4
                6, 3,//Frame 5
                6, 3,//Frame 6
                6, 3,//Frame 7
                6, 3,//Frame 8
                6, 3,//Frame 9
                9//Frame 10
        };
        for (int point : inputs) {
            scoreBoardHandler.updateScore(point);
        }

        possiblePoints = scoreBoardHandler.getPossiblePoints();
        assertEquals(2, possiblePoints.size());
        assertEquals(1, possiblePoints.get(possiblePoints.size() - 1).intValue());
    }

    @Test
    public void isGameOverForStrikeAtLastFrameShouldReturnFalse() {
        ScoreboardHandler scoreBoardHandler = getScoreBoardHandler();
        int[] inputs = {
                5, 2,//Frame 1
                8, 1,//Frame 2
                7, 3,//Frame 3
                6, 3,//Frame 4
                6, 3,//Frame 5
                6, 3,//Frame 6
                6, 3,//Frame 7
                6, 3,//Frame 8
                6, 3,//Frame 9
                10//Frame 10
        };
        for (int point : inputs) {
            scoreBoardHandler.updateScore(point);
        }
        assertFalse(scoreBoardHandler.isGameOver());
    }

    @Test
    public void isGameOverForStrikeAtLastTwoRollsShouldReturnFalse() {
        ScoreboardHandler scoreBoardHandler = getScoreBoardHandler();
        int[] inputs = {
                5, 2,//Frame 1
                8, 1,//Frame 2
                7, 3,//Frame 3
                6, 3,//Frame 4
                6, 3,//Frame 5
                6, 3,//Frame 6
                6, 3,//Frame 7
                6, 3,//Frame 8
                6, 3,//Frame 9
                10,10//Frame 10
        };
        for (int point : inputs) {
            scoreBoardHandler.updateScore(point);
        }
        assertFalse(scoreBoardHandler.isGameOver());
    }

    @Test
    public void isGameOverForThreeStrikeAtLastFrameShouldReturnTrue() {
        ScoreboardHandler scoreBoardHandler = getScoreBoardHandler();
        int[] inputs = {
                5, 2,//Frame 1
                8, 1,//Frame 2
                7, 3,//Frame 3
                6, 3,//Frame 4
                6, 3,//Frame 5
                6, 3,//Frame 6
                6, 3,//Frame 7
                6, 3,//Frame 8
                6, 3,//Frame 9
                10,10,10//Frame 10
        };
        for (int point : inputs) {
            scoreBoardHandler.updateScore(point);
        }
        assertTrue(scoreBoardHandler.isGameOver());
    }

    @Test
    public void isGameOverShouldReturnTrue() {
        ScoreboardHandler scoreBoardHandler = getScoreBoardHandler();
        int[] inputs = {
                5, 2,//Frame 1
                8, 1,//Frame 2
                7, 3,//Frame 3
                6, 3,//Frame 4
                6, 3,//Frame 5
                6, 3,//Frame 6
                6, 3,//Frame 7
                6, 3,//Frame 8
                6, 3,//Frame 9
                9,0//Frame 10
        };
        for (int point : inputs) {
            scoreBoardHandler.updateScore(point);
        }
        assertTrue(scoreBoardHandler.isGameOver());
    }

    @Test
    public void isGameOverShouldReturnFalse() {
        ScoreboardHandler scoreBoardHandler = getScoreBoardHandler();
        int[] inputs = {
                5, 2,//Frame 1
                8, 1,//Frame 2
                7, 3,//Frame 3
                6, 3,//Frame 4
                6, 3,//Frame 5
                6, 3,//Frame 6
                6, 3,//Frame 7
                6, 3,//Frame 8
                6, 3,//Frame 9
                9//Frame 10
        };
        for (int point : inputs) {
            scoreBoardHandler.updateScore(point);
        }
        assertFalse(scoreBoardHandler.isGameOver());
    }

    private ScoreboardHandler getScoreBoardHandler() {
        Player player = new Player("test");
        Scoreboard scoreBoard = new Scoreboard(player, GameConstants.MAX_FRAMES);
        return new ScoreboardHandler(scoreBoard);
    }
}