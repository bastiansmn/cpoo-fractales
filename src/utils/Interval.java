package utils;

public final class Interval {

    private final int min;
    private final int max;

    public Interval(int min, int max) {
        if (min > max) {
            this.min = max;
            this.max = min;
        } else {
            this.min = min;
            this.max = max;
        }
    }

    @Override
    public String toString() {
        return "[" + min + "; " + max +
                "]";
    }

    public static Interval parse(String s) {
        String[] parts = s.replace("[", "").replace("]", "").split(";");
        if (parts.length != 2)
            throw new IllegalArgumentException(String.format("Interval '%s' must have only 2 bounds", s));
        int min = Integer.parseInt(parts[0].trim());
        int max = Integer.parseInt(parts[1].trim());
        return new Interval(min, max);
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
