package com.androidnerds.bowling.game.domain;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.androidnerds.bowling.game.domain.exception.GameNotInitializedException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class GameEngineTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void rollGameWithNoBonus() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {
                5,2,//Frame 1
                8,1,//Frame 2
                7,3,//Frame 3
                6,3,//Frame 4
                6,3,//Frame 5
                6,3,//Frame 6
                6,3,//Frame 7
                6,3,//Frame 8
                6,3,//Frame 9
                6,3//Frame 10
        };

        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertTrue(result);
        }

    }

    @Test
    public void rollGutter() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {
                0,0,//Frame 1
                0,0,//Frame 2
                0,0,//Frame 3
                0,0,//Frame 4
                0,0,//Frame 5
                0,0,//Frame 6
                0,0,//Frame 7
                0,0,//Frame 8
                0,0,//Frame 9
                0,0//Frame 10
        };

        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertTrue(result);
        }

    }

    @Test
    public void rollPerfectTen() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {
                10,//Frame 1
                10,//Frame 2
                10,//Frame 3
                10,//Frame 4
                10,//Frame 5
                10,//Frame 6
                10,//Frame 7
                10,//Frame 8
                10,//Frame 9
                10,//Frame 10,
                10,//Bonus1
                10//Bonus2
        };

        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertTrue(result);
        }

    }

    @Test
    public void rollGameWithStrikeOnLastFrameAndStrikeOnBonus1() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {
                10,//Frame 1
                10,//Frame 2
                10,//Frame 3
                10,//Frame 4
                10,//Frame 5
                10,//Frame 6
                10,//Frame 7
                10,//Frame 8
                10,//Frame 9
                10,//Frame 10
                10,//Bonus 1
                6,//Bonus 2
        };

        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertTrue(result);
        }

    }

    @Test
    public void rollGameWithStrikeOnLastFrameAndSpareOnBonus() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {
                10,//Frame 1
                10,//Frame 2
                10,//Frame 3
                10,//Frame 4
                10,//Frame 5
                10,//Frame 6
                10,//Frame 7
                10,//Frame 8
                10,//Frame 9
                10,//Frame 10
                9,//Bonus 1
                1,//Bonus 2
        };

        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertTrue(result);
        }
    }

    @Test
    public void rollGameWithSpareOnLastFrameAndStrikeOnBonus() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {
                10,//Frame 1
                10,//Frame 2
                10,//Frame 3
                10,//Frame 4
                10,//Frame 5
                10,//Frame 6
                10,//Frame 7
                10,//Frame 8
                10,//Frame 9
                9,//Frame 10
                1,//Bonus 1
                6,//Bonus 2
        };

        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertTrue(result);
        }
    }

    @Test
    public void rollGameWithSpareOnLastFrame() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {
                10,//Frame 1
                10,//Frame 2
                10,//Frame 3
                10,//Frame 4
                10,//Frame 5
                10,//Frame 6
                10,//Frame 7
                10,//Frame 8
                10,//Frame 9
                9,//Frame 10
                1,//Bonus 1
                6,//Bonus 2
        };

        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertTrue(result);
        }
    }

    private boolean[] executeGame(GameEngine gameEngine, int[] inputs) throws GameNotInitializedException {
        boolean[] results = new boolean[inputs.length];
        int index = 0;
        for (int roll : inputs) {
            results[index++] = gameEngine.roll(roll);
        }
        return results;
    }

    @Test
    public void rollGameWithInvalidPoints() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = { 11, -1 };
        boolean[] results = executeGame(gameEngine, inputs);
        for(boolean result: results) {
            assertFalse(result);
        }
    }

    @Test
    public void rollGameWithInvalidSecondRollsForFrame() throws GameNotInitializedException {
        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.init();

        int[] inputs = {9,2};
        boolean[] results = executeGame(gameEngine, inputs);
        int index = 0;
        for(boolean result: results) {
            if(index % 2 == 0) {
                assertTrue(result);
            } else {
                assertFalse(result);
            }
            index++;
        }
    }
}