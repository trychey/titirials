package com.baeldung.hexagonal.banking.domain;

import java.math.BigDecimal;

public class StubAccountFactory {
    
    private static Long accountNumber;
    private static BigDecimal balance;
    
    public static Account stubDefaultAccount() {
        return new Account(new AccountHolder(1234, "Mary", "Test"), 1234, BigDecimal.ONE);
    }
    
    public StubAccountFactory withAccountNumber(Long accountNumber) {
        StubAccountFactory.accountNumber = accountNumber;
        return new StubAccountFactory();
    }
    
    public StubAccountFactory withBalance(BigDecimal balance) {
        StubAccountFactory.balance = balance;
        return new StubAccountFactory();
    }
    
    public Account build() {
        return new Account(accountNumber, new AccountHolder(1234, "Mary", "Test"), 1234, balance);
    }

}
