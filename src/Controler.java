import fractals.FractalBuilder;
import fractals.FractalGenerator;
import fractals.JuliaSet;
import fractals.MandelbrotSet;
import utils.*;
import utils.complex.Complex;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Controler {
    private static Fenetre affichage;
    private int intervalle_1 = 0;
    private int intervalle_2 = 121;
    private Complex complexe;
    private double zoom;
    private int size;
    private double minBrightness;
    private double horoffset;
    private double veroffset;
    private boolean is_correct = true;

    Controler() {
    }

    public void lancement() {
        affichage = new Fenetre(new Controler());
        affichage.init();
        affichage.setVisible(true);
    }

    public void correction() {
        affichage.init();
        affichage.setVisible(true);
    }

    public void set_intervalle_1(Color color) {
        float[] comp = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), comp);
        comp[0] *= 360;
        intervalle_1 = (int) comp[0];
    }

    public void set_intervalle_2(Color color) {
        float[] comp = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), comp);
        comp[0] *= 360;
        intervalle_2 = (int) comp[0];
    }

    public void set_complexe(String s) {
        try {
            complexe = Complex.parse(s);
        } catch (Exception e) {
            is_correct = false;
        }
    }

    public void set_zoom(String s) {
        try {
            zoom = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void set_size(String s) {
        try {
            size = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void set_luminosity(String s) {
        try {
            minBrightness = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void set_horizontal(String s){
        try {
            horoffset = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void set_vertical(String s){
        try {
            veroffset = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public FractalGenerator gen_fractale_julia() {
        Interval colorRange = new Interval(intervalle_1, intervalle_2);
        FractalGenerator gen;
        FractalBuilder fractalBuilder = new FractalBuilder(zoom, size).minBrightness(minBrightness).colorRange(colorRange).horoffset(horoffset).veroffset(veroffset);
        gen = new JuliaSet(
                fractalBuilder,
                (Complex z) -> complexe.add(z.pow(2)),
                complexe
        );
        return gen.fill();
    }

    public FractalGenerator gen_fractale_mandelbrot() {
        Interval colorRange = new Interval(intervalle_1, intervalle_2);
        FractalGenerator gen;
        FractalBuilder fractalBuilder = new FractalBuilder(zoom, size).minBrightness(minBrightness).colorRange(colorRange).horoffset(horoffset).veroffset(veroffset);
        gen = new MandelbrotSet(
                fractalBuilder,
                (Complex z, Complex comp) -> comp.add(z.pow(2))
        );
        return gen.fill();
    }

    public boolean get_is_correct() {
        return is_correct;
    }
    public void set_is_correct(boolean b) {
        is_correct = b;
    }
}

