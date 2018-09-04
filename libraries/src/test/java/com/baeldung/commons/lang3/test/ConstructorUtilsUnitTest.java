package com.baeldung.commons.lang3.test;

import com.baeldung.commons.lang3.beans.User;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class ConstructorUtilsUnitTest {

    @Test
    public void givenConstructorUtilsClass_whenCalledgetAccessibleConstructor_thenCorrect() {
        Constructor constructor = ConstructorUtils.getAccessibleConstructor(User.class, String.class, String.class);
        assertThat(constructor).isInstanceOf(Constructor.class);
    }
    
    @Test
    public void givenConstructorUtilsClass_whenCalledgetinvokeConstructor_thenCorrect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        assertThat(ConstructorUtils.invokeConstructor(User.class, "name", "email")).isInstanceOf(User.class);
    }
    
    @Test
    public void givenConstructorUtilsClass_whenCalledgetinvokeExactConstructor_thenCorrect() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String[] args = {"name", "email"};
        Class[] parameterTypes= {String.class, String.class};
        assertThat(ConstructorUtils.invokeExactConstructor(User.class, args, parameterTypes)).isInstanceOf(User.class);
    }
}
