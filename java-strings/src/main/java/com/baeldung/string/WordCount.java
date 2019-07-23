package com.baeldung.string;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shwetaJ
 */

public class WordCount {
	private static final Logger logger = LoggerFactory.getLogger(WordCount.class);

	static int countUsingCharAt(String stringTocheck, char delimiter) {
		int counter = 0;

		char myChar[] = new char[stringTocheck.length()];
		for (int i = 0; i < stringTocheck.length(); i++) {
			myChar[i] = stringTocheck.charAt(i);
			if (((i > 0) && (myChar[i] != delimiter) && (myChar[i - 1] == delimiter))
					|| ((myChar[0] != delimiter) && (i == 0)))
				counter++;
		}
		logger.info("Total no of words are: " + counter);

		return counter;
	}

	/*
	static int countUsingSpringStringUtils(String stringTocheck, String delimiter) {
		return org.springframework.util.StringUtils.countOccurrencesOf(stringTocheck, delimiter) + 1;
	}
	*/

	static int countUsingSplit(String stringTocheck, String delimiter) {
		int counter = stringTocheck.split(delimiter).length;
		logger.info("Total no of words using split are: " + counter);

		return counter;
	}

	static int countUsingStringTokenizer(String stringTocheck, String delimiter) {
		StringTokenizer tokenizer = new StringTokenizer(stringTocheck, delimiter);
		logger.info("Total no of words in StringTokenizer are: " + tokenizer.countTokens());

		return tokenizer.countTokens();
	}

	static int countUsingApacheStringUtils(String stringTocheck, String delimiter) {
		return org.apache.commons.lang3.StringUtils.countMatches(stringTocheck, delimiter) + 1;
	}
}