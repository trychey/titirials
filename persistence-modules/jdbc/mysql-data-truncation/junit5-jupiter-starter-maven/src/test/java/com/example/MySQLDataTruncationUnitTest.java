package com.example.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDataTruncationUnitTest {

    @Test
    void
    givenTableInsert_whenDataTruncationExceptionThrown_thenAssertionSucceeds() throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/university?" +"user=root");
        Statement stmt = conn.createStatement ();
        Exception exception = assertThrows(SQLException.class, () -> {
            stmt.execute ("INSERT INTO DEPARTMENT(id,name,code) VALUES(6,'Data Science','DATSCI')");
        });

        String expectedMessage = "Data truncation: Data too long for column";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenTableInsert_whenStatementRun_thenEnsureNoExceptionThrown() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/university?" +"user=root");
        Statement stmt = conn.createStatement ();
        assertDoesNotThrow(() -> {
            stmt.execute ("INSERT INTO DEPARTMENT(id,name,code) VALUES(6,'Data Science','DS')");
        });
    }

}
