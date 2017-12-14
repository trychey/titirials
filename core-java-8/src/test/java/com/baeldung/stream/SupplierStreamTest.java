<<<<<<< HEAD
package com.baeldung.stream;

import static org.junit.Assert.fail;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

public class SupplierStreamTest {

    @Test(expected = IllegalStateException.class)
    public void givenStream_whenStreamUsedTwice_thenThrowException() {
        Stream<String> stringStream = Stream.of("A", "B", "C", "D");
        Optional<String> result1 = stringStream.findAny();
        System.out.println(result1.get());
        Optional<String> result2 = stringStream.findFirst();
        System.out.println(result2.get());
    }

    @Test
    public void givenStream_whenUsingSupplier_thenNoExceptionIsThrown() {
        try {
            Supplier<Stream<String>> streamSupplier = () -> Stream.of("A", "B", "C", "D");
            Optional<String> result1 = streamSupplier.get().findAny();
            System.out.println(result1.get());
            Optional<String> result2 = streamSupplier.get().findFirst();
            System.out.println(result2.get());
        } catch (IllegalStateException e) {
            fail();
        }
    }

=======
package com.baeldung.stream;

import static org.junit.Assert.fail;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

public class SupplierStreamTest {

    @Test(expected = IllegalStateException.class)
    public void givenStream_whenStreamUsedTwice_thenThrowException() {
        Stream<String> stringStream = Stream.of("A", "B", "C", "D");
        Optional<String> result1 = stringStream.findAny();
        System.out.println(result1.get());
        Optional<String> result2 = stringStream.findFirst();
        System.out.println(result2.get());
    }

    @Test
    public void givenStream_whenUsingSupplier_thenNoExceptionIsThrown() {
        try {
            Supplier<Stream<String>> streamSupplier = () -> Stream.of("A", "B", "C", "D");
            Optional<String> result1 = streamSupplier.get().findAny();
            System.out.println(result1.get());
            Optional<String> result2 = streamSupplier.get().findFirst();
            System.out.println(result2.get());
        } catch (IllegalStateException e) {
            fail();
        }
    }

>>>>>>> 7a4e60a49ab53bd7e2f3be495e7e03d412054f82
}