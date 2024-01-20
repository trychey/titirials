package com.baeldung.setenvironment;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

class SettingSameProcessEnvironmentVariableUnitTest {

    private static final String PROCESS_ENVIRONMENT = "java.lang.ProcessEnvironment";
    private static final String ENVIRONMENT = "theUnmodifiableEnvironment";
    private static final String SOURCE_MAP = "m";
    private static final Object STATIC_METHOD = null;
    private static final Class<?> UMODIFIABLE_MAP_CLASS
      = Collections.unmodifiableMap(Collections.emptyMap()).getClass();
    private static final Class<?> MAP_CLASS = Map.class;

    @ParameterizedTest
    @CsvSource(value = {"test, Hello World"})
    void givenReflexiveAccess_whenGetSourceMap_thenSuccessfullyModifyVariables(String environmentVariable, String value)
      throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Map<String, String> modifiableEnvironment = getModifiableEnvironment();
        assertThat(modifiableEnvironment).isNotNull();

        modifiableEnvironment.put(environmentVariable, value);
        String actual = modifiableEnvironment.get(environmentVariable);
        assertThat(actual).isEqualTo(value);
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "PATH", matches = ".*",
      disabledReason = "The test relies on the presence of PATH variable")
    void givenOS_whenGetPath_thenVariableIsPresent() {
        String classPath = System.getenv("PATH");
        assertThat(classPath).isNotNull();
    }

    @Test
    void givenOS_whenGetPath_thenVariablesArePresent() {
        Map<String, String> environment = System.getenv();
        assertThat(environment).isNotNull();
    }
    @Test
    @SetEnvironmentVariable(key = "test", value = "Hello World")
    void givenVariableSet_whenGetEnvironmentVariable_thenReturnsCorrectValue() {
        String expected = "Hello World";
        String actual = System.getenv("test");
        assertThat(actual).isEqualTo(expected);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> getModifiableEnvironment()
      throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> environmentClass = Class.forName(PROCESS_ENVIRONMENT);
        Field environmentField = environmentClass.getDeclaredField(ENVIRONMENT);
        assertThat(environmentField).isNotNull();
        environmentField.setAccessible(true);

        Object unmodifiableEnvironmentMap = environmentField.get(STATIC_METHOD);
        assertThat(unmodifiableEnvironmentMap).isNotNull();
        assertThat(unmodifiableEnvironmentMap).isInstanceOf(UMODIFIABLE_MAP_CLASS);

        Field underlyingMapField = unmodifiableEnvironmentMap.getClass().getDeclaredField(SOURCE_MAP);
        underlyingMapField.setAccessible(true);
        Object underlyingMap = underlyingMapField.get(unmodifiableEnvironmentMap);
        assertThat(underlyingMap).isNotNull();
        assertThat(underlyingMap).isInstanceOf(MAP_CLASS);

        return (Map<String, String>) underlyingMap;
    }
}
