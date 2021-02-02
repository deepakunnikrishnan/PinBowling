package com.androidnerds.bowling.game.components.controls.pointselector;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnerds.bowling.R;

import java.util.ArrayList;
import java.util.List;

public class PointSelectorView extends ConstraintLayout {

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
        View view = inflater.inflate(R.layout.content_point_selector, this, true);
        RecyclerView recyclerViewPointsList = view.findViewById(R.id.recyclerViewPointsList);
        recyclerViewPointsList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewPointsList.addItemDecoration(new DefaultItemDecorator(context.getResources().getDimensionPixelSize(R.dimen.default_spacing)));
        this.pointSelectorAdapter = new PointSelectorAdapter(points);
        recyclerViewPointsList.setAdapter(this.pointSelectorAdapter);

    }

    public void setOnPointSelectedChangeListener(OnPointSelectedChangeListener listener) {
        this.listener = listener;
    }

    public void showPoints(List<Integer> points) {
        this.points = points;
        this.pointSelectorAdapter.setData(this.points);
    }

    class PointSelectorAdapter extends RecyclerView.Adapter<PointViewHolder> {

        private List<Integer> points = new ArrayList<>();

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
