package sp25_bcs_037;
public class CityHousing {
    private final String cityName;
    private HousingSociety[] societies;
    private int societyCount;

    public CityHousing(String cityName) {
        this.cityName = cityName;
        societies = new HousingSociety[2];
        societyCount = 0;
    }

    // convenience factory to create the default Lahore with two societies
    public static CityHousing createDefaultLahore() {
        CityHousing ch = new CityHousing("Lahore");
        ch.addSociety(new HousingSociety("LDA Avenue 1"));
        ch.addSociety(new HousingSociety("LDA Avenue 2"));
        return ch;
    }

    private void ensureCapacity(int min) {
        if (min <= societies.length) return;
        int newSize = societies.length * 2;
        HousingSociety[] ns = new HousingSociety[newSize];
        for (int i = 0; i < societyCount; i++) ns[i] = societies[i];
        societies = ns;
    }

    public void addSociety(HousingSociety s) {
        if (societyCount >= societies.length) ensureCapacity(societyCount * 2);
        societies[societyCount++] = s;
    }

    public void printAllLayouts() {
        System.out.println("City: " + cityName);
        for (int i = 0; i < societyCount; i++) {
            HousingSociety hs = societies[i];
            hs.printSummary();
        }
    }

    public HousingSociety getSociety(String name) {
        for (int i = 0; i < societyCount; i++) if (societies[i].getName().equalsIgnoreCase(name)) return societies[i];
        return null;
    }

    public boolean bookPlot(String societyName, String blockName, String plotId) {
        HousingSociety hs = getSociety(societyName);
        if (hs == null) return false;
        return hs.bookPlot(blockName, plotId);
    }

    public boolean cancelPlot(String societyName, String blockName, String plotId) {
        HousingSociety hs = getSociety(societyName);
        if (hs == null) return false;
        return hs.cancelPlot(blockName, plotId);
    }

    // find first RES_1_KANAL corner across all societies
    public Plot findFirstAvailableKanalCorner() {
        for (int i = 0; i < societyCount; i++) {
            Plot p = societies[i].findFirstAvailableRes1KanalCorner();
            if (p != null) return p;
        }
        return null;
    }

    // find first N vacant shops in particular society/block (two-pass inside market)
    public Shop[] findVacantShops(String societyName, String blockName, int n) {
        HousingSociety hs = getSociety(societyName);
        if (hs == null) return new Shop[0];
        Block b = hs.getBlockByName(blockName);
        if (b == null) return new Shop[0];
        return b.getMarket().firstNAvailableShops(n);
    }
}
