package com.androidnerds.bowling.game.components.controls.pointselector;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.androidnerds.bowling.R;

public class PointButton extends AppCompatButton {

    public PointButton(Context context) {
        this(context, null);
    }

    public PointButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setSingleLine(true);
        setMaxLines(1);
        setElevation(2);
        setGravity(Gravity.CENTER);
        setPadding(context.getResources().getDimensionPixelSize(R.dimen.default_spacing),
                context.getResources().getDimensionPixelSize(R.dimen.default_spacing),
                context.getResources().getDimensionPixelSize(R.dimen.default_spacing),
                context.getResources().getDimensionPixelSize(R.dimen.default_spacing));
        setBackground(ContextCompat.getDrawable(context, R.drawable.background_point_border));
        setTextColor(ContextCompat.getColor(getContext(), R.color.purple_700));
        setLayoutParams(new ViewGroup.LayoutParams(context.getResources().getDimensionPixelSize(R.dimen.default_point_button_width), context.getResources().getDimensionPixelSize(R.dimen.default_point_button_width)));
    }

    public void setLabel(String number) {
        if(!TextUtils.isEmpty(number)) {
            setText(number);
        }
    }

}
