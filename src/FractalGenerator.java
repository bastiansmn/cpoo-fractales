import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Random;

public class FractalGenerator {

    private final int MAX_ITER = 1000;
    private final int RADIUS = 2;

    private final BufferedImage canvas;
    private final int size;
    private final int FRAMESIZE = 2;

    private final ComplexFunction function;

    public FractalGenerator(ComplexFunction function, int size) {
        this.function = function;
        this.canvas = new BufferedImage(size, size, BufferedImage.TYPE_INT_BGR);
        this.size = size;
    }

    private int divergenceIndex(Complex z0) {
        int ite = 0;
        Complex zn = z0;
        while (ite < MAX_ITER-1 && zn.mod() <= RADIUS) {
            zn = function.apply(zn);
            ite++;
        }
        return ite;
    }

    public FractalGenerator fill() {
        double h = (double) FRAMESIZE / this.size;
        int startX =  -(FRAMESIZE / 2);
        int startY =  (FRAMESIZE / 2);
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                int ind = divergenceIndex(
                        Complex.of(startX + j*h, startY + i*h) // = z
                );
                // If f(z) diverge
                int color = new Random().nextInt(255);
                if (ind < MAX_ITER - 1) {
                    canvas.setRGB(i, j, color);
                } else {
                    canvas.setRGB(i, j, color);
                }
            }
        }
        return this;
    }

    public void render(String filename) throws IOException {
        File f = new File(filename);
        ImageIO.write(canvas, "PNG", f);
    }

    public BufferedImage getCanvas() {
        return canvas;
    }
}
