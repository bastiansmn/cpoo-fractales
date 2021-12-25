import utils.*;
import utils.complex.Complex;


import java.awt.*;

public class Controler {
    private static Fenetre affichage;
    private int intervalle_1;
    private int intervalle_2;
    private Complex complexe;
    private int zoom;
    private int framesize;
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

}
