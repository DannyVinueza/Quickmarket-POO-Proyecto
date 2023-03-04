import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    private JTextField cantidadTF;
    private JButton limpiar;

    private Connection con;

    public Cajero(int ind){

        cantidadTF.setEnabled(false);
        cantidadSPN.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cantidadTF.setText(cantidadSPN.getValue().toString());
            }
        });
        //Llamada a la pantalla de cajero
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

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();

                try{
                    con = conBD.conectar();
                    String buscar = "SELECT * FROM productos WHERE nombre = '" + idprodTXT.getText() + "'";
                    Statement stBuscar = con.createStatement();
                    ResultSet reBuscar = stBuscar.executeQuery(buscar);
                    if (reBuscar.next()){
                        if(idprodTXT.getText().equals(reBuscar.getString(2))){
                            NOMProdTXT.setText(reBuscar.getString(3));
                            precioVenTXT.setText(reBuscar.getString(4));
                            cantidadSPN.setValue(reBuscar.getInt(5));
                        }
                    }else{
                        mensajeTXT.setText("No se encuentra el producto!!!!");
                    }
                    con.close();
                }catch (SQLException es){
                    System.out.println("Se presento un error" + es.getMessage());
                }
            }
        });

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();

            }
        });
    }

    public void limpiar(){
        Object ob = new Object();
        ob = 0;
        idprodTXT.setText("");
        NOMProdTXT.setText("");
        precioVenTXT.setText("");
        cantidadTF.setText("");
        cantidadSPN.setValue(ob);
    }
}
