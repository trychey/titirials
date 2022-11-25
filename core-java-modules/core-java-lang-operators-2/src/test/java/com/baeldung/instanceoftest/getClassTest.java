package com.baeldung.instanceoftest;

import static org.junit.Assert.*;
import org.junit.Test;

import com.baeldung.getclassalt.*;

public class getClassTest {

	@Test
	public void testAnatotitan() {

		assertEquals("very aggressive", dinoBehavior(new Anatotitan()));
	}

	@Test
	public void testEuraptor() {
		assertEquals("calm", dinoBehavior(new Euraptor()));
	}

	public static String dinoBehavior(Dinosaur dinosaur) {

		if (dinosaur.getClass().equals(Anatotitan.class)) {

			Anatotitan anatotitan = (Anatotitan) dinosaur;
			return anatotitan.behavior();
		} else if (dinosaur.getClass().equals(Euraptor.class)) {
			Euraptor euraptor = (Euraptor) dinosaur;
			return euraptor.behavior();
		}
		return "";
	}

}
