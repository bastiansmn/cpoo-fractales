package fractals;

import utils.Complex;
import utils.ComplexFunction;
import utils.Interval;

public class JuliaSet extends FractalGenerator{

    private ComplexFunction function;

    // Default constructor, using super, can't be called externally
    private JuliaSet(double framesize, int size, Interval colorRange) {
        super(framesize, size, colorRange);
    }

    public JuliaSet(ComplexFunction function, double framesize, int size, Interval colorRange) {
        this(framesize, size, colorRange);
        this.function = function;
    }

    public JuliaSet(ComplexFunction function, double framesize, int size) {
        this(function, framesize, size, new Interval(0, 360));
    }


    @Override
    protected int divergenceIndex(Complex z0) {
        int ite = 0;
        Complex zn = z0;
        while (ite < MAX_ITER-1 && zn.mod() <= RADIUS) {
            zn = function.apply(zn);
            ite++;
        }
        return ite;
    }
}
