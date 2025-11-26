package sp25_bcs_037;
public class CityHousingDemo {
    public static void main(String[] args) {
        System.out.println("=== Housing Society Management System ===");

        CityHousing lahore = CityHousing.createDefaultLahore();

        System.out.println("\n-- Initial layouts and amenities --");
        lahore.printAllLayouts();

        System.out.println("\n-- Booking workflow (3-007 in LDA Avenue 1, Block A) --");
        boolean b1 = lahore.bookPlot("LDA Avenue 1", "A", "3-007");
        System.out.println("First booking attempt: " + (b1 ? "Succeeded" : "Failed"));
        boolean b2 = lahore.bookPlot("LDA Avenue 1", "A", "3-007");
        System.out.println("Second booking attempt (should fail): " + (b2 ? "Succeeded (ERROR)" : "Rejected"));
        boolean c1 = lahore.cancelPlot("LDA Avenue 1", "A", "3-007");
        System.out.println("Cancellation: " + (c1 ? "Succeeded" : "Failed"));

        System.out.println("\n-- Updated Block A layout --");
        HousingSociety s1 = lahore.getSociety("LDA Avenue 1");
        if (s1 != null) {
            Block a = s1.getBlockByName("A");
            if (a != null) a.printStreetLayout();
        }

        System.out.println("\n-- Search: first available RES_1_KANAL CornerPlot in LDA Avenue 2 --");
        Plot kanal = lahore.findFirstAvailableKanalCorner();
        if (kanal != null) {
            System.out.println("Found: " + kanal.getSummary());
        } else {
            System.out.println("No available RES_1_KANAL CornerPlot found.");
        }

        System.out.println("\n-- First 3 vacant shops in Block C market of LDA Avenue 1 --");
        Shop[] shops = lahore.findVacantShops("LDA Avenue 1", "C", 3);
        if (shops.length == 0) {
            System.out.println("No vacant shops found.");
        } else {
            for (int i = 0; i < shops.length; i++) {
                System.out.println(shops[i].summary());
            }
        }

    }
}
