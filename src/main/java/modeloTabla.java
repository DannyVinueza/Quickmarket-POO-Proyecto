import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class modeloTabla extends AbstractTableModel {
    private String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Cantidad"};
    private ArrayList<comprandoProductos> listaProductos;

    public modeloTabla(ArrayList<comprandoProductos> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Override
    public int getRowCount() {
        return listaProductos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        comprandoProductos productoLista = listaProductos.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return productoLista.getIdProductos();
            case 1:
                return productoLista.getNombreP();
            case 2:
                return productoLista.getDescripcionP();
            case 3:
                return productoLista.getPrecioP();
            case 4:
                return productoLista.getCantidadP();
            default:
                throw new IndexOutOfBoundsException("Índice de columna fuera de rango: " + columnIndex);
        }
    }
}
