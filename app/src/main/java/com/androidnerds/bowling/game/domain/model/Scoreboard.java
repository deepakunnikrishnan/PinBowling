package com.androidnerds.bowling.game.domain.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {

    private Player player;
    private List<Frame> frames;

    public Scoreboard(@NonNull Player player, int maxFrames) {
        this.player = player;
        this.frames = new ArrayList<>();
        for (int i = 0; i < maxFrames; i++) {

            this.frames.add(new Frame(i+1, (i+1) == maxFrames));
        }
    }
    public Player getPlayer() {
        return player;
    }

    public List<Frame> getFrames() {
        return frames;
    }

}
