package sp25_bcs_037;
public class Block {
    private final String name;
    private Plot[][] plots;    // jagged grid
    private Park[] parks;
    private int parkCount;
    private CommercialMarket market;

    private static final int DEFAULT_STREETS = 5;
    private static final int[] DEFAULT_STREET_LENGTHS = {10,11,12,13,14};

    public Block(String name) {
        this(name, DEFAULT_STREET_LENGTHS);
    }

    public Block(String name, int[] streetLengths) {
        this.name = name;
        materializePlots(streetLengths);
        parks = new Park[2];
        parkCount = 0;
        market = new CommercialMarket("MKT-" + name, 12);
        preloadAmenities();
    }

    private void materializePlots(int[] streetLengths) {
        plots = new Plot[streetLengths.length][];
        for (int s = 0; s < streetLengths.length; s++) {
            int len = streetLengths[s];
            plots[s] = new Plot[len];
            for (int p = 0; p < len; p++) {
                int streetIndex = s + 1;
                int plotNumber = p + 1;
                PlotType type = mapStreetToType(streetIndex);
                if ((plotNumber % 5) == 0) type = PlotType.PARKING;
                ShapeType shape = defaultShapeForType(type, streetIndex);
                double[] dims = defaultDimsFor(type, streetIndex, plotNumber);
                boolean isCorner = (streetIndex >=1 && streetIndex <=3) && ((plotNumber % 4) == 0) && (type != PlotType.PARKING);
                if (isCorner) {
                    double secondFront = (dims.length >=1) ? dims[0] + 2.0 : 8.0;
                    plots[s][p] = new CornerPlot(streetIndex, plotNumber, type, shape, dims, secondFront);
                } else {
                    plots[s][p] = new Plot(streetIndex, plotNumber, type, shape, dims);
                }
            }
        }
    }

    private PlotType mapStreetToType(int streetIndex) {
        switch (streetIndex) {
            case 1: return PlotType.RES_5_MARLA;
            case 2: return PlotType.RES_10_MARLA;
            case 3: return PlotType.RES_1_KANAL;
            case 4: return PlotType.COMM_SHOP;
            case 5: return PlotType.COMM_OFFICE;
            default: return PlotType.RES_5_MARLA;
        }
    }

    private ShapeType defaultShapeForType(PlotType type, int streetIndex) {
        if (type == PlotType.RES_1_KANAL) return ShapeType.TRAPEZOID;
        return ShapeType.RECTANGLE;
    }

    private double[] defaultDimsFor(PlotType type, int streetIndex, int plotNumber) {
        if (type == PlotType.PARKING) return new double[]{4.0,5.0};
        if (type == PlotType.RES_5_MARLA) return new double[]{22.0 + (plotNumber % 3), 25.0};
        if (type == PlotType.RES_10_MARLA) return new double[]{30.0 + (plotNumber % 4), 35.0};
        if (type == PlotType.RES_1_KANAL) return new double[]{40.0 + (plotNumber % 5), 38.0 + (plotNumber % 3), 50.0};
        if (type == PlotType.COMM_SHOP) return new double[]{10.0 + (plotNumber % 2), 15.0};
        if (type == PlotType.COMM_OFFICE) return new double[]{14.0 + (plotNumber % 3), 18.0};
        return new double[]{10.0,10.0};
    }

    private void preloadAmenities() {
        addPark(new Park(name + "-P1", ShapeType.RECTANGLE, new double[]{20.0,30.0}));
        if ("B".equalsIgnoreCase(name) || "C".equalsIgnoreCase(name)) {
            addPark(new Park(name + "-P2", ShapeType.RECTANGLE, new double[]{15.0,10.0}));
        }
    }

    private void addPark(Park p) {
        if (parkCount >= parks.length) {
            Park[] np = new Park[parks.length * 2];
            for (int i = 0; i < parks.length; i++) np[i] = parks[i];
            parks = np;
        }
        parks[parkCount++] = p;
    }

    public String getName() { return name; }

    public void printStreetLayout() {
        System.out.println("Block " + name + " - street layout:");
        for (int s = 0; s < plots.length; s++) {
            System.out.print(" Street " + (s+1) + ": ");
            for (int p = 0; p < plots[s].length; p++) {
                System.out.print(plots[s][p].getId() + "(" + plots[s][p].shortMarker() + ") ");
            }
            System.out.println();
        }
    }

    public int getTotalPlots() {
        int total = 0;
        for (int s = 0; s < plots.length; s++) total += plots[s].length;
        return total;
    }

    public int getAvailableCount() {
        int count = 0;
        for (int s = 0; s < plots.length; s++) for (int p = 0; p < plots[s].length; p++) if (plots[s][p].isAvailable()) count++;
        return count;
    }

    public Plot findPlotById(String id) {
        for (int s = 0; s < plots.length; s++) for (int p = 0; p < plots[s].length; p++) if (plots[s][p].getId().equals(id)) return plots[s][p];
        return null;
    }

    public boolean bookPlot(String id) {
        Plot pl = findPlotById(id);
        if (pl == null) return false;
        return pl.book();
    }

    public boolean cancelPlot(String id) {
        Plot pl = findPlotById(id);
        if (pl == null) return false;
        return pl.cancel();
    }

    public Park[] listParks() {
        Park[] out = new Park[parkCount];
        for (int i = 0; i < parkCount; i++) out[i] = parks[i];
        return out;
    }

    public CommercialMarket getMarket() { return market; }

    // helpers used by HousingSociety/CityHousing
    public Plot getFirstAvailableShopFacingMarket() {
        int idx = 4 - 1;
        if (idx < 0 || idx >= plots.length) return null;
        for (int p = 0; p < plots[idx].length; p++) {
            Plot pl = plots[idx][p];
            if (pl.getType() == PlotType.COMM_SHOP && pl.isAvailable()) return pl;
        }
        return null;
    }

    public Plot getLargestAvailableResidentialPlot() {
        Plot best = null;
        int last = Math.min(3, plots.length);
        for (int s = 0; s < last; s++) {
            for (int p = 0; p < plots[s].length; p++) {
                Plot pl = plots[s][p];
                if ((pl.getType() == PlotType.RES_5_MARLA || pl.getType() == PlotType.RES_10_MARLA || pl.getType() == PlotType.RES_1_KANAL) && pl.isAvailable()) {
                    if (best == null || pl.getArea() > best.getArea()) best = pl;
                }
            }
        }
        return best;
    }

    public Plot getFirstAvailableRes1KanalCorner() {
        for (int s = 0; s < plots.length; s++) {
            for (int p = 0; p < plots[s].length; p++) {
                Plot pl = plots[s][p];
                if ((pl instanceof CornerPlot) && pl.getType() == PlotType.RES_1_KANAL && pl.isAvailable()) return pl;
            }
        }
        return null;
    }
}
