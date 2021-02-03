package com.androidnerds.bowling.game.domain.model;

import org.junit.Test;

import pl.pojo.tester.api.assertion.Assertions;

public class FrameTest {

    @Test
    public void testFrameShouldPassAll() {
        final Class<?> classUnderTest = Frame.class;
        Assertions.assertPojoMethodsFor(classUnderTest).areWellImplemented();
    }
}