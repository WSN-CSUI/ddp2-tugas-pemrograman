// package assignments.assignment3.tp2;
//
// import java.util.ArrayList;
// import java.util.Scanner;
//
// public class MainMenu {
//    public static final Scanner input = new Scanner(System.in);
//    public static ArrayList<Restaurant> restoList;
//    public static ArrayList<User> userList;
//    public static User userLoggedIn;
//
//    public static void main(String[] args) {
//        restoList = new ArrayList<>();
//        boolean programRunning = true;
//        while (programRunning) {
//            int command = input.nextInt();
//            input.nextLine();
//
//            if (command == 1) {
//                System.out.println("\nSilakan Login:");
//                System.out.print("Nama: ");
//                String nama = input.nextLine();
//                System.out.print("Nomor Telepon: ");
//                String noTelp = input.nextLine();
//                userLoggedIn = getUser(nama, noTelp);
//                boolean isLoggedIn = true;
//                if (userLoggedIn == null) {
//                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
//                    isLoggedIn = false;
//                } else if (userLoggedIn.role == "Customer") {
//                    System.out.println("Selamat Datang " + userLoggedIn.getNama() + "!");
//                    while (isLoggedIn) {
//                        int commandCust = input.nextInt();
//                        input.nextLine();
//
//                        switch (commandCust) {
//                            case 1 -> handleBuatPesanan();
//                            case 2 -> handleCetakBill();
//                            case 3 -> handleLihatMenu();
//                            case 4 -> handleUpdateStatusPesanan();
//                            case 5 -> isLoggedIn = false;
//                            default ->
//                                    System.out.println(
//                                            "Perintah tidak diketahui, silakan coba kembali");
//                        }
//                    }
//                } else {
//                    System.out.println("Selamat Datang " + userLoggedIn.getNama() + "!");
//                    while (isLoggedIn) {
//                        int commandAdmin = input.nextInt();
//                        input.nextLine();
//
//                        switch (commandAdmin) {
//                            case 1 -> handleTambahRestoran();
//                            case 2 -> handleHapusRestoran();
//                            case 3 -> isLoggedIn = false;
//                            default ->
//                                    System.out.println(
//                                            "Perintah tidak diketahui, silakan coba kembali");
//                        }
//                    }
//                }
//            } else if (command == 2) {
//                programRunning = false;
//            } else {
//                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
//            }
//        }
//        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
//    }
//
//    public static User getUser(String nama, String nomorTelepon) {
//        for (User user : userList) {
//            if (user.getNama().equals(nama.trim())
//                    && user.getNomorTelepon().equals(nomorTelepon.trim())) {
//                return user;
//            }
//        }
//        return null;
//    }
//
    //    public static void handleBuatPesanan() {
    //        System.out.println("--------------Buat Pesanan----------------");
    //        while (true) {
    //            System.out.print("Nama Restoran: ");
    //            String restaurantName = input.nextLine().trim();
    //            Restaurant restaurant = getRestaurantByName(restaurantName);
    //            if (restaurant == null) {
    //                System.out.println("Restoran tidak terdaftar pada sistem.\n");
    //                continue;
    //            }
    //            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
    //            String tanggalPemesanan = input.nextLine().trim();
    //            if (!OrderGenerator.validateDate(tanggalPemesanan)) {
    //                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
    //                System.out.println();
    //                continue;
    //            }
    //            System.out.print("Jumlah Pesanan: ");
    //            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
    //            System.out.println("Order: ");
    //            List<String> listMenuPesananRequest = new ArrayList<>();
    //            for (int i = 0; i < jumlahPesanan; i++) {
    //                listMenuPesananRequest.add(input.nextLine().trim());
    //            }
    //            if (!validateRequestPesanan(restaurant, listMenuPesananRequest)) {
    //                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
    //                continue;
    //            }
    //            Order order =
    //                    new Order(
    //                            OrderGenerator.generateOrderID(
    //                                    restaurantName,
    //                                    tanggalPemesanan,
    //                                    userLoggedIn.getNomorTelepon()),
    //                            tanggalPemesanan,
    //                            OrderGenerator.calculateDeliveryCost(userLoggedIn.getLokasi()),
    //                            restaurant,
    //                            getMenuRequest(restaurant, listMenuPesananRequest));
    //            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
    //            userLoggedIn.addOrderHistory(order);
    //            return;
    //        }
    //    }
    //
    //    public static boolean validateRequestPesanan(
    //            Restaurant restaurant, List<String> listMenuPesananRequest) {
    //        return listMenuPesananRequest.stream()
    //                .allMatch(
    //                        pesanan ->
    //                                restaurant.getMenu().stream()
    //                                        .anyMatch(menu ->
    // menu.getNamaMakanan().equals(pesanan)));
    //    }
    //
    //    public static Menu[] getMenuRequest(
    //            Restaurant restaurant, List<String> listMenuPesananRequest) {
    //        Menu[] menu = new Menu[listMenuPesananRequest.size()];
    //        for (int i = 0; i < menu.length; i++) {
    //            for (Menu existMenu : restaurant.getMenu()) {
    //                if (existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))) {
    //                    menu[i] = existMenu;
    //                }
    //            }
    //        }
    //        return menu;
    //    }
    //
    //    public static String getMenuPesananOutput(Order order) {
    //        StringBuilder pesananBuilder = new StringBuilder();
    //        DecimalFormat decimalFormat = new DecimalFormat();
    //        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    //        symbols.setGroupingSeparator('\u0000');
    //        decimalFormat.setDecimalFormatSymbols(symbols);
    //        for (Menu menu : order.getSortedMenu()) {
    //            pesananBuilder
    //                    .append("- ")
    //                    .append(menu.getNamaMakanan())
    //                    .append(" ")
    //                    .append(decimalFormat.format(menu.getHarga()))
    //                    .append("\n");
    //        }
    //        if (pesananBuilder.length() > 0) {
    //            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
    //        }
    //        return pesananBuilder.toString();
    //    }
    //
    //    public static String outputBillPesanan(Order order) {
    //        DecimalFormat decimalFormat = new DecimalFormat();
    //        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    //        symbols.setGroupingSeparator('.');
    //        decimalFormat.setDecimalFormatSymbols(symbols);
    //        return String.format(
    //                "Bill:%n"
    //                        + "Order ID: %s%n"
    //                        + "Tanggal Pemesanan: %s%n"
    //                        + "Lokasi Pengiriman: %s%n"
    //                        + "Status Pengiriman: %s%n"
    //                        + "Pesanan:%n%s%n"
    //                        + "Biaya Ongkos Kirim: Rp %s%n"
    //                        + "Total Biaya: Rp %s%n",
    //                order.getOrderId(),
    //                order.getTanggal(),
    //                userLoggedIn.getLokasi(),
    //                !order.getOrderFinished() ? "Not Finished" : "Finished",
    //                getMenuPesananOutput(order),
    //                decimalFormat.format(order.getOngkir()),
    //                decimalFormat.format(order.getTotalHarga()));
    //    }
    //
    //    public static Restaurant getRestaurantByName(String name) {
    //        Optional<Restaurant> restaurantMatched =
    //                restoList.stream()
    //                        .filter(restoran -> restoran.getNama().equalsIgnoreCase(name))
    //                        .findFirst();
    //        if (restaurantMatched.isPresent()) {
    //            return restaurantMatched.get();
    //        }
    //        return null;
    //    }
    //
    //    public static void handleCetakBill() {
    //        System.out.println("--------------Cetak Bill----------------");
    //        while (true) {
    //            System.out.print("Masukkan Order ID: ");
    //            String orderId = input.nextLine().trim();
    //            Order order = getOrderOrNull(orderId);
    //            if (order == null) {
    //                System.out.println("Order ID tidak dapat ditemukan.\n");
    //                continue;
    //            }
    //            System.out.println();
    //            System.out.print(outputBillPesanan(order));
    //            return;
    //        }
    //    }
    //
    //    public static Order getOrderOrNull(String orderId) {
    //        for (User user : userList) {
    //            for (Order order : user.getOrderHistory()) {
    //                if (order.getOrderId().equals(orderId)) {
    //                    return order;
    //                }
    //            }
    //        }
    //        return null;
    //    }
    //
    //    public static void handleLihatMenu() {
    //        System.out.println("--------------Lihat Menu----------------");
    //        while (true) {
    //            System.out.print("Nama Restoran: ");
    //            String restaurantName = input.nextLine().trim();
    //            Restaurant restaurant = getRestaurantByName(restaurantName);
    //            if (restaurant == null) {
    //                System.out.println("Restoran tidak terdaftar pada sistem.\n");
    //                continue;
    //            }
    //            System.out.print(restaurant.printMenu());
    //            return;
    //        }
    //    }
//
//    public static void handleUpdateStatusPesanan() {
//        System.out.println("--------------Update Status Pesanan---------------");
//        while (true) {
//            System.out.print("Order ID: ");
//            String orderId = input.nextLine().trim();
//            Order order = getOrderOrNull(orderId);
//            if (order == null) {
//                System.out.println("Order ID tidak dapat ditemukan.\n");
//                continue;
//            }
//            System.out.print("Status: ");
//            String newStatus = input.nextLine().trim();
//            if (newStatus.equalsIgnoreCase("SELESAI")) {
//                if (order.getOrderFinished()) {
//                    System.out.printf(
//                            "Status pesanan dengan ID %s tidak berhasil diupdate!",
//                            order.getOrderId());
//                } else {
//                    System.out.printf(
//                            "Status pesanan dengan ID %s berhasil diupdate!", order.getOrderId());
//                    order.setOrderFinished(true);
//                }
//            }
//            return;
//        }
//    }
//
    //    public static void handleTambahRestoran() {
    //        System.out.println("--------------Tambah Restoran---------------");
    //        Restaurant restaurant = null;
    //        while (restaurant == null) {
    //            String namaRestaurant = getValidRestaurantName();
    //            restaurant = new Restaurant(namaRestaurant);
    //            restaurant = handleTambahMenuRestaurant(restaurant);
    //        }
    //        restoList.add(restaurant);
    //        System.out.print("Restaurant " + restaurant.getNama() + " Berhasil terdaftar.");
    //    }
    //
    //    public static Restaurant handleTambahMenuRestaurant(Restaurant restoran) {
    //        System.out.print("Jumlah Makanan: ");
    //        int jumlahMenu = Integer.parseInt(input.nextLine().trim());
    //        boolean isMenuValid = true;
    //        for (int i = 0; i < jumlahMenu; i++) {
    //            String inputValue = input.nextLine().trim();
    //            String[] splitter = inputValue.split(" ");
    //            String hargaStr = splitter[splitter.length - 1];
    //            boolean isDigit = checkIsDigit(hargaStr);
    //            String namaMenu =
    //                    String.join(" ", Arrays.copyOfRange(splitter, 0, splitter.length - 1));
    //            if (isDigit) {
    //                int hargaMenu = Integer.parseInt(hargaStr);
    //                restoran.addMenu(new Menu(namaMenu, hargaMenu));
    //            } else {
    //                isMenuValid = false;
    //            }
    //        }
    //        if (!isMenuValid) {
    //            System.out.println("Harga menu harus bilangan bulat!");
    //            System.out.println();
    //        }
    //
    //        return isMenuValid ? restoran : null;
    //    }
    //
    //    public static boolean checkIsDigit(String digits) {
    //        return digits.chars().allMatch(Character::isDigit);
    //    }
    //
    //    public static String getValidRestaurantName() {
    //        String name = "";
    //        boolean isRestaurantNameValid = false;
    //
    //        while (!isRestaurantNameValid) {
    //            System.out.print("Nama: ");
    //            String inputName = input.nextLine().trim();
    //            boolean isRestaurantExist =
    //                    restoList.stream()
    //                            .anyMatch(restoran ->
    // restoran.getNama().equalsIgnoreCase(inputName));
    //            boolean isRestaurantNameLengthValid = inputName.length() >= 4;
    //
    //            if (isRestaurantExist) {
    //                System.out.printf(
    //                        "Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama
    // yang berbeda!%n",
    //                        inputName);
    //                System.out.println();
    //            } else if (!isRestaurantNameLengthValid) {
    //                System.out.println("Nama Restoran tidak valid! Minimal 4 karakter
    // diperlukan.");
    //                System.out.println();
    //            } else {
    //                name = inputName;
    //                isRestaurantNameValid = true;
    //            }
    //        }
    //        return name;
    //    }
    //
    //    public static void handleHapusRestoran() {
    //        System.out.println("--------------Hapus Restoran----------------");
    //        boolean isActionDeleteEnded = false;
    //        while (!isActionDeleteEnded) {
    //            System.out.print("Nama Restoran: ");
    //            String restaurantName = input.nextLine().trim();
    //            boolean isRestaurantExist =
    //                    restoList.stream()
    //                            .anyMatch(
    //                                    restaurant ->
    //
    // restaurant.getNama().equalsIgnoreCase(restaurantName));
    //            if (!isRestaurantExist) {
    //                System.out.println("Restoran tidak terdaftar pada sistem.");
    //                System.out.println();
    //            } else {
    //                restoList =
    //                        new ArrayList<>(
    //                                restoList.stream()
    //                                        .filter(
    //                                                restaurant ->
    //                                                        !restaurant
    //                                                                .getNama()
    //
    // .equalsIgnoreCase(restaurantName))
    //                                        .toList());
    //                System.out.println("Restoran berhasil dihapus");
    //                isActionDeleteEnded = true;
    //            }
    //        }
    //    }
// }
