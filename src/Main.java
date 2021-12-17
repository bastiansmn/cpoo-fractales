import java.io.IOException;
import java.util.Arrays;

public class Main {

    private final static Complex c1 = Complex.of(.3, .5);
    private final static Complex c2 = Complex.of(.285, .01);
    private final static Complex c3 = Complex.of(.038088, .9754633);
    private final static Complex c4 = Complex.of(.285, .013);
    private final static Complex c5 = Complex.of(.285, .01);
    private final static Complex c6 = Complex.of(-1.476, .0);


    public static void main(String[] args) {
        FractalGenerator gen = new FractalGenerator(
                (Complex z) -> {
                    // Equivalent à :
                    // f(z) = c + z^2 où on choisis c parmi les constantes définies ci dessus
                    return c1.add(z.pow(2));
                },
                500
        );

        try {
            gen.fill().render("gen/fractal.png");
        } catch (IOException ignore) {}

    }

}
