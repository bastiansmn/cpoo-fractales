package fractals;

import utils.Complex;
import utils.ComplexFunction;
import utils.ComplexTriFunction;
import utils.Interval;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class FractalGenerator {

    protected final int MAX_ITER = 1000;
    protected final int RADIUS = 2;

    // Le canvas dans lequel on dessine l'image
    private final BufferedImage canvas;
    // La longueur du carré de dessin pour le code (ici [-1;1];[-1;1])
    // Ce paramètre gère le zoom
    private final double framesize;
    // L'image est un carré, donc la longueur
    private final int size;
    // Le décalage horizontal
    private double horoffset = 0;
    // Le décalage vertical
    private double veroffset = 0;
    // La range de couleur de l'image :
    private Interval colorRange;


    public FractalGenerator(double framesize, int size, Interval colorRange) {
        this.framesize = framesize;
        this.size = size;
        this.colorRange = colorRange;
        this.canvas = new BufferedImage(size, size, BufferedImage.TYPE_INT_BGR);
    }


    // Calcul de l'index de divergence dans l'énoncé
    protected abstract int divergenceIndex(Complex z0);

    // Remplie le canvas
    // renvoie this, pour permettre les appels enchaîne
    // (ex : gen.fill().render()
    // On pourrait donc imaginer après :
    // gen.fill().addAntialiasing().render()
    public FractalGenerator fill() {
        double h = framesize / (this.size);
        double startX =  -(framesize / 2) + horoffset;
        double startY =  -(framesize / 2) - veroffset;
        int max = -1;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int ind = divergenceIndex(
                        Complex.of(startX + j*h, startY + i*h)
                );
                // If f(z) diverge
                if (ind == MAX_ITER - 1) {
                    canvas.setRGB(j, i, 0);
                } else {
                    if (ind > max) max = ind;
                    // TODO : Plus ça s'éloigne, plus c'est sombre
                    Color color = Color.getHSBColor(
                            this.computeHue(ind),
                            1.0f,
                            1.0f
                    );
                    canvas.setRGB(j, i, color.getRGB());
                }
            }
        }
        return this;
    }

    private float computeBrightness(int ind) {
        float min1 = 1;
        float max1 = (float) MAX_ITER;

        float min2 = 0.6f;
        float max2 = 1f;

        float v = min2 + ((max2 - min2) / (max1 - min1)) * (ind - min1);

        return v;
    }

    private float computeHue(int ind) {
        float min1 = 1;
        // TODO : ind dépasse parfois max1 donc la range n'est pas bonne
        // 1) On calcule maxind avant de computeHue (lourd)
        // ou 2) On trouve un moyen de projeter logarithmiquement les valeurs de ind entre 0 et 1
        float max1 = (float) MAX_ITER / 10;

        float min2 = (float) this.colorRange.getMin() / 360;
        float max2 = (float) this.colorRange.getMax() / 360;

        float v = (float) (min2 + ((max2 - min2) / (max1 - min1)) * (ind - min1));

        return v;
    }

    public FractalGenerator antiAliasing() {
        // TODO
        return this;
    }

    // Ne renvoie pas this car fin de traitement.
    // Se contente de créer le fichier avec le canvas.
    public void render(String filename) throws IOException {
        File f = new File(filename);
        ImageIO.write(canvas, "PNG", f);
    }

    // GETTERS AND SETTERS
    public BufferedImage getCanvas() {
        return canvas;
    }

    public double getFramesize() {
        return framesize;
    }

    public double getHoroffset() {
        return horoffset;
    }

    public double getVeroffset() {
        return veroffset;
    }

    public int getMAX_ITER() {
        return MAX_ITER;
    }

    public int getRADIUS() {
        return RADIUS;
    }

    public int getSize() {
        return size;
    }

    public void setHoroffset(double horoffset) {
        this.horoffset = horoffset;
    }

    public void setVeroffset(double veroffset) {
        this.veroffset = veroffset;
    }
}
