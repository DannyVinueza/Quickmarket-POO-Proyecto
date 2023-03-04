import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bodega extends Login{
    private JTextField ingresaElProductoTextField;
    private JTextField detallesTextField;
    private JTextField precioTextField;
    private JSpinner spinner1;
    private JTable table1;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton regresarL;
    private JButton buscar;
    private JButton agregar;
    private JButton eliminar;
    private JPanel bodega_content;

    public Bodega(int ind){

        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setIconImage(img);
        setTitle("Bodega");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(bodega_content);
        pack();
        setVisible(true);

        regresarL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Administrador login = new Administrador(ind);
            }
        });

    }
}
