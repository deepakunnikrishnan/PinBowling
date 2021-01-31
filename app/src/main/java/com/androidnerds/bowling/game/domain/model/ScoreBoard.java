package com.androidnerds.bowling.game.domain.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private Player player;
    private List<Frame> frames;
    private int totalScore;

    public ScoreBoard(@NonNull Player player, int maxFrames) {
        this.player = player;
        this.frames = new ArrayList<>();
        for (int i = 0; i < maxFrames; i++) {
            this.frames.add(new Frame());
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /*public void updateScore(int points) {
        Log.i(TAG, "updateScore(frameIndex: " + currentFrameIndex + ", roll: " + currentRoll + ", points:" + points + ")");
        Frame frame = this.frames.get(currentFrameIndex);
        List<Integer> rolls = frame.getRolls();
        rolls.add(points);
        if (frame.getFrameStatus() == Frame.FrameStatus.EMPTY) {
            frame.setFrameStatus(Frame.FrameStatus.PLAYED);
        }
        frame.setTotalScore(frame.getTotalScore() + points);
        Log.i(TAG, "totalScore(frameIndex:" + currentFrameIndex + ") = " + frame.getTotalScore());

        if (!isLastFrame(currentFrameIndex)) {
            updateBonusRollsForFrame(frame);
        }

        addBonusForPreviousFrames(frames, currentFrameIndex, points);
        updateCumulativeScores(frames, currentFrameIndex);

        if (isLastFrame(currentFrameIndex)) {
            currentRoll++;
        } else {
            //Update roll count
            if (isRollAStrike(currentFrameIndex)) {
                Log.i(TAG, "Roll IS A Strike - increment by 2");
                currentRoll += 2;
            } else {
                Log.i(TAG, "Roll NOT A Strike - increment by 1");
                currentRoll++;
            }
            //Update the frame Index
            if (GameUtils.isNextFrame(currentRoll)) {
                Log.i(TAG, "********* END OF FRAME " + (currentFrameIndex + 1) + " *********");
                currentFrameIndex++;
                Log.i(TAG, "********* START OF FRAME " + (currentFrameIndex + 1) + " *********");
            }
        }
    }

    private void updateBonusRollsForFrame(Frame frame) {
        if (isRollAStrike(frame)) {
            Log.i(TAG, "isStrike");
            frame.setBonusRolls(2);
        } else if (isRollASpare(frame)) {
            Log.i(TAG, "isSpare");
            frame.setBonusRolls(1);
        }
    }

    private void updateCumulativeScores(List<Frame> frames, int frameIndex) {
        //Find if two rolls before the current is a Strike.
        //If is Strike - add the
        int cumulativeTotal = 0;
        for (int i = 0; i <= frameIndex; i++) {
            Frame frame = frames.get(i);
            if (frame.getBonusRolls() == 0) {
                cumulativeTotal += frame.getTotalScore() + frame.getBonusScore();
                frame.setCumulativeScore(cumulativeTotal);
                Log.i(TAG, "cumulativeScore(frameIndex:" + i + ") = " + frames.get(i).getCumulativeScore());
            }
        }
    }

    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public Player getPlayer() {
        return player;
    }

    private void addBonusForPreviousFrames(List<Frame> frames, int currentFrameIndex, int points) {
        Log.i(TAG, "addBonusForPreviousFrames(currentFrameIndex:" + currentFrameIndex + ", points: " + points + ")");
        if (currentFrameIndex > 0) {
            Log.i(TAG, "currentFrameIndex > 0");
            for (int i = 0; i < currentFrameIndex; i++) {
                int bonusRolls = frames.get(i).getBonusRolls();
                Log.i(TAG, "(bonusRolls, frameIndex) = " + bonusRolls + "," + i);
                if (bonusRolls > 0) {
                    Log.i(TAG, "bonusRolls > 0");
                    frames.get(i).setBonusScore(frames.get(i).getBonusScore() + points);
                    Log.i(TAG, "setBonusScore(frameIndex: " + i + ") = " + frames.get(i).getBonusScore());
                    frames.get(i).setBonusRolls(--bonusRolls);
                }
            }
        }
    }

    public boolean isValidPoint(int points) {
        return GameUtils.isValidPoint(points) && currentFrameIndex < frames.size() && isValidPointFor(currentFrameIndex, points);
    }

    private boolean isRollAStrike(int frameIndex) {
        return isRollAStrike(frames.get(frameIndex));
    }

    private boolean isRollAStrike(@NonNull Frame frame) {
        return frame.getRolls().size() == 1 && GameUtils.isStrike(frame.getTotalScore());
    }

    private boolean isRollASpare(@NonNull Frame frame) {
        return frame.getRolls().size() == 2 && GameUtils.isStrike(frame.getTotalScore());
    }

    private boolean isValidPointFor(int frameIndex, int points) {
        if (frameIndex == frames.size() - 1) {
            return validForLastFrame(points);
        }
        return GameUtils.isValidPoint(points, frames.get(frameIndex).getTotalScore());
    }

    private boolean validForLastFrame(int points) {
        Frame frame = frames.get(frames.size() - 1);
        List<Integer> rolls = frame.getRolls();
        if (rolls.size() == 0) {
            return true;
        } else if (rolls.size() == 1) {
            int firstRoll = frame.getRolls().get(0);
            return validateWithPreviousRoll(points, firstRoll);
        } else {
            int firstRoll = frame.getRolls().get(0);
            int secondRoll = frame.getRolls().get(1);
            if (GameUtils.isStrike(firstRoll)) {
                return validateWithPreviousRoll(points, secondRoll);
            } else {
                return GameUtils.isSpare(secondRoll, firstRoll);
            }
        }
    }

    private boolean validateWithPreviousRoll(int points, int previousRoll) {
        if (GameUtils.isStrike(previousRoll)) {
            return true;
        } else {
            return GameUtils.isValidPoint(points, previousRoll);
        }
    }

    private boolean isLastFrame(int frameIndex) {
        return frameIndex == frames.size() - 1;
    }

    private boolean isValidFrameIndex(int frameIndex) {
        return frameIndex >= 0 && frameIndex < frames.size();
    }

    @NonNull
    public List<Integer> getPossiblePoints() {
        Frame frame = frames.get(currentFrameIndex);
        List<Integer> rolls = frame.getRolls();
        int end = GameConstants.MAX_POINTS_FOR_ROLL;
        if (isLastFrame(currentFrameIndex)) {
            if (rolls.size() == 1) {
                if (!GameUtils.isStrike(rolls.get(0))) {
                    end = GameConstants.MAX_POINTS_FOR_ROLL - rolls.get(0);
                }
            } else if (rolls.size() == 2) {
                if(GameUtils.isStrike(rolls.get(0)) && !GameUtils.isStrike(rolls.get(1))) {
                    end = GameConstants.MAX_POINTS_FOR_ROLL - rolls.get(1);
                }
            }
        } else if (rolls.size() == 1) {
            end = GameConstants.MAX_POINTS_FOR_ROLL - rolls.get(0);
        }
        return generatePossiblePoints(end);
    }

    @NonNull
    private List<Integer> generatePossiblePoints(int end) {
        List<Integer> possiblePoints = new ArrayList<>();
        possiblePoints.add(0);
        for (int i = 1; i <= end ; i++) {
            possiblePoints.add(i);
        }
        return possiblePoints;
    }*/

}
