import fractals.FractalGenerator;
import fractals.JuliaSet;
import fractals.MandelbrotSet;
import utils.*;
import utils.complex.Complex;


import java.awt.*;

public class Controler {
    private static Fenetre affichage;
    private int intervalle_1;
    private int intervalle_2;
    private Complex complexe;
    private int zoom;
    private double framesize;
    private double minBrightness;
    private boolean is_correct;
    Controler(){}
    public void lancement() {
        affichage = new Fenetre(new Controler());
        affichage.init();
        affichage.setVisible(true);
    }

    public void set_intervalle_1 (Color color){
        float[] comp = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), comp);
        comp[0]*= 360;
        intervalle_1 =(int) comp[0];
    }

    public void set_intervalle_2 (Color color){
        float[] comp = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), comp);
        comp[0]*= 360;
        intervalle_2 =(int) comp[0];
    }

    public void set_complexe (String s){
            complexe = Complex.parse(s);
    }

    public void set_zoom (String s){
        try {
            zoom = Integer.parseInt(s);
        }catch (Exception e){
            is_correct = false;
        }
    }

    public void set_size (String s){
        try {
            framesize = Double.parseDouble(s);
        }catch (Exception e){
            is_correct = false;
        }
    }

    public void set_luminosity (String s){
        try {
            minBrightness = Double.parseDouble(s);
        }catch (Exception e){
            is_correct = false;
        }
    }

    public FractalGenerator gen_fractale_julia(){
        Interval colorRange = new Interval(intervalle_1, intervalle_2);
        FractalGenerator gen;
        gen = new JuliaSet(
                (Complex z) -> complexe.add(z.pow(2)),
                complexe,
                framesize,
                zoom,
                colorRange,
                minBrightness
        );
        return gen;
    }

    public  FractalGenerator gen_fractale_mandelbrot(){
        Interval colorRange = new Interval(intervalle_1, intervalle_2);
        FractalGenerator gen;
        gen = new MandelbrotSet(
                (Complex z, Complex comp) -> comp.add(z.pow(2)),
                framesize,
                zoom,
                colorRange
        );
        return gen;
    }

    public boolean get_is_correct() {
        return is_correct;
    }
}
