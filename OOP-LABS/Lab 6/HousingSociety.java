package sp25_bcs_037;
public class HousingSociety {
    private final String name;
    private Block[] blocks;
    private int blockCount;

    public HousingSociety(String name) {
        this.name = name;
        this.blocks = new Block[3];
        this.blockCount = 0;
        preloadDefaults();
    }

    private void preloadDefaults() {
        addBlock(new Block("A"));
        addBlock(new Block("B"));
        addBlock(new Block("C"));
    }

    public String getName() { return name; }

    private void ensureCapacity(int min) {
        if (min <= blocks.length) return;
        int newSize = blocks.length * 2;
        Block[] nb = new Block[newSize];
        for (int i = 0; i < blockCount; i++) nb[i] = blocks[i];
        blocks = nb;
    }

    public void addBlock(Block b) {
        if (blockCount >= blocks.length) ensureCapacity(blocks.length * 2);
        blocks[blockCount++] = b;
    }

    public Block getBlockByName(String bname) {
        for (int i = 0; i < blockCount; i++) if (blocks[i].getName().equalsIgnoreCase(bname)) return blocks[i];
        return null;
    }

    public boolean bookPlot(String blockName, String plotId) {
        Block b = getBlockByName(blockName);
        if (b == null) return false;
        return b.bookPlot(plotId);
    }

    public boolean cancelPlot(String blockName, String plotId) {
        Block b = getBlockByName(blockName);
        if (b == null) return false;
        return b.cancelPlot(plotId);
    }

    public void printSummary() {
        System.out.println("Society: " + name);
        for (int i = 0; i < blockCount; i++) {
            System.out.println();
            Block b = blocks[i];
            System.out.println(" Block " + b.getName() + " summary:");
            b.printStreetLayout();
            System.out.println("  Amenities:");
            Park[] ps = b.listParks();
            for (int j = 0; j < ps.length; j++) System.out.println("   " + ps[j].summary());
            System.out.println("   " + b.getMarket().summary());
        }
    }

    public Plot findFirstAvailableCommShopFacingMarket() {
        for (int i = 0; i < blockCount; i++) {
            Plot p = blocks[i].getFirstAvailableShopFacingMarket();
            if (p != null) return p;
        }
        return null;
    }

    public Plot findLargestAvailableResidentialByArea() {
        Plot best = null;
        for (int i = 0; i < blockCount; i++) {
            Plot p = blocks[i].getLargestAvailableResidentialPlot();
            if (p != null && (best == null || p.getArea() > best.getArea())) best = p;
        }
        return best;
    }

    public Plot findFirstAvailableRes1KanalCorner() {
        for (int i = 0; i < blockCount; i++) {
            Plot p = blocks[i].getFirstAvailableRes1KanalCorner();
            if (p != null) return p;
        }
        return null;
    }

    public Block[] listBlocks() {
        Block[] out = new Block[blockCount];
        for (int i = 0; i < blockCount; i++) out[i] = blocks[i];
        return out;
    }
}
