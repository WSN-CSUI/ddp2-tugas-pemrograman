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

                System.out.print("Nama: ");
                String nama = input.nextLine();

                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                User userLoggedIn = getUser(nama, noTelp);
                if (userLoggedIn == null) {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!\n");
                    continue;
                } else {
                    System.out.printf("Selamat Datang %s!\n", userLoggedIn.getNama());
                }

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

    public static User getUser(String nama, String nomorTelepon) {
        for (User user : userList) {
            if (user.getNama().equals(nama) && user.getNomorTelepon().equals(nomorTelepon)) {
                return user;
            }
        }
        return null;
    }

    public static Restaurant getRestaurant(String nama) {
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(nama)) {
                return resto;
            }
        }
        return null;
    }

    public static Order getOrder(User user, String orderID) {
        for (Order order : user.getOrderHistory()) {
            if (order.getOrderID().equalsIgnoreCase(orderID)) {
                return order;
            }
        }
        return null;
    }

    public static Menu getMenu(Order order, String nama) {
        for (Menu menu : order.getItems()) {
            if (menu.getNamaMakanan().equals(nama)) {
                return menu;
            }
        }
        return null;
    }

    public static Menu getMenu(Restaurant restaurant, String nama) {
        for (Menu menu : restaurant.getMenu()) {
            if (menu.getNamaMakanan().equals(nama)) {
                return menu;
            }
        }
        return null;
    }

    public static void handleBuatPesanan(User user) {
        System.out.print("\n--------- Buat Pesanan ---------\n");

        loopBuatPesanan: while (true) {
            System.out.print("Nama Restoran: ");
            String restoName = input.nextLine();

            Restaurant currResto = getRestaurant(restoName);
            if (currResto == null) {
                System.out.println("Restoran tidak terdafter pada sistem.\n");
                continue;
            }

            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String orderDate = input.nextLine();

            try {
                LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException err) {
                System.out.println("Masukkan tanggal yang sesuai format (DD/MM/YYYY)!\n");
                continue;
            }

            System.out.print("Jumlah pesanan: ");
            int orderAmount = input.nextInt();
            input.nextLine();

            System.out.println("Order: ");
            ArrayList<String> menuNames = new ArrayList<>();
            for (int i = 0; i < orderAmount; i++) {
                String menuName = input.nextLine();
                menuNames.add(menuName);
            }

            ArrayList<Menu> menuItems = new ArrayList<>();
            for (String menuName : menuNames) {
                Menu currMenu = getMenu(currResto, menuName);
                if (currMenu == null) {
                    System.out.println("Mohon memesan menu yang tersedia di Restoran!\n");
                    continue loopBuatPesanan;
                }
                menuItems.add(currMenu);
            }

            String orderID =
                    OrderGenerator.generateOrderID(restoName, orderDate, user.getNomorTelepon());
            int deliveryCost = OrderGenerator.determineDeliveryCost(user.getLokasi());
            Order newOrder = new Order(orderID, orderDate, deliveryCost, currResto, menuItems);

            user.addOrderHistory(newOrder);
            System.out.printf("Pesanan dengan ID %s diterima!\n", orderID);
            break;
        }
    }

    public static void handleCetakBill(User user) {
        System.out.print("\n--------- Cetak Bill ---------\n");

        loopCetakBill: while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderID = input.nextLine();

            Order currOrder = getOrder(user, orderID);
            if (currOrder == null) {
                System.out.println("Order ID tidak dapat ditemukan.");
                continue;
            }

            System.out.println(OrderGenerator.generateBill(user, currOrder));
            break;
        }
    }

    public static void sortMenus(ArrayList<Menu> menus) {
        for (int i = 0; i < menus.size() - 1; i++) {
            for (int j = 0; j < menus.size() - i - 1; j++) {
                Menu menu1 = menus.get(j);
                Menu menu2 = menus.get(j + 1);
                int priceComparison = Double.compare(menu1.getHarga(), menu2.getHarga());
                if (priceComparison > 0 || (priceComparison == 0
                        && menu1.getNamaMakanan().compareTo(menu2.getNamaMakanan()) > 0)) {
                    menus.set(j, menu2);
                    menus.set(j + 1, menu1);
                }
            }
        }
    }

    public static void handleLihatMenu(User user) {
        System.out.print("\n--------- Lihat Menu ---------\n");

        loopLihatMenu: while (true) {
            System.out.print("Nama Restoran: ");
            String restoName = input.nextLine();

            Restaurant currResto = getRestaurant(restoName);
            if (currResto == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.");
                continue;
            }

            sortMenus(currResto.getMenu());

            System.out.println("Menu:");
            for (int i = 0; i < currResto.getMenu().size(); i++) {
                Menu currMenu = currResto.getMenu().get(i);
                System.out.printf("%d. %s\n", i + 1, currMenu);
            }

            break;
        }
    }

    public static void handleUpdateStatusPesanan(User user) {
        System.out.print("\n--------- Update Status Pesanan ---------\n");

        loopUpdateStatusPesanan: while (true) {
            System.out.print("Order ID: ");
            String orderID = input.nextLine();

            Order currOrder = getOrder(user, orderID);
            if (currOrder == null) {
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }

            System.out.print("Status: ");
            boolean status = input.nextLine().equalsIgnoreCase("selesai");

            if (currOrder.isOrderFinished() == status) {
                System.out.printf("Status pesanan dengan ID %s tidak berhasil diupdate!\n",
                        currOrder.getOrderID());
            } else {
                currOrder.setOrderFinished(status);
                System.out.printf("Status pesanan dengan ID %s berhasil diupdate!\n",
                        currOrder.getOrderID());
            }

            break;
        }
    }

    public static void handleTambahRestoran(User user) {
        System.out.print("\n--------- Tambah Restoran ---------\n");

        loopTambahRestoran: while (true) {
            System.out.print("Nama: ");
            String restoName = input.nextLine();

            if (restoName.length() < 4) {
                System.out.println("Nama Restoran tidak valid!\n");
                continue;
            }

            Restaurant currResto = getRestaurant(restoName);
            if (currResto != null) {
                System.out.printf(
                        "Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!\n\n",
                        restoName);
                continue;
            } else {
                currResto = new Restaurant(restoName);
            }

            System.out.print("Jumlah Makanan: ");
            int menuAmount = input.nextInt();
            input.nextLine();

            ArrayList<String[]> menuItems = new ArrayList<>();
            for (int i = 0; i < menuAmount; i++) {
                String[] menuInput = input.nextLine().split(" ");
                String menuName =
                        String.join(" ", Arrays.copyOfRange(menuInput, 0, menuInput.length - 1));
                String menuPrice = menuInput[menuInput.length - 1];
                String[] menuItem = new String[] {menuName, menuPrice};
                menuItems.add(menuItem);
            }

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

            restoList.add(currResto);
            System.out.printf("Restaurant %s berhasil terdaftar.\n", restoName);
            break;
        }
    }

    public static void handleHapusRestoran(User user) {
        System.out.print("\n--------- Hapus Restoran ---------\n");

        loopHapusRestoran: while (true) {
            System.out.print("Nama Restoran: ");
            String restoName = input.nextLine();

            Restaurant currResto = getRestaurant(restoName);
            if (currResto == null) {
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }

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
