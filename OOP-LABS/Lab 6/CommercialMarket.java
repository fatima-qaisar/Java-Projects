package sp25_bcs_037;
public class CommercialMarket {
    private final String id;
    private Shop[] shops;
    private int shopCount;

    public CommercialMarket(String id, int initial) {
        this.id = id;
        if (initial < 1) initial = 12;
        shops = new Shop[initial];
        shopCount = 0;
        preloadShops(initial);
    }

    private void preloadShops(int n) {
        for (int i = 0; i < n; i++) {
            String sid = String.format("%s-S%02d", id, i + 1);
            shops[shopCount++] = new Shop(sid, PlotType.COMM_SHOP.getBasePrice());
        }
    }

    public String getId() { return id; }

    private void ensureCapacity(int min) {
        if (min <= shops.length) return;
        int newSize = shops.length * 2;
        if (newSize < min) newSize = min;
        Shop[] ns = new Shop[newSize];
        for (int i = 0; i < shopCount; i++) ns[i] = shops[i];
        shops = ns;
    }

    // two-pass selection
    public Shop[] firstNAvailableShops(int n) {
        int count = 0;
        for (int i = 0; i < shopCount; i++) if (shops[i].isAvailable()) count++;
        if (count == 0) return new Shop[0];
        int take = Math.min(count, n);
        Shop[] res = new Shop[take];
        int idx = 0;
        for (int i = 0; i < shopCount && idx < take; i++) {
            if (shops[i].isAvailable()) res[idx++] = shops[i];
        }
        return res;
    }

    public boolean bookShopById(String shopId) {
        for (int i = 0; i < shopCount; i++) {
            if (shops[i].getId().equals(shopId)) return shops[i].book();
        }
        return false;
    }

    public boolean cancelShopById(String shopId) {
        for (int i = 0; i < shopCount; i++) {
            if (shops[i].getId().equals(shopId)) return shops[i].cancel();
        }
        return false;
    }

    public String summary() {
        int avail = 0;
        for (int i = 0; i < shopCount; i++) if (shops[i].isAvailable()) avail++;
        return String.format("%s | shops: %d | available: %d", id, shopCount, avail);
    }

    public Shop[] allShops() {
        Shop[] out = new Shop[shopCount];
        for (int i = 0; i < shopCount; i++) out[i] = shops[i];
        return out;
    }
}
