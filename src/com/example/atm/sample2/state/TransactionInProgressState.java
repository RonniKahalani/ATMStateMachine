package com.example.atm.sample2.state;

import com.example.atm.sample2.context.ATMContext;
import com.example.atm.sample2.user.UserAction;

// Transaction in Progress State
public class TransactionInProgressState implements ATMState {
    @Override
    public void handleAction(ATMContext context, UserAction action, String... params) {
        switch (action) {
            case WITHDRAW_AMOUNT:
                if (params.length > 0) {
                    try {
                        double amount = Double.parseDouble(params[0]);
                        if (context.getTransactionProcessor().performWithdrawal(context, amount)) {
                            System.out.println("Withdrawal successful: $" + amount);
                            System.out.println("Remaining balance: $" + context.getAvailableBalance());
                        } else {
                            System.out.println("Withdrawal failed: Insufficient funds or ATM cash.");
                        }
                        context.setState(new PinVerifiedState());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount entered.");
                        context.setState(new PinVerifiedState());
                    }
                }
                break;
            case EJECT_CARD:
                context.setCardValid(false);
                context.setPinCorrect(false);
                context.setState(new IdleState());
                System.out.println("Card ejected. ATM is idle.");
                break;
            default:
                System.out.println("Invalid action. Enter amount or eject card.");
        }
    }
}
