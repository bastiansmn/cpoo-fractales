package utils;

import java.util.function.Function;

public interface ComplexFunction extends Function<Complex, Complex> {



    static ComplexFunction parse(Polynomial p) {

        

        return (z) -> Complex.of(.285, .01).add(z.pow(2));
    }
}
