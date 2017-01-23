package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import vista.cliente;
import vista.home;
import vista.mecanico;
import vista.reparaciones;
import vista.vehiculo;

/**
 * Clase controlador que se encargará de oir los eventos ocurridos en las vistas
 * y llamar a los métodos y funciones contenidas en el Modelo
 *
 * @author Víctor. Proyecto taller.
 * @version 1.0
 */
public class controlador implements ActionListener, MouseListener {

    // Intanciamos todas las vistas
    home vistaHome;
    cliente vistaCliente;

    controlador(home vistaHome, cliente vistaCliente, vehiculo vistaVehiculo, mecanico vistaMecanico) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Se declaran en un ENUM las acciones que se realizan desde la interfaz de
     * usuario home y posterior ejecución desde el controlador
     */
    public enum AccionMenu {
        __Cliente,
        __Vehiculo,
        __Mecanico,
        __Reparacion,
        __Ayuda,
        __Cerrar,
        __Salir,

    }

    /**
     * Constructor de la clase
     *
     * @param vistaHome es la vista del Home
     */
    public controlador(home vistaHome) {
        this.vistaHome = vistaHome;

    }

    /**
     * Inicia el skin y las diferentes variables que se utilizan
     */
    public void iniciar() {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vistaHome);
            vistaHome.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        // Botones del Menú Home
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaHome.cliButton.setActionCommand("__Cliente");
        this.vistaHome.cliButton.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaHome.vehButton.setActionCommand("__Vehiculo");
        this.vistaHome.vehButton.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaHome.mecButton.setActionCommand("__Mecanico");
        this.vistaHome.mecButton.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaHome.RepButton.setActionCommand("__Reparacion");
        this.vistaHome.RepButton.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaHome.btnSalir.setActionCommand("__Salir");
        this.vistaHome.btnSalir.addActionListener(this);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    //Control de eventos de los controles que tienen definido un "ActionCommand"
    public void actionPerformed(ActionEvent e) {

        // Este es el switch case del JDialog de Menu
        switch (AccionMenu.valueOf(e.getActionCommand())) {
            //Botón de Cliente
            case __Cliente:
                new controladorCliente(new cliente()).iniciar();
                this.vistaHome.setVisible(false);
                break;
            //Botón de Vehiculo
            case __Vehiculo:
                new controladorVehiculo(new vehiculo()).iniciar();
                this.vistaHome.setVisible(false);
                break;
//            //Botón de mecanico
            case __Mecanico:
                new controladorMecanico(new mecanico()).iniciar();
                this.vistaHome.setVisible(false);
                break;
//            //Botón de Reparacion
            case __Reparacion:
                new controladorReparaciones(new reparaciones()).iniciar();
                this.vistaHome.setVisible(false);
                break;
               
                
            //Botón de Salir
            case __Salir:
                Object[] opciones = {"Aceptar", "Cancelar"}; //Es un array para guardar las dos opciones
                int eleccion = JOptionPane.showOptionDialog(vistaHome, "¿Deseas salir de la aplicación?", "Mensaje de Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
                if (eleccion == JOptionPane.YES_OPTION) {
                    System.exit(0); //Se cierra la aplicación.
                } else {
                }  //Sino, no se hace nada. 
                break;

        }
    }
}
