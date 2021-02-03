package com.androidnerds.bowling.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.game.components.controls.pointselector.PointSelectorView;
import com.androidnerds.bowling.game.components.scoreboard.ScoreboardView;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel mViewModel;
    private Button buttonReset;
    private ScoreboardView scoreboardView;
    private PointSelectorView pointSelectorView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        this.scoreboardView = view.findViewById(R.id.scoreboard);
        this.pointSelectorView = view.findViewById(R.id.pointSelector);
        this.buttonReset = view.findViewById(R.id.buttonReset);
        this.buttonReset.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        observeViewModel(mViewModel);
        this.pointSelectorView.setOnPointSelectedChangeListener(this::onInputEntered);
    }

    private void observeViewModel(HomeViewModel mViewModel) {
        mViewModel.scoreboardMutableLiveData.observe(getViewLifecycleOwner(), scoreboard -> scoreboardView.onScoreboardUpdated(scoreboard));
        mViewModel.possibleValues.observe(getViewLifecycleOwner(), integers -> pointSelectorView.showPoints(integers));
        mViewModel.gameCompletionStatusLiveData.observe(getViewLifecycleOwner(), status -> onGameCompleted());
    }

    private void onInputEntered(int points) {
        mViewModel.onPointSelected(points);
    }

    private void onGameCompleted() {
        Toast.makeText(getActivity(), "Game over",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonReset) {
            mViewModel.onResetClicked();
        }

    }
}