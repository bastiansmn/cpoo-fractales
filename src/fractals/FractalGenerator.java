package fractals;

import utils.complex.Complex;
import utils.Interval;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public abstract class FractalGenerator {

    protected final int MAX_ITER = 1000;
    protected final int RADIUS = 2;

    private final BufferedImage canvas;

    private final double framesize;
    private final int size;
    private double horoffset = 0;
    private double veroffset = 0;
    private final Interval colorRange;

    private int threadnum;
    private int THREADSQUARESIZE;

    private final double minBrightness;


    protected FractalGenerator(double framesize, int size, Interval colorRange, double minBrightness) {
        this.canvas = new BufferedImage(size, size, BufferedImage.TYPE_INT_BGR);
        this.framesize = framesize;
        this.size = size;
        this.colorRange = colorRange;
        this.minBrightness = minBrightness;
    }

    protected FractalGenerator(Properties properties) {
        this.size = Math.max(0, Integer.parseInt(properties.getProperty("size", "500")));
        this.canvas = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_BGR);
        this.framesize = Double.parseDouble(properties.getProperty("framesize", "2.5"));
        this.colorRange = new Interval(
            Math.min(360, Math.max(0, Integer.parseInt(properties.getProperty("minhue", "0")))),
            Math.min(360, Math.max(0, Integer.parseInt(properties.getProperty("maxhue", "360"))))
        );
        this.minBrightness = Math.min(1, Math.max(0, Double.parseDouble(properties.getProperty("minBrightness", "0.5"))));
        this.horoffset = Double.parseDouble(properties.getProperty("horoffset", "0"));
        this.veroffset = -Double.parseDouble(properties.getProperty("veroffset", "0"));
    }

    protected abstract int divergenceIndex(Complex z0);

    public FractalGenerator fill() {
        long startTime = System.currentTimeMillis();

        this.fillWithSquareThreads();
        System.out.println(this.getInformations());

        System.out.println("fill(): " + (System.currentTimeMillis() - startTime) + "ms");
        return this;
    }

    private void fillWithLinesThreads() {
        this.THREADSQUARESIZE = this.size / 10;
        this.threadnum = (this.size / THREADSQUARESIZE) + 1;

        LinkedList<Thread> threads = new LinkedList<>();
        for (int i = 0; i < this.threadnum; i++) {
            int finalI = i;
            Thread t = new Thread(() -> {
                for (int y = 0; y < THREADSQUARESIZE; y++) {
                    for (int x = 0; x < this.size; x++) {
                        int ii = finalI*THREADSQUARESIZE + y;
                        if (ii < this.size) {
                            this.fillFractalForPixel(x, ii);
                        }
                    }
                }
            });
            threads.add(t);
            t.start();
        }
        threads.forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void fillWithSquareThreads() {
        this.THREADSQUARESIZE = this.size / 2;
        this.threadnum = (this.size / THREADSQUARESIZE) + 1;

        LinkedList<Thread> threads = new LinkedList<>();
        for (int i = 0; i < this.threadnum; i++) {
            for (int j = 0; j < this.threadnum; j++) {
                int finalJ = j;
                int finalI = i;
                Thread t = new Thread(() -> {
                    for (int y = 0; y < THREADSQUARESIZE; y++) {
                        for (int x = 0; x < THREADSQUARESIZE; x++) {
                            int ii = finalI * THREADSQUARESIZE + y;
                            int jj = finalJ * THREADSQUARESIZE + x;
                            if (ii < this.size && jj < this.size) {
                                this.fillFractalForPixel(ii, jj);
                            }
                        }
                    }
                });
                threads.add(t);
                t.start();
            }
        }
        threads.forEach(e -> {
            try {
                e.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void fillWithoutThreads() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                fillFractalForPixel(i, j);
            }
        }
    }

    private void fillFractalForPixel(int i, int j) {
        double h = framesize / (this.size);
        double startX =  -(framesize / 2) + horoffset;
        double startY =  (framesize / 2) - veroffset;

        int ind = divergenceIndex(
                Complex.of(startX + j*h, startY + (-i)*h)
        );

        if (ind == MAX_ITER - 1) {
            canvas.setRGB(j, i, 0);
        } else {
            Color color = Color.getHSBColor(
                    this.computeHue(ind),
                    1.0f,
                    this.computeBrightness(ind)
            );
            canvas.setRGB(j, i, color.getRGB());
        }
    }


    private float computeBrightness(int ind) {
        return (float) (Math.min(.1*ind, 1 - this.minBrightness) + (this.minBrightness));
    }

    private float computeHue(int ind) {
        float min1 = 1;
        float max1 = (float) MAX_ITER / 10;

        float min2 = (float) this.colorRange.getMin() / 360;
        float max2 = (float) this.colorRange.getMax() / 360;

        double v = min2 + ((max2 - min2) / (max1 - min1)) * (ind - min1);

        return (float) v;
    }

    public void render(String filename) throws IOException {
        File f = new File(filename);
        ImageIO.write(canvas, "PNG", f);
    }

    protected abstract String getInformations();

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

    public double getMinBrightness() {
        return minBrightness;
    }

    public int getThreadnum() {
        return threadnum;
    }

    public int getTHREADSQUARESIZE() {
        return THREADSQUARESIZE;
    }

    public Interval getColorRange() {
        return colorRange;
    }

    public void setHoroffset(double horoffset) {
        this.horoffset = horoffset;
    }

    public void setVeroffset(double veroffset) {
        this.veroffset = veroffset;
    }
}
