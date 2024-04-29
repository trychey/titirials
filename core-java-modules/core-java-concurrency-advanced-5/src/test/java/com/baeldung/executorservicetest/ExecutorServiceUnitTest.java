package com.baeldung.executorservicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class ExecutorServiceUnitTest {

    @Test
    void whenNoBlocking_thenTestFails() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        executorService.submit(r);
        assertEquals(null, r.getResult());
    }

    @Test
    void whenUsingFuture_thenTestSucceeds() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        Future<?> future = executorService.submit(r);
        future.get();
        assertEquals(2305843005992468481L, r.getResult());
    }

    @Test
    void whenShuttingDownExecutorAndWait_thenTestSucceeds() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyRunnable r = new MyRunnable();
        executorService.submit(r);
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.SECONDS);
        assertEquals(2305843005992468481L, r.getResult());
    }

    @Test
    void whenTwoTasksAndShuttingDownExecutorAndWait_thenTestSucceeds() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        MyRunnable r1 = new MyRunnable();
        MyRunnable r2 = new MyRunnable();
        executorService.submit(r1);
        executorService.submit(r2);
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.SECONDS);
        assertEquals(2305843005992468481L, r1.getResult());
        assertEquals(2305843005992468481L, r2.getResult());
    }
   
    @Test
    void whenDirectlyUsingThreadPoolExecutor_thenTestSucceeds() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        MyRunnable r = new MyRunnable();
        threadPoolExecutor.submit(r);
        while (threadPoolExecutor.getCompletedTaskCount() < 1) {
        }
        assertEquals(2305843005992468481L, r.getResult());
    }
}
