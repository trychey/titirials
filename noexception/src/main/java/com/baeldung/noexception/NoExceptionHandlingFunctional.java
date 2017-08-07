package com.baeldung.noexception;

import com.machinezoo.noexception.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoExceptionHandlingFunctional {

    private static Logger logger = LoggerFactory.getLogger(NoExceptionHandlingFunctional.class);

    public static void main(String[] args) {
        Exceptions.log(logger)
            .run(() -> System.out.println("Result is " + Integer.parseInt("foobar")));
    }
}
