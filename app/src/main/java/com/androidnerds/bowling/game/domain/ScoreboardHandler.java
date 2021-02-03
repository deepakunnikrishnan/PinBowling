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

/**
 * <p>
 *     ScoreboardHandler class implements the core business logic related to executing the game.
 * </p>
 */
public class ScoreboardHandler {

    private final Scoreboard scoreBoard;
    private int currentRoll = 1;
    private int currentFrameIndex = 0;

    public ScoreboardHandler(@NonNull Scoreboard scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    /**
     * Validates the point passed and then applies the business logic to update the scoreboard.
     * <p>
     *     1. Updates the point to the current frame.
     *     2. Calculates the bonus points for previous frames if required.
     *     3. Calculates the cumulative points for the frame.
     *     4. Increments the roll count and frame index.
     *
     * </p>
     * @param points
     * @return If the point is invalid for the current frame and current roll, then returns false.
     * else it returns true.
     */
    public boolean updateScore(int points) {
        if (!isValidPoint(points)) {
            return false;
        }
        Log.d(TAG, "********* START OF FRAME " + currentFrameIndex + " *********");
        Log.d(TAG, "updateScore(frameIndex: " + currentFrameIndex + ", roll: " + currentRoll + ", points:" + points + ")");
        Frame frame = this.getFrames().get(currentFrameIndex);
        updatePointsToFrame(frame, points);
        Log.d(TAG, "totalScore(frameIndex:" + currentFrameIndex + ") = " + frame.getTotalScore());
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

    /**
     * Adds the points for the roll to the rolls list in the frame.
     * Adds the same point to the total score for the frame.
     * @param frame
     * @param points
     */
    private void updatePointsToFrame(Frame frame, int points) {
        List<Integer> rolls = frame.getRolls();
        rolls.add(points);
        if (frame.getFrameStatus() == Frame.FrameStatus.EMPTY) {
            frame.setFrameStatus(Frame.FrameStatus.PLAYING);
        }
        frame.setTotalScore(frame.getTotalScore() + points);
    }

    /**
     * Based on the game's current status, the roll & frame index are updated.
     * Applies different logic to based on the currentFrameIndex.
     * @param frame
     */
    private void updateRollAndFrameIndices(@NonNull Frame frame) {
        if (isLastFrame(currentFrameIndex)) {
            updateRollAndFrameIndexForLastFrame();
        } else {
            updateRollAndFrameIndexForOtherFrame(frame);
        }
    }

    /**
     * If the current roll is a Strike, then the currentRoll is incremented by 2.
     * For all other cases, increment the currentRoll by 1.
     * If the user has reached the end of the frame,
     *  then the status of the frame is set as {@link com.androidnerds.bowling.game.domain.model.Frame.FrameStatus#COMPLETED}
     *  and increments the frameIndex by 1.
     * @param frame
     */
    private void updateRollAndFrameIndexForOtherFrame(@NonNull Frame frame) {
        //Update roll count
        if (GameUtils.isRollAStrike(frame)) {
            Log.d(TAG, "Roll IS A Strike - increment by 2");
            currentRoll += 2;
        } else {
            Log.d(TAG, "Roll NOT A Strike - increment by 1");
            currentRoll++;
        }
        //Update the frame Index
        if (GameUtils.isNextFrame(currentRoll)) {
            setFrameAsCompleted(frame);
        }
    }

    /**
     * For the Last frame in the board, user can have at most 3 rolls.
     * Based on the status, the Frame's status is updated.
     */
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
        Log.d(TAG, "********* END OF FRAME " + (currentFrameIndex + 1) + " *********");
        currentFrameIndex++;
        Log.d(TAG, "********* START OF FRAME " + (currentFrameIndex + 1) + " *********");
    }
    /**
     * If the user rolls a Strike or Spare, the user gets bonus rolls.
     * If its Strike, user receives 2 bonus rolls.
     * If its Spare, user receives 1 bonus roll.
     * @param frame
     */
    private void updateBonusRollsForFrame(Frame frame) {
        if (GameUtils.isRollAStrike(frame)) {
            Log.d(TAG, "isStrike");
            frame.setBonusRolls(2);
        } else if (GameUtils.isRollASpare(frame)) {
            Log.d(TAG, "isSpare");
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
                if (rolls.size() == 2 && frame.getTotalScore() < MAX_POINTS_FOR_ROLL) {
                    calculateCumulativeScoreForFrame(frame, previousFrame);
                } else if (rolls.size() == 3) {
                    calculateCumulativeScoreForFrame(frame, previousFrame);
                }
            } else {
                if ((GameUtils.isRollAStrike(frame) || GameUtils.isRollASpare(frame))) {
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
        Log.d(TAG, "cumulativeScore(frameNumber:" + currentFrame.getFrameNumber() + ") = " + currentFrame.getCumulativeScore());
    }

    public List<Frame> getFrames() {
        return scoreBoard.getFrames();
    }

    public Player getPlayer() {
        return scoreBoard.getPlayer();
    }

    /**
     * Calculates the bonus for the previous frames with Strike/Spare.
     * After adding to the bonus point, the bonus rolls for that frame is decremented.
     * @param frames
     * @param currentFrameIndex
     * @param points
     */
    private void updateBonusForPreviousFrames(List<Frame> frames, int currentFrameIndex, int points) {
        Log.d(TAG, "addBonusForPreviousFrames(currentFrameIndex:" + currentFrameIndex + ", points: " + points + ")");
        int i = currentFrameIndex - 2;
        if (i < 0) {
            i = 0;
        }
        if (currentFrameIndex > 0) {
            Log.d(TAG, "currentFrameIndex > 0");
            for (; i < currentFrameIndex; i++) {
                int bonusRolls = frames.get(i).getBonusRolls();
                Log.d(TAG, "(bonusRolls, frameIndex) = " + bonusRolls + "," + i);
                if (bonusRolls > 0) {
                    Log.d(TAG, "bonusRolls > 0");
                    frames.get(i).setBonusScore(frames.get(i).getBonusScore() + points);
                    Log.d(TAG, "setBonusScore(frameIndex: " + i + ") = " + frames.get(i).getBonusScore());
                    frames.get(i).setBonusRolls(--bonusRolls);
                }
            }
        }
    }

    private boolean isValidPoint(int points) {
        return GameUtils.isValidPoint(points) &&
                currentFrameIndex < this.getFrames().size() && isValidPointFor(currentFrameIndex, points);
    }

    private boolean isValidPointFor(int frameIndex, int points) {
        if (isLastFrame(frameIndex)) {
            return isValidForLastFrame(points);
        }
        return GameUtils.isValidPointWithPreviousPointAs(points, this.getFrames().get(frameIndex).getTotalScore());
    }

    /**
     * Validates whether the point is acceptable for the LastFrame.
     * @param points
     * @return
     */
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

    /**
     * Method returns the list of points that are possible for the next roll.
     * It is calculated based on the frameIndex and the no:of rolls left in the frame.
     * @return
     */
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
