import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JButton eliminar;
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
                            throw new SQLException("Ingresa bien los datos");
                        }else{
                            if(rs.next()){
                                textFieldNom_Cli.setText(rs.getString("Nombre"));
                                textFieldDirec_Cli.setText(rs.getString("Direccion"));
                                textFieldTlf_Cli.setText(rs.getString("Telefono"));
                                textFieldCorreo_Cli.setText(rs.getString("correo_elec"));
                            }else{
                                System.out.println("Error no funciona");
                                Mensajelabel.setText("No haz ingresado un cedula inexistente");
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

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();
                try {
                    con = conBD.conectar();



                    con.close();
                }catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();
                try {
                    con = conBD.conectar();



                    con.close();
                }catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });
        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();
                try {
                    con = conBD.conectar();




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
