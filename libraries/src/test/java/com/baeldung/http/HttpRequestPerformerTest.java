package com.baeldung.http;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;

public class HttpRequestPerformerTest {

    private HttpRequestPerformer requestPerformer;

    @Before
    public void setup() {
        requestPerformer = new HttpRequestPerformer();
    }

    @Test
    public void whenGetRequest_thenOk() {
        HttpResponseWrapper response = requestPerformer.sendRequest("http://www.example.com", "GET", null, null);
        assertEquals("status code incorrect", response.getStatus(), 200);
        assertTrue("content incorrect", response.getContent()
            .contains("Example Domain"));
    }

    @Test
    public void whenPostRequest_thenOk() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("param1", "val");
        Map<String, String> properties = new HashMap<>();
        properties.put("Content-Type", "application/json");
        HttpResponseWrapper response = requestPerformer.sendRequest("http://www.example.com", "POST", parameters, properties);
        assertEquals("status code incorrect", response.getStatus(), 200);
    }

}
