package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

// Out of Service State
public class OutOfServiceState implements ATMState {
    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        System.out.println("ATM is out of service. No actions allowed.");
    }
}