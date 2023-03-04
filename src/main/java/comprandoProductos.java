public class comprandoProductos {
    private int idProductos;
    private String nombreP;
    private String descripcionP;
    private double precioP;
    private int cantidadP;

    public comprandoProductos(int idProductos, String nombreP, String descripcionP, double precioP, int cantidadP) {
        this.idProductos = idProductos;
        this.nombreP = nombreP;
        this.descripcionP = descripcionP;
        this.precioP = precioP;
        this.cantidadP = cantidadP;
    }

    public int getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(int idProductos) {
        this.idProductos = idProductos;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getDescripcionP() {
        return descripcionP;
    }

    public void setDescripcionP(String descripcionP) {
        this.descripcionP = descripcionP;
    }

    public double getPrecioP() {
        return precioP;
    }

    public void setPrecioP(double precioP) {
        this.precioP = precioP;
    }

    public int getCantidadP() {
        return cantidadP;
    }

    public void setCantidadP(int cantidadP) {
        this.cantidadP = cantidadP;
    }
}
