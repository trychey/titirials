package com.baeldung.regex.countdigits;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.google.common.base.CharMatcher;

/**
 * Unit Test to count the number of digits in a String
 */
public class CountDigitsUnitTest {

    private static final Pattern DIGIT_REGEX_PATTERN = Pattern.compile("\\d");
    private static final CharMatcher DIGIT_CHAR_MATCHER = CharMatcher.inRange('0', '9');
    
    private static final String STR_WITH_ALL_DIGITS = "970987678607608";
    private static final String STR_WITH_SINGLE_DIGITS_SEP_BY_NON_DIGITS = "9kjl()4f*(&6~3dfd8&5dfd8a";
    private static final String STR_WITH_SEQUENCES_OF_1_OR_MORE_DIGITS_SEP_BY_NON_DIGITS = "64.6lk.l~453lkdsf9wg038.68*()(k;95786fsd7986";
    
    private static int countDigitsUsingDigitRegexPatternOnString(String stringToSearch) {
        Matcher countEmailMatcher = DIGIT_REGEX_PATTERN.matcher(stringToSearch);

        int count = 0;
        while (countEmailMatcher.find()) {
            count++;
        }

        return count;
    }

    @Test
    public void givenStringOfAllDigits_whenRegexMatchByDigit_thenFifteenDigitsCounted() {
        int count = countDigitsUsingDigitRegexPatternOnString(STR_WITH_ALL_DIGITS);

        assertEquals(15, count);
    }

    @Test
    public void givenStringWithSingleDigitsSepByNonDigits_whenRegexMatchByDigit_thenSevenDigitsCounted() {
        int count = countDigitsUsingDigitRegexPatternOnString(STR_WITH_SINGLE_DIGITS_SEP_BY_NON_DIGITS);

        assertEquals(7, count);
    }

    @Test
    public void givenStringWithSeqOfOneOrMoreDigitsSepByNonDigits_whenRegexMatchByDigit_thenTwentyOneDigitsCounted() {
        int count = countDigitsUsingDigitRegexPatternOnString(STR_WITH_SEQUENCES_OF_1_OR_MORE_DIGITS_SEP_BY_NON_DIGITS);

        assertEquals(21, count);
    }
    
    @Test
    public void givenStringOfAllDigits_whenGuavaCharMatchByDigit_thenFifteenDigitsCounted() {
        int count = DIGIT_CHAR_MATCHER.countIn(STR_WITH_ALL_DIGITS);

        assertEquals(15, count);
    }
    
    @Test
    public void givenStringWithSingleDigitsSepByNonDigits_whenGuavaCharMatchByDigit_thenSevenDigitsCounted() {
        int count = DIGIT_CHAR_MATCHER.countIn(STR_WITH_SINGLE_DIGITS_SEP_BY_NON_DIGITS);

        assertEquals(7, count);
    }
    
    @Test
    public void givenStringWithSeqOfOneOrMoreDigitsSepByNonDigits_whenGuavaCharMatchByDigit_thenTwentyOneDigitsCounted() {
        int count = DIGIT_CHAR_MATCHER.countIn(STR_WITH_SEQUENCES_OF_1_OR_MORE_DIGITS_SEP_BY_NON_DIGITS);

        assertEquals(21, count);
    }
}
