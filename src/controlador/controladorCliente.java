package controlador;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;
import modelo.modeloCliente;
import vista.cliente;
import vista.home;

/**
 * @author Víctor. Proyecto taller.
 * @version 1.0
 */
public class controladorCliente implements ActionListener, MouseListener {

    //Se intancian la vista cliente y la home
    cliente vistaCliente;
    home vistaHome;
    //Se crea un objeto del modelo Cliente
        modeloCliente modeloCliente = new modeloCliente();

    /**
     * Se declaran en un ENUM las acciones que se realizan desde la interfaz de
     * usuario Cliente y posterior ejecución desde el controladorCliente
     */
    public enum AccionCliente {
        // Enum Cliente
        __añadirCliente,
        __modificarCliente,
        __eliminarCliente,
        __limpiarCliente,
        __homeCliente,

    }

    /**
     * Constrcutor de clase
     *
     * @param vistaCliente es la vista del menú Cliente
     */
    public controladorCliente(cliente vistaCliente) {
        this.vistaCliente = vistaCliente;

    }

    /**
     * Inicia el skin y las diferentes variables que se utilizan
     */
    public void iniciar() {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vistaCliente);
            vistaCliente.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaCliente.btnAgregarCliente.setActionCommand("__añadirCliente");
        this.vistaCliente.btnAgregarCliente.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaCliente.btnModificarCliente.setActionCommand("__modificarCliente");
        this.vistaCliente.btnModificarCliente.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaCliente.btnEliminarCliente.setActionCommand("__eliminarCliente");
        this.vistaCliente.btnEliminarCliente.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaCliente.btnLimpiarCliente.setActionCommand("__limpiarCliente");
        this.vistaCliente.btnLimpiarCliente.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaCliente.homeCliente.setActionCommand("__homeCliente");
        this.vistaCliente.homeCliente.addActionListener(this);

        //añade e inicia el jtable con un DefaultTableModel vacio
        this.vistaCliente.tableCliente.addMouseListener(this);

        

        this.vistaCliente.tableCliente.setDefaultRenderer(JButton.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object objeto, boolean estaSeleccionado, boolean tieneElFoco, int fila, int columna) {
                /**
                 * Observen que todo lo que hacemos en éste método es retornar
                 * el objeto que se va a dibujar en la celda. Esto significa que
                 * se dibujará en la celda el objeto que devuelva el TableModel.
                 * También significa que este renderer nos permitiría dibujar
                 * cualquier objeto gráfico en la grilla, pues retorna el objeto
                 * tal y como lo recibe.
                 */
                return (Component) objeto;
            }
        });

        this.vistaCliente.tableCliente.setModel(modeloCliente.getTablaCliente()); // Esto es para que cada vez que lo inicies 

    }

    /**
     * Método para cuando se clickea obtenga los datos pulsados en los
     * JTextField
     *
     * @param e con el evento de ratón
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Cliente   
        if (e.getButton() == 1)//boton izquierdo
        {
            int fila = this.vistaCliente.tableCliente.rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.vistaCliente.txtIdCliente.setText(String.valueOf(this.vistaCliente.tableCliente.getValueAt(fila, 0)));
                this.vistaCliente.txtNombreCliente.setText(String.valueOf(this.vistaCliente.tableCliente.getValueAt(fila, 1)));
                this.vistaCliente.txtTelefonoCliente.setText(String.valueOf(this.vistaCliente.tableCliente.getValueAt(fila, 2)));
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
                case __añadirCliente:
                    //añade un nuevo registro
                    if (this.modeloCliente.nuevoCliente(
                            this.vistaCliente.txtNombreCliente.getText(), this.vistaCliente.txtTelefonoCliente.getText())) {
                        JOptionPane.showMessageDialog(vistaCliente, "Exito: Nuevo registro agregado.");
                        this.vistaCliente.tableCliente.setModel(modeloCliente.getTablaCliente());
                        this.vistaCliente.txtIdCliente.setText("");
                        this.vistaCliente.txtNombreCliente.setText("");
                        this.vistaCliente.txtTelefonoCliente.setText("");
                    } else //ocurrio un error
                    {
                        JOptionPane.showMessageDialog(vistaCliente, "Error: Rellene todos los campos.");
                    }
                    break;
                //Botón de modificar un cliente
                case __modificarCliente:

                    String nombreCliente,
                     id,
                     telefono;
                    //Aquí se obtiene los datos de los JtextField
                    id = this.vistaCliente.txtIdCliente.getText();
                    nombreCliente = this.vistaCliente.txtNombreCliente.getText();
                    telefono = this.vistaCliente.txtTelefonoCliente.getText();
                    //Aquí se introducen en la consulta de la Base de datos
                    if (this.modeloCliente.modificarCliente(id, nombreCliente, telefono)) {
                        JOptionPane.showMessageDialog(vistaCliente, "Exito: Registro modificado con éxito.");
                        this.vistaCliente.tableCliente.setModel(modeloCliente.getTablaCliente());
                        this.vistaCliente.txtIdCliente.setText("");
                        this.vistaCliente.txtNombreCliente.setText("");
                        this.vistaCliente.txtTelefonoCliente.setText("");
                    } else {
                        JOptionPane.showMessageDialog(vistaCliente, "Error: algo salió mal.");
                    }
                    break;
                //Botón de eliminar cliente
                case __eliminarCliente:
                    if (this.modeloCliente.eliminarCliente(this.vistaCliente.txtIdCliente.getText())) {
                        JOptionPane.showMessageDialog(vistaCliente, "Éxito: Se ha eliminado correctamente.");
                        this.vistaCliente.tableCliente.setModel(modeloCliente.getTablaCliente());
                        this.vistaCliente.txtIdCliente.setText("");
                        this.vistaCliente.txtNombreCliente.setText("");
                        this.vistaCliente.txtTelefonoCliente.setText("");
                    } else {
                        JOptionPane.showMessageDialog(vistaCliente, "Error: No se ha podido eliminar.");
                    }
                    break;
                //Botón de limpiar campos del cliente
                case __limpiarCliente:
                    this.vistaCliente.txtIdCliente.setText("");
                    this.vistaCliente.txtNombreCliente.setText("");
                    this.vistaCliente.txtTelefonoCliente.setText("");
                    break;
                //Botón de volver al menú home
                case __homeCliente:
                    this.vistaCliente.setVisible(false);
                    new controlador(new home()).iniciar();
                    break; //Fin del Switch case
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaCliente, "Error: Compruebe que ningún campo esté vacío.");
        }
    }
}
