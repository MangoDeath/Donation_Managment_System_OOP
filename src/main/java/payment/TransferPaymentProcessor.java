package payment;

public class TransferPaymentProcessor implements PaymentProcessor {
    @Override
    public String process(double amount) {
        return amount > 0 ? "PENDING" : "REJECTED";
    }
}
