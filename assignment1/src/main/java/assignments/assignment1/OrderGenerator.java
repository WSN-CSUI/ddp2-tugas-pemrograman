package assignments.assignment1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    /*
     * Anda boleh membuat method baru sesuai kebutuhan Anda Namun, Anda tidak boleh menghapus
     * ataupun memodifikasi return type method yang sudah ada.
     */

    /*
     * Method ini untuk menampilkan menu
     */
    public static void showMenu() {
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    public static String getRestaurantName() throws Exception {
        System.out.print("Nama Restoran: ");
        String userInput = input.nextLine();

        if (userInput.length() < 4) {
            throw new Exception("Nama restoran minimal 4 karakter");
        }

        return userInput;
    }

    public static String getOrderDate() throws Exception {
        System.out.print("Tanggal Pemesanan: ");
        String userInput = input.nextLine();

        try {
            LocalDate.parse(userInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return userInput;
        } catch (DateTimeParseException err) {
            throw new Exception("Tanggal Pemesanan dalam format DD/MM/YYYY!");
        }
    }

    public static String getPhoneNumber() throws Exception {
        System.out.print("No. Telepon: ");
        String userInput = input.nextLine();

        if (!userInput.matches("\\d+")) {
            throw new Exception("Harap masukkan nomor telepon dalam bilangan bulat positif.");
        }

        return userInput;
    }

    public static String getOrderID() throws Exception {
        System.out.print("Order ID: ");
        String userInput = input.nextLine();

        if (userInput.length() < 16) {
            throw new Exception("Order ID minimal 16 karakter!");
        }

        if (!userInput.substring(userInput.length() - 2).equals(generateChecksumID(userInput))) {
            throw new Exception("Silahkan masukkan Order ID yang valid!");
        }

        return userInput;
    }

    public static String getLocation() throws Exception {
        System.out.print("Lokasi Pengiriman: ");
        String userInput = input.nextLine();

        if (determineDeliveryCost(userInput) == null) {
            throw new Exception("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
        }

        return userInput;
    }

    public static void performGenerateOrderID() {
        String restaurantName;
        String orderDate;
        String phoneNumber;

        while (true) {
            try {
                restaurantName = getRestaurantName();
                orderDate = getOrderDate();
                phoneNumber = getPhoneNumber();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String output = generateOrderID(restaurantName, orderDate, phoneNumber);
        System.out.println(output);
    }

    /*
     * Method ini digunakan untuk membuat ID dari nama restoran, tanggal order, dan nomor telepon
     * 
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder,
            String noTelepon) {
        String restaurantID = generateRestaurantID(namaRestoran);
        String orderDateID = generateOrderDateID(tanggalOrder);
        String phoneNumberID = generatePhoneNumberID(noTelepon);
        String checksumID = generateChecksumID(
                String.format("%s%s%s", restaurantID, orderDateID, phoneNumberID));

        return String.format("Order ID %s%s%s%s diterima!", restaurantID, orderDateID,
                phoneNumberID, checksumID);
    }

    public static void performGenerateBill() {
        String orderID;
        String location;

        while (true) {
            try {
                orderID = getOrderID();
                location = getLocation();
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String output = generateBill(orderID, location);
        System.out.println(output);
    }


    /*
     * Method ini digunakan untuk membuat bill dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah: Bill: Order ID: [Order ID] Tanggal
     * Pemesanan: [Tanggal Pemesanan] Lokasi Pengiriman: [Kode Lokasi] Biaya Ongkos Kirim: [Total
     * Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi) {
        LocalDate date =
                LocalDate.parse(OrderID.substring(4, 12), DateTimeFormatter.ofPattern("ddMMyyy"));

        return String.format(
                "Bill:\nOrder ID: %s\nTanggal Pemesanan: %s\nLokasi Pengiriman: %s\nBiaya Ongkos Kirim: Rp%,d\n",
                OrderID, date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                lokasi.toUpperCase(), determineDeliveryCost(lokasi));
    }

    public static Integer determineDeliveryCost(String location) {
        switch (location.toUpperCase()) {
            case "P":
                return 10000;
            case "U":
                return 20000;
            case "T":
                return 35000;
            case "S":
                return 40000;
            case "B":
                return 60000;
            default:
                return null;
        }
    }

    public static int convertCode39FromChar(char character) {
        switch (character) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
                return 10;
            case 'B':
                return 11;
            case 'C':
                return 12;
            case 'D':
                return 13;
            case 'E':
                return 14;
            case 'F':
                return 15;
            case 'G':
                return 16;
            case 'H':
                return 17;
            case 'I':
                return 18;
            case 'J':
                return 19;
            case 'K':
                return 20;
            case 'L':
                return 21;
            case 'M':
                return 22;
            case 'N':
                return 23;
            case 'O':
                return 24;
            case 'P':
                return 25;
            case 'Q':
                return 26;
            case 'R':
                return 27;
            case 'S':
                return 28;
            case 'T':
                return 29;
            case 'U':
                return 30;
            case 'V':
                return 31;
            case 'W':
                return 32;
            case 'X':
                return 33;
            case 'Y':
                return 34;
            case 'Z':
                return 35;
            default:
                return Integer.MAX_VALUE;
        }
    }

    public static char convertCode39FromInt(int code) {
        switch (code) {
            case 0:
                return '0';
            case 1:
                return '1';
            case 2:
                return '2';
            case 3:
                return '3';
            case 4:
                return '4';
            case 5:
                return '5';
            case 6:
                return '6';
            case 7:
                return '7';
            case 8:
                return '8';
            case 9:
                return '9';
            case 10:
                return 'A';
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            case 16:
                return 'G';
            case 17:
                return 'H';
            case 18:
                return 'I';
            case 19:
                return 'J';
            case 20:
                return 'K';
            case 21:
                return 'L';
            case 22:
                return 'M';
            case 23:
                return 'N';
            case 24:
                return 'O';
            case 25:
                return 'P';
            case 26:
                return 'Q';
            case 27:
                return 'R';
            case 28:
                return 'S';
            case 29:
                return 'T';
            case 30:
                return 'U';
            case 31:
                return 'V';
            case 32:
                return 'W';
            case 33:
                return 'X';
            case 34:
                return 'Y';
            case 35:
                return 'Z';
            default:
                return Character.MAX_VALUE;
        }
    }

    public static String generateRestaurantID(String restaurant) {
        String restaurantCode = restaurant.replaceAll(" ", "").substring(0, 4);
        return restaurantCode.toUpperCase();
    }

    public static String generateOrderDateID(String orderDate) {
        LocalDate date = LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("dd/MM/yyy"));

        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d%02d%d", day, month, year);
    }

    public static String generatePhoneNumberID(String phoneNumber) {
        int result = 0;

        for (int i = 0; i < phoneNumber.length(); i++) {
            String letter = String.valueOf(phoneNumber.charAt(i));
            result += Integer.parseInt(letter);
        }

        return String.format("%02d", (result % 100));
    }

    public static String generateChecksumID(String prevID) {
        int oddValue = 0;
        int evenValue = 0;

        for (int i = 0; i < 14; i++) {
            if (i % 2 == 0) {
                oddValue += convertCode39FromChar(prevID.charAt(i));
            } else {
                evenValue += convertCode39FromChar(prevID.charAt(i));
            }
        }

        int checksum1 = convertCode39FromInt(oddValue % 36);
        int checksum2 = convertCode39FromInt(evenValue % 36);

        return String.format("%c%c", checksum1, checksum2);
    }

    public static void main(String[] args) {
        while (true) {
            showMenu();

            System.out.print("Pilihan menu: ");
            int menuInput = input.nextInt();
            input.nextLine();

            switch (menuInput) {
                case 1:
                    performGenerateOrderID();
                    break;
                case 2:
                    performGenerateBill();
                    break;
                case 3:
                    System.out.println("Terimakasih telah menggunakan DepeFood!");
                    return;
                default:
                    System.out.println("Pilihan tidak tersedia!");
                    break;
            }
        }
    }
}
