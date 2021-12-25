package fractals;

import utils.complex.Complex;
import utils.complex.ComplexBiFunction;
import utils.Interval;

import java.util.Properties;

public final class MandelbrotSet extends FractalGenerator {

    private ComplexBiFunction function;

    public MandelbrotSet(FractalBuilder fractalBuilder, ComplexBiFunction function) {
        super(fractalBuilder);
        this.function = function;
    }

    public MandelbrotSet(Properties properties, ComplexBiFunction function) {
        super(properties);
        this.function = function;
    }

    @Override
    protected int divergenceIndex(Complex z0) {
        int ite = 0;
        Complex zn = z0;
        double re = z0.getRe();
        double im = z0.getIm();
        while (ite < MAX_ITER-1 && zn.mod() <= RADIUS) {
            zn = function.apply(zn, Complex.of(re, im));
            ite++;
        }
        return ite;
    }

    @Override
    protected String getInformations() {
        return """
               Fractal type : MandelbrotSet
               Color range : %s
               Image size : %dx%dpx
               Square size : [%f, %f] x [%f, %f]
               """.formatted(
                this.getColorRange().toString(),
                this.getSize(), this.getSize(),
                -(this.getFramesize()/2), this.getFramesize()/2,
                -(this.getFramesize()/2), this.getFramesize()/2
        );
    }
}
