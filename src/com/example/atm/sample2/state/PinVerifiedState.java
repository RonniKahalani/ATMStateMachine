package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

// PIN Verified State
public class PinVerifiedState implements ATMState {
    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {
            case SELECT_WITHDRAWAL:
                context.setState(new TransactionInProgressState());
                System.out.println("Withdrawal selected. Enter amount.");
                break;
            case EJECT_CARD:
                context.setCardValid(false);
                context.setPinCorrect(false);
                context.setState(new IdleState());
                System.out.println("Card ejected. ATM is idle.");
                break;
            default:
                System.out.println("Invalid action. Select withdrawal or eject card.");
        }
    }
}