package sp25_bcs_037;
public class PlotDemo {
    public static void main(String[] args) {
        System.out.println("=== Plot Demo ===");

        // Regular plot (Street 1, Plot 005) with rectangle dims: width x depth
        double[] dims1 = new double[] {30.0, 50.0};
        Plot p1 = new Plot(1, 5, PlotType.RES_5_MARLA, ShapeType.RECTANGLE, dims1);

        System.out.println("Regular Plot:");
        System.out.println(p1.getSummary());

        System.out.println("Booking...");
        System.out.println("Success? " + p1.book());
        System.out.println(p1.getSummary());

        System.out.println("Cancel...");
        System.out.println("Success? " + p1.cancel());
        System.out.println(p1.getSummary());

        // Corner plot (Street 3, Plot 008) trapezoid dims: front, back, depth
        double[] dims2 = new double[] {50.0, 60.0, 55.0};
        CornerPlot cp = new CornerPlot(3, 8, PlotType.RES_1_KANAL, ShapeType.TRAPEZOID, dims2, 52.0);

        System.out.println("\nCorner Plot:");
        System.out.println(cp.getSummary());

        System.out.println("Booking corner...");
        System.out.println("Success? " + cp.book());
        System.out.println(cp.getSummary());

    }
}
