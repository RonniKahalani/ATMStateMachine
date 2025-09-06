package com.example.atm.good.state;

import com.example.atm.good.context.ATMContext;
import com.example.atm.good.user.UserAction;

/**
 * Interface for ATM states
 */
public interface ATMState {
    void handleAction(ATMContext context, UserAction action, String... params);
}