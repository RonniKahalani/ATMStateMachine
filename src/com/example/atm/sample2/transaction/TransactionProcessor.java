package com.example.atm.sample2.transaction;

import com.example.atm.sample2.context.ATMContext;

/**
 * Interface for transaction processing
 */
public interface TransactionProcessor {
    boolean performWithdrawal(ATMContext context, double amount);
}