import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bodega extends Administrador{
    private JTextField ingresaElProductoTextField;
    private JTextField detallesTextField;
    private JTextField precioTextField;
    private JSpinner spinner1;
    private JTable table1;
    private JButton regresarL;
    private JButton buscar;
    private JButton agregar;
    private JButton eliminar;
    private JPanel bodega_content;
    private JButton modificar;

    public Bodega(){
        super(2);

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
                Administrador login = new Administrador(2);
            }
        });

    }
}
