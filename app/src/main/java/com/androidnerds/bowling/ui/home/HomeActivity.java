package com.androidnerds.bowling.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.androidnerds.bowling.R;
import com.androidnerds.bowling.databinding.HomeActivityBinding;


/**
 * The LauncherActivity for the applications.
 * Created as the container for the Home screen.
 * Adds the {@link HomeFragment} which forms the content for the screen.
 */
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