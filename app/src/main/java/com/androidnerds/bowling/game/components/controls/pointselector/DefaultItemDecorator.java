package com.androidnerds.bowling.game.components.controls.pointselector;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DefaultItemDecorator extends RecyclerView.ItemDecoration {

    private int spacing;

    public DefaultItemDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if(parent.getChildLayoutPosition(view) == 0) {
            outRect.left = 0;
        }else {
            outRect.left = spacing;
        }

        outRect.top = spacing;
        outRect.bottom = spacing;
        outRect.right = 0;
        //outRect.right = spacing;

    }
}
