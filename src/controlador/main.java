package controlador;

import vista.cliente;
import vista.home;

/**
 * Programa para gestionar talleres.
 *
 * @author Victor
 * @version 1.0
 */
public class main {

    public static void main(String[] args) {

        home vistaHome = new home();
        cliente vistaCliente = new cliente();

        new controlador(vistaHome).iniciar();

    }
}
