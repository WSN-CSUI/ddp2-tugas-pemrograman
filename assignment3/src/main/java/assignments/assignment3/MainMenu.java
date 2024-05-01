package assignments.assignment3;

import assignments.assignment3.lib.tp2.Restaurant;
import assignments.assignment3.lib.tp2.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    public static User userLoggedIn;
    public static ArrayList<Restaurant> restoList = new ArrayList<>();
    public static ArrayList<User> userList = new ArrayList<>();
    private final LoginManager loginManager;
    private final Scanner input;

    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
    }

    public static void main(String[] args) {
        MainMenu mainMenu =
                new MainMenu(
                        new Scanner(System.in),
                        new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        mainMenu.run();
    }

    private static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    private static void startMenu() {
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void initUser() {
        userList.add(
                new User(
                        "Thomas N",
                        "9928765403",
                        "thomas.n@gmail.com",
                        "P",
                        "Customer",
                        new DebitPayment(),
                        500000));
        userList.add(
                new User(
                        "Sekar Andita",
                        "089877658190",
                        "dita.sekar@gmail.com",
                        "B",
                        "Customer",
                        new CreditCardPayment(),
                        2000000));
        userList.add(
                new User(
                        "Sofita Yasusa",
                        "084789607222",
                        "sofita.susa@gmail.com",
                        "T",
                        "Customer",
                        new DebitPayment(),
                        750000));
        userList.add(
                new User(
                        "Dekdepe G",
                        "080811236789",
                        "ddp2.gampang@gmail.com",
                        "S",
                        "Customer",
                        new CreditCardPayment(),
                        1800000));
        userList.add(
                new User(
                        "Aurora Anum",
                        "087788129043",
                        "a.anum@gmail.com",
                        "U",
                        "Customer",
                        new DebitPayment(),
                        650000));
        userList.add(
                new User(
                        "Admin",
                        "123456789",
                        "admin@gmail.com",
                        "-",
                        "Admin",
                        new CreditCardPayment(),
                        0));
        userList.add(
                new User(
                        "Admin Baik",
                        "9123912308",
                        "admin.b@gmail.com",
                        "-",
                        "Admin",
                        new CreditCardPayment(),
                        0));
    }

    // Mendapatkan user berdasarkan nama dan nomor telepon
    public static User getUser(String nama, String nomorTelepon) {
        for (User user : userList) {
            if (user.getNama().equals(nama.trim())
                    && user.getNomorTelepon().equals(nomorTelepon.trim())) {
                return user;
            }
        }
        return null;
    }

    // Menjalankan program
    public void run() {
        initUser();
        printHeader();
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }

        input.close();
    }

    // Login dengan nama dan nomor telepon
    private void login() {
        System.out.println("\nSilakan Login:");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelp = input.nextLine();

        userLoggedIn = getUser(nama, noTelp);
        // Validasi user jika tidak ditemukan
        if (userLoggedIn == null) {
            System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
            return;
        }

        // Menjalankan sistem berdasarkan role user
        UserSystemCLI userSystem = loginManager.getSystem(userLoggedIn.role);
        userSystem.run();
    }
}
