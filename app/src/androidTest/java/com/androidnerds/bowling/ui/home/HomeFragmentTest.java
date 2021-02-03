package com.androidnerds.bowling.ui.home;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomeFragmentTest {

    @Test
    public void testHomeFragment() {
        FragmentScenario<HomeFragment> scenario = FragmentScenario.launchInContainer(HomeFragment.class);
        scenario.moveToState(Lifecycle.State.CREATED);

       /* Espresso.onView(ViewMatchers.withId(R.id.scoreboard))
                .check(ViewAssertions.matches())*/

    }
}