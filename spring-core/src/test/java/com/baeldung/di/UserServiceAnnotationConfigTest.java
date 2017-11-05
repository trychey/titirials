package com.baeldung.di;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@TestConfiguration
public class UserServiceAnnotationConfigTest {
    @Autowired
    private UserService userService;

    @Test
    public void givenApplicationConfig_whenUserServiceIsInstantiated_thenDependenciesShouldNotBeNull() {
        assertNotNull(userService);
        assertNotNull(userService.getUserRepository());
    }

    @Configuration
    @ComponentScan(basePackages = {"com.baeldung.di"})
    static class ApplicationConfig {}
}