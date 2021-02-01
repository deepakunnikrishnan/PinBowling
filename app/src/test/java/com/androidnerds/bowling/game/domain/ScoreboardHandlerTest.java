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

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

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

        possiblePoints = scoreBoardHandler.getPossiblePoints();
        assertEquals(6, possiblePoints.size());
        assertEquals(0, possiblePoints.get(0).intValue());
        assertEquals(5, possiblePoints.get(possiblePoints.size() - 1).intValue());

        for (int i = 1; i < inputs.length; i++) {
            scoreBoardHandler.updateScore(inputs[i]);
        }

        possiblePoints = scoreBoardHandler.getPossiblePoints();
        assertEquals(11, possiblePoints.size());

        scoreBoardHandler.updateScore(7);

        possiblePoints = scoreBoardHandler.getPossiblePoints();
        assertEquals(4, possiblePoints.size());
        assertEquals(3, possiblePoints.get(possiblePoints.size() - 1).intValue());
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

    private ScoreboardHandler getScoreBoardHandler() {
        Player player = new Player("test");
        Scoreboard scoreBoard = new Scoreboard(player, GameConstants.MAX_FRAMES);
        return new ScoreboardHandler(scoreBoard);
    }
}