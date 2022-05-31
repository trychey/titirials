package test.java.com.baeldung.tutorials;

import org.junit.Assert;
import org.junit.Test;

public class StringToIntegerUnitTest {

    @Test
    public void whenValidNumericStringIsPassed_thenShouldConvertToPrimitiveInt() {
        int givenNumber = 11;
        int givenNegativeNumber = -11;
        
        int expectedUnsignedNumber = Integer.parseInt("11");
        int expectedSignedNumber1 = Integer.parseInt("+11");
        int expectedSignedNumber2 = Integer.parseInt("-11");
        
        Assert.assertEquals(expectedUnsignedNumber, givenNumber);
        Assert.assertEquals(expectedSignedNumber1, givenNumber);
        Assert.assertEquals(expectedSignedNumber2, givenNegativeNumber);
    }

    @Test
    public void whenValidNumericStringWithRadixIsPassed_thenShouldConvertToPrimitiveInt() {
        int givenNumber1 = 17;
        int givenNumber2 = 10;
        int givenNumber3 = 7;
        
        int expectedNumber1 = Integer.parseInt("11", 16);
        int expectedNumber2 = Integer.parseInt("A", 16);
        int expectedNumber3 = Integer.parseInt("7", 8);
        
        Assert.assertEquals(expectedNumber1, givenNumber1);
        Assert.assertEquals(expectedNumber2, givenNumber2);
        Assert.assertEquals(expectedNumber3, givenNumber3);
    }

    @Test
    public void whenValidNumericStringWithRadixAndSubstringIsPassed_thenShouldConvertToPrimitiveInt() {
        int givenNumber1 = 14701;
        int givenNumber2 = 19053015;

        int expectedNumber1 = Integer.parseInt("ABCDEFG", 1, 4, 36);
        int expectedNumber2 = Integer.parseInt("abcdefg", 1, 6, 36);

        Assert.assertEquals(expectedNumber1, givenNumber1);
        Assert.assertEquals(expectedNumber2, givenNumber2);
    }

    @Test(expected = NumberFormatException.class)
    public void whenInValidNumericStringIsPassed_thenShouldThrowNumberFormatException() {
        int number = Integer.parseInt("abcd");
    }

    @Test
    public void whenValidNumericStringIsPassed_thenShouldConvertToInteger() {
        Integer givenNumber = 11;
        Integer givenNegativeNumber = -11;
        
        Integer expectedUnsignedNumber = Integer.valueOf("11");
        Integer expectedSignedNumber1 = Integer.valueOf("+11");
        Integer expectedSignedNumber2 = Integer.valueOf("-11");
        
        Assert.assertEquals(expectedUnsignedNumber, givenNumber);
        Assert.assertEquals(expectedSignedNumber1, givenNumber);
        Assert.assertEquals(expectedSignedNumber2, givenNegativeNumber);
    }

    @Test
    public void whenNumberIsPassed_thenShouldConvertToInteger() {
        Integer givenNumber = 11;
        Integer givenNegativeNumber = -11;
        Integer givenUnicodeValue = 65;
        
        Integer expectedUnsignedNumber = Integer.valueOf(11);
        Integer expectedSignedNumber1 = Integer.valueOf(+11);
        Integer expectedSignedNumber2 = Integer.valueOf(-11);
        Integer expectedUnicodeValue = Integer.valueOf('A');
        
        Assert.assertEquals(expectedUnsignedNumber, givenNumber);
        Assert.assertEquals(expectedSignedNumber1, givenNumber);
        Assert.assertEquals(expectedSignedNumber2, givenNegativeNumber);
        Assert.assertEquals(expectedUnicodeValue, givenUnicodeValue);
    }

    @Test
    public void whenValidNumericStringWithRadixIsPassed_thenShouldConvertToInetger() {
        Integer givenNumber1 = 17;
        Integer givenNumber2 = 10;
        Integer givenNumber3 = 7;
        
        Integer expectedNumber1 = Integer.valueOf("11", 16);
        Integer expectedNumber2 = Integer.valueOf("A", 16);
        Integer expectedNumber3 = Integer.parseInt("7", 8);
        
        Assert.assertEquals(expectedNumber1, givenNumber1);
        Assert.assertEquals(expectedNumber2, givenNumber2);
        Assert.assertEquals(expectedNumber3, givenNumber3);
    }

    @Test(expected = NumberFormatException.class)
    public void whenInvalidValueOfNumericStringPassed_thenShouldThrowException() {
        Integer number = Integer.valueOf("abcd");
    }
}
