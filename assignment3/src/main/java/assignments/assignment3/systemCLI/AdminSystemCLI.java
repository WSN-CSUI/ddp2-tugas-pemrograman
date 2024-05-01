package assignments.assignment3.systemCLI;

import assignments.assignment3.MainMenu;
import assignments.assignment3.lib.tp2.Menu;
import assignments.assignment3.lib.tp2.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminSystemCLI extends UserSystemCLI {
    @Override
    public boolean handleMenu(int command) {
        switch (command) {
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    private void handleTambahRestoran() {
        System.out.println("---------- Tambah Restoran ----------");
        Restaurant restaurant = null;
        while (restaurant == null) {
            String namaRestaurant = getValidRestaurantName();
            restaurant = new Restaurant(namaRestaurant);
            restaurant = handleTambahMenuRestaurant(restaurant);
        }
        MainMenu.restoList.add(restaurant);
        System.out.print("Restaurant " + restaurant.getNama() + " Berhasil terdaftar.");
    }

    private void handleHapusRestoran() {
        System.out.println("---------- Hapus Restoran ----------");
        boolean isActionDeleteEnded = false;
        while (!isActionDeleteEnded) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            boolean isRestaurantExist =
                    MainMenu.restoList.stream()
                            .anyMatch(
                                    restaurant ->
                                            restaurant.getNama().equalsIgnoreCase(restaurantName));
            if (!isRestaurantExist) {
                System.out.println("Restoran tidak terdaftar pada sistem.");
                System.out.println();
            } else {
                MainMenu.restoList =
                        new ArrayList<>(
                                MainMenu.restoList.stream()
                                        .filter(
                                                restaurant ->
                                                        !restaurant
                                                                .getNama()
                                                                .equalsIgnoreCase(restaurantName))
                                        .toList());
                System.out.println("Restoran berhasil dihapus");
                isActionDeleteEnded = true;
            }
        }
    }

    private Restaurant handleTambahMenuRestaurant(Restaurant restoran) {
        System.out.print("Jumlah Makanan: ");
        int jumlahMenu = input.nextInt();
        input.nextLine();

        boolean isMenuValid = true;
        for (int i = 0; i < jumlahMenu; i++) {
            String inputValue = input.nextLine().trim();
            String[] splitter = inputValue.split(" ");
            String hargaStr = splitter[splitter.length - 1];
            boolean isDigit = checkIsDigit(hargaStr);
            String namaMenu =
                    String.join(" ", Arrays.copyOfRange(splitter, 0, splitter.length - 1));
            if (isDigit) {
                int hargaMenu = Integer.parseInt(hargaStr);
                restoran.addMenu(new Menu(namaMenu, hargaMenu));
            } else {
                isMenuValid = false;
            }
        }

        if (!isMenuValid) {
            System.out.println("Harga menu harus bilangan bulat!");
            System.out.println();
        }

        return isMenuValid ? restoran : null;
    }

    private boolean checkIsDigit(String digits) {
        return digits.chars().allMatch(Character::isDigit);
    }

    private String getValidRestaurantName() {
        String name = "";
        boolean isRestaurantNameValid = false;

        while (!isRestaurantNameValid) {
            System.out.print("Nama: ");
            String inputName = input.nextLine().trim();
            boolean isRestaurantExist =
                    MainMenu.restoList.stream()
                            .anyMatch(restoran -> restoran.getNama().equalsIgnoreCase(inputName));
            boolean isRestaurantNameLengthValid = inputName.length() >= 4;

            if (isRestaurantExist) {
                System.out.printf(
                        "Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!%n\n",
                        inputName);
            } else if (!isRestaurantNameLengthValid) {
                System.out.println("Nama Restoran tidak valid! Minimal 4 karakter diperlukan.\n");
            } else {
                name = inputName;
                isRestaurantNameValid = true;
            }
        }

        return name;
    }
}
