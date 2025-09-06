package com.example.atm.good.transaction;

import com.example.atm.good.context.ATMContext;
import com.example.atm.good.state.OutOfServiceState;

/**
 * Transaction Processor Implementation
 */
public class TransactionProcessorImpl implements TransactionProcessor {

    @Override
    public boolean performWithdrawal(ATMContext context, double amount) {
        if (amount <= 0 || amount > context.getAvailableBalance() || amount > context.getAtmCash()) {
            return false;
        }
        context.setAvailableBalance(context.getAvailableBalance() - amount);
        context.setAtmCash(context.getAtmCash() - amount);
        if (context.getAtmCash() <= 0) {
            context.setState(new OutOfServiceState());
            System.out.println("ATM is out of cash. Moving to out-of-service state.");
        }
        return true;
    }
}