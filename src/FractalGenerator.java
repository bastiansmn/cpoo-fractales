import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Random;

public class FractalGenerator {

    private final int MAX_ITER = 1000;
    private final int RADIUS = 2;

    // Le canvas dans lequel on déssine l'image
    private final BufferedImage canvas;
    // L'image est un carré, donc la longueur
    private final int size;
    // La longueur du carré de dessin pour le code (ici [-1;1];[-1;1]
    private final int FRAMESIZE = 2;

    // La function polynomiale
    private final ComplexFunction function;

    public FractalGenerator(ComplexFunction function, int size) {
        this.function = function;
        this.canvas = new BufferedImage(size, size, BufferedImage.TYPE_INT_BGR);
        this.size = size;
    }

    // Calcul de l'index de divergence dans l'énoncé
    private int divergenceIndex(Complex z0, int i, int j) {
        int ite = 0;
        Complex zn = z0;
        while (ite < MAX_ITER-1 && zn.mod() <= RADIUS) {
            zn = function.apply(zn);
            ite++;
        }
        return ite;
    }

    // Remplie le canvas
    // renvoie this, pour permettre les appels enchaîne
    // (ex : gen.fill().render()
    // On pourrait donc imaginer après :
    // gen.fill().addAntialiasing().render()
    public FractalGenerator fill() {
        double h = (double) FRAMESIZE / (this.size);
        int startX =  -(FRAMESIZE / 2);
        int startY =  -(FRAMESIZE / 2);
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int ind = divergenceIndex(
                        Complex.of(startX + j*h, startY + i*h), // = z
                        i,
                        j
                );
                // If f(z) diverge
                if (ind == MAX_ITER - 1) {
                    int color = new Color(255, 255, 255).getRGB();
                    canvas.setRGB(j, i, color);
                } else {
                    int color = new Color(255 - (255 / ind), 255 - (255 / ind), 255 - (255 / ind)).getRGB();
                    canvas.setRGB(j, i, color);
                }
            }
        }
        return this;
    }

    // Ne renvoie pas this car fin de traitement.
    // Se contente de créer le fichier avec le canvas.
    public void render(String filename) throws IOException {
        File f = new File(filename);
        ImageIO.write(canvas, "PNG", f);
    }

    public BufferedImage getCanvas() {
        return canvas;
    }
}
