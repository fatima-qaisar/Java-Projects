package sp25_bcs_037;
public class Plot {
    private final String id;        
    private final PlotType type;
    private final ShapeType shape;
    private final double[] dims;
    private final double area;
    private final double basePrice;
    private boolean available;


    public Plot(int streetNo, int plotNo, PlotType type, ShapeType shape, double[] dims) {
        this.id = makeId(streetNo, plotNo);
        this.type = type;
        this.shape = shape;
        this.dims = dims.clone();
        this.area = shape.computeArea(this.dims);
        this.basePrice = type.getBasePrice();
        this.available = true;
    }

    public static String makeId(int streetNo, int plotNo) {
        return String.format("%d-%03d", streetNo, plotNo);
    }

    public String getId() { return id; }
    public PlotType getType() { return type; }
    public ShapeType getShape() { return shape; }
    public double[] getDims() { return dims.clone(); }
    public double getArea() { return area; }
    public double getBasePrice() { return basePrice; }

    // price (CornerPlot overrides)
    public double getPrice() {
        return basePrice;
    }

    public boolean book() {
        if (!available) return false;
        available = false;
        return true;
    }

    public boolean cancel() {
        if (available) return false;
        available = true;
        return true;
    }

    public boolean isAvailable() { return available; }

    public String shortMarker() { return available ? "A" : "X"; }

    
    public String getSummary() {
        return String.format("%s | %s | area: %.2f su | price: %,.0f PKR | %s",
                id, type.name(), area, getPrice(), (available ? "Available" : "Booked"));
    }

    public String summary() { return getSummary(); }
}

