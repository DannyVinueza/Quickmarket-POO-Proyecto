import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VR_Usuario extends Ventas{
    private JPanel contentPane;
    private JTable vruTable;
    private JTextField usuarioTXT;
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

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();

                try{
                    con = conBD.conectar();
                    String buscar = "SELECT * FROM usuarios WHERE usuario = '" + usuarioTXT.getText() + "'";
                    Statement stBuscar = con.createStatement();
                    ResultSet reBuscar = stBuscar.executeQuery(buscar);

                    if (reBuscar.next()){
                        mensajeJ.setText("¡Usuario Encontrado!");
                        llenartabla(usuarioTXT.getText());
                    }else{
                        mensajeJ.setText("No se pudo realizar, ¡Usuario no encontrado!");
                    }
                    con.close();
                }catch (SQLException es){
                    System.out.println("Se presento un error" + es.getMessage());
                }
            }
        });
    }
    private void llenartabla(String usuario) {

        DefaultTableModel modelo = mostrar_VRU(usuario);

        vruTable.setModel(modelo);
    }

    private DefaultTableModel mostrar_VRU(String usuario)
    {
        String [] nombresColumnas = {"N. Factura","Cliente","Cajero","Fecha","Total"};
        String [] registros = new String[5];

        DefaultTableModel modelo = new DefaultTableModel(null,nombresColumnas);
        String sql = "SELECT v.id_venta, c.nombre, e.nombre_completo, v.fecha_venta, v.importe_total\n" +
                "FROM Facturas v\n" +
                "JOIN Clientes c ON v.id_cliente = c.id_cliente\n" +
                "JOIN usuarios e ON v.idusuario = e.idusuario\n" +
                "where e.usuario = '" + usuario + "'\n"+
                "ORDER BY v.fecha_venta DESC;";
        Conexion conBD = new Conexion();

        PreparedStatement pst = null;

        ResultSet rs = null;

        try {
            con = conBD.conectar();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while(rs.next())
            {
                registros[0] = rs.getString("v.id_venta");
                registros[1] = rs.getString("c.nombre");
                registros[2] = rs.getString("e.nombre_completo");
                registros[3] = rs.getString("v.fecha_venta");
                registros[4] = rs.getString("v.importe_total");

                modelo.addRow(registros);
            }

            con.close();

        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }

        return modelo;
    }
}
