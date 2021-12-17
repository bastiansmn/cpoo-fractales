import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        FractalGenerator gen = new FractalGenerator(
                (Complex z) -> {
                    // Equivalent à :
                    // f(z) = c + z^2 où c=1+i
                    return Complex.of(1, 1).add(z.pow(2));
                },
                100
        );

        try {
            gen.fill().render("fractal.png");
        } catch (IOException ignore) {}

    }

}
