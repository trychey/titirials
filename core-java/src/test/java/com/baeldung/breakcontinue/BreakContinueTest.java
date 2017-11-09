package com.baeldung.breakcontinue;

import static com.baeldung.breakcontinue.BreakContinue.labeledBreak;
import static com.baeldung.breakcontinue.BreakContinue.labeledContinue;
import static com.baeldung.breakcontinue.BreakContinue.unlabeledBreak;
import static com.baeldung.breakcontinue.BreakContinue.unlabeledBreakNestedLoops;
import static com.baeldung.breakcontinue.BreakContinue.unlabeledContinue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Santosh
 *
 */
public class BreakContinueTest {

    @Test
    public void whenUnlabeledBreak_ThenEqual() {
        assertEquals(3, unlabeledBreak());
    }

    @Test
    public void whenUnlabeledBreakNestedLoops_ThenEqual() {
        assertEquals(3, unlabeledBreakNestedLoops());
    }

    @Test
    public void whenLabeledBreak_ThenEqual() {
        assertEquals(1, labeledBreak());
    }

    @Test
    public void whenUnlabeledContinue_ThenEqual() {
        assertEquals(3, unlabeledContinue());
    }

    @Test
    public void whenLabeledContinue_ThenEqual() {
        assertEquals(3, labeledContinue());
    }

}
