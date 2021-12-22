package utils;

public class Monome {

    private final Complex coef;
    private final int pow;

    public Monome(Complex coef, int pow) {
        this.coef = coef;
        this.pow = pow;
    }

    public Complex getCoef() {
        return coef;
    }

    public int getPow() {
        return pow;
    }

    @Override
    public String toString() {
        return String.format("(%s)*z^%d ", coef.toString(), this.pow);
    }
}
