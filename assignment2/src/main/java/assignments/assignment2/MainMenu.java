package assignments.assignment2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<Restaurant> restoList = new ArrayList<>();
    private static final ArrayList<User> userList = new ArrayList<>();

    public static void main(String[] args) {
        initUser();
        printHeader();

        while (true) {
            startMenu();

            int command = input.nextInt();
            input.nextLine();

            if (command == 1) {
                System.out.println("\nSilahkan Login");

                // Mengambil input nama dari user
                System.out.print("Nama: ");
                String nama = input.nextLine();

                // Mengambil input nomer telepon dari user
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // Mengambil user yang akan login dari userList
                User userLoggedIn = getUser(nama, noTelp);
                if (userLoggedIn == null) {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!\n");
                    continue;
                } else {
                    System.out.printf("Selamat Datang %s!\n", userLoggedIn.getNama());
                }

                // Menentukan action apa yang dapat dilakukan berdasarkan role user
                switch (userLoggedIn.getRole().toLowerCase()) {
                    case "admin":
                        runAdminProgram(userLoggedIn);
                        break;
                    case "customer":
                        runCustomerProgram(userLoggedIn);
                        break;
                }

            } else if (command == 2) {
                break;

            } else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }

        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    // Method untuk menjalankan operasi-operasi yang dapat dilakukan oleh customer
    public static void runAdminProgram(User currUser) {
        while (true) {
            menuAdmin();
            int command = input.nextInt();
            input.nextLine();

            if (command == 1) {
                handleTambahRestoran(currUser);
            } else if (command == 2) {
                handleHapusRestoran(currUser);
            } else if (command == 3) {
                break;
            } else {
                System.out.println("Perintah tidak diketahui, silakan coba kembali");
            }
        }
    }

    // Method untuk menjalankan operasi-operasi yang dapat dilakukan oleh admin
    public static void runCustomerProgram(User currUser) {
        while (true) {
            menuCustomer();
            int command = input.nextInt();
            input.nextLine();

            if (command == 1) {
                handleBuatPesanan(currUser);
            } else if (command == 2) {
                handleCetakBill(currUser);
            } else if (command == 3) {
                handleLihatMenu(currUser);
            } else if (command == 4) {
                handleUpdateStatusPesanan(currUser);
            } else if (command == 5) {
                return;
            } else {
                System.out.println("Perintah tidak diketahui, silakan coba kembali");
            }
        }
    }

    // Method ini digunakan untuk mengambil user dari userList
    // berdasarkan input nama dan nomorTelepon
    public static User getUser(String nama, String nomorTelepon) {
        for (User user : userList) {
            if (user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)) {
                return user;
            }
        }
        return null;
    }

    // Method ini digunakan untuk mengambil restoran dari restoList
    // berdasarkan input nama restoran
    public static Restaurant getRestaurant(String nama) {
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(nama)) {
                return resto;
            }
        }
        return null;
    }

    // Method ini digunakan untuk mengambil order dari order history user
    // berdasarkan input order id
    public static Order getOrder(User user, String orderID) {
        for (Order order : user.getOrderHistory()) {
            if (order.getOrderID().equalsIgnoreCase(orderID)) {
                return order;
            }
        }
        return null;
    }

    // Method ini digunakan untuk mengambil menu dari list menu dari order
    // berdasarkan input nama menu
    public static Menu getMenu(Order order, String nama) {
        for (Menu menu : order.getItems()) {
            if (menu.getNamaMakanan().equals(nama)) {
                return menu;
            }
        }
        return null;
    }

    // Method ini digunakan untuk mengambil menu dari list menu dari restoran
    // berdasarkan input nama menu
    public static Menu getMenu(Restaurant restaurant, String nama) {
        for (Menu menu : restaurant.getMenu()) {
            if (menu.getNamaMakanan().equals(nama)) {
                return menu;
            }
        }
        return null;
    }

    // Method untuk handle buat pesanan customer
    public static void handleBuatPesanan(User user) {
        System.out.print("\n--------- Buat Pesanan ---------\n");

        loopBuatPesanan: while (true) {
            // Mengambil input nama restoran dari user
            System.out.print("Nama Restoran: ");
            String restoName = input.nextLine();

            // Mengambil restoran dari restoList sesuai input nama restoran
            Restaurant currResto = getRestaurant(restoName);
            if (currResto == null) {
                System.out.println("Restoran tidak terdafter pada sistem.\n");
                continue;
            }

            // Mengambil input tanggal pemesanan dari user
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String orderDate = input.nextLine();

            try {
                LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException err) {
                System.out.println("Masukkan tanggal yang sesuai format (DD/MM/YYYY)!\n");
                continue;
            }

            // Mengambil input jumlah pesanan dari user
            System.out.print("Jumlah pesanan: ");
            int orderAmount = input.nextInt();
            input.nextLine();

            // Mengambil input nama-nama menu dari user
            System.out.println("Order: ");
            ArrayList<String> menuNames = new ArrayList<>();
            for (int i = 0; i < orderAmount; i++) {
                String menuName = input.nextLine();
                menuNames.add(menuName);
            }

            // Konversi nama-nama menu menjadi Menu List
            ArrayList<Menu> menuItems = new ArrayList<>();
            for (String menuName : menuNames) {
                Menu currMenu = getMenu(currResto, menuName);
                if (currMenu == null) {
                    System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                    continue loopBuatPesanan;
                }
                menuItems.add(currMenu);
            }

            // Generate order ID dan membuat order baru
            String orderID =
                    OrderGenerator.generateOrderID(restoName, orderDate, user.getNomorTelepon());
            int deliveryCost = OrderGenerator.determineDeliveryCost(user.getLokasi());
            Order newOrder = new Order(orderID, orderDate, deliveryCost, currResto, menuItems);

            // Append order ke order history user yang login
            user.addOrderHistory(newOrder);
            System.out.printf("Pesanan dengan ID %s diterima!\n", orderID);
            break;
        }
    }

    // Method ini digunakan untuk print out bill user
    public static void handleCetakBill(User user) {
        System.out.print("\n--------- Cetak Bill ---------\n");

        loopCetakBill: while (true) {
            // Mengambil input order ID dari user
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();

            // Mengambil order berdasarkan input order ID
            Order currOrder = getOrder(user, orderID);
            if (currOrder == null) {
                System.out.println("Order ID tidak dapat ditemukan.");
                continue;
            }

            // Print out bill user
            System.out.println(OrderGenerator.generateBill(user, currOrder));
            break;
        }
    }

    // Sorting menggunakkan Bubble Sort
    public static void sortMenus(ArrayList<Menu> menus) {
        for (int i = 0; i < menus.size() - 1; i++) {
            for (int j = 0; j < menus.size() - i - 1; j++) {
                // Ambil menu ke-n dan menu ke-(n+1)
                Menu menu1 = menus.get(j);
                Menu menu2 = menus.get(j + 1);

                // Tukar menu jika harga1 > harga2
                // Apabila harga1 == harga2, sort alphabetically
                int priceComparison = Double.compare(menu1.getHarga(), menu2.getHarga());
                if (priceComparison > 0 || (priceComparison == 0
                        && menu1.getNamaMakanan().compareTo(menu2.getNamaMakanan()) > 0)) {
                    menus.set(j, menu2);
                    menus.set(j + 1, menu1);
                }
            }
        }
    }

    // Method ini digunakan untuk print out menu-menu dari restoran oleh customer
    public static void handleLihatMenu(User user) {
        System.out.print("\n--------- Lihat Menu ---------\n");

        loopLihatMenu: while (true) {
            // Mengambil input nama restoran dari user
            System.out.print("Nama Restoran: ");
            String restoName = input.nextLine();

            // Mengambil restoran dari restoList berdasarkan input nama restoran
            Restaurant currResto = getRestaurant(restoName);
            if (currResto == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.");
                continue;
            }

            // Sort menu-menu dari restoran
            sortMenus(currResto.getMenu());

            // Print out menu-menu restoran
            System.out.println("Menu:");
            for (int i = 0; i < currResto.getMenu().size(); i++) {
                Menu currMenu = currResto.getMenu().get(i);
                System.out.printf("%d. %s\n", i + 1, currMenu);
            }

            break;
        }
    }

    // Method ini digunakan untuk merubah status pesanan oleh customer
    public static void handleUpdateStatusPesanan(User user) {
        System.out.print("\n--------- Update Status Pesanan ---------\n");

        loopUpdateStatusPesanan: while (true) {
            // Mengambil input order ID dari user
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            // Mengambil order dari user berdasarkan order ID
            Order currOrder = getOrder(user, orderID);
            if (currOrder == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            // Mengambil input status dari user
            System.out.print("Status: ");
            String status = input.nextLine();
            boolean isStatusFinished = status.equalsIgnoreCase("selesai") || status.equalsIgnoreCase("finished");

            // Mengubah status pemesanan, jika status tidak sama dengan status pemesanan sekarang
            if (currOrder.isOrderFinished() == isStatusFinished) {
                System.out.printf("Status pesanan dengan ID %s tidak berhasil diupdate!\n",
                        currOrder.getOrderID());
            } else {
                currOrder.setOrderFinished(isStatusFinished);
                System.out.printf("Status pesanan dengan ID %s berhasil diupdate!\n",
                        currOrder.getOrderID());
            }

            break;
        }
    }

    // Method ini digunakan untuk menambah restoran oleh admin
    public static void handleTambahRestoran(User user) {
        System.out.print("\n--------- Tambah Restoran ---------\n");

        loopTambahRestoran: while (true) {
            // Mengambil input nama restoran dari user
            System.out.print("Nama: ");
            String restoName = input.nextLine();

            if (restoName.length() < 4) {
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }

            // Mengambil restoran dari restoList berdasarkan input nama restoran
            Restaurant currResto = getRestaurant(restoName);
            if (currResto != null) {
                System.out.printf(
                        "Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n\n",
                        restoName);
                continue;
            } else {
                currResto = new Restaurant(restoName);
            }

            // Mengambil input jumlah makanan dari user
            System.out.print("Jumlah Makanan: ");
            int menuAmount = input.nextInt();
            input.nextLine();

            // Mengambil input nama-nama menu dari user
            ArrayList<String[]> menuItems = new ArrayList<>();
            for (int i = 0; i < menuAmount; i++) {
                String[] menuInput = input.nextLine().split(" ");
                String menuName =
                        String.join(" ", Arrays.copyOfRange(menuInput, 0, menuInput.length - 1));
                String menuPrice = menuInput[menuInput.length - 1];
                String[] menuItem = new String[] {menuName, menuPrice};
                menuItems.add(menuItem);
            }

            // Konversi nama-nama menu ke Menu List
            for (String[] menuItem : menuItems) {
                try {
                    String menuName = menuItem[0];
                    double menuPrice = Double.parseDouble(menuItem[1]);
                    Menu currMenu = new Menu(menuName, menuPrice);
                    currResto.addMenu(currMenu);
                } catch (Exception err) {
                    System.out.println("Harga menu harus bilangan bulat!\n");
                    continue loopTambahRestoran;
                }
            }

            // Menambahkan restoran yang baru ke restoList
            restoList.add(currResto);
            System.out.printf("Restaurant %s berhasil terdaftar.\n", restoName);
            break;
        }
    }

    // Method ini digunakan untuk menghapus restoran oleh admin
    public static void handleHapusRestoran(User user) {
        System.out.print("\n--------- Hapus Restoran ---------\n");

        loopHapusRestoran: while (true) {
            // Mengambil input nama restoran dari user
            System.out.print("Nama Restoran: ");
            String restoName = input.nextLine();

            // Mengambil restoran dari restoList berdasarkan input nama restoran
            Restaurant currResto = getRestaurant(restoName);
            if (currResto == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

            // Menghapus restoran
            restoList.remove(currResto);
            System.out.println("Restoran berhasil dihapus");
            break;
        }
    }

    public static void initUser() {
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
        userList.add(
                new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T",
                "Customer"));
        userList.add(
                new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu() {
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}
