package com.androidnerds.bowling.game.domain;

import android.util.Log;

import androidx.annotation.NonNull;

import com.androidnerds.bowling.game.domain.model.Frame;
import com.androidnerds.bowling.game.domain.model.Player;
import com.androidnerds.bowling.game.domain.model.Scoreboard;
import com.androidnerds.bowling.game.domain.utils.GameUtils;

import java.util.ArrayList;
import java.util.List;

import static com.androidnerds.bowling.game.domain.GameEngine.TAG;
import static com.androidnerds.bowling.game.domain.constant.GameConstants.MAX_POINTS_FOR_ROLL;

public class ScoreboardHandler {

    private Scoreboard scoreBoard;
    private int currentRoll = 1;
    private int currentFrameIndex = 0;

    public ScoreboardHandler(@NonNull Scoreboard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public boolean updateScore(int points) {
        if (!isValidPoint(points)) {
            return false;
        }
        Log.i(TAG, "********* START OF FRAME " + currentFrameIndex + " *********");
        Log.i(TAG, "updateScore(frameIndex: " + currentFrameIndex + ", roll: " + currentRoll + ", points:" + points + ")");
        Frame frame = this.getFrames().get(currentFrameIndex);
        updatePointsToFrame(frame, points);
        Log.i(TAG, "totalScore(frameIndex:" + currentFrameIndex + ") = " + frame.getTotalScore());
        if (!isLastFrame(currentFrameIndex)) {
            updateBonusRollsForFrame(frame);
        }
        updateBonusForPreviousFrames(this.getFrames(), currentFrameIndex, points);
        updateCumulativeScores(this.getFrames(), currentFrameIndex);
        updateRollAndFrameIndices(frame);
        return true;
    }

    public boolean isGameOver() {
        return currentFrameIndex >= getFrames().size();
    }

    private void updatePointsToFrame(Frame frame, int points) {
        List<Integer> rolls = frame.getRolls();
        rolls.add(points);
        if (frame.getFrameStatus() == Frame.FrameStatus.EMPTY) {
            frame.setFrameStatus(Frame.FrameStatus.PLAYING);
        }
        frame.setTotalScore(frame.getTotalScore() + points);
    }

    private void updateRollAndFrameIndices(@NonNull Frame frame) {
        if (isLastFrame(currentFrameIndex)) {
            updateRollAndFrameIndexForLastFrame();
        } else {
            updateRollAndFrameIndexForOtherFrame(frame);
        }
    }

    private void updateRollAndFrameIndexForOtherFrame(@NonNull Frame frame) {
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
            setFrameAsCompleted(frame);
        }
    }

    private void updateRollAndFrameIndexForLastFrame() {
        currentRoll++;
        Frame lastFrame = getFrames().get(getFrames().size() - 1);
        List<Integer> rolls = lastFrame.getRolls();
        if (rolls.size() == 3 || (rolls.size() == 2 && lastFrame.getTotalScore() < MAX_POINTS_FOR_ROLL)) {
            setFrameAsCompleted(lastFrame);
        }
    }

    private void setFrameAsCompleted(@NonNull Frame frame) {
        frame.setFrameStatus(Frame.FrameStatus.COMPLETED);
        Log.i(TAG, "********* END OF FRAME " + (currentFrameIndex + 1) + " *********");
        currentFrameIndex++;
        Log.i(TAG, "********* START OF FRAME " + (currentFrameIndex + 1) + " *********");
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
        int i = frameIndex - 2;
        if (i < 0) {
            i = 0;
        }
        for (; i <= frameIndex; i++) {
            Frame frame = frames.get(i);
            Frame previousFrame = i > 0 ? frames.get(i - 1) : null;
            if (isLastFrame(i)) {
                List<Integer> rolls = frame.getRolls();
                if (rolls.size() == 2 && frame.getTotalScore() < MAX_POINTS_FOR_ROLL/*(!GameUtils.isStrike(rolls.get(0)) && !isRollASpare(frame))*/) {
                    calculateCumulativeScoreForFrame(frame, previousFrame);
                } else if (rolls.size() == 3) {
                    calculateCumulativeScoreForFrame(frame, previousFrame);
                }
            } else {
                if ((isRollAStrike(frame) || isRollASpare(frame))) {
                    if (frame.getBonusRolls() == 0) {
                        calculateCumulativeScoreForFrame(frame, previousFrame);
                    }
                } else if (frame.getRolls().size() == 2) {
                    calculateCumulativeScoreForFrame(frame, previousFrame);
                }
            }
        }
    }

    private void calculateCumulativeScoreForFrame(Frame currentFrame, Frame previousFrame) {
        int cumulativeTotal = 0;
        if (null != previousFrame) {
            cumulativeTotal = previousFrame.getCumulativeScore();
        }
        cumulativeTotal += currentFrame.getTotalScore() + currentFrame.getBonusScore();
        currentFrame.setCumulativeScore(cumulativeTotal);
        Log.i(TAG, "cumulativeScore(frameNumber:" + currentFrame.getFrameNumber() + ") = " + currentFrame.getCumulativeScore());
    }

    public List<Frame> getFrames() {
        return null != scoreBoard ? scoreBoard.getFrames() : null;
    }

    public Player getPlayer() {
        return null != scoreBoard ? scoreBoard.getPlayer() : null;
    }

    private void updateBonusForPreviousFrames(List<Frame> frames, int currentFrameIndex, int points) {
        Log.i(TAG, "addBonusForPreviousFrames(currentFrameIndex:" + currentFrameIndex + ", points: " + points + ")");
        int i = currentFrameIndex - 2;
        if (i < 0) {
            i = 0;
        }
        if (currentFrameIndex > 0) {
            Log.i(TAG, "currentFrameIndex > 0");
            for (; i < currentFrameIndex; i++) {
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

    private boolean isValidPoint(int points) {
        return GameUtils.isValidPoint(points) &&
                currentFrameIndex < this.getFrames().size() && isValidPointFor(currentFrameIndex, points);
    }

    private boolean isRollAStrike(@NonNull Frame frame) {
        return frame.getRolls().size() == 1 && GameUtils.isStrike(frame.getTotalScore());
    }

    private boolean isRollAStrike(int frameIndex) {
        return isRollAStrike(this.getFrames().get(frameIndex));
    }

    private boolean isRollASpare(@NonNull Frame frame) {
        return frame.getRolls().size() == 2 && GameUtils.isSpare(frame.getRolls().get(1), frame.getRolls().get(0));
    }

    private boolean isValidPointFor(int frameIndex, int points) {
        if (isLastFrame(frameIndex)) {
            return isValidForLastFrame(points);
        }
        return GameUtils.isValidPointWithPreviousPointAs(points, this.getFrames().get(frameIndex).getTotalScore());
    }

    private boolean isValidForLastFrame(int points) {
        Frame frame = this.getFrames().get(this.getFrames().size() - 1);
        List<Integer> rolls = frame.getRolls();
        if (rolls.size() == 0) {
            return true;
        } else if (rolls.size() == 1) {
            int firstRoll = frame.getRolls().get(0);
            return validateWithPreviousRoll(points, firstRoll);
        } else if (rolls.size() == 2) {
            int firstRoll = frame.getRolls().get(0);
            int secondRoll = frame.getRolls().get(1);
            if (GameUtils.isStrike(firstRoll)) {
                return validateWithPreviousRoll(points, secondRoll);
            } else {
                return GameUtils.isSpare(secondRoll, firstRoll);
            }
        }
        return false;
    }

    private boolean validateWithPreviousRoll(int points, int previousRoll) {
        if (GameUtils.isStrike(previousRoll)) {
            return true;
        } else {
            return GameUtils.isValidPointWithPreviousPointAs(points, previousRoll);
        }
    }

    private boolean isLastFrame(int frameIndex) {
        return frameIndex == this.getFrames().size() - 1;
    }

    @NonNull
    public List<Integer> getPossiblePoints() {
        int end = MAX_POINTS_FOR_ROLL;
        if(currentFrameIndex < this.getFrames().size()) {
            Frame frame = this.getFrames().get(currentFrameIndex);
            List<Integer> rolls = frame.getRolls();
            if (isLastFrame(currentFrameIndex)) {
                if (rolls.size() == 1) {
                    if (!GameUtils.isStrike(rolls.get(0))) {
                        end = MAX_POINTS_FOR_ROLL - rolls.get(0);
                    }
                } else if (rolls.size() == 2) {
                    if (GameUtils.isStrike(rolls.get(0)) && !GameUtils.isStrike(rolls.get(1))) {
                        end = MAX_POINTS_FOR_ROLL - rolls.get(1);
                    }
                }
            } else if (rolls.size() == 1) {
                end = MAX_POINTS_FOR_ROLL - rolls.get(0);
            }
        }
        return generatePossiblePoints(end);
    }

    @NonNull
    private List<Integer> generatePossiblePoints(int end) {
        List<Integer> possiblePoints = new ArrayList<>();
        possiblePoints.add(0);
        for (int i = 1; i <= end; i++) {
            possiblePoints.add(i);
        }
        return possiblePoints;
    }
}
