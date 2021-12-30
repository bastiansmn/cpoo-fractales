package fractals;

import utils.Interval;

public final class FractalBuilder {
    private final double framesize;
    private final int size;

    private double horoffset = 0;
    private double veroffset = 0;
    private Interval colorRange = new Interval(0, 360);
    private double minBrightness = 0.4;

    public FractalBuilder(double framesize, int size) {
        this.framesize = framesize;
        this.size = size;
    }

    public FractalBuilder horoffset(double horoffset) {
        this.horoffset = horoffset;
        return this;
    }

    public FractalBuilder veroffset(double veroffset) {
        this.veroffset = veroffset;
        return this;
    }

    public FractalBuilder colorRange(Interval colorRange) {
        this.colorRange = colorRange;
        return this;
    }

    public FractalBuilder minBrightness(double minBrightness) {
        this.minBrightness = minBrightness;
        return this;
    }

    public Interval getColorRange() {
        return colorRange;
    }

    public double getMinBrightness() {
        return minBrightness;
    }

    public double getFramesize() {
        return framesize;
    }

    public double getHoroffset() {
        return horoffset;
    }

    public double getVeroffset() {
        return veroffset;
    }

    public int getSize() {
        return size;
    }
}
