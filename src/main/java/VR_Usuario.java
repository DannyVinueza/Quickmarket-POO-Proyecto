import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VR_Usuario extends Ventas{
    private JPanel contentPane;
    private JTable table1;
    private JTextField textField1;
    private JLabel mensajeJ;
    private JButton regresarL;
    private JButton buscar;

    public VR_Usuario() {
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setIconImage(img);
        setTitle("Ventas Realizadas - Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        pack();
        setVisible(true);

        regresarL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Ventas ventas = new Ventas();
            }
        });
    }
}
