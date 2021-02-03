package com.androidnerds.bowling.game.domain.model;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

public class PlayerTest {

    @Test
    public void testPlayerShouldPassAll() {
        final Class<?> classUnderTest = Player.class;
        Assertions.assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER, Method.CONSTRUCTOR)
                .areWellImplemented();
    }
}