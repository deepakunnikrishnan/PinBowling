package com.androidnerds.bowling.game.components.controls.pointselector;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Implementation of the {@link RecyclerView.ItemDecoration} for margin between the items added in the {@link RecyclerView}.
 * In this case, we are adding a default padding on the sides of the item.
 */
public class DefaultItemDecorator extends RecyclerView.ItemDecoration {

    private final int spacing;

    public DefaultItemDecorator(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = spacing;
        outRect.top = spacing;
        outRect.bottom = spacing;
        outRect.right = 0;
    }
}
