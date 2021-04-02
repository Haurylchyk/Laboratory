package by.haurylchyk.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilsTest {

    @Test
    public void isPositiveNumberTestTrue(){
        boolean isPositive = StringUtils.isPositiveNumber("101");
        assertTrue(isPositive);
    }

    @Test
    public void isPositiveNumberTestFalse(){
        boolean isPositive = StringUtils.isPositiveNumber("-101");
        assertFalse(isPositive);
    }
}
