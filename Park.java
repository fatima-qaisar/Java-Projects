package sp25_bcs_037;
public class Park {
    private final String id;
    private final ShapeType shape;
    private final double[] dims;
    private final double area;

    public Park(String id, ShapeType shape, double[] dims) {
        this.id = id;
        this.shape = shape;
        this.dims = dims.clone();
        this.area = shape.computeArea(this.dims);
    }

    public String getId() { return id; }
    public double getArea() { return area; }

    public String summary() {
        return String.format("%s | %s | area: %.2f su", id, shape.name(), area);
    }
}
