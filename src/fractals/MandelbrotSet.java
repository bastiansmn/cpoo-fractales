package fractals;

import utils.Complex;
import utils.ComplexTriFunction;
import utils.Interval;

public class MandelbrotSet extends FractalGenerator {

    private ComplexTriFunction function;

    // Default constructor, using super, can't be called externally
    private MandelbrotSet(double framesize, int size, Interval colorRange) {
        super(framesize, size, colorRange);
    }

    public MandelbrotSet(ComplexTriFunction function, double framesize, int size, Interval colorRange) {
        this(framesize, size, colorRange);
        this.function = function;
    }

    @Override
    protected int divergenceIndex(Complex z0) {
        int ite = 0;
        Complex zn = z0;
        Double re = z0.getRe();
        Double im = z0.getIm();
        while (ite < MAX_ITER-1 && zn.mod() <= RADIUS) {
            zn = function.apply(zn, re, im);
            ite++;
        }
        return ite;
    }
}
