import fractals.FractalGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

public class Fenetre extends JFrame {
    Controler c;
    BufferedImage fractale;
    public Fenetre (Controler control) {
        c = control;
        init();
    }


    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Fractales");

        JPanel panel_fond = new JPanel(new GridLayout(1, 2));
        setContentPane(panel_fond);

        JPanel boutons = new JPanel(new GridLayout(17, 1));
        getContentPane().add(boutons);

        JCheckBox is_mandelbrot= new JCheckBox("Mandelbrot");
        boutons.add(is_mandelbrot);

        JLabel text_complex = new JLabel();
        text_complex.setText("Nombre complexe ( si julia )");
        text_complex.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(text_complex);


        JTextField nb_complex = new JTextField();
        nb_complex.setColumns(10);
        boutons.add(nb_complex);


        JLabel text_zoom = new JLabel();
        text_zoom.setText("Zoom");
        text_zoom.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(text_zoom);

        JTextField nb_zoom = new JTextField();
        nb_zoom.setColumns(10);
        boutons.add(nb_zoom);


        JLabel text_size = new JLabel();
        text_size.setText("Taille du fichier en cas de sauvegarde");
        text_size.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(text_size);

        JTextField nb_size = new JTextField();
        nb_size.setColumns(10);
        boutons.add(nb_size);

        JLabel text_luminosity = new JLabel();
        text_luminosity.setText("Luminosité minimale");
        text_luminosity.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(text_luminosity);


        JTextField nb_luminosity = new JTextField();
        nb_luminosity.setColumns(10);
        boutons.add(nb_luminosity);

        JLabel space = new JLabel();
        boutons.add(space);

        JPanel color = new JPanel(new GridLayout(1,2));
        boutons.add(color);

        JButton  color_1 = new JButton();
        color_1.setText("Choix couleur 1");
        color_1.setFont(new Font ("Arial", Font.BOLD, 14));
        color_1.addActionListener(event -> {
            JColorChooser color_chose = new JColorChooser();
            Color color1 = color_chose.showDialog(this,"Choix couleur 1", Color.red);
            c.set_intervalle_1(color1);
        });
        color.add(color_1);

        JButton color_2 = new JButton();
        color_2.setText("Choix couleur 2");
        color_2.setFont(new Font ("Arial", Font.BOLD, 14));
        color_2.addActionListener(event -> {
            JColorChooser color_chose = new JColorChooser();
            Color color2 = color_chose.showDialog(this,"Choix couleur 2", Color.red);
            c.set_intervalle_2(color2);
        });
        color.add(color_2);

        JLabel space2 = new JLabel();
        boutons.add(space2);

        JButton genere = new JButton();
        genere.setText("GENERER LA FRACTALE");
        genere.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(genere);

        JLabel space3 = new JLabel();
        boutons.add(space3);


        JButton save_image = new JButton();
        save_image.setText("SAUVEGARDER");
        save_image.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(save_image);
        save_image.setVisible(false);
        save_image.addActionListener(event -> {
            if ((is_mandelbrot.isSelected())) {
                try {
                    FractalGenerator gen = c.gen_fractale_mandelbrot();
                    gen.fill().save("gen/fractal.png", false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    FractalGenerator gen = c.gen_fractale_julia();
                    gen.fill().save("gen/fractal.png", false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JLabel error = new JLabel();
        PanelFractale generation_fractale = new PanelFractale();
        getContentPane().add(generation_fractale);

        genere.addActionListener(event -> {
            error.setVisible(false);
            c.set_is_correct(true);
            if(!(is_mandelbrot.isSelected())) {
                String complexe = nb_complex.getText();
                c.set_complexe(complexe);
            }
            String zoom = nb_zoom.getText();
            c.set_zoom(zoom);
            String size = nb_size.getText();
            c.set_size(size);
            String luminosity = nb_luminosity.getText();
            c.set_luminosity(luminosity);
            if (c.get_is_correct()) {
                if (is_mandelbrot.isSelected()) {
                    fractale = c.gen_fractale_mandelbrot().getCanvas();
                    generation_fractale.updateImage(fractale);
                    save_image.setVisible(true);
                } else {
                    fractale = c.gen_fractale_julia().getCanvas();
                    generation_fractale.updateImage(fractale);
                    save_image.setVisible(true);
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

    public class PanelFractale extends JPanel {

        private BufferedImage image;

        public PanelFractale() {
            setLayout(new BorderLayout());
        }

        public void updateImage(BufferedImage im){
            image = im;
            updateUI();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
    }

}
