package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * Clase modeloReparaciones que contiene los métodos para interactuar con la vista de Reparaciones
 *
 * @author Víctor proyecto Taller.
 * @version 1.0
 */
public class modeloReparaciones extends database {

    /**
     * Constructor vacío de la clase
     */
    public modeloReparaciones() {
    }

    
    /**
     * Método para añadir una nueva reparación.
     * @param matricula de tipo int
     * @param idMecanico de tipo int
     * @param diagnostico de tipo String
     * @param coste de tipo int
     * @return un boleano con el valor true/false en caso de que la inserción se haya hecho correctamente en la tabla
     */
    public boolean nuevaReparacion(String matricula, int idMecanico, String diagnostico, int coste) {
        //Se valida los datos
        if (validarDatos(matricula, idMecanico, diagnostico, coste) == true) {
            String q = " INSERT INTO reparaciones (matricula, idmecanico, diagnostico, coste) "
                    + "VALUES ('" + matricula + "', '" + idMecanico + "', '" + diagnostico + "', '" + coste + "') ";
            //se ejecuta la consulta
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Método para modificar las reparaciones
     * @param idreparacion de tipo int
     * @param matricula de tipo string
     * @param idmecanico de tipo int
     * @param diagnostico de tipo String con la causa de la avería
     * @param coste de tipo int
     * @return boolean en caso de que la modificación en la tabla reparaciones se haya realizado correctamente
     */
    public boolean modificarReparaciones(int idreparacion, String matricula, int idmecanico, String diagnostico, int coste) {
        
        String q = " UPDATE reparaciones SET matricula = '" + matricula + "' , idmecanico = '" + idmecanico + "' , diagnostico = '" + diagnostico + "' , coste = '" + coste + "'  WHERE idreparacion = '" + idreparacion + "' ;";

        //se ejecuta la consulta
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;

    }

    
  /**
   * Método para eliminar tuplas de la tabla repaciones
     * @param idreparacion de tipo ing con el id de la clave reparaciones
   * @return boolean en caso de que la eliminación se haya realizado o no.
   */
    public boolean eliminarReparaciones(int idreparacion) {
        boolean res = false;
        if (String.valueOf(idreparacion).equals("") != true) {
            //se arma la consulta
            String q = " DELETE FROM reparaciones WHERE idreparacion='" + idreparacion + "' ";
            //se ejecuta la consulta
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                res = true;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } else {
            res = false;

        }
        return res;
    }
    
    /**
     * Método para validar que los datos que se usan como parámetros de entrada en los demás métodos
     * no estén vacíos.
     * @param matricula de tipo String
     * @param idMecanico de tipo int con el id de la tabla mecanico
     * @param diagnostico de tipo String
     * @param coste de tipo int
     * @return  boleano con un true en caso de que no existan campos vacíos
     */
     public boolean validarDatos(String matricula, int idMecanico, String diagnostico, int coste) {
        boolean exito = false;
        if (matricula.equals("") || "".equals(String.valueOf(idMecanico)) || "".equals(diagnostico) || "".equals(String.valueOf(coste))) {
            exito = false;
        } else {
            exito = true;
        }
        return exito;
    }

    /**
     * Obtiene registros de la tabla reparaciones y los devuelve en un
     * objeto tablemodel
     *
     * @return devuelve el modelo de tabla
     */
    public DefaultTableModel getTablaReparaciones() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"idreparacion", "matricula", "idmecanico", "diagnostico", "coste"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM reparaciones");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][6];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM reparaciones");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("idreparacion");
                data[i][1] = res.getString("matricula");
                data[i][2] = res.getString("idmecanico");
                data[i][3] = res.getString("diagnostico");
                data[i][4] = res.getString("coste");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }
    
    /**
     * Método que devuelve en un objeto tablemodel todos los datos de la tabla vehiculo
     * @return un objeto tablemodel con la tabla vehiculo
     */
    public DefaultTableModel gettableVehiculo() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"matricula", "idCliente", "marca", "modelo"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM vehiculo");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][5];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM vehiculo");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("matricula");
                data[i][1] = res.getString("idCliente");
                data[i][2] = res.getString("marca");
                data[i][3] = res.getString("modelo");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }
    

    /**
     * Método que devuelve un tablemodel con las tuplas que cumplen el requisito de coincidir con la matrícula indicada
     * y pasada como parámetro.
     * @param matricula de tipo String
     * @return devuelve un bjeto tablemodel 
     */
    public DefaultTableModel gettableVehiculoReparaciones(String matricula) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"matricula", "idCliente", "marca", "modelo"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM vehiculo");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][5];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM vehiculo WHERE matricula LIKE '%" + matricula + "%'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("matricula");
                data[i][1] = res.getString("idCliente");
                data[i][2] = res.getString("marca");
                data[i][3] = res.getString("modelo");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }
    
    /**
     * Método que devuelve un objeto de tipo tablemodel con todas tuplas de la tabla mecánico.
     * Será mostrada en un Jtable en la vista Reparaciones
     * @return un objeto de tipo tablemodel con las tuplas de la tabla mecánico
     */
    public DefaultTableModel gettableBMecanico() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"idMecanico", "nombre", "salario"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM mecanico");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][4];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM mecanico");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("idMecanico");
                data[i][1] = res.getString("nombre");
                data[i][2] = res.getString("salario");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }
    

    /**
     * * Método para dado un valor int que representa a un id de la tabla, se devuelva la consulta en forma de tabla
     * En este caso, devuelve todos las tuplas cuyo nombre de mecánico  coinciden con el valor pasado como parámetro.
     * @param nombre de tipo String, nombre del mecánico
     * @return objeto de tipo tablemodel con las tuplas que cumplen la condición del nombre del mecánico
     */
    public DefaultTableModel gettableBMecanicoNombre (String nombre) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"idMecanico", "nombre", "salario"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM mecanico");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][4];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM mecanico WHERE nombre LIKE '%" + nombre + "%'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("idMecanico");
                data[i][1] = res.getString("nombre");
                data[i][2] = res.getString("salario");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    /**
     * Método para dado un valor int que representa a un id de la tabla, se devuelva la consulta en forma de tabla
     * @param idMecanico de tipo int con la id del mecánico
     * @return devuelve un objeto tablemodel con las tuplas de la tabla mecánico cuya id sea coincidente.
     */
    public DefaultTableModel gettableBMecanicoId (int idMecanico) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"idMecanico", "nombre", "salario"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM mecanico");
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][4];
        try {
            //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM mecanico WHERE idMecanico LIKE '%" + idMecanico + "%'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("idMecanico");
                data[i][1] = res.getString("nombre");
                data[i][2] = res.getString("salario");
                i++;
            }
            res.close();
            //se añade la matriz de datos en el DefaultTableModel
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

}