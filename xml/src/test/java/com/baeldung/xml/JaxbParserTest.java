package com.baeldung.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.baeldung.xml.binding.Tutorials;

public class JaxbParserTest {

	
	final String fileName = "src/test/resources/example.xml";

	JaxbParser parser;
	
	@Test
	public void getFullDocumentTest(){
		parser = new JaxbParser(new File(fileName));
		Tutorials tutorials = parser.getFullDocument();

		assertNotNull(tutorials);
		assertTrue(tutorials.getTutorial().size() == 4);
		assertTrue(tutorials.getTutorial().get(0).getType().equalsIgnoreCase("java"));
	}
}
