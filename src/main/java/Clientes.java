import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


public class Clientes extends Login {
    PreparedStatement ps;
    private JPanel clientes1;
    public JTextField textFieldNom_Cli;
    public JTextField textFieldCorreo_Cli;
    public JTextField textFieldTlf_Cli;
    public JTextField textFieldDirec_Cli;
    public JTextField cedula;
    private JLabel Mensajelabel;
    private JPanel clientes2;
    private JButton buscar;
    private JButton agregar;
    private JButton regresarL;
    private JButton modificar;
    private JButton aceptar;
    private Connection con;
    public static String clienteAgregado;
    public static boolean verC = false;

    public Clientes(int ind){
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Clientes");
        setIconImage(img);
        setLocationRelativeTo(null);
        setContentPane(clientes1);
        pack();
        setVisible(true);

        regresarL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Cajero cajero = new Cajero(ind);
            }
        });

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();
                ResultSet rs;
                try {
                    con = conBD.conectar();
                    ps = con.prepareStatement("SELECT * FROM clientes WHERE cedula = ?" );
                    ps.setString(1, cedula.getText());
                    rs = ps.executeQuery();
                    try{
                        if( !cedula.getText().matches("[0-9]*") ){
                            Mensajelabel.setText("Ingresa bien los datos");
                        }else{
                            if(rs.next()){
                                textFieldNom_Cli.setText(rs.getString("Nombre"));
                                textFieldDirec_Cli.setText(rs.getString("Direccion"));
                                textFieldTlf_Cli.setText(rs.getString("Telefono"));
                                textFieldCorreo_Cli.setText(rs.getString("correo"));
                                Mensajelabel.setText("Cliente si EXISTE");
                            }else{
                                Mensajelabel.setText("Cliente no EXISTE");
                                textFieldNom_Cli.setText("");
                                textFieldDirec_Cli.setText("");
                                textFieldTlf_Cli.setText("");
                                textFieldCorreo_Cli.setText("");
                            }
                        }
                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                    }
                    con.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();
                try {
                    con = conBD.conectar();
                    ps = con.prepareStatement("UPDATE clientes SET nombre = ?, direccion = ?, telefono = ?, correo = ? WHERE cedula ="+cedula.getText() );
                    try{

                        if( !cedula.getText().matches("[0-9]*") ){
                            throw new SQLException("Ingrese bien los datos");
                        }else{
                            ps.setString(1, textFieldNom_Cli.getText());
                            ps.setString(2, textFieldDirec_Cli.getText());
                            ps.setString(3, textFieldTlf_Cli.getText());
                            ps.setString(4, textFieldCorreo_Cli.getText());
                        }
                    }catch (SQLException es){
                        System.out.println("Error: " + es + "||||");
                        Mensajelabel.setText("Error al Actualizar los datos del Cliente");
                    }
                    System.out.println(ps);
                    int res = ps.executeUpdate();
                    if(res > 0){
                        Mensajelabel.setText("Se actualizo los datos del cliente");
                    }
                    con.close();
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();
                try {
                    con = conBD.conectar();
                    ps = con.prepareStatement("INSERT INTO clientes( cedula, nombre, direccion , telefono , correo ) values (?,?,?,?,?) ");
                    try{

                        if( !cedula.getText().matches("[0-9]*") ){
                            Mensajelabel.setText("Ingrese bien lo datos ");

                        }else{
                            ps.setString(1, cedula.getText());
                            ps.setString(2, textFieldNom_Cli.getText());
                            ps.setString(3, textFieldDirec_Cli.getText());
                            ps.setString(4, textFieldTlf_Cli.getText());
                            ps.setString(5, textFieldCorreo_Cli.getText());
                        }
                    }catch (SQLException es) {
                        System.out.println("Error: " + es + "||||");
                        Mensajelabel.setText("Error al Ingresar al nuevo Cliente");
                         }
                    int res = ps.executeUpdate();
                    if(res > 0){
                        Mensajelabel.setText(" Se ingreso al nuevo Cliente con EXITO");
                    }
                    con.close();
                }catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        aceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Obtener los datos de los JTextField
                String cedulaCliente = cedula.getText();
                String nombreCliente = textFieldNom_Cli.getText();
                String direccionCliente = textFieldDirec_Cli.getText();
                String telefonoCliente = textFieldTlf_Cli.getText();
                String correoCliente = textFieldCorreo_Cli.getText();

                // Agregar los datos a la tabla de clientes en la clase Cajero
                Cajero cajero = new Cajero(ind);
                JTable tablaClientes = cajero.getTablaClientes();
                DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
                Object[] rowData = {cedulaCliente, nombreCliente, direccionCliente, telefonoCliente, correoCliente};
                model.addRow(rowData);

                JTable tablaCajeroe = cajero.enviarJTable();
                cajero.actualizarTabla(tablaCajeroe, cajero.getListaProductos());
                //System.out.println("Esta es una prueba" + cajero.getListaProductos().toString());
                //Envio del cliente agregado a la compra a la clase cajero cedula

                Clientes.verC = true;
                Clientes.clienteAgregado = cedula.getText();


            }
        });


    }

    public static String obtenerClienteAgregado() {
        return clienteAgregado;
    }

    public static boolean verClienteAg() {
        return verC;
    }
}
