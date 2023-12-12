package com.baeldung.spring.cloud.aws.sqs;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

@Component
public class CountDownLatchContainer {

    public CountDownLatch stringPayloadLatch = new CountDownLatch(1);
    public CountDownLatch recordPayloadLatch = new CountDownLatch(1);
    public CountDownLatch customHeadersPayloadLatch = new CountDownLatch(1);

}
