package com.baeldung.resttemplate.json.consumer.controller;

import com.baeldung.resttemplate.json.model.Address;
import com.baeldung.resttemplate.json.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
public class UserConsumerControllerUnitTest {
    private MockRestServiceServer mockServer;
    private final RestTemplate restTemplate = new RestTemplate();
    private UserConsumerController tested = new UserConsumerController(restTemplate);

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetUsersAsObjects_thenOK() {
        // Given
        String url = "http://localhost :8080/users";
        Object[] expected = new Object[]{new User(1, "user1",
                Arrays.asList(
                        new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "user1_address1_postCode"),
                        new Address("address2_addressLine1", "address2_addressLine2", "address2_town", "user1_address2_postCode"))),
                new User(2,
                        "user2",
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "user2_address1_postCode")))};

        String userJson = "[{\"id\":1,\"name\":\"user1\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user1_address1_postCode\"}," +
                                  "{\"addressLine1\":\"address2_addressLine1\",\"addressLine2\":\"address2_addressLine2\",\"town\":\"address2_town\",\"postCode\":\"user1_address2_postCode\"}]}," +
                                  "{\"id\":2,\"name\":\"user2\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user2_address1_postCode\"}]}]";

        // When
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:8080/users"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(userJson)
                );
        Object[] actual = tested.getUsersAsObjects();

        // Then
        mockServer.verify();
        assertEquals(actual.length, expected.length);
        assertThat(actual).contains(expected[0], expected[1]);

    }

    @Test
    public void whenGetUsersAsArray_thenOK() {
        // Given
        String url = "http://localhost :8080/users";
        List<String> expected = Arrays.asList("user1_address1_postCode", "user1_address2_postCode", "user2_address1_postCode");

        String userJson = "[{\"id\":1,\"name\":\"user1\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user1_address1_postCode\"}," +
                                  "{\"addressLine1\":\"address2_addressLine1\",\"addressLine2\":\"address2_addressLine2\",\"town\":\"address2_town\",\"postCode\":\"user1_address2_postCode\"}]}," +
                                  "{\"id\":2,\"name\":\"user2\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user2_address1_postCode\"}]}]";

        // When
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:8080/users"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(userJson)
                );
        List<String> actual = tested.getPostCodesFromUserArray();

        // Then
        mockServer.verify();
        assertEquals(actual.size(), expected.size());
        assertThat(actual).containsExactly(expected.get(0), expected.get(1), expected.get(2));
    }

    @Test
    public void whenGetUsersAsPOJO_thenOK() {
        // Given
        String url = "http://localhost :8080/users";
        List<String> expected = Arrays.asList("user1", "user2");

        String userJson = "[{\"id\":1,\"name\":\"user1\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user1_address1_postCode\"}," +
                                  "{\"addressLine1\":\"address2_addressLine1\",\"addressLine2\":\"address2_addressLine2\",\"town\":\"address2_town\",\"postCode\":\"user1_address2_postCode\"}]}," +
                                  "{\"id\":2,\"name\":\"user2\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user2_address1_postCode\"}]}]";

        // When
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:8080/users"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(userJson)
                );
        List<String> actual = tested.getUserNames();

        // Then
        mockServer.verify();
        assertEquals(actual.size(), expected.size());
        assertThat(actual).containsExactly(expected.get(0), expected.get(1));
    }
}