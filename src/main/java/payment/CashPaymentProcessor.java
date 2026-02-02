package payment;

public class CashPaymentProcessor implements PaymentProcessor {
    @Override
    public String process(double amount) {
        return amount > 0 ? "RECEIVED" : "REJECTED";
    }
}
