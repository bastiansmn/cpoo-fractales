package fractals;

import utils.complex.Complex;
import utils.complex.ComplexFunction;
import utils.Interval;

import java.util.Properties;

public final class JuliaSet extends FractalGenerator{

    private final ComplexFunction function;
    private final Complex c;

    public JuliaSet(ComplexFunction function, Complex c, double framesize, int size, Interval colorRange, double minBrightness) {
        super(framesize, size, colorRange, minBrightness);
        this.function = function;
        this.c = c;
    }

    public JuliaSet(ComplexFunction function, Complex c,double framesize, int size, Interval colorRange) {
        this(function, c, framesize, size, colorRange, .0);
    }

    public JuliaSet(ComplexFunction function, Complex c, double framesize, int size) {
        this(function, c, framesize, size, new Interval(0, 360));
    }

    public JuliaSet(ComplexFunction function, Complex c, double framesize, int size, double minBrightness) {
        this(function, c, framesize, size, new Interval(0, 360), minBrightness);
    }

    // TODO : optional args constructors

    public JuliaSet(ComplexFunction function, Complex c, Properties properties) {
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
