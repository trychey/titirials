package com.baeldung.regexp.optmization;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class OptimizedMatcherUnitTest {

    private long time;
    private long mstimePreCompiled;
    private long mstimeNotPreCompiled;

    private String action;

    private List<String> items;

    @Before
    public void setup() {
        Random random = new Random();
        items = new ArrayList<String>();
        long average = 0;

        for (int i = 0; i < 100000; ++i) {
            StringBuilder s = new StringBuilder();
            int characters = random.nextInt(7) + 1;
            for (int k = 0; k < characters; ++ k) {
                char c = (char)(random.nextInt('Z' - 'A') + 'A');
                int rep = random.nextInt(95) + 5;
                for (int j = 0; j < rep; ++ j)
                    s.append(c);
                average += rep;
            }
            items.add(s.toString());
        }

        average /= 100000;
        System.out.println("generated data, average length: " + average);
    }


    @Test
    public void givenANotPreCompiledAndAPreCompiledPatternA_whenMatcheItems_thenPreCompiledFasterThanNotPreCompiled() {

        testPatterns("A*");
        assertTrue(mstimePreCompiled < mstimeNotPreCompiled);
    }

    @Test
    public void givenANotPreCompiledAndAPreCompiledPatternABC_whenMatcheItems_thenPreCompiledFasterThanNotPreCompiled() {

        testPatterns("A*B*C*");
        assertTrue(mstimePreCompiled < mstimeNotPreCompiled);
    }

    @Test
    public void givenANotPreCompiledAndAPreCompiledPatternECWF_whenMatcheItems_thenPreCompiledFasterThanNotPreCompiled() {

        testPatterns("E*C*W*F*");
        assertTrue(mstimePreCompiled < mstimeNotPreCompiled);
    }

    private void testPatterns(String regex) {
        time = System.nanoTime();
        int matched = 0;
        int unmatched = 0;

        for (String item : this.items) {
            if (item.matches(regex)) {
                ++matched;
            }
            else {
                ++unmatched;
            }
        }

        this.action = "uncompiled: regex=" + regex + " matched=" + matched + " unmatched=" + unmatched;

        this.mstimeNotPreCompiled = (System.nanoTime() - time) / 1000000;
        System.out.println(this.action + ": " + mstimeNotPreCompiled + "ms");

        time = System.nanoTime();

        Matcher matcher = Pattern.compile(regex).matcher("");
        matched = 0;
        unmatched = 0;

        for (String item : this.items) {
            if (matcher.reset(item).matches()) {
                ++matched;
            }
            else {
                ++unmatched;
            }
        }

        this.action = "compiled: regex=" + regex + " matched=" + matched + " unmatched=" + unmatched;

        this.mstimePreCompiled = (System.nanoTime() - time) / 1000000;
        System.out.println(this.action + ": " + mstimePreCompiled + "ms");
    }
}
