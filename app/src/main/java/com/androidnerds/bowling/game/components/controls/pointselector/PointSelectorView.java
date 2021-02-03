package com.androidnerds.bowling.game.components.controls.pointselector;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.databinding.ContentPointSelectorBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A CompoundView to display list of possible points. The user has the option to select an item from the list.
 * This class is an extension of the {@link CardView} and contains:
 *  1. A Label - {@link android.widget.TextView}
 *  2. A {@link RecyclerView} for displaying the points.
 *  </p>
 *  <p>
 *  The layout is inflated to this ViewGroup. Based on the orientation configuration of the screen,
 *  the view adjust itself to display the buttons for the points in the screen.
 *  If the user is in the {@link Configuration#ORIENTATION_PORTRAIT}, then displays the points as a Grid with 6 columns.
 *  If the user is in the {@link Configuration#ORIENTATION_LANDSCAPE}, then displays the points as a horizontal list.
 *  </p>
 *
 *  <p>
 *  When the user selects an point from the selector, the class sends the selected point via {@link OnPointSelectedChangeListener#onPointSelected(int)}.
 *  </p>
 */
@BindingMethods(@BindingMethod(type = PointSelectorView.class, attribute = "app:onPointSelected", method = "setOnPointSelectedChangeListener"))
public class PointSelectorView extends CardView {

    public interface OnPointSelectedChangeListener {
        void onPointSelected(int points);
    }

    private List<Integer> points = new ArrayList<>();
    private PointSelectorAdapter pointSelectorAdapter;
    private OnPointSelectedChangeListener listener;

    public PointSelectorView(Context context) {
        this(context, null);
    }

    public PointSelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ContentPointSelectorBinding binding = DataBindingUtil.inflate(inflater, R.layout.content_point_selector,this, true);
        initPointsListView(context, binding);
    }

    /**
     * Initializes the view.
     * @param context
     * @param binding
     */
    private void initPointsListView(Context context, ContentPointSelectorBinding binding) {
        setCardViewStyles(context);
        setupPointsList(context, binding);
    }

    private void setCardViewStyles(Context context) {
        setRadius(context.getResources().getDimension(R.dimen.card_radius));
        setCardElevation(context.getResources().getDimension(R.dimen.card_elevation));
        int padding = (int) context.getResources().getDimension(R.dimen.default_padding);
        setContentPadding(padding,padding,padding,padding);
    }

    /**
     * Sets the layoutManager for the recylerview based on the orientation.
     * @param context
     * @param binding
     */
    private void setupPointsList(Context context, com.androidnerds.bowling.databinding.ContentPointSelectorBinding binding) {
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerViewPointsList.setLayoutManager(new GridLayoutManager(context, 6));
        } else {
            binding.recyclerViewPointsList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
        binding.recyclerViewPointsList.addItemDecoration(new DefaultItemDecorator(context.getResources().getDimensionPixelSize(R.dimen.default_spacing)));
        this.pointSelectorAdapter = new PointSelectorAdapter(points);
        binding.recyclerViewPointsList.setAdapter(this.pointSelectorAdapter);
    }

    public void setOnPointSelectedChangeListener(OnPointSelectedChangeListener listener) {
        this.listener = listener;
    }

    public void showPoints(@NonNull List<Integer> points) {
        this.points = points;
        this.pointSelectorAdapter.setData(this.points);
    }

    class PointSelectorAdapter extends RecyclerView.Adapter<PointViewHolder> {

        private final List<Integer> points = new ArrayList<>();

        PointSelectorAdapter(List<Integer> points) {
            this.points.clear();
            this.points.addAll(points);
        }

        public void setData(List<Integer> points) {
            this.points.clear();
            this.points.addAll(points);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PointViewHolder(new PointButton(parent.getContext()), listener);
        }

        @Override
        public void onBindViewHolder(@NonNull PointViewHolder holder, int position) {
            holder.bind(points.get(position));
        }

        @Override
        public int getItemCount() {
            return points.size();
        }

    }

    static class PointViewHolder extends RecyclerView.ViewHolder {

        private PointButton pointButton;
        private int points;

        public PointViewHolder(@NonNull View itemView, OnPointSelectedChangeListener listener) {
            super(itemView);
            if(itemView instanceof PointButton) {
                pointButton = (PointButton) itemView;
                pointButton.setOnClickListener(v -> {
                    if(null != listener) {
                        listener.onPointSelected(points);
                    }
                });
            }
        }

        public void bind(int points) {
            this.points = points;
            pointButton.setLabel(String.valueOf(points));
        }
    }
}
