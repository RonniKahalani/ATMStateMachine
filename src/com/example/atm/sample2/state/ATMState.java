package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

// Interface for ATM states
public interface ATMState {
    void handleAction(ATMContext context, UserAction action, String... params);
}