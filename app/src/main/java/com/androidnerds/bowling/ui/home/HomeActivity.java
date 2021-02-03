package com.androidnerds.bowling.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.databinding.HomeActivityBinding;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.home_activity);
        binding.setLifecycleOwner(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commitNow();
        }
    }
}