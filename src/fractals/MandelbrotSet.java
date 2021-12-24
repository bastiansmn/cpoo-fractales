package fractals;

import utils.complex.Complex;
import utils.complex.ComplexBiFunction;
import utils.Interval;

import java.util.Properties;

public class MandelbrotSet extends FractalGenerator {

    private ComplexBiFunction function;

    // Default constructor, using super, can't be called externally
    private MandelbrotSet(double framesize, int size, Interval colorRange) {
        super(framesize, size, colorRange, .5);
    }

    public MandelbrotSet(ComplexBiFunction function, double framesize, int size, Interval colorRange) {
        this(framesize, size, colorRange);
        this.function = function;
    }

    // TODO : optionnal args constructors

    public MandelbrotSet(ComplexBiFunction function, Properties properties) {
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
