package utils;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ComplexFunction extends Function<Complex, Complex> {



    static ComplexFunction parse(Polynomial p) {

        

        return (z) -> Complex.of(.285, .01).add(z.pow(2));
    }
}
