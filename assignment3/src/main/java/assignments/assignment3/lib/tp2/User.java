package assignments.assignment3.lib.tp2;

import assignments.assignment3.payment.DepeFoodPaymentSystem;

import java.util.ArrayList;

public class User {
    private final String nama;
    private final String email;
    private final String nomorTelepon;
    private final ArrayList<Order> orderHistory;
    private final DepeFoodPaymentSystem payment;
    private final String lokasi;
    public String role;
    private long saldo;

    public User(
            String nama,
            String nomorTelepon,
            String email,
            String lokasi,
            String role,
            DepeFoodPaymentSystem payment,
            long saldo) {
        this.nama = nama;
        this.email = email;
        this.lokasi = lokasi;
        this.nomorTelepon = nomorTelepon;
        this.orderHistory = new ArrayList<>();
        this.role = role;
        this.saldo = saldo;
        this.payment = payment;
    }

    public String getNama() {
        return this.nama;
    }

    public String getEmail() {
        return this.email;
    }

    public String getLokasi() {
        return this.lokasi;
    }

    public String getNomorTelepon() {
        return this.nomorTelepon;
    }

    public long getSaldo() {
        return this.saldo;
    }

    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    public DepeFoodPaymentSystem getPayment() {
        return this.payment;
    }

    public ArrayList<Order> getOrderHistory() {
        return this.orderHistory;
    }

    public void addOrderHistory(Order order) {
        this.orderHistory.add(order);
    }

    public boolean isOrderBelongsToUser(String orderId) {
        for (Order order : this.orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }
}
