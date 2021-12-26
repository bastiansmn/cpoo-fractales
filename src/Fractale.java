import fractals.FractalBuilder;
import fractals.FractalGenerator;
import fractals.JuliaSet;
import fractals.MandelbrotSet;
import utils.*;
import utils.complex.Complex;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

public class Fractale {

    private final static Complex c1 = Complex.of(.3, .5);
    private final static Complex c2 = Complex.of(.285, .01);
    private final static Complex c3 = Complex.of(.038088, .9754633);
    private final static Complex c4 = Complex.of(.285, .013);
    private final static Complex c5 = Complex.of(-1.476, .0);
    private final static Complex c6 = Complex.of(-0.7269, 0.1889);
    private final static Complex c7 = Complex.of(-1.417022285618, 0.0099534);
    private final static Complex c8 = Complex.of(-.8, .156);
    private final static Complex c9 = Complex.of(-.4, .6);

    public static boolean openFile = true;

    public static void main(String[] args) {
//        Controler c = new Controler();
//        c.lancement();

        CLIArgsParser cliArgsParser = new CLIArgsParser(List.of(
            new CLIArgsParser.Option("-c", "--complex", true),
            new CLIArgsParser.Option("-s", "--size", 500),
            new CLIArgsParser.Option("-f", "--framesize", 2.5),
            new CLIArgsParser.Option("-b", "--minBrightness", 0.3),
            new CLIArgsParser.Option("-r", "--colorRange", new Interval(0, 360)),
            new CLIArgsParser.Option("-p", "--properties", ""),
            new CLIArgsParser.Option("-t", "--type", "julia"),
            new CLIArgsParser.Option("-v", "--veroffset", .0),
            new CLIArgsParser.Option("-h", "--horoffset", .0),
            new CLIArgsParser.Option("-o", "--open", "false")
        ));
        cliArgsParser.parse(args);
        LinkedHashMap<String, CLIArgsParser.Option> options = cliArgsParser.getProvidedOptions();

        Complex comp = (Complex) options.get("-c").getValue();
        int size = (int) options.get("-s").getValue();
        double framesize = (double) options.get("-f").getValue();
        double minBrightness = (double) options.get("-b").getValue();
        Interval colorRange = (Interval) options.get("-r").getValue();
        String propsFilename = (String) options.get("-p").getValue();
        String type = (String) options.get("-t").getValue();
        double horoffset = (double) options.get("-h").getValue();
        double veroffset = (double) options.get("-v").getValue();
        openFile = options.get("-o").getValue().equals("true");

        FractalGenerator gen;
        if (propsFilename.equals("")) {
            FractalBuilder fractalBuilder = new FractalBuilder(framesize, size).minBrightness(minBrightness).colorRange(colorRange).horoffset(horoffset).veroffset(veroffset);
            if (type.equals("mandelbrot")) {
                gen = new MandelbrotSet(
                        fractalBuilder,
                        (Complex z, Complex complex) -> complex.add(z.pow(2))
                );
            } else {
                gen = new JuliaSet(
                        fractalBuilder,
                        (Complex z) -> comp.add(z.pow(2)),
                        comp
                );
            }
        } else {
            Properties properties = new Properties();
            try {
                FileReader fileReader = new FileReader(propsFilename);
                properties.load(fileReader);
                fileReader.close();
                Complex complex = Complex.parse(properties.getProperty(
                    "complex"
                ));
                if (properties.getProperty("type", "").equals("mandelbrot")) {
                    gen = new MandelbrotSet(
                            properties,
                            (Complex z, Complex complex1) -> complex1.add(z.pow(2))
                    );
                } else {
                    gen = new JuliaSet(
                            properties,
                            (Complex z) -> complex.add(z.pow(2)),
                            complex
                    );
                }
            } catch (Exception e) {
                FractalBuilder fractalBuilder = new FractalBuilder(framesize, size).veroffset(veroffset).horoffset(horoffset).colorRange(colorRange).minBrightness(minBrightness);
                e.printStackTrace();
                gen = new JuliaSet(
                        fractalBuilder,
                        (Complex z) -> comp.add(z.pow(2)),
                        comp
                );
            }
        }

        try {
            gen.fill().save("gen/fractal.png", openFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}