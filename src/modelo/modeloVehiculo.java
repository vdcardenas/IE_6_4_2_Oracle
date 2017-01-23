package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * Clase modeloVehiculo que contiene los métodos y consultas SQL o PL/SQL
 *
 * @author Víctor proyecto Taller.
 * @version 1.0
 */
public class modeloVehiculo extends database {

    /**
     * Constructor vacío de la clase
     */
    public modeloVehiculo() {
    }

    /**
     * Método para añadir un nuevo vehículo
     *
     * @param matricula del vehículo de tipo String
     * @param marca del vehículo de tipo String
     * @param modelo del vehículo de tipo String
     * @param idCliente del propietario del vehículo de tipo int
     * @return Devuelve true/false en caso de que la inserción sea correcta o no
     */
    public boolean nuevoVehiculo(String matricula, String marca, String modelo, int idCliente) {
        //Se valida los datos
        if (validarDatos(matricula, marca, modelo, idCliente) == true) {
            String q = " INSERT INTO vehiculo (matricula, marca, modelo, idCliente) "
                    + "VALUES ('" + matricula + "', '" + marca + "', '" + modelo + "', '" + idCliente + "') ";
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
     * Método para modificar los datos de un vehículo SALVO el conductor
     * asociado, en dicho caso deberá borrar el registro del vehículo y crearlo
     * de nuevo
     *
     * @param matricula de tipo String
     * @param marca de tipo String sacado de un ComboBox en la vista
     * @param modelo de tipo String
     * @param matriculaux de tipo String
     * @return true/false en caso de que el método se haya realizado con éxito o
     * no
     */
    public boolean modificarVehiculo(String matricula, String marca, String modelo, String matriculaux) {

        String q = " UPDATE vehiculo SET matricula = '" + matricula + "' , marca = '" + marca + "' , modelo = '" + modelo + "'  WHERE matricula = '" + matriculaux + "' ;";

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
     * Método para validar que se han introducido algún dato y no están los
     * campos vacíos
     *
     * @param matricula de tipo String
     * @param marca de tipo String
     * @param modelo de tipo String
     * @param idCliente de tipo int
     * @return un resultado boolean que indica si se ha detectado algún campo
     * vacío o no
     */
    public boolean validarDatos(String matricula, String marca, String modelo, int idCliente) {
        boolean exito = false;
        if ("".equals(matricula) || "".equals(marca) || "".equals(modelo) || "".equals(String.valueOf(idCliente))) {
            exito = false;
        } else {
            exito = true;
        }
        return exito;
    }

    /**
     * Elimina un registro dado su ID - Llave primaria
     *
     * @param matricula que es la clave primaria de la tupla a eliminar
     * @return devuelve un true si se ha eliminado satisfactoriamente o false en
     * caso contrario
     */
    public boolean eliminarVehiculo(String matricula) {
        boolean res = false;
        if (matricula.equals("") != true) {
            //se arma la consulta
            String q = " DELETE FROM vehiculo WHERE matricula='" + matricula + "' ";
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
     * Obtiene registros de la tabla mecanico y los devuelve en un
     * DefaultTableMode
     *
     * @return devuelve el modelo de tabla
     */
    public DefaultTableModel getTablaVehiculo() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"matricula", "marca", "modelo", "idCliente"};
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
                data[i][1] = res.getString("marca");
                data[i][2] = res.getString("modelo");
                data[i][3] = res.getString("idCliente");
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

    public DefaultTableModel BuscarPropietarioNombre(String nombre) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"idCliente", "Nombre", "Teléfono"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM cliente");
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
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM cliente WHERE Nombre LIKE '%" + nombre + "%'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("idCliente");
                data[i][1] = res.getString("Nombre");
                data[i][2] = res.getString("Telefono");
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

    public DefaultTableModel BuscarPropietarioTelefono(String telefono) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"idCliente", "Nombre", "Teléfono"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM cliente");
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
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM cliente WHERE Telefono LIKE '%" + telefono + "%'");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("idCliente");
                data[i][1] = res.getString("Nombre");
                data[i][2] = res.getString("Telefono");
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

    public DefaultTableModel BuscarPropietario() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int registros = 0;
        String[] columNames = {"idCliente", "Nombre", "Teléfono"};
        //obtenemos la cantidad de registros existentes en la tabla y se almacena en la variable "registros"
        //para formar la matriz de datos
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM cliente");
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
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT * FROM cliente");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("idCliente");
                data[i][1] = res.getString("Nombre");
                data[i][2] = res.getString("Telefono");
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