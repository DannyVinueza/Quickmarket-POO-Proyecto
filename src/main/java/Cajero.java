import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cajero extends Login{
    public JPanel cajero_panel;
    private JButton regresarL;
    private JPanel panel1;
    private JButton buscar;
    private JSpinner cantidadSPN;
    private JButton agregar;
    private JButton eliminar;
    private JTable table1;
    private JTable table2;
    private JTextField idprodTXT;
    private JLabel mensajeTXT;
    private JButton clienteButton;
    private JButton facturarButton;
    private JTextField NOMProdTXT;
    private JTextField precioVenTXT;

    public Cajero(int ind){
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cajero");
        setIconImage(img);
        setLocationRelativeTo(null);
        setContentPane(cajero_panel);
        pack();
        setVisible(true);
        regresarL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (ind == 2) {
                    Login login = new Login();
                } else if(ind == 1) {
                    Administrador administrador = new Administrador (ind);
                }
            }
        });

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Clientes cliente = new Clientes(ind);
            }
        });


    }
}
