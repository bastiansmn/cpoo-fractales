import java.awt.*;
import javax.swing.*;

public class Fenetre extends JFrame {
    Controler c;
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

        JPanel boutons = new JPanel(new GridLayout(15, 1));
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
            JColorChooser color_chose = new JColorChooser(Color.RED);
            Color color1 = color_chose.showDialog(this,"Choix couleur 1", Color.red);
            c.set_intervalle_1(color1);
        });
       color.add(color_1);

        JButton color_2 = new JButton();
        color_2.setText("Choix couleur 2");
        color_2.setFont(new Font ("Arial", Font.BOLD, 14));
        color_2.addActionListener(event -> {
            JColorChooser color_chose = new JColorChooser(Color.RED);
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
        genere.addActionListener(event -> {
            String complexe = text_complex.getText();
            c.set_complexe(complexe);
            String zoom = text_zoom.getText();
            c.set_zoom(zoom);
            String size = text_size.getText();
            c.set_size(size);
            String luminosity = text_luminosity.getText();
            c.set_luminosity(luminosity);
            if (c.get_is_correct()){
                if(is_mandelbrot.isSelected()){

                }

            }else {
                JLabel error = new JLabel();
                error.setText("Un des champs n'est pas du bon type");
                error.setFont(new Font ("Arial", Font.BOLD, 20));
                error.setForeground(Color.RED);
                boutons.add(error);
                repaint();
                setVisible(true);
            }
        });
    }


    public void fractale() {
        JPanel generation_fractale = new JPanel();
        getContentPane().add(generation_fractale);

    }

}
