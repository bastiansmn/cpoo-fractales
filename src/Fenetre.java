import fractals.FractalGenerator;
import utils.complex.Complex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.*;

public class Fenetre extends JFrame {
    private final Controler c;
    private BufferedImage fractale;
    private FractalGenerator gen;

    public Fenetre (Controler control) {
        c = control;
        init();
    }


    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Fractales");

        JPanel panelFond = new JPanel(new GridLayout(1, 2));
        setContentPane(panelFond);

        JPanel boutons = new JPanel(new GridLayout(18, 1));
        getContentPane().add(boutons);

        JCheckBox isMandelbrot= new JCheckBox("Mandelbrot");
        boutons.add(isMandelbrot);

        JLabel defaultComplex = new JLabel();
        defaultComplex.setText("Nombres complexe de référence");
        defaultComplex.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(defaultComplex);

        String[] valeurs = Arrays.stream(Fractale.cval).map(Complex::toString).toArray(String[]::new);
        JList<String> defaut = new JList<>(valeurs);
        defaut.setVisibleRowCount(2);
        defaut.setSelectionMode(0);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(defaut);
        defaut.setLayoutOrientation(JList.VERTICAL);
        boutons.add(scrollPane);

        JLabel textComplex = new JLabel();
        textComplex.setText("Nombre complexe personnalisé ( si julia )");
        textComplex.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(textComplex);


        JTextField nbComplex = new JTextField();
        nbComplex.setColumns(10);
        boutons.add(nbComplex);

        JPanel paramZoom = new JPanel(new GridLayout(2, 3));
        boutons.add(paramZoom);

        JLabel textZoom = new JLabel();
        textZoom.setText("Zoom");
        textZoom.setFont(new Font ("Arial", Font.BOLD, 14));
        paramZoom.add(textZoom);

        JLabel paramHorizontal= new JLabel();
        paramHorizontal.setText("Deplacement Horizontal");
        paramHorizontal.setFont(new Font ("Arial", Font.BOLD, 9));
        paramZoom.add(paramHorizontal);

        JLabel paramVertical = new JLabel();
        paramVertical.setText("Deplacement Vertical");
        paramVertical.setFont(new Font ("Arial", Font.BOLD, 9));
        paramZoom.add(paramVertical);

        JTextField nbZoom = new JTextField("2.5");
        paramZoom.add(nbZoom);

        JTextField deplacementHorizontal = new JTextField("0.0");
        paramZoom.add(deplacementHorizontal);

        JTextField deplacementVertical = new JTextField("0.0");
        paramZoom.add(deplacementVertical);


        JLabel textSize = new JLabel();
        textSize.setText("Taille du fichier en cas de sauvegarde");
        textSize.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(textSize);

        JTextField nbSize = new JTextField("500");
        nbSize.setColumns(10);
        boutons.add(nbSize);

        JLabel textLuminosity = new JLabel();
        textLuminosity.setText("Luminosité minimale");
        textLuminosity.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(textLuminosity);


        JTextField nbLuminosity = new JTextField("0.3");
        nbLuminosity.setColumns(10);
        boutons.add(nbLuminosity);

        JLabel space = new JLabel();
        boutons.add(space);

        JPanel color = new JPanel(new GridLayout(1,2));
        boutons.add(color);

        JButton  color1 = new JButton();
        color1.setText("Choix couleur 1");
        color1.setFont(new Font ("Arial", Font.BOLD, 14));
        color1.addActionListener(event -> {
            Color _color1 = JColorChooser.showDialog(this,"Choix couleur 1", Color.red);
            if(color1 != null) {
                c.setIntervalle1(_color1);
            }
        });
        color.add(color1);

        JButton color2 = new JButton();
        color2.setText("Choix couleur 2");
        color2.setFont(new Font ("Arial", Font.BOLD, 14));
        color2.addActionListener(event -> {
            Color _color2 = JColorChooser.showDialog(this,"Choix couleur 2", Color.green);
            if(color2 != null) {
                c.setIntervalle2(_color2);
            }
        });
        color.add(color2);

        JLabel space2 = new JLabel();
        boutons.add(space2);

        JButton genere = new JButton();
        genere.setText("GENERER LA FRACTALE");
        genere.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(genere);

        JLabel space3 = new JLabel();
        boutons.add(space3);


        JButton saveImage = new JButton();
        saveImage.setText("SAUVEGARDER");
        saveImage.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(saveImage);
        saveImage.setVisible(false);
        saveImage.addActionListener(event -> {
            try {
                gen.save("gen/fractal.png", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        JLabel error = new JLabel();
        PanelFractale generationFractale = new PanelFractale();
        getContentPane().add(generationFractale);

        genere.addActionListener(event -> {
            error.setVisible(false);
            c.setIsCorrect(true);
            if(!(isMandelbrot.isSelected())) {
                String complexe;
                if(nbComplex.getText().equals("") && !(defaut.getSelectedIndex() == -1) ){
                    complexe = valeurs[defaut.getSelectedIndex()];
                }else{
                    complexe = nbComplex.getText();
                }
                c.setComplexe(complexe);
            }
            String zoom = nbZoom.getText();
            c.setZoom(zoom);
            String size = nbSize.getText();
            c.setSize(size);
            String luminosity = nbLuminosity.getText();
            c.setLuminosity(luminosity);
            String horizontal = deplacementHorizontal.getText();
            c.setHorizontal(horizontal);
            String vertical = deplacementVertical.getText();
            c.setVertical(vertical);
            if (c.getIsCorrect()) {
                if (isMandelbrot.isSelected()) {
                    gen = c.genFractaleMandelbrot();
                    fractale = gen.getCanvas();
                    generationFractale.updateImage(fractale);
                    saveImage.setVisible(true);
                } else {
                    gen = c.genFractaleJulia();
                    fractale = gen.getCanvas();
                    generationFractale.updateImage(fractale);
                    saveImage.setVisible(true);
                }
            }else{
                error.setText("Un des champs n'est pas du bon type");
                error.setFont(new Font ("Arial", Font.BOLD, 16));
                error.setForeground(Color.RED);
                boutons.add(error);
                repaint();
                error.setVisible(true);
            }
        });
    }

    public static class PanelFractale extends JPanel {

        private BufferedImage image;

        public PanelFractale() {
            setLayout(new BorderLayout());
        }

        public void updateImage(BufferedImage im){
            this.image = im;
            updateUI();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

}
