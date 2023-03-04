import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

public class Cajero extends Login{
    public JPanel cajero_panel;
    private JButton regresarL;
    private JPanel panel1;
    private JButton buscar;
    private JSpinner cantidadSPN;
    private JButton agregar;
    private JButton eliminar;
    private JTable productosCompra;
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

    private int filaSeleccionada;

    private ArrayList<comprandoProductos> listaProductos = new ArrayList<>();;

    public Cajero(int ind){
        //JScrollPane scroll = new JScrollPane(productosCompra);
        /*scrollTabla = new JScrollPane(productosCompra);
        scrollTabla.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollTabla.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollTabla.setBounds(10, 10,35,100);
        setBounds(0, 0,35,100);
        getContentPane().add(scrollTabla);*/

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

        //Llenar la tabla con los products que se agreguen
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conBD = new Conexion();
                try{
                    con = conBD.conectar();
                    int var = 0;
                    //Actualizar el stock
                    String acStock = "UPDATE productos SET stock = stock - " + ((int) cantidadSPN.getValue()) + " WHERE id_producto = (" +
                            "SELECT subquery.id_producto FROM (SELECT id_producto FROM productos WHERE nombre = '" + idprodTXT.getText() + "') AS subquery)";

                    PreparedStatement pstAc = con.prepareStatement(acStock);


                    String buscar = "SELECT * FROM productos WHERE nombre = '" + idprodTXT.getText() + "'";
                    Statement stBuscar = con.createStatement();
                    ResultSet reBuscar = stBuscar.executeQuery(buscar);

                    if (reBuscar.next()){
                        if(idprodTXT.getText().equals(reBuscar.getString(2))){
                            if(comprobarStock() == true){
                                int cantidadaFinal = (int) cantidadSPN.getValue();
                                comprandoProductos compraProductos = new comprandoProductos(
                                        reBuscar.getInt(1), reBuscar.getString(2),
                                        reBuscar.getString(3),reBuscar.getDouble(4),
                                        cantidadaFinal
                                );
                                pstAc.executeUpdate();
                                listaProductos.add(compraProductos);

                            }else{
                                mensajeTXT.setText("Ingrese una cantidad menor a " + reBuscar.getInt(5));
                            }
                        }
                    }else{
                        mensajeTXT.setText("No se encuentra el producto!!!!");
                    }
                    con.close();
                    System.out.println(listaProductos.toString());
                    DefaultTableModel modTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Descripción", "Precio", "Cantidad"}, 0);
                    productosCompra.setModel(modTabla);
                    modTabla.addRow(new Object[]{"ID", "Nombre", "Descripción", "Precio", "Cantidad"});
                    for(comprandoProductos pr: listaProductos){
                        Object[] fila = new Object[]{
                                pr.getIdProductos(),
                                pr.getNombreP(),
                                pr.getDescripcionP(),
                                pr.getPrecioP(),
                                pr.getCantidadP()
                        };
                        modTabla.addRow(fila);
                    }
                }catch (SQLException es){
                    System.out.println("Se presento un error" + es.getMessage());
                }
            }
        });
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel modeloTabla = (DefaultTableModel) productosCompra.getModel();
                Conexion conBaD = new Conexion();
                con = conBaD.conectar();

                if(filaSeleccionada == 0){
                    System.out.println("No puede eliminar la cabecera");
                }else{
                    modeloTabla.removeRow(filaSeleccionada);
                    comprandoProductos prod = listaProductos.get(filaSeleccionada - 1);
                    String stockMas = "UPDATE productos SET stock = stock + " + prod.getCantidadP() + " WHERE id_producto = (" +
                            "SELECT subquery.id_producto FROM (SELECT id_producto FROM productos WHERE nombre = '" + prod.getNombreP() + "') AS subquery)";

                    try{
                        PreparedStatement masStock = con.prepareStatement(stockMas);
                        masStock.executeUpdate();
                    }catch (SQLException ex){
                        System.out.println(ex);
                    }
                    listaProductos.remove(filaSeleccionada-1);
                }

                System.out.println(listaProductos.toString());
                conBaD.desconectar();
            }
        });

        productosCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                filaSeleccionada = productosCompra.getSelectedRow();
                System.out.println(filaSeleccionada);
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
        mensajeTXT.setText("Ingresa los Datos!!!!");
    }

    public boolean comprobarStock(){
        Conexion conBD = new Conexion();
        int cantidadaFinal = (int) cantidadSPN.getValue();
        boolean ver = false;
        try{
            con = conBD.conectar();


            String buscar = "SELECT * FROM productos WHERE nombre = '" + idprodTXT.getText() + "'";
            Statement stBuscar = con.createStatement();
            ResultSet reBuscar = stBuscar.executeQuery(buscar);


            if (reBuscar.next()){
                if(idprodTXT.getText().equals(reBuscar.getString(2))){

                    if ((cantidadaFinal > 0) && (cantidadaFinal <= reBuscar.getInt(5))){
                        ver = true;
                    }else{
                        ver = false;
                    }
                }
            }else{
                mensajeTXT.setText("No se encuentra el producto!!!!");
            }
            con.close();
        }catch (SQLException es){
            System.out.println("Se presento un error" + es.getMessage());
        }
        return ver;
    }

}
