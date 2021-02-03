package com.androidnerds.bowling.game.domain.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameUtilsTest {

    @Test
    public void testIsValidPointsForValidValuesShouldReturnTrue() {
        assertTrue(GameUtils.isValidPoint(0));
        assertTrue(GameUtils.isValidPoint(5));
        assertTrue(GameUtils.isValidPoint(10));
    }

    @Test
    public void testIsValidPointsForInvalidValueShouldReturnFalse() {
        assertFalse(GameUtils.isValidPoint(-1));
        assertFalse(GameUtils.isValidPoint(11));
    }

    @Test
    public void testIsValidPointsForTwoValidValuesShouldReturnTrue() {
        assertTrue(GameUtils.isValidPointWithPreviousPointAs(0,10));
        assertTrue(GameUtils.isValidPointWithPreviousPointAs(5,5));
        assertTrue(GameUtils.isValidPointWithPreviousPointAs(10,0));
    }

    @Test
    public void testIsValidPointsForInvalidValuesShouldReturnFalse() {
        assertFalse(GameUtils.isValidPointWithPreviousPointAs(0,11));
        assertFalse(GameUtils.isValidPointWithPreviousPointAs(11,0));
        assertFalse(GameUtils.isValidPointWithPreviousPointAs(-5,5));
        assertFalse(GameUtils.isValidPointWithPreviousPointAs(5,-5));
        assertFalse(GameUtils.isValidPointWithPreviousPointAs(5,-5));
    }

    @Test
    public void testIsStrikeForValidValueShouldReturnTrue() {
        assertTrue(GameUtils.isStrike(10));
    }

    @Test
    public void testIsStrikeForInValidValueShouldReturnFalse() {
        assertFalse(GameUtils.isStrike(9));
        assertFalse(GameUtils.isStrike(0));
        assertFalse(GameUtils.isStrike(-1));
        assertFalse(GameUtils.isStrike(11));
    }

    @Test
    public void testIsSpareForValidValueShouldReturnTrue() {
        assertTrue(GameUtils.isSpare(10,0));
        assertTrue(GameUtils.isSpare(5,5));
        assertTrue(GameUtils.isSpare(0,10));
    }

    @Test
    public void testIsSpareForInValidValueShouldReturnFalse() {
        assertFalse(GameUtils.isSpare(0,0));
        assertFalse(GameUtils.isSpare(9,2));
        assertFalse(GameUtils.isSpare(2,9));
        assertFalse(GameUtils.isSpare(-1,10));
        assertFalse(GameUtils.isSpare(10,-1));
    }

    @Test
    public void testIsGutterForValidValueShouldReturnTrue() {
        assertTrue(GameUtils.isGutter(0));
    }

    @Test
    public void testIsGutterForInValidValueShouldReturnFalse() {
        assertFalse(GameUtils.isGutter(-1));
        assertFalse(GameUtils.isGutter(1));
        assertFalse(GameUtils.isGutter(10));
    }

}