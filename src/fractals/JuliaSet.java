package fractals;

import utils.complex.Complex;
import utils.complex.ComplexFunction;
import utils.Interval;

import java.util.Properties;

public final class JuliaSet extends FractalGenerator{

    private final ComplexFunction function;
    private final Complex c;

    public JuliaSet(FractalBuilder fractalBuilder, ComplexFunction function, Complex c) {
        super(fractalBuilder);
        this.function = function;
        this.c = c;
    }

    public JuliaSet(Properties properties, ComplexFunction function, Complex c) {
        super(properties);
        this.function = function;
        this.c = c;
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

    @Override
    protected String getInformations() {
        return """
               Fractal type : JuliaSet
               Constant used : c=%s
               Color range : %s
               Image size : %dx%dpx
               Square size : [%f, %f] x [%f, %f]
               """.formatted(
               this.c.toString(),
               this.getColorRange().toString(),
               this.getSize(), this.getSize(),
               -(this.getFramesize()/2), this.getFramesize()/2,
               -(this.getFramesize()/2), this.getFramesize()/2
        );
    }
}
