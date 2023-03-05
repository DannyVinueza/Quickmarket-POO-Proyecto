import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
//import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;

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
    private JLabel cajeroL;

    private int filaSeleccionada;

    private ArrayList<comprandoProductos> listaProductos = new ArrayList<>();
    private String cajeroVen;

    public static DecimalFormat dc = new DecimalFormat("##.00");

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
        //Llamada a la pantalla de cajero
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Imagenes/LOGO.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cajero");
        setIconImage(img);
        setLocationRelativeTo(null);
        setContentPane(cajero_panel);
        pack();
        setVisible(true);

        String us = "SELECT * FROM usuarios WHERE usuario = '" + Login.obtenerUsuarioLogeado() + "'";
        try{
            Conexion conUS = new Conexion();
            con = conUS.conectar();
            Statement usSt = con.createStatement();
            ResultSet reUsu = usSt.executeQuery(us);
            if(reUsu.next()){
                cajeroVen = reUsu.getString(3);
                cajeroL.setText(cajeroVen);
                con.close();
            }
        }catch (SQLException ex){
            System.out.println(ex);
        }
        //cajeroL.setText(Login.obtenerUsuarioLogeado());
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
        facturarButton.addActionListener(new ActionListener() {//Boton para generar la factura
            @Override
            public void actionPerformed(ActionEvent e) {
                Document document = new Document();
                Conexion conCli = new Conexion();

                try{
                    con = conCli.conectar();
                    String cliente = "SELECT * FROM clientes WHERE cedula = '" + 1755355422 + "'";
                    String empresaDatos = "SELECT * FROM quickmarket";

                    PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
                    document.open();

                    // Añadir título
                    Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
                    Paragraph title = new Paragraph("Factura N° " + (int) (Math.random() * 9999) + 1000, titleFont);
                    title.setAlignment(Element.ALIGN_CENTER);
                    document.add(title);

                    // Añadir datos del cliente
                    Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
                    Paragraph subtitle = new Paragraph("Datos del cliente", subtitleFont);
                    document.add(subtitle);

                    String nombreCliente = "";
                    String direccionCliente = "";
                    String correo = "";
                    String telefono = "";

                    Statement stmt;
                    ResultSet rs;
                    try {
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(cliente);
                        while(rs.next()){
                            nombreCliente = rs.getString(2);
                            direccionCliente = rs.getString(3);
                            correo = rs.getString(4);
                            telefono = rs.getString(5);
                        }

                    }catch (SQLException ess){
                        System.out.println(ess);
                    }

                    Paragraph nombre = new Paragraph("Nombre: " + nombreCliente);
                    Paragraph direccion = new Paragraph("Dirección: " + direccionCliente);
                    Paragraph ciudad = new Paragraph("Correo: " + correo);
                    Paragraph pais = new Paragraph("Telefono: " + telefono);
                    Paragraph vacio = new Paragraph("       ");//Insertar un salto de linea en el pdf
                    document.add(nombre);
                    document.add(direccion);
                    document.add(ciudad);
                    document.add(pais);

                    // Añadir datos de la empresa
                    Paragraph datosCli = new Paragraph("Datos de la empresa", subtitleFont);
                    document.add(datosCli);

                    String empresa = "";
                    String correoEmpresa = "";
                    String telefonoEmpresa = "";
                    String nomCajero = cajeroVen;

                    try {
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(empresaDatos);
                        while(rs.next()){
                            empresa = rs.getString(2);
                            correoEmpresa = rs.getString(3);
                            telefonoEmpresa = rs.getString(4);
                        }

                    }catch (SQLException esas){
                        System.out.println(esas);
                    }

                    Paragraph empresaNombre = new Paragraph("Nombre del minimarket: " + empresa);
                    Paragraph corEmpresa= new Paragraph("Correo del minimarket: " + correoEmpresa);
                    Paragraph teleEmpresa = new Paragraph("Telefono del minimarket: " + telefonoEmpresa);
                    Paragraph cajVendiendo = new Paragraph("Nombre del cajero: " + nomCajero);

                    document.add(empresaNombre);
                    document.add(corEmpresa);
                    document.add(teleEmpresa);
                    document.add(cajVendiendo);

                    // Añadir detalles de la factura
                    Paragraph detalles = new Paragraph("Detalles de la factura\n", subtitleFont);
                    document.add(detalles);
                    document.add(vacio);

                    PdfPTable tabla = new PdfPTable(6);
                    tabla.setWidthPercentage(100);
                    PdfPCell celda1 = new PdfPCell(new Phrase("ID"));
                    PdfPCell celda2 = new PdfPCell(new Phrase("Nombre"));
                    PdfPCell celda3 = new PdfPCell(new Phrase("Descripcion"));
                    PdfPCell celda4 = new PdfPCell(new Phrase("Precio unitario"));
                    PdfPCell celda5 = new PdfPCell(new Phrase("Cantidad"));
                    PdfPCell celda6 = new PdfPCell(new Phrase("Total"));

                    tabla.addCell(celda1);
                    tabla.addCell(celda2);
                    tabla.addCell(celda3);
                    tabla.addCell(celda4);
                    tabla.addCell(celda5);
                    tabla.addCell(celda6);

                    // Añadir filas con los detalles de los productos

                    double totalPagarPC = 0;
                    for(comprandoProductos pro: listaProductos){
                        int idPC = pro.getIdProductos();
                        String nomPC = pro.getNombreP();
                        String desPC = pro.getDescripcionP();
                        double prePC = pro.getPrecioP();
                        int cantPC = pro.getCantidadP();
                        double totalPC = prePC * cantPC;
                        totalPagarPC += totalPC;

                        //Añadimos los datos a las celdas filas
                        tabla.addCell(dc.format(idPC));
                        tabla.addCell(nomPC);
                        tabla.addCell(desPC);
                        tabla.addCell(dc.format(prePC));
                        tabla.addCell(dc.format(cantPC));
                        tabla.addCell(dc.format(totalPC));
                    }
                    document.add(tabla);

                    // Añadir total de la factura
                    double totalFactura = totalPagarPC;
                    double ivaA = totalFactura * 0.12;
                    Paragraph subtotal = new Paragraph("Sub Total: " + dc.format(totalFactura));
                    Paragraph iva = new Paragraph("Iva: " + dc.format(ivaA));
                    Paragraph totalPago = new Paragraph("Total a pagar: " + dc.format(ivaA + totalFactura));

                    //Alineamos a la derecha
                    subtotal.setAlignment(Element.ALIGN_RIGHT);
                    iva.setAlignment(Element.ALIGN_RIGHT);
                    totalPago.setAlignment(Element.ALIGN_RIGHT);

                    //Añadir al documento el subtotal, iva, total a pagar
                    document.add(subtotal);
                    document.add(iva);
                    document.add(totalPago);

                    //Pie de factura
                    PdfPTable tablaPie = new PdfPTable(2);
                    tablaPie.setWidthPercentage(100);
                    tablaPie.addCell("Cajero: "+ cajeroVen + " ------------------");
                    tablaPie.addCell("Cliente: " + nombreCliente + " ------------------");

                    //Añadimos el pie un espacio en blanco al pdf
                    document.add(vacio);
                    document.add(tablaPie);

                    // Cerrar documento
                    document.close();
                    //documentoPdf.close();
                    System.out.println("La factura se ha generado correctamente");
                    limpiar();
                    listaProductos.clear();
                    DefaultTableModel model = (DefaultTableModel) productosCompra.getModel();
                    model.setRowCount(0);
                    productosCompra.revalidate();

                }catch (Exception ex){
                    System.out.println(ex);
                }
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
