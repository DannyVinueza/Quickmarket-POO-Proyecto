import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ventas extends Administrador{
    private JPanel contentPane;
    private JButton generalButton;

    private JButton regresarL;
    private JButton usuarioButton;

    public Ventas() {
        super(2);
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setIconImage(img);
        setTitle("Ventas Realizadas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        pack();
        setVisible(true);

        regresarL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Administrador login = new Administrador(2);
            }
        });

        generalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                VR_General general = new VR_General();
            }
        });

        usuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                VR_Usuario usuario = new VR_Usuario();
            }
        });

    }
}
