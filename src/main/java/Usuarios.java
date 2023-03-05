import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuarios extends Administrador {
    private JPanel contentPane;
    private JTextField nombreTXT;
    private JTextField userTXT;
    private JTextField contraTXT;
    private JTable tableUsuarios;
    private JButton regresarL;
    private JButton buscar;
    private JButton eliminar;
    private JButton modificar;
    private JLabel mensajeJ;
    private JComboBox rolComboBox;
    private JButton agregar;

    public Usuarios() {
        super(2);
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setIconImage(img);
        setTitle("Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        pack();
        setVisible(true);

        limpiar();
        llenartabla();

        regresarL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Administrador login = new Administrador(2);
            }
        });
        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();

                try{
                    con = conBD.conectar();
                    String buscar = "SELECT * FROM usuarios WHERE usuario = '" + userTXT.getText() + "'";
                    Statement stBuscar = con.createStatement();
                    ResultSet reBuscar = stBuscar.executeQuery(buscar);
                    if (reBuscar.next()){
                        if(userTXT.getText().equals(reBuscar.getString(4))){
                            if(reBuscar.getString(2).equals("1")){
                                rolComboBox.setSelectedIndex(1);
                            } else {
                                rolComboBox.setSelectedIndex(2);
                            }
                            nombreTXT.setText(reBuscar.getString(3));
                            userTXT.setText(reBuscar.getString(4));
                            contraTXT.setText(reBuscar.getString(5));
                        }

                        mensajeJ.setText("¡Usuario Encontrado!");
                    }else{
                        mensajeJ.setText("¡No existe éste Usuario!");
                    }
                    con.close();
                }catch (SQLException es){
                    System.out.println("Se presento un error" + es.getMessage());
                }
            }
        });

        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();

                try {
                    con = conBD.conectar();
                    ps = con.prepareStatement("INSERT INTO usuarios (idroles, nombre_completo, usuario, contrasenia) VALUES (?, ?, ?, ?)");
                    ps.setString(1, String.valueOf(rolComboBox.getSelectedIndex()));
                    ps.setString(2, nombreTXT.getText());
                    ps.setString(3, userTXT.getText());
                    ps.setString(4, contraTXT.getText());
                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if (res > 0) {
                        mensajeJ.setText("¡UsuarioAgregado con éxito!");
                        llenartabla();
                    } else {
                        mensajeJ.setText("¡Error al agregar Usuario!");
                    }

                    limpiar();
                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        modificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar_usuario();
                Conexion conBD = new Conexion();

                try {
                    con = conBD.conectar();
                    ps = con.prepareStatement("UPDATE usuarios SET idroles=?, nombre_completo=?, usuario=?, contrasenia=? Where usuario = ?");

                    ps.setString(1, String.valueOf(rolComboBox.getSelectedIndex()));
                    ps.setString(2, nombreTXT.getText());
                    ps.setString(3, userTXT.getText());
                    ps.setString(4, contraTXT.getText());
                    ps.setString(5, userTXT.getText());


                    System.out.println(ps);

                    int res = ps.executeUpdate();

                    if (res > 0) {
                        mensajeJ.setText("¡Usuario modificado con éxito!");
                        llenartabla();

                    } else {
                        mensajeJ.setText("Error al Modificar Usuario, ingrese un nickname");
                    }

                    limpiar();
                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });

        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscar_usuario();
                Conexion conBD = new Conexion();

                try {
                    con = conBD.conectar();
                    ps = con.prepareStatement("DELETE FROM usuarios Where usuario = ?;");

                    ps.setString(1, userTXT.getText());

                    int res = ps.executeUpdate();

                    if (res > 0) {
                        mensajeJ.setText("¡Usuario Eliminado con éxito!");
                        llenartabla();
                    } else {
                        mensajeJ.setText("Error al Eliminar Usuario, ingrese un nickname válido");
                    }
                    limpiar();
                    con.close();

                } catch (HeadlessException | SQLException f) {
                    System.err.println(f);
                }
            }
        });
    }
    private void limpiar() {
        rolComboBox.setSelectedIndex(0);
        nombreTXT.setText(null);
        userTXT.setText(null);
        contraTXT.setText(null);
    }

    private void llenartabla() {
        DefaultTableModel modelo = mostrarProductos();

        tableUsuarios.setModel(modelo);
    }

    private void buscar_usuario(){
        Conexion conBD = new Conexion();

        try{
            con = conBD.conectar();
            String buscar = "SELECT * FROM usuarios WHERE usuario = '" + userTXT.getText() + "'";
            Statement stBuscar = con.createStatement();
            ResultSet reBuscar = stBuscar.executeQuery(buscar);

            if (reBuscar.next()){
                mensajeJ.setText("¡Usuario Encontrado!");
            }else{
                mensajeJ.setText("No se pudo realizar, ¡Usuario no encontrado!");
            }
            con.close();
        }catch (SQLException es){
            System.out.println("Se presento un error" + es.getMessage());
        }
    }

    private DefaultTableModel mostrarProductos()
    {
        String [] nombresColumnas = {"ID Usuario","Rol","Nombre Completo","Usuario","Contraseña"};
        String [] registros = new String[5];

        DefaultTableModel modelo = new DefaultTableModel(null,nombresColumnas);
        String sql = "SELECT * FROM usuarios";
        Conexion conBD = new Conexion();

        PreparedStatement pst = null;

        ResultSet rs = null;

        try {
            con = conBD.conectar();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while(rs.next())
            {
                registros[0] = rs.getString("idusuario");
                if (rs.getString("idroles").equals("1")) {
                    registros[1] = "Administrador";
                } else {
                    registros[1] = "Cajero";
                }
                registros[2] = rs.getString("nombre_completo");
                registros[3] = rs.getString("usuario");
                registros[4] = rs.getString("contrasenia");

                modelo.addRow(registros);
            }

            con.close();

        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }

        return modelo;
    }
}
