package com.example.atm.good.transaction;

import com.example.atm.good.context.ATMContext;

/**
 * Interface for transaction processing
 */
public interface TransactionProcessor {
    boolean performWithdrawal(ATMContext context, double amount);
}