package factory;

import payment.CardPaymentProcessor;
import payment.CashPaymentProcessor;
import payment.PaymentProcessor;
import payment.TransferPaymentProcessor;

public class PaymentProcessorFactory {

    // beginner-style: simple if/else (no switch expression)
    public PaymentProcessor createProcessor(String method) {

        if (method == null) {
            return new CashPaymentProcessor();
        }

        String m = method.trim().toUpperCase();

        if (m.equals("CARD")) {
            return new CardPaymentProcessor();
        } else if (m.equals("TRANSFER")) {
            return new TransferPaymentProcessor();
        } else {
            return new CashPaymentProcessor();
        }
    }
}
