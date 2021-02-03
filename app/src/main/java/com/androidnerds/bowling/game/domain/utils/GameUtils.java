package com.androidnerds.bowling.game.domain.utils;

import androidx.annotation.NonNull;

import com.androidnerds.bowling.game.domain.constant.GameConstants;
import com.androidnerds.bowling.game.domain.model.Frame;

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

    public static boolean isRollAStrike(@NonNull Frame frame) {
        return frame.getRolls().size() == 1 && isStrike(frame.getTotalScore());
    }

    public static boolean isRollASpare(@NonNull Frame frame) {
        return frame.getRolls().size() == 2 && isSpare(frame.getRolls().get(1), frame.getRolls().get(0));
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
