package modelo;

import java.awt.Component;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import vista.cliente;

/**
 * Clase modeloCliente que contiene los métodos con sus algoritmos o llamadas a
 * rutinas plsql
 *
 * @author Víctor. Proyecto taller.
 * @version 1.0
 */
public class modeloCliente extends database {

    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableEjemplo;
    cliente vistacliente;

    /**
     * Constructor vacío de la clase
     */
    public modeloCliente() {
    }

    /**
     * Método para insertar un nuevo cliente en la base de datos
     *
     * @param telefono de tipo String
     * @param nombre nombre del cliente
     * @return devuelve true cuando se ha insertado con éxito y sino devuelve
     * false
     */
    public boolean nuevoCliente(String nombre, String telefono) {
        //Se valida los datos
        if (validarCliente(nombre) == true && validarTelefono(telefono) == true) {
            String q = " INSERT INTO cliente (Nombre, Telefono) "
                    + "VALUES ('" + nombre + "', '" + telefono + "') ";
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
     *
     * @param nombre nombre del cliente
     * @return devuelve si está o no relleno el campo del nombre del cliente
     */
    public boolean validarCliente(String nombre) {
        boolean exito = false;
        if ("".equals(nombre)) {
            exito = false;
        } else {
            exito = true;
        }
        return exito;
    }

    /**
     * Método para validar que se ha introducido texto en el campo
     *
     * @param telefono del cliente
     * @return devuelve si está o no relleno el campo de telefono del cliente
     */
    public boolean validarTelefono(String telefono) {
        boolean exito = false;
        if ("".equals(telefono)) {
            exito = false;
        } else {
            exito = true;
        }
        return exito;
    }

    /**
     * Elimina un registro dado su ID - Llave primaria
     *
     * @param id la clave primaria que queramos eliminar
     * @return devuelve un true si se ha eliminado satisfactoriamente o sino un
     * false
     */
    public boolean eliminarCliente(String id) {
        boolean res = false;
        if (id.equals("") != true) {
            //se arma la consulta
            String q = " DELETE FROM cliente WHERE  idCliente='" + id + "' ";
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
     * Obtiene registros de la tabla cliente y los devuelve en un
     * DefaultTableMode
     *
     * @return devuelve el modelo de tabla
     */
    public DefaultTableModel getTablaCliente() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        Statement s;
        ResultSet rs = null;

        try {
            s = this.getConexion().createStatement();
            rs = s.executeQuery("SELECT * FROM TABLA_PROYECTOS P");
            ResultSetMetaData rsMd = rs.getMetaData();
            //La cantidad de columnas que tiene la consulta
            int cantidadColumnas = rsMd.getColumnCount();
            //Establecer como cabezeras el nombre de las colimnas
            for (int i = 1; i <= cantidadColumnas; i++) {
                tablemodel.addColumn(rsMd.getColumnLabel(i));
            }
            //Creando las filas para el JTable
            while (rs.next()) {
                Object[] fila = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                tablemodel.addRow(fila);
            }
            rs.close();
            this.getConexion().close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return tablemodel;
    }

    /**
     * Método para modificar algún dato de la tabla cliente
     *
     * @param id es la id del cliente, esta no se modifica simplemente es para
     * ejecutar la busqueda con la consulta
     * @param nombre del cliente
     * @param telefono del cliente
     * @return devuelve true en caso de que se haya modificado correctamente y
     * false en caso contrario.
     */
    public boolean modificarCliente(String id, String nombre, String telefono) {

        String q = " UPDATE cliente SET Nombre = '" + nombre + "' , Telefono = '" + telefono + "' WHERE idCliente = '" + id + "' ;";

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

}
