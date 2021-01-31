package com.androidnerds.bowling.game.domain.model;

import static org.junit.Assert.assertEquals;

public class ScoreBoardTest {

    /*@Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getPossiblePoints() {
        Player player = new Player("test");
        ScoreBoard scoreBoard = new ScoreBoard(player, GameConstants.MAX_FRAMES);
        List<Integer> possiblePoints = scoreBoard.getPossiblePoints();
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

        scoreBoard.updateScore(inputs[0]);

        possiblePoints = scoreBoard.getPossiblePoints();
        assertEquals(6, possiblePoints.size());
        assertEquals(0, possiblePoints.get(0).intValue());
        assertEquals(5, possiblePoints.get(possiblePoints.size() - 1).intValue());

        for (int i = 1; i < inputs.length; i++) {
            scoreBoard.updateScore(inputs[i]);
        }

        possiblePoints = scoreBoard.getPossiblePoints();
        assertEquals(11, possiblePoints.size());

        scoreBoard.updateScore(7);

        possiblePoints = scoreBoard.getPossiblePoints();
        assertEquals(4, possiblePoints.size());
        assertEquals(3, possiblePoints.get(possiblePoints.size() - 1).intValue());
    }

    @Test
    public void getPossiblePointsWithoutStrikeInFirstRollForLastFrame() {

        Player player = new Player("test");
        ScoreBoard scoreBoard = new ScoreBoard(player, GameConstants.MAX_FRAMES);
        List<Integer> possiblePoints = scoreBoard.getPossiblePoints();
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
            scoreBoard.updateScore(point);
        }

        possiblePoints = scoreBoard.getPossiblePoints();
        assertEquals(2, possiblePoints.size());
        assertEquals(1, possiblePoints.get(possiblePoints.size() - 1).intValue());
    }*/
}