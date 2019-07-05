package com.baeldung.r2dbc;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class R2dbcApplicationUnitTest {


    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    DatabaseClient client;


    @Before
    public void setup() {

        Hooks.onOperatorDebug();

        List<String> statements = Arrays.asList(//
                "DROP TABLE IF EXISTS player;",
                "CREATE table player (id INT AUTO_INCREMENT NOT NULL, name VARCHAR2, age INT NOT NULL);");

        statements.forEach(it -> client.execute() //
                .sql(it) //
                .fetch() //
                .rowsUpdated() //
                .as(StepVerifier::create) //
                .expectNextCount(1) //
                .verifyComplete());

    }

    @Test
    public void whenDeleteAll_then0IsExpected() {


        playerRepository.deleteAll()
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void whenInsertFive_then6AreExpected() {

        insertPlayers();

        playerRepository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void whenSearchForCR7_then1IsExpected() {

        insertPlayers();

        playerRepository.findAllByName("CR7")
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void whenSearchFor32YearsOld_then2AreExpected() {
        insertPlayers();

        playerRepository.findByAge(32)
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }

    private void insertPlayers() {
        List<Player> players = Arrays.asList(
                new Player(1, "Kaka", 37),
                new Player(2, "Messi", 32),
                new Player(3, "Mbappé", 20),
                new Player(4, "CR7", 34),
                new Player(5, "Lewandowski", 30),
                new Player(6, "Cavani", 32)
        );

        playerRepository.saveAll(players).subscribe();
    }
}

