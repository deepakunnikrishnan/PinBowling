package com.androidnerds.bowling.ui.home;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.androidnerds.bowling.game.domain.model.Scoreboard;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class HomeViewModelTest {

    @Mock
    private HomeViewModel homeViewModel;
    @Mock
    private Observer<Scoreboard> scoreboardObserver;
    @Mock
    private Observer<List<Integer>> possibleValuesObserver;
    @Mock
    private Observer<Boolean> gameCompletedObserver;

    @Captor
    private ArgumentCaptor<List<Integer>> possibleValuesArgumentCaptor;
    @Captor
    private ArgumentCaptor<Scoreboard> scoreboardArgumentCaptor;
    @Captor
    private ArgumentCaptor<Boolean> gameCompletedArgumentCaptor;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        homeViewModel = Mockito.spy(new HomeViewModel());
    }

    @Test
    public void testScoreboardLiveDataValuesAfterHomeViewModelInitialize() {
        assertNotNull(homeViewModel);

        homeViewModel.scoreboard.observeForever(scoreboardObserver);

        Mockito.verify(scoreboardObserver, Mockito.times(1)).onChanged(scoreboardArgumentCaptor.capture());
        assertNotNull(scoreboardArgumentCaptor.getValue());
    }

    @Test
    public void testPossibleValuesLiveDataValuesAfterHomeViewModelInitialize() {
        assertNotNull(homeViewModel);

        homeViewModel.possibleValues.observeForever(possibleValuesObserver);

        Mockito.verify(possibleValuesObserver, Mockito.times(1)).onChanged(possibleValuesArgumentCaptor.capture());
        assertNotNull(possibleValuesArgumentCaptor.getValue());
        assertEquals(11, possibleValuesArgumentCaptor.getValue().size());
    }

    @Test
    public void testLiveDataOnPointSelected() {
        assertNotNull(homeViewModel);
        homeViewModel.possibleValues.observeForever(possibleValuesObserver);

        homeViewModel.onPointSelected(5);

        Mockito.verify(possibleValuesObserver, Mockito.times(2)).onChanged(possibleValuesArgumentCaptor.capture());
        assertNotNull(possibleValuesArgumentCaptor.getValue());
        assertEquals(6, possibleValuesArgumentCaptor.getValue().size());
    }

    @Test
    public void testGameCompletedLiveData() {
        assertNotNull(homeViewModel);
        homeViewModel.gameCompletionStatusLiveData.observeForever(gameCompletedObserver);

        int[] inputs = {
                10,//Frame 1
                10,//Frame 2
                10,//Frame 3
                10,//Frame 4
                10,//Frame 5
                10,//Frame 6
                10,//Frame 7
                10,//Frame 8
                10,//Frame 9
                9,//Frame 10
                1,//Bonus 1
                6,//Bonus 2
        };

        for(int point: inputs) {
            homeViewModel.onPointSelected(point);
        }

        Mockito.verify(gameCompletedObserver, Mockito.times(1)).onChanged(gameCompletedArgumentCaptor.capture());
        assertTrue(gameCompletedArgumentCaptor.getValue());
    }

}