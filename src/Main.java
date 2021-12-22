import fractals.FractalGenerator;
import fractals.JuliaSet;
import fractals.MandelbrotSet;
import utils.*;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.System.exit;

public class Main {

    private final static Complex c1 = Complex.of(.3, .5);
    private final static Complex c2 = Complex.of(.285, .01);
    private final static Complex c3 = Complex.of(.038088, .9754633);
    private final static Complex c4 = Complex.of(.285, .013);
    private final static Complex c5 = Complex.of(.285, .01);
    private final static Complex c6 = Complex.of(-1.476, .0);

    private final static Polynomial p = new Polynomial(List.of(
            new Monome(Complex.of(1, 0), 2),
            new Monome(c1, 0)
    ));

    public static void main(String[] args){
        double re = 0.0;
        double im = 0.0;
        int size = 500;
        if (args.length != 0 && args.length != 3) {
            System.out.println(" Usage : - sans argument : génère une fractale basique en nuance de gris \n " +
                    "- <./fractale> <Nombre complexe partie réelle> <Nombre complexe partie imaginaire> <Taille fenetre>" +
                    "\n\n" + " Fractales génériques : " +
                    "- réel : 0.3, imaginaire : 0.5 \n" +
                    "- réel : 0.285, imaginaire : 0.01 \n" +
                    "- réel : 0.038088, imaginaire : 0.9754633 \n" +
                    "- réel : 0.285, imaginaire : 0.013 \n" +
                    "- réel : -1.476, imaginaire : 0.0 \n");
            exit(0);
        }
        if (args.length == 0) {
            FractalGenerator gen = new JuliaSet(
                    ComplexFunction.parse(p),
                    2.5,
                    500,
                    new Interval(0, 360)
            );
//            FractalGenerator gen = new MandelbrotSet(
//                    (Complex z, Double x, Double y) -> {
//                        return Complex.of(x, y).add(z.pow(2));
//                    },
//                    2,
//                    500,
//                    new Interval(0, 360)
//            );
            try {
                gen.fill().render("fractal.png");
            } catch (IOException ignore) {
            }
        } else {
            try {
                re = Double.parseDouble(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Modèle pour la partie réelle : 0.0 ->" +
                        " Remplacez les 0 par des valeurs ou mettez plus de nombres");
            }
            try {
                im = Double.parseDouble(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Modèle pour la partie imaginaire : 0.0 ->" +
                        " Remplacez les 0 par des valeurs ou mettez plus de nombres");
            }
            try {
                size = parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Modèle pour la taille de l'écran : 00 ->" +
                        " Remplacez les 0 par des valeurs pour avoir la taille de l'écran en pixel");
            }
            Complex c = Complex.of(re, im);
            FractalGenerator gen = new JuliaSet(
                    (Complex z) -> {
                        return c.add(z.pow(2));
                    },
                    2,
                    500,
                    new Interval(0, 360)
            );
            try {
                gen.fill().render("fractal.png");
            } catch (IOException ignore) {
            }
        }
    }
}