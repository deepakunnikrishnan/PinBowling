package com.androidnerds.bowling.game.domain.model;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class ScoreBoardTest {

    @Test
    public void testScoreboardShouldPassAll() {
        final Class<?> classUnderTest = Scoreboard.class;
        Assertions.assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }


}