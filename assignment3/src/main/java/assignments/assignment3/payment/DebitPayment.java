package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem {
    public static final double MINIMUM_TOTAL_PRICE = 500000; // Minimum total price: Rp 500.000

    @Override
    public long processPayment(long amount) {
        return amount;
    }
}
