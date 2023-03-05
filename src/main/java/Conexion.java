import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection con;
    private String nombreBD = "quickmarket";
    private String url = "jdbc:mysql://localhost/" + nombreBD;
    private String usuario = "root";
    private String contrasenia = "UGPCUGR2002";

    public Connection conectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,usuario,contrasenia);
        }catch(ClassNotFoundException | SQLException ex){
            System.err.println(ex);
            System.out.println("Error al conectar la base de datos");
        }
        return con;
    }

    public void desconectar(){
        try{
            if(con != null){
                con.close();
                System.out.println("Desconexion exitosa de la base de datos");
            }
        }catch(SQLException ex){
            System.out.println("Error al desconectar la base de datos");
            ex.printStackTrace();
        }
    }
}
