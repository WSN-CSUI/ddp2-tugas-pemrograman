package assignments.assignment2;

import java.util.ArrayList;
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
        // TODO: Implementasi method untuk mendapat user dari userList
        return null;
    }

    public static void handleBuatPesanan(User user) {
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
    }

    public static void handleCetakBill(User user) {
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
    }

    public static void handleLihatMenu(User user) {
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    public static void handleUpdateStatusPesanan(User user) {
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
    }

    public static void handleTambahRestoran(User user) {
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
    }

    public static void handleHapusRestoran(User user) {
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
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
