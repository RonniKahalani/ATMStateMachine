package com.example.atm.good.state;

import com.example.atm.good.context.ATMContext;
import com.example.atm.good.user.UserAction;

/**
 * Out of Service State
 */
public class OutOfServiceState implements ATMState {

    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        System.out.println("ATM is out of service. No actions allowed.");
    }
}