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

        CLIArgsParser cliArgsParser = new CLIArgsParser(List.of(
                new CLIArgsParser.Option("-c", "--complex", true),
                new CLIArgsParser.Option("-s", "--size", 750),
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

        Complex c = (Complex) options.get("-c").getValue();
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
            if (type.equals("mandelbrot")) {
                gen = new MandelbrotSet(
                        (Complex z, Complex comp) -> comp.add(z.pow(2)),
                        framesize,
                        size,
                        colorRange
                );
            } else {
                gen = new JuliaSet(
                        (Complex z) -> c.add(z.pow(2)),
                        c,
                        framesize,
                        size,
                        colorRange,
                        minBrightness
                );
            }
        } else {
            Properties properties = new Properties();
            try {
                FileReader fileReader = new FileReader(propsFilename);
                properties.load(fileReader);
                fileReader.close();
                Complex complex = Complex.parse(properties.getProperty(
                    "complex",
                    "0.285+0.5i"
                ));
                if (properties.getProperty("type", "").equals("mandelbrot")) {
                    gen = new MandelbrotSet(
                            (Complex z, Complex comp) -> comp.add(z.pow(2)),
                            properties
                    );
                } else {
                    gen = new JuliaSet(
                            (Complex z) -> complex.add(z.pow(2)),
                            complex,
                            properties
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
                gen = new JuliaSet(
                        (Complex z) -> c.add(z.pow(2)),
                        c,
                        framesize,
                        size,
                        colorRange,
                        minBrightness
                );
            }
        }

        gen.setHoroffset(horoffset);
        gen.setVeroffset(veroffset);

        try {
            gen.fill().render("gen/fractal.png");
            if (openFile) {
                File file = new File("gen/fractal.png");
                if(!Desktop.isDesktopSupported()) {
                    System.out.println("not supported");
                    return;
                }
                Desktop desktop = Desktop.getDesktop();
                if(file.exists())
                    desktop.open(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}