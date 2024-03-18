package assignments.assignment2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderGenerator {
    /*
     * Method ini digunakan untuk membuat ID dari nama restoran, tanggal order, dan nomor telepon
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder,
            String noTelepon) {
        String restaurantID = generateRestaurantID(namaRestoran);
        String orderDateID = generateOrderDateID(tanggalOrder);
        String phoneNumberID = generatePhoneNumberID(noTelepon);
        String checksumID = generateChecksumID(
                String.format("%s%s%s", restaurantID, orderDateID, phoneNumberID));

        return String.format("%s%s%s%s", restaurantID, orderDateID, phoneNumberID, checksumID);
    }

    /*
     * Method ini digunakan untuk membuat bill dari order id dan lokasi
     */
    public static String generateBill(User user, Order order) {
        StringBuilder orderedMenu = new StringBuilder();
        double orderedMenuPrice = 0;

        for (Menu menu : order.getItems()) {
            orderedMenu.append(String.format("- %s\n", menu));
            orderedMenuPrice += menu.getHarga();
        }

        return String.format(
                "Bill:\nOrder ID: %s\nTanggal Pemesanan: %s\nRestaurant: %s\nLokasi Pengiriman: %s\nStatus Pengiriman: %s\nPesanan:\n %sBiaya Ongkos Kirim: Rp%d\nTotal Biaya: Rp%.0f\n",
                order.getOrderID(), order.getTanggalPemesanan(), order.getRestaurant().getNama(),
                user.getLokasi(), order.getOrderFinished(), orderedMenu,
                order.getBiayaOngkosKirim(), orderedMenuPrice + order.getBiayaOngkosKirim());
    }

    /*
     * Method ini digunakan untuk mententukan harga pengiriman sesuai lokasi (return null jika
     * lokasi tidak ada yang sesuai)
     */
    public static int determineDeliveryCost(String location) {
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
                return 0;
        }
    }

    /*
     * Method ini digunakan untuk mengkonversi char ke int sesuai dengan Code39 Encoding
     */
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

    /*
     * Method ini digunakan untuk mengkonversi int ke char sesuai dengan Code39 Encoding
     */
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

    /*
     * Method untuk membuat kode restoran menggunakkan nama restoran
     */
    public static String generateRestaurantID(String restaurant) {
        String restaurantCode = restaurant.replaceAll(" ", "").substring(0, 4);
        return restaurantCode.toUpperCase();
    }

    /*
     * Method untuk membuat kode tanggal pesan menggunakkan tanggal kapan order dipesan
     */
    public static String generateOrderDateID(String orderDate) {
        LocalDate date = LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("dd/MM/yyy"));

        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        return String.format("%d%02d%d", day, month, year);
    }

    /*
     * Method untuk membuat kode nomor telepon menggunakkan nomor telepon user
     */
    public static String generatePhoneNumberID(String phoneNumber) {
        int result = 0;

        for (int i = 0; i < phoneNumber.length(); i++) {
            String letter = String.valueOf(phoneNumber.charAt(i));
            result += Integer.parseInt(letter);
        }

        return String.format("%02d", (result % 100));
    }

    /*
     * Method untuk membuat checksum dengan nama restoran, tanggal pesan, dan nomor telepon user
     */
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
}
