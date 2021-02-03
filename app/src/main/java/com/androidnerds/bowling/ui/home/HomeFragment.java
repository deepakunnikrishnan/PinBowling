package com.androidnerds.bowling.ui.home;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.databinding.HomeFragmentBinding;

/**
 * Fragment class representing the content for the Home screen.
 * Binds to the {@link HomeViewModel} to update the Home screen.
 * <p>
 * The UI mainly contains a {@link com.androidnerds.bowling.game.components.scoreboard.ScoreboardView} &
 * {@link com.androidnerds.bowling.game.components.controls.pointselector.PointSelectorView}.
 * </p>
 *
 * <p>
 * HomeFragment class observes to the LiveData exposed by the {@link HomeViewModel#gameCompletionStatusLiveData} &
 * {@link HomeViewModel#messagesLiveData} to display prompts or messages to the user.
 * </p>
 *
 *
 */
public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeViewModel mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        this.binding.setLifecycleOwner(getViewLifecycleOwner());
        this.binding.setViewModel(mViewModel);
        observeViewModel(mViewModel);
    }

    private void observeViewModel(HomeViewModel mViewModel) {
        mViewModel.gameCompletionStatusLiveData.observe(getViewLifecycleOwner(), this::onGameCompleted);
        mViewModel.messagesLiveData.observe(getViewLifecycleOwner(), this::onMessageReceived);
    }

    /**
     * Display a message to the user.
     * @param message
     */
    private void onMessageReceived(String message) {
        Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
    }

    private void onGameCompleted(boolean status) {
        if(status) {
            Toast.makeText(getActivity(), "Game over",Toast.LENGTH_SHORT).show();
        }
    }

}