import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Clientes extends Login {
    PreparedStatement ps;
    private JPanel clientes1;
    private JTextField textFieldNom_Cli;
    private JTextField textFieldCorreo_Cli;
    private JTextField textFieldTlf_Cli;
    private JTextField textFieldDirec_Cli;
    private JTextField cedula;
    private JLabel Mensajelabel;
    private JPanel clientes2;
    private JButton buscar;
    private JButton agregar;
    private JButton regresarL;
    private JButton modificar;
    private JButton aceptar;
    private Connection con;

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
                    ps = con.prepareStatement("SELECT * FROM clientes WHERE id_cliente = ?" );
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
                                textFieldCorreo_Cli.setText(rs.getString("correo_elec"));
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
                    ps = con.prepareStatement("UPDATE clientes SET nombre = ?, direccion = ?, telefono = ?, correo_elec = ? WHERE id_cliente ="+cedula.getText() );
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
                    ps = con.prepareStatement("INSERT INTO clientes( id_cliente, nombre, direccion , telefono , correo_elec ) values (?,?,?,?,?) ");
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
                Cajero cajero = new Cajero(ind);

            }
        });

    }




}
