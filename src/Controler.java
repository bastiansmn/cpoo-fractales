import fractals.FractalBuilder;
import fractals.FractalGenerator;
import fractals.JuliaSet;
import fractals.MandelbrotSet;
import utils.*;
import utils.complex.Complex;
import java.awt.*;

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

    public void setIntervalle1(Color color) {
        float[] comp = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), comp);
        comp[0] *= 360;
        intervalle_1 = (int) comp[0];
    }

    public void setIntervalle2(Color color) {
        float[] comp = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), comp);
        comp[0] *= 360;
        intervalle_2 = (int) comp[0];
    }

    public void setComplexe(String s) {
        try {
            complexe = Complex.parse(s);
        } catch (Exception e) {
            is_correct = false;
        }
    }

    public void setZoom(String s) {
        try {
            zoom = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void setSize(String s) {
        try {
            size = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void setLuminosity(String s) {
        try {
            minBrightness = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void setHorizontal(String s){
        try {
            horoffset = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public void setVertical(String s){
        try {
            veroffset = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            is_correct = false;
        }
    }

    public FractalGenerator genFractaleJulia() {
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

    public FractalGenerator genFractaleMandelbrot() {
        Interval colorRange = new Interval(intervalle_1, intervalle_2);
        FractalGenerator gen;
        FractalBuilder fractalBuilder = new FractalBuilder(zoom, size).minBrightness(minBrightness).colorRange(colorRange).horoffset(horoffset).veroffset(veroffset);
        gen = new MandelbrotSet(
                fractalBuilder,
                (Complex z, Complex comp) -> comp.add(z.pow(2))
        );
        return gen.fill();
    }

    public boolean getIsCorrect() {
        return is_correct;
    }
    public void setIsCorrect(boolean b) {
        is_correct = b;
    }
}

