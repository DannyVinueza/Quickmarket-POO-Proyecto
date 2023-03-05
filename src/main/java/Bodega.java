import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Bodega extends Administrador{
    private JTextField productoTXT;
    private JTextField descripcionTXT;
    private JTextField precioTXT;
    private JSpinner stockSpn;
    private JTable tableProductos;
    private JButton regresarL;
    private JButton buscar;
    private JButton agregar;
    private JButton eliminar;
    private JPanel bodega_content;
    private JButton modificar;
    private JLabel mensaje;

    int cantidad_stock = 0;

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

        limpiar();
        llenartabla();


        stockSpn.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cantidad_stock = Integer.parseInt(stockSpn.getValue().toString());
            }
        });

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
                    String buscar = "SELECT * FROM productos WHERE nombre = '" + productoTXT.getText() + "'";
                    Statement stBuscar = con.createStatement();
                    ResultSet reBuscar = stBuscar.executeQuery(buscar);
                    if (reBuscar.next()){
                        if(productoTXT.getText().equals(reBuscar.getString(2))){
                            descripcionTXT.setText(reBuscar.getString(3));
                            precioTXT.setText(reBuscar.getString(4));
                            stockSpn.setValue(reBuscar.getInt(5));
                        }
                    }else{
                        mensaje.setText("Producto no encontrado");
                    }
                    con.close();
                }catch (SQLException es){
                    System.out.println("Se presento un error" + es.getMessage());
                }
            }
        });

    }

    private void limpiar() {
        productoTXT.setText(null);
        descripcionTXT.setText(null);
        precioTXT.setText(null);
        stockSpn.setValue(0);
    }

    private void llenartabla() {
        DefaultTableModel modelo = mostrarProductos();

        tableProductos.setModel(modelo);
    }

    private DefaultTableModel mostrarProductos()
    {
        String []  nombresColumnas = {"Id Producto","Nombre","Descripcón","Precio","Stock"};
        String [] registros = new String[5];

        DefaultTableModel modelo = new DefaultTableModel(null,nombresColumnas);

        String sql = "SELECT * FROM productos";

        Conexion conBD = new Conexion();


        PreparedStatement pst = null;

        ResultSet rs = null;

        try {
            con = conBD.conectar();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            while(rs.next())
            {
                registros[0] = rs.getString("id_producto");
                registros[1] = rs.getString("nombre");
                registros[2] = rs.getString("descripcion");
                registros[3] = rs.getString("precio");
                registros[4] = rs.getString("stock");

                modelo.addRow(registros);
            }

            con.close();

        } catch (HeadlessException | SQLException f) {
            System.err.println(f);
        }

        return modelo;
    }
}
