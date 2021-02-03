package com.androidnerds.bowling.game.domain.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Frame {

    public enum FrameStatus {
        EMPTY,
        PLAYING,
        COMPLETED
    }
    @NonNull
    private FrameStatus frameStatus = FrameStatus.EMPTY;
    @NonNull
    private List<Integer> rolls = new ArrayList<>();
    private int frameNumber;
    private int bonusRolls;
    private int totalScore;
    private int bonusScore;
    private int cumulativeScore = -1;

    private boolean isLastFrame;

    public Frame(int frameNumber, boolean isLastFrame) {
        this.frameNumber = frameNumber;
        this.isLastFrame = isLastFrame;
    }

    public int getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public void setRolls(List<Integer> rolls) {
        this.rolls = rolls;
    }

    public int getBonusScore() {
        return bonusScore;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }

    public int getBonusRolls() {
        return bonusRolls;
    }

    public void setBonusRolls(int bonusRolls) {
        this.bonusRolls = bonusRolls;
    }

    public FrameStatus getFrameStatus() {
        return frameStatus;
    }

    public void setFrameStatus(FrameStatus frameStatus) {
        this.frameStatus = frameStatus;
    }

    public int getCumulativeScore() {
        return cumulativeScore;
    }

    public void setCumulativeScore(int cumulativeScore) {
        this.cumulativeScore = cumulativeScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public List<Integer> getRolls() {
        return rolls;
    }

    public boolean isLastFrame() {
        return isLastFrame;
    }

    public void setLastFrame(boolean lastFrame) {
        isLastFrame = lastFrame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Frame)) return false;
        Frame frame = (Frame) o;
        return getFrameNumber() == frame.getFrameNumber() &&
                getBonusRolls() == frame.getBonusRolls() &&
                getTotalScore() == frame.getTotalScore() &&
                getBonusScore() == frame.getBonusScore() &&
                getCumulativeScore() == frame.getCumulativeScore() &&
                isLastFrame() == frame.isLastFrame() &&
                getFrameStatus() == frame.getFrameStatus() &&
                getRolls().equals(frame.getRolls());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFrameStatus(), getRolls(), getFrameNumber(), getBonusRolls(), getTotalScore(), getBonusScore(), getCumulativeScore(), isLastFrame());
    }
}
