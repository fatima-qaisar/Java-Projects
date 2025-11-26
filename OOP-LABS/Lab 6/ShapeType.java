package sp25_bcs_037;
public enum ShapeType {
    RECTANGLE {
        @Override
        public double computeArea(double[] dims) {
            if (dims == null || dims.length < 2) return 0.0;
            return dims[0] * dims[1];
        }
    },
    TRAPEZOID {
        @Override
        public double computeArea(double[] dims) {
            if (dims == null || dims.length < 3) return 0.0;
            return ((dims[0] + dims[1]) / 2.0) * dims[2];
        }
    },
    L_SHAPE {
        @Override
        public double computeArea(double[] dims) {
            if (dims == null || dims.length < 4) return 0.0;
            return (dims[0] * dims[1]) + (dims[2] * dims[3]);
        }
    };

    public abstract double computeArea(double[] dims);
}
