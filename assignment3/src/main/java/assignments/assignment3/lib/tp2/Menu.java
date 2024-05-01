package assignments.assignment3.lib.tp2;

public class Menu {
    private final String namaMakanan;
    private final double harga;

    public Menu(String namaMakanan, double harga) {
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    public double getHarga() {
        return harga;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }
}
