package assignments.assignment3.payment;

public class DebitPayment implements DepeFoodPaymentSystem {
    public static final double MINIMUM_TOTAL_PRICE = 50000; // Minimum total price: Rp 50.000

    @Override
    public long processPayment(long amount) {
        return amount;
    }
}
