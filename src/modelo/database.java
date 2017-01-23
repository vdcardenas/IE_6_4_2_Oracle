package modelo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Clase database que se encargará crear un objeto de conexción para las
 * llamadas a ORACLE
 *
 * @author Victor_ORACLE
 * @version 1.0
 */
public class database {

//    /* DATOS PARA LA CONEXION */
//    private String db="web";
//    private String user="dam43";
//    private String password="salesianas";
//    //79.148.236.236
//    //192.168.28.3
//    private String url="jdbc:mysql://192.168.28.3/";
    private Connection conn = null;
//    

    /**
     * Constructor de clase
     */
    public database() {
        //Segunda parte
        try {
            //obtenemos el driver de para mysql
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //obtenemos la conexión
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","0045");
            //conn = DriverManager.getConnection(this.url, this.user, this.password);
            System.out.println("conectazo");
        } catch (SQLException e) {
            System.err.println(e.getMessage());

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public Connection getConexion() {
        return this.conn;
    }
    
    public static void main (String[] args){
    
        database d = new database ();
        d.getConexion();
        
    }
    
}