package com.baeldung.jcommander.usagebilling.service;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.fill;
import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

import com.baeldung.jcommander.usagebilling.model.CurrentChargesRequest;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesResponse;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesResponse.LineItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class DefaultFetchCurrentChargesService implements FetchCurrentChargesService {

  DefaultFetchCurrentChargesService() {
  }

  @Override
  public CurrentChargesResponse fetch(CurrentChargesRequest request) {
    List<String> subscriptions = request.getSubscriptionIds();

    if (subscriptions == null || subscriptions.isEmpty()) {
      System.out.println("Fetching ALL charges for customer: " + request.getCustomerId());
      subscriptions = mockSubscriptions();

    } else {
      System.out.println(format("Fetching charges for customer: %s and subscriptions: %s",
          request.getCustomerId(), subscriptions));
    }

    CurrentChargesResponse charges = mockCharges(request.getCustomerId(), subscriptions);
    System.out.println("Fetched charges...");
    return charges;
  }

  private List<LineItem> lineItems(List<String> subscriptions) {
    return subscriptions.stream()
        .map(subscription -> LineItem.builder()
            .subscriptionId(subscription)
            .quantity(current().nextInt(20))
            .amount(new BigDecimal(current().nextDouble(1_000)))
            .build())
        .collect(toList());
  }

  private CurrentChargesResponse mockCharges(String customerId, List<String> subscriptions) {
    List<LineItem> lineItems = lineItems(subscriptions);
    BigDecimal amountDue = lineItems.stream()
        .map(li -> li.getAmount())
        .reduce(new BigDecimal("0"), BigDecimal::add);

    return CurrentChargesResponse.builder()
        .customerId(customerId)
        .lineItems(lineItems)
        .amountDue(amountDue)
        .build();
  }

  private List<String> mockSubscriptions() {
    String[] subscriptions = new String[5];
    fill(subscriptions, UUID.randomUUID().toString());
    return asList(subscriptions);
  }
}
