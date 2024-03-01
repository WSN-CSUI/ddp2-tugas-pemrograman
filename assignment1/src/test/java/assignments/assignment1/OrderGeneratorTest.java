package assignments.assignment1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OrderGeneratorTest {
    @Test
    public void testCode39ConverterInt() {
        assertEquals('C', OrderGenerator.convertCode39FromInt(12));
        assertEquals('1', OrderGenerator.convertCode39FromInt(1));
    }

    @Test
    public void testCode39ConverterChar() {
        assertEquals(12, OrderGenerator.convertCode39FromChar('C'));
        assertEquals(1, OrderGenerator.convertCode39FromChar('1'));
    }

    @Test
    public void testCreateRestaurantID() {
        assertEquals("HOLY", OrderGenerator.generateRestaurantID("Holycow!"));
    }

    @Test
    public void testCreateOrderDateID() {
        assertEquals("18022024", OrderGenerator.generateOrderDateID("18/02/2024"));
    }

    @Test
    public void testCreatePhoneNumberID() {
        assertEquals("53", OrderGenerator.generatePhoneNumberID("9928765403"));
    }

    @Test
    public void testCreateChecksumID() {
        String prevID = String.format("%s%s%s",
                OrderGenerator.generateRestaurantID("Holycow!"),
                OrderGenerator.generateOrderDateID("18/02/2024"),
                OrderGenerator.generatePhoneNumberID("9928765403")
        );

        assertEquals("C3", OrderGenerator.generateChecksumID(prevID));
    }


    @Test
    public void testGenerateOrderID1() {
        assertEquals("HOLY1802202453C3", OrderGenerator.generateOrderID("Holycow!", "18/02/2024", "9928765403"));
    }

    @Test
    public void testGenerateBill1() {
        String solution = "Bill:\n" + //
                "Order ID: HOLY1802202453C3\n" + //
                "Tanggal Pemesanan: 18/02/2024\n" + //
                "Lokasi Pengiriman: S\n" + //
                "Biaya Ongkos Kirim: Rp40,000\n" + // Change back to . (dot) later
                "";
        assertEquals(solution, OrderGenerator.generateBill("HOLY1802202453C3", "s"));
    }
}
