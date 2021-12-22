package utils;

public final class Interval {

    private final int min;
    private final int max;

    public Interval(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "[" + min + "; " + max +
                "]";
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getRange() {
        return this.max - this.min;
    }
}
