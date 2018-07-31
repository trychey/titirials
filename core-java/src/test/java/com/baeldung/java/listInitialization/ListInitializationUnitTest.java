package com.baeldung.java.listInitialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.java.Log;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

@Log
public class ListInitializationUnitTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void withAnonymousInnerClass_thenInitialiseList() {
		List<String> cities = new ArrayList() {
			//Inside declaration of the subclass
			
			//You can have multiple initializer block
			{
				log.info("Inside the first initializer block.");
			}
			
			{
				log.info("Inside the second initializer block.");
				add("New York");
				add("Rio");
				add("Tokyo");
			}
		};
		
		Assert.assertTrue(cities.contains("New York"));
	}
	
	@Test
	public void withArraysAsList_thenInitialiseList() {
		List<String> list = Arrays.asList("foo", "bar");
		
		Assert.assertTrue(list.contains("foo"));
	}
	
	@Test
	public void withArraysAsList_whenAdd_thenUnsupportedException() {
		List<String> list = Arrays.asList("foo", "bar");
		
		exception.expect(UnsupportedOperationException.class);
		list.add("baz");
	}
	
	@Test
	public void withArrayAsList_whenCreated_thenShareReference() {
		String[] array = { "foo", "bar" };
		List<String> list = Arrays.asList(array);
		array[0] = "baz";
		Assert.assertEquals("baz", list.get(0));
	}
	
	@Test
	public void withStream_thenInitializeList() {
		List<String> list = Stream.of("foo", "bar").collect(Collectors.toList());
		
		Assert.assertTrue(list.contains("foo"));
	}
}
