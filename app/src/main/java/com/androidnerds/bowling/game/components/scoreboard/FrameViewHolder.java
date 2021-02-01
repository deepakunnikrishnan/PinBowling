package com.androidnerds.bowling.game.components.scoreboard;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.game.domain.model.Frame;

import java.util.List;

public class FrameViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewFrameNumber;
    private TextView textViewFirstRoll;
    private TextView textViewSecondRoll;
    private TextView textViewThirdRoll;
    private TextView textViewCumulativeScore;


    public FrameViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewFrameNumber = itemView.findViewById(R.id.textViewFrameNumber);
        textViewFirstRoll = itemView.findViewById(R.id.textViewFirstRoll);
        textViewSecondRoll = itemView.findViewById(R.id.textViewSecondRoll);
        textViewThirdRoll = itemView.findViewById(R.id.textViewThirdRoll);
        textViewCumulativeScore = itemView.findViewById(R.id.textViewCumulativeScore);
    }

    public void bind(Frame frame) {
        textViewFrameNumber.setText(String.valueOf(frame.getFrameNumber()));
        List<Integer> rolls = frame.getRolls();
        if (frame.isLastFrame()) {
            textViewThirdRoll.setVisibility(View.VISIBLE);
        } else {
            textViewThirdRoll.setVisibility(View.GONE);
        }
        if (frame.getFrameStatus() == Frame.FrameStatus.EMPTY) {
            resetFrame();
        } else {
            if (!rolls.isEmpty()) {
                setPoints(frame, rolls);
            }
            if (frame.getCumulativeScore() != -1) {
                textViewCumulativeScore.setText(String.valueOf(frame.getCumulativeScore()));
            }
        }
    }

    private void setPoints(Frame frame, List<Integer> roll) {
        textViewFirstRoll.setText(getStringValueForRoll(frame.getRolls().get(0)));
        textViewSecondRoll.setText("");
        if (roll.size() >= 2) {
            textViewSecondRoll.setText(getStringValueForRoll(frame.getRolls().get(1)));
        }
        if (frame.isLastFrame() && roll.size() == 3) {
            textViewThirdRoll.setText(getStringValueForRoll(frame.getRolls().get(2)));
        }
    }

    private void resetFrame() {
        textViewFirstRoll.setText("");
        textViewSecondRoll.setText("");
        textViewThirdRoll.setText("");
        textViewCumulativeScore.setText("");
    }

    private String getStringValueForRoll(int roll) {
        if (roll == 0) {
            return "-";
        } else if (roll == 10) {
            return "X";
        } else {
            return String.valueOf(roll);
        }
    }
}
