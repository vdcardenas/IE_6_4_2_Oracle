package modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * Clase modeloMecanico que contiene los métodos y consultas SQL o PL/SQL
 * @author Víctor proyecto Taller.
 * @version 1.0
 */
public class modeloMecanico extends database {

    /**
     * Constructor vacío de la clase
     */
    public modeloMecanico() {
    }

    /**
     * Método para insertar un nuevo mecanico en la base de datos
     *
     * @param salario de tipo int
     * @param nombre del mecanico
     * @return devuelve true cuando se ha insertado con éxito y sino devuelve
     * false
     */
    public boolean nuevoMecanico(String nombre, int salario) {
        //Se valida los datos
        if (validarMecanico(nombre) == true && validarSalario(salario) == true) {
            String q = " INSERT INTO mecanico (nombre, salario) "
                    + "VALUES ('" + nombre + "', '" + salario + "') ";
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
     * Método para validar que no existan parámetros sin definir, con valor String ""
     * @param nombre nombre del cliente
     * @return devuelve si está o no relleno el campo del nombre del cliente
     */
    public boolean validarMecanico(String nombre) {
        boolean exito = false;
        if ("".equals(nombre)) {
            exito = false;
        } else {
            exito = true;
        }
        return exito;
    }

    /**
     *  Método para validar que se ha introducido texto en el campo salario
     * @param salario del mecánico
     * @return devuelve si está o no relleno el campo de salario del mecanico
     */
    public boolean validarSalario (int salario) {
        boolean exito = false;
        if ("".equals(salario)) {
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
    public boolean eliminarMecanico(String id) {
        boolean res = false;
        if (id.equals("") != true) {
            //se arma la consulta
            String q = " DELETE FROM mecanico WHERE idMecanico='" + id + "' ";
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
    public DefaultTableModel getTablaMecanico() {
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
     * Método para modificar algún dato de la tabla mecanico
     *
     * @param id es la id del mecanico, esta no se modifica simplemente es para
     * ejecutar la busqueda con la consulta
     * @param nombre del mecanico
     * @param salario del mecanico
     * @return devuelve true en caso de que se haya modificado correctamente y
     * false en caso contrario.
     */
    public boolean modificarMecanico(String id, String nombre, int salario) {

        String q = " UPDATE mecanico SET nombre = '" + nombre + "' , salario = '" + salario + "' WHERE idMecanico = '" + id + "' ;";

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
