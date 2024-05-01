package assignments.assignment3.systemCLI;

import static assignments.assignment3.MainMenu.*;

import assignments.assignment3.lib.tp1.OrderGenerator;
import assignments.assignment3.lib.tp2.Menu;
import assignments.assignment3.lib.tp2.Order;
import assignments.assignment3.lib.tp2.Restaurant;
import assignments.assignment3.lib.tp2.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerSystemCLI extends UserSystemCLI {
    // Override method handleMenu di UserSystemCLI
    @Override
    public boolean handleMenu(int choice) {
        switch (choice) {
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    // Override method displayMenu di UserSystemCLI
    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    // Handle buat pesanan sesuai dengan implementasi di TP2
    private void handleBuatPesanan() {
        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if (restaurant == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if (!OrderGenerator.validateDate(tanggalPemesanan)) {
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for (int i = 0; i < jumlahPesanan; i++) {
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if (!validateRequestPesanan(restaurant, listMenuPesananRequest)) {
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            }
            Order order =
                    new Order(
                            OrderGenerator.generateOrderID(
                                    restaurantName,
                                    tanggalPemesanan,
                                    userLoggedIn.getNomorTelepon()),
                            tanggalPemesanan,
                            OrderGenerator.calculateDeliveryCost(userLoggedIn.getLokasi()),
                            restaurant,
                            getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
            userLoggedIn.addOrderHistory(order);
            return;
        }
    }

    // Handle cetak bill sesuai dengan implementasi di TP2
    private void handleCetakBill() {
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if (order == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println();
            System.out.print(outputBillPesanan(order));
            return;
        }
    }

    // Handle lihat menu sesuai dengan implementasi di TP2
    private void handleLihatMenu() {
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if (restaurant == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu());
            return;
        }
    }

    // Method yang digunakan untuk membayar sebuah pesanan
    private void handleBayarBill() {
        System.out.println("-------------- Bayar Bill ---------------");
        while (true) {
            // Mengambil order id dari user
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            // Mencari order berdasarkan order id
            Order order = getOrderOrNull(orderId);
            // Validasi order
            if (order == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            // Cek apakah order sudah lunas
            if (order.getOrderFinished()) {
                System.out.println("Pesanan dengan ID ini sudah lunas");
                break;
            }
            // Menampilkan bill pesanan
            System.out.println();
            System.out.print(outputBillPesanan(order));
            System.out.println();

            // Menampilkan opsi pembayaran
            while (true) {
                System.out.println("Opsi Pembayaran:");
                System.out.println("1. Credit Card");
                System.out.println("2. Debit");
                System.out.print("Pilihan Metode Pembayaran: ");

                // Memilih metode pembayaran dan validasi
                int choosenPaymentMethod = input.nextInt();
                input.nextLine();
                if (choosenPaymentMethod != 1 && choosenPaymentMethod != 2) {
                    System.out.println("Pilihan tidak valid, silakan coba kembali");
                    continue;
                }

                DepeFoodPaymentSystem userPaymentMethod = userLoggedIn.getPayment();
                long totalHarga = userPaymentMethod.processPayment((long) order.getTotalHarga());

                // Validasi jika user memiliki metode pembayaran kredit
                if (userPaymentMethod instanceof CreditCardPayment) {
                    if (choosenPaymentMethod == 2) {
                        System.out.println("User belum memiliki metode pembayaran ini!");
                        continue;
                    }

                    long transactionFee =
                            ((CreditCardPayment) userPaymentMethod)
                                    .countTransactionFee((long) order.getTotalHarga());

                    // Jika proses validasi berhasil, maka order dianggap lunas
                    // dan saldo user akan berkurang
                    order.setOrderFinished(true);
                    userLoggedIn.setSaldo(userLoggedIn.getSaldo() - totalHarga);
                    System.out.printf(
                            "Berhasil Membayar Bill sebesar Rp %d dengan biaya transaksi sebesar Rp %d.",
                            totalHarga - transactionFee, transactionFee);
                }

                // Validasi jika user memiliki metode pembayaran debit
                if (userPaymentMethod instanceof DebitPayment) {
                    if (choosenPaymentMethod == 1) {
                        System.out.println("User belum memiliki metode pembayaran ini!");
                        continue;
                    }
                    if (totalHarga < DebitPayment.MINIMUM_TOTAL_PRICE) {
                        System.out.println(
                                "Jumlah pesanan < 50000, mohon menggunakan metode pembayaran yang lain");
                        return;
                    }
                    if (totalHarga > userLoggedIn.getSaldo()) {
                        System.out.println(
                                "Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
                        return;
                    }
                    // Jika proses validasi berhasil, maka order dianggap lunas
                    // dan saldo user akan berkurang
                    order.setOrderFinished(true);
                    userLoggedIn.setSaldo(userLoggedIn.getSaldo() - totalHarga);
                    System.out.printf("Berhasil Membayar Bill sebesar Rp %d.", totalHarga);
                }
                return;
            }
        }
    }

    // Handle cek saldo untuk user yang login
    private void handleCekSaldo() {
        System.out.println("-------------- Cek Saldo ----------------");
        System.out.printf("Sisa saldo sebesar Rp %s%n", userLoggedIn.getSaldo());
    }

    /* Helper Methods */

    private Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest) {
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for (int i = 0; i < menu.length; i++) {
            for (Menu existMenu : restaurant.getMenu()) {
                if (existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))) {
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    private boolean validateRequestPesanan(
            Restaurant restaurant, List<String> listMenuPesananRequest) {
        return listMenuPesananRequest.stream()
                .allMatch(
                        pesanan ->
                                restaurant.getMenu().stream()
                                        .anyMatch(menu -> menu.getNamaMakanan().equals(pesanan)));
    }

    private String getMenuPesananOutput(Order order) {
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder
                    .append("- ")
                    .append(menu.getNamaMakanan())
                    .append(" ")
                    .append(decimalFormat.format(menu.getHarga()))
                    .append("\n");
        }
        if (!pesananBuilder.isEmpty()) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    public String outputBillPesanan(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format(
                "Bill:%n"
                        + "Order ID: %s%n"
                        + "Tanggal Pemesanan: %s%n"
                        + "Lokasi Pengiriman: %s%n"
                        + "Status Pengiriman: %s%n"
                        + "Pesanan:%n%s%n"
                        + "Biaya Ongkos Kirim: Rp %s%n"
                        + "Total Biaya: Rp %s%n",
                order.getOrderId(),
                order.getTanggal(),
                userLoggedIn.getLokasi(),
                !order.getOrderFinished() ? "Not Finished" : "Finished",
                getMenuPesananOutput(order),
                decimalFormat.format(order.getOngkir()),
                decimalFormat.format(order.getTotalHarga()));
    }

    private Restaurant getRestaurantByName(String name) {
        Optional<Restaurant> restaurantMatched =
                restoList.stream()
                        .filter(restoran -> restoran.getNama().equalsIgnoreCase(name))
                        .findFirst();
        return restaurantMatched.orElse(null);
    }

    private Order getOrderOrNull(String orderId) {
        for (User user : userList) {
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
    }
}
