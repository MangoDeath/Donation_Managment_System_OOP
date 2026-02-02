package payment;

public class CardPaymentProcessor implements PaymentProcessor {
    @Override
    public String process(double amount) {
        return amount > 0 ? "APPROVED" : "DECLINED";
    }
}