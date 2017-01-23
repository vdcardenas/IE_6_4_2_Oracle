package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.modeloMecanico;
import vista.home;
import vista.mecanico;

/** 
 * Controlador de la vista mecanico
 * @author Víctor. Proyecto taller.
 * @version 1.0
 */
public class controladorMecanico implements ActionListener, MouseListener {

    //Se intancian la vista cliente y la home
    mecanico vistaMecanico;
    home vistaHome;
    //Se crea un objeto del modelo Cliente
    modeloMecanico modeloMecanico = new modeloMecanico();

    /**
     * Se declaran en un ENUM las acciones que se realizan desde la interfaz de
     * usuario Cliente y posterior ejecución desde el controladorCliente
     */
    public enum AccionCliente {
        // Enum Cliente
        __añadirMecanico,
        __modificarMecanico,
        __eliminarMecanico,
        __limpiarMecanico,
        __homeMecanico,

    }

    /**
     * Constructor de clase
     *
     * @param vistaMecanico es la vista del menú Mecanico
     */
    public controladorMecanico(mecanico vistaMecanico) {
        this.vistaMecanico = vistaMecanico;

    }

    /**
     * Inicia el skin y las diferentes variables que se utilizan
     */
    public void iniciar() {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vistaMecanico);
            vistaMecanico.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaMecanico.btnAgregarMecanico.setActionCommand("__añadirMecanico");
        this.vistaMecanico.btnAgregarMecanico.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaMecanico.btnModificarMecanico.setActionCommand("__modificarMecanico");
        this.vistaMecanico.btnModificarMecanico.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaMecanico.btnEliminarMecanico.setActionCommand("__eliminarMecanico");
        this.vistaMecanico.btnEliminarMecanico.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaMecanico.btnLimpiarMecanico.setActionCommand("__limpiarMecanico");
        this.vistaMecanico.btnLimpiarMecanico.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaMecanico.homeMecanico.setActionCommand("__homeMecanico");
        this.vistaMecanico.homeMecanico.addActionListener(this);

        //añade e inicia el jtable con un DefaultTableModel vacio
        this.vistaMecanico.tableMecanico.addMouseListener(this);
        this.vistaMecanico.tableMecanico.setModel(modeloMecanico.getTablaMecanico()); // Esto es para que cada vez que lo inicies 

    }

    /**
     * Método para cuando se clickea obtenga los datos pulsados en los
     * JTextField
     *
     * @param e con el evento de clickear con el ratón
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Cliente   
        if (e.getButton() == 1)//boton izquierdo
        {
            int fila = this.vistaMecanico.tableMecanico.rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.vistaMecanico.txtIdMecanico.setText(String.valueOf(this.vistaMecanico.tableMecanico.getValueAt(fila, 0)));
                this.vistaMecanico.txtNombreMecanico.setText(String.valueOf(this.vistaMecanico.tableMecanico.getValueAt(fila, 1)));
                this.vistaMecanico.txtSalarioMecanico.setText(String.valueOf(this.vistaMecanico.tableMecanico.getValueAt(fila, 2)));
            }
        }

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

        try { //Este try es para cuando nada más entrar en una pestaña y esta todos los datos vacíos y le das a un botón no salte un error.
            switch (AccionCliente.valueOf(e.getActionCommand())) {
                //Botón de añadir cliente
                case __añadirMecanico:
                    //añade un nuevo registro
                    if (this.modeloMecanico.nuevoMecanico(this.vistaMecanico.txtNombreMecanico.getText(),
                            Integer.parseInt(this.vistaMecanico.txtSalarioMecanico.getText()))) {
                        JOptionPane.showMessageDialog(vistaMecanico, "Exito: Nuevo mecanico añadido.");
                        this.vistaMecanico.tableMecanico.setModel(modeloMecanico.getTablaMecanico());
                        this.vistaMecanico.txtIdMecanico.setText("");
                        this.vistaMecanico.txtNombreMecanico.setText("");
                        this.vistaMecanico.txtSalarioMecanico.setText("");
                    } else //ocurrio un error
                    {
                        JOptionPane.showMessageDialog(vistaMecanico, "Error: No ha introducido todos los datos o no están correctos.");
                    }
                    break;
                //Botón de modificar un cliente
                case __modificarMecanico:

                    String nombreMecanico,
                     id;
                    int salario;
                    //Aquí se obtiene los datos de los JtextField
                    id = this.vistaMecanico.txtIdMecanico.getText();
                    nombreMecanico = this.vistaMecanico.txtNombreMecanico.getText();
                    salario = Integer.parseInt(this.vistaMecanico.txtSalarioMecanico.getText());
                    //Aquí se introducen en la consulta de la Base de datos
                    if (this.modeloMecanico.modificarMecanico(id, nombreMecanico, salario)) {
                        JOptionPane.showMessageDialog(vistaMecanico, "Exito: Registro modificado con éxito.");
                        this.vistaMecanico.tableMecanico.setModel(modeloMecanico.getTablaMecanico());
                        this.vistaMecanico.txtIdMecanico.setText("");
                        this.vistaMecanico.txtNombreMecanico.setText("");
                        this.vistaMecanico.txtSalarioMecanico.setText("");
                    } else {
                        JOptionPane.showMessageDialog(vistaMecanico, "Error: algo salió mal.");
                    }
                    break;
                //Botón de eliminar cliente
                case __eliminarMecanico:
                    if (this.modeloMecanico.eliminarMecanico(this.vistaMecanico.txtIdMecanico.getText())) {
                        JOptionPane.showMessageDialog(vistaMecanico, "Éxito: Se ha eliminado correctamente.");
                        this.vistaMecanico.tableMecanico.setModel(modeloMecanico.getTablaMecanico());
                        this.vistaMecanico.txtIdMecanico.setText("");
                        this.vistaMecanico.txtNombreMecanico.setText("");
                        this.vistaMecanico.txtSalarioMecanico.setText("");
                    } else {
                        JOptionPane.showMessageDialog(vistaMecanico, "Error: No se ha podido eliminar.");
                    }
                    break;
                //Botón de limpiar campos del cliente
                case __limpiarMecanico:
                    this.vistaMecanico.txtIdMecanico.setText("");
                    this.vistaMecanico.txtNombreMecanico.setText("");
                    this.vistaMecanico.txtSalarioMecanico.setText("");
                    break;
                //Botón de volver al menú home
                case __homeMecanico:
                    this.vistaMecanico.setVisible(false);
                    new controlador(new home()).iniciar();
                    break; //Fin del Switch case
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaMecanico, "Error: Compruebe que ningún campo esté vacío.");
        }
    }
}
