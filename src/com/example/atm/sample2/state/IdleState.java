package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

// Idle State
public class IdleState implements ATMState {
    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {
            case INSERT_CARD:
                if (params.length > 0 && context.getCardValidator().validateCard(params[0])) {
                    context.setCardValid(true);
                    context.setState(new CardInsertedState());
                    System.out.println("Card inserted successfully. Please enter PIN.");
                } else {
                    System.out.println("Invalid card. Please try again.");
                }
                break;
            case SHUTDOWN:
                context.setState(new OutOfServiceState());
                System.out.println("ATM is now out of service.");
                break;
            default:
                System.out.println("Invalid action. Please insert card.");
        }
    }
}
