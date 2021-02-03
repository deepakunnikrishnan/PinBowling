package com.androidnerds.bowling.game.domain.utils;

import com.androidnerds.bowling.game.domain.constant.GameConstants;

public final class GameUtils {


    private GameUtils() {
        //empty constructor
    }

    public static boolean isValidPoint(int currentPoints) {
        return currentPoints >= 0 && currentPoints <= GameConstants.MAX_POINTS_FOR_ROLL;
    }

    public static boolean isValidPointWithPreviousPointAs(int currentPoints, int previousPoints) {
        return isValidPoint(currentPoints) &&
                isValidPoint(previousPoints) &&
                currentPoints + previousPoints <= GameConstants.MAX_POINTS_FOR_ROLL;
    }

    public static boolean isGutter(int points) {
        return points == 0;
    }

    public static boolean isStrike(int points) {
        return points == GameConstants.MAX_POINTS_FOR_ROLL;
    }

    public static boolean isSpare(int currentPoints, int previousPoints) {
        return isValidPoint(currentPoints) &&
                isValidPoint(previousPoints) &&
                currentPoints + previousPoints == GameConstants.MAX_POINTS_FOR_ROLL;
    }

    public static boolean isNextFrame(int roll) {
        return roll % 2 != 0;
    }
}
