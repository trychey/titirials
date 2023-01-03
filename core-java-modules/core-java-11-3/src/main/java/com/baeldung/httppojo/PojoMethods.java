package com.baeldung.httppojo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import static java.net.http.HttpResponse.BodyHandlers.*;

public class PojoMethods {
    public static void main(String[] args) throws Exception {
        // gsonMethod();
        // jacksonRe();
        String s = jacksonRe();
        System.out.println(s);

    }

    public static String gsonMethod() throws Exception {
        String response = sampleApiRequest();

        Gson gson = new GsonBuilder().create();

        List<TodoApp> sectionlist = gson.fromJson(response, new TypeToken<List<TodoApp>>() {
        }.getType());

        return sectionlist.toString();

    }

    public static String jacksonRe() throws Exception {
        String response = sampleApiRequest();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        TodoApp[] app = objectMapper.treeToValue(jsonNode, TodoApp[].class);

        return Arrays.toString(app);

    }

    public static String sampleApiRequest() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();

        HttpResponse<String> response = client.send(request, ofString());

        return response.body();

    }

    public static String asynJackson() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();

        ObjectMappingJackson o = new ObjectMappingJackson();

        List<TodoApp> model = HttpClient.newHttpClient()
            .sendAsync(request, ofString())
            .thenApply(HttpResponse::body)
            .thenApply(o::readValue)
            .get();

        return model.toString();

    }

    public static String asynGson() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://jsonplaceholder.typicode.com/todos"))
            .build();
        ObjectMappingGson o = new ObjectMappingGson();

        List<TodoApp> model = HttpClient.newHttpClient()
            .sendAsync(request, ofString())
            .thenApply(HttpResponse::body)
            .thenApply(o::readValue)
            .get();

        return model.toString();

    }

}