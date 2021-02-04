package com.androidnerds.bowling.game.components.scoreboard;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.databinding.ItemFrameBinding;
import com.androidnerds.bowling.game.domain.model.Frame;
import com.androidnerds.bowling.game.domain.utils.GameUtils;

import java.util.List;

/**
 * ViewHolder class representing the {@link Frame} in the {@link ScoreboardView}
 *
 */
public class FrameViewHolder extends RecyclerView.ViewHolder {

    private ItemFrameBinding binding;
    public FrameViewHolder(@NonNull ItemFrameBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Frame frame) {
        binding.textViewFrameNumber.setText(String.valueOf(frame.getFrameNumber()));
        List<Integer> rolls = frame.getRolls();
        if (frame.isLastFrame()) {
            binding.textViewThirdRoll.setVisibility(View.VISIBLE);
        } else {
            binding.textViewThirdRoll.setVisibility(View.GONE);
        }
        if (frame.getFrameStatus() == Frame.FrameStatus.EMPTY) {
            resetFrame(frame);
        } else {
            setBackgroundColor(frame);
            if (!rolls.isEmpty()) {
                setPoints(frame, rolls);
            }
            if (frame.getCumulativeScore() != -1) {
                binding.textViewCumulativeScore.setText(String.valueOf(frame.getCumulativeScore()));
            }
        }
    }

    private void setBackgroundColor(Frame frame) {
        itemView.setBackgroundColor(itemView.getContext().getColor(R.color.white));
    }

    private void setPoints(Frame frame, List<Integer> roll) {
        binding.textViewFirstRoll.setText(getStringValueForRoll(frame.getRolls().get(0)));
        binding.textViewSecondRoll.setText("");
        if (roll.size() >= 2) {
            binding.textViewSecondRoll.setText(getStringValueForRoll(frame.getRolls().get(1)));
        }
        if (frame.isLastFrame() && roll.size() == 3) {
            binding.textViewThirdRoll.setText(getStringValueForRoll(frame.getRolls().get(2)));
        }
    }

    private void resetFrame(Frame frame) {
        binding.textViewFirstRoll.setText("");
        binding.textViewSecondRoll.setText("");
        binding.textViewThirdRoll.setText("");
        binding.textViewCumulativeScore.setText("");
        setBackgroundColor(frame);
    }

    private String getStringValueForRoll(int roll) {
        if (GameUtils.isGutter(roll)) {
            return "-";
        } else if (GameUtils.isStrike(roll)) {
            return "X";
        } else {
            return String.valueOf(roll);
        }
    }
}
