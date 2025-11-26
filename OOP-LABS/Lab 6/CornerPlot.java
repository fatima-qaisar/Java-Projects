package sp25_bcs_037;
public class CornerPlot extends Plot {
    private static final double CORNER_PREMIUM = 0.08;
    private final double secondFront; 

    public CornerPlot(int streetNo, int plotNo, PlotType type, ShapeType shape, double[] dims, double secondFront) {
        super(streetNo, plotNo, type, shape, dims);
        this.secondFront = secondFront;
    }

    @Override
    public double getPrice() {
        return super.getPrice() * (1.0 + CORNER_PREMIUM);
    }

    @Override
    public String getSummary() {
        return String.format("%s | %s CORNER | area: %.2f su | price: %,.0f PKR | %s",
                getId(), getType().name(), getArea(), getPrice(), (isAvailable() ? "Available" : "Booked"));
    }
}
