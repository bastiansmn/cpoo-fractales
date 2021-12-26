import fractals.FractalGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

public class Fenetre extends JFrame {
    Controler c;
    BufferedImage fractale;
    FractalGenerator gen;
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

        JPanel boutons = new JPanel(new GridLayout(18, 1));
        getContentPane().add(boutons);

        JCheckBox is_mandelbrot= new JCheckBox("Mandelbrot");
        boutons.add(is_mandelbrot);

        JLabel defaut_complex = new JLabel();
        defaut_complex.setText("Nombres complexe de référence");
        defaut_complex.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(defaut_complex);

        String[] valeurs = {"0.3+i0.5","0.285+i0.01","0.38088+i0.9754633","0.285+i0.013","-1.476+i0.0",
                "-0.7269+i0.1889","-1.417022285618+i0.0" , "0.8+i0.156", "0.4+i0.6"};
        JList defaut = new JList(valeurs);
        defaut.setVisibleRowCount(2);
        defaut.setSelectionMode(0);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(defaut);
        defaut.setLayoutOrientation(JList.VERTICAL);
        boutons.add(scrollPane);

        JLabel text_complex = new JLabel();
        text_complex.setText("Nombre complexe personnalisé ( si julia )");
        text_complex.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(text_complex);


        JTextField nb_complex = new JTextField();
        nb_complex.setColumns(10);
        boutons.add(nb_complex);

        JPanel param_zoom = new JPanel(new GridLayout(2, 3));
        boutons.add(param_zoom);

        JLabel text_zoom = new JLabel();
        text_zoom.setText("Zoom");
        text_zoom.setFont(new Font ("Arial", Font.BOLD, 14));
        param_zoom.add(text_zoom);

        JLabel param_horizontal= new JLabel();
        param_horizontal.setText("Deplacement Horizontal");
        param_horizontal.setFont(new Font ("Arial", Font.BOLD, 9));
        param_zoom.add(param_horizontal);

        JLabel param_vertical = new JLabel();
        param_vertical.setText("Deplacement Vertical");
        param_vertical.setFont(new Font ("Arial", Font.BOLD, 9));
        param_zoom.add(param_vertical);

        JTextField nb_zoom = new JTextField("2.5");
        param_zoom.add(nb_zoom);

        JTextField deplacement_horizontal = new JTextField("0.0");
        param_zoom.add(deplacement_horizontal);

        JTextField deplacement_vertical = new JTextField("0.0");
        param_zoom.add(deplacement_vertical);


        JLabel text_size = new JLabel();
        text_size.setText("Taille du fichier en cas de sauvegarde");
        text_size.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(text_size);

        JTextField nb_size = new JTextField("500");
        nb_size.setColumns(10);
        boutons.add(nb_size);

        JLabel text_luminosity = new JLabel();
        text_luminosity.setText("Luminosité minimale");
        text_luminosity.setFont(new Font ("Arial", Font.BOLD, 14));
        boutons.add(text_luminosity);


        JTextField nb_luminosity = new JTextField("0.3");
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
                    gen.fill().save("gen/fractal.png", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    gen.fill().save("gen/fractal.png", true);
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
                String complexe="";
                if(nb_complex.getText().equals("")){
                    complexe = valeurs[defaut.getSelectedIndex()];
                }else{
                    complexe = nb_complex.getText();
                }
                c.set_complexe(complexe);
            }
            String zoom = nb_zoom.getText();
            c.set_zoom(zoom);
            String size = nb_size.getText();
            c.set_size(size);
            String luminosity = nb_luminosity.getText();
            c.set_luminosity(luminosity);
            String horizontal = deplacement_horizontal.getText();
            c.set_horizontal(horizontal);
            String vertical = deplacement_vertical.getText();
            c.set_vertical(vertical);
            if (c.get_is_correct()) {
                if (is_mandelbrot.isSelected()) {
                    gen = c.gen_fractale_mandelbrot();
                    fractale = gen.getCanvas();
                    generation_fractale.updateImage(fractale);
                    save_image.setVisible(true);
                } else {
                    gen = c.gen_fractale_julia();
                    fractale = gen.getCanvas();
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
