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
        mViewModel.gameCompletionStatusLiveData.observe(getViewLifecycleOwner(), status -> onGameCompleted());
        mViewModel.messagesLiveData.observe(getViewLifecycleOwner(), this::onMessageReceived);
    }

    /**
     * Display a message to the user.
     * @param message
     */
    private void onMessageReceived(String message) {
        Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
    }

    private void onGameCompleted() {
        Toast.makeText(getActivity(), "Game over",Toast.LENGTH_SHORT).show();
    }

}