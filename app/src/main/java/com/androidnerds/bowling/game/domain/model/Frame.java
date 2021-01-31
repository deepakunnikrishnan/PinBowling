package com.androidnerds.bowling.game.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    public enum FrameStatus {
        EMPTY,
        PLAYED
    }

    private FrameStatus frameStatus = FrameStatus.EMPTY;
    private List<Integer> rolls = new ArrayList<>();
    private int bonusRolls;
    private int totalScore;
    private int bonusScore;
    private int cumulativeScore;

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




}
