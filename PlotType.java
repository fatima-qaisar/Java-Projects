package sp25_bcs_037;
public enum PlotType {
    RES_5_MARLA(4_000_000),
    RES_10_MARLA(7_500_000),
    RES_1_KANAL(14_000_000),
    COMM_SHOP(3_000_000),
    COMM_OFFICE(5_000_000),
    PARKING(200_000);

    private final double basePrice;

    PlotType(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }
}
