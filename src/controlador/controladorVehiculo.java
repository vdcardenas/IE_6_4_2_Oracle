package controlador;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.modeloVehiculo;
import vista.home;
import vista.vehiculo;

/** 
 * Controlador de la vista vehiculo
 * @author Víctor. Proyecto taller.
 * @version 1.0
 */
public class controladorVehiculo implements ActionListener, MouseListener {

    //Se intancian la vista vehiculo y la home
    vehiculo vistaVehiculo;
    home vistaHome;
    // Variable matriculaux para que guarde el id anterior en las operaciones de modificación de un registro.
    String matriculaaux;
    //Se crea un objeto del modelo vehiculo
    modeloVehiculo modeloVehiculo = new modeloVehiculo();

    /**
     * Se declaran en un ENUM las acciones que se realizan desde la interfaz de
     * usuario Cliente y posterior ejecución desde el controladorCliente
     */
    public enum AccionCliente {
        // Enum Cliente
        __elegirMarca,
        __buscarCliente,
        __buscar,
        __aceptarCliente,
        __volverCliente,
        __añadirVehiculo,
        __modificarVehiculo,
        __eliminarVehiculo,
        __limpiarVehiculo,
        __homeVehiculo,

    }

    /**
     * Constructor de clase
     *
     * @param vistaVehiculo es la vista de la inferfaz vehiculo
     */
    public controladorVehiculo(vehiculo vistaVehiculo) {
        this.vistaVehiculo = vistaVehiculo;

    }

    /**
     * Inicia el skin y las diferentes variables que se utilizan
     */
    public void iniciar() {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vistaVehiculo);
            vistaVehiculo.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.ComboMarca.setActionCommand("__elegirMarca");
        this.vistaVehiculo.ComboMarca.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.buscarClienteButton.setActionCommand("__buscarCliente");
        this.vistaVehiculo.buscarClienteButton.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.btnAgregarVehiculo.setActionCommand("__añadirVehiculo");
        this.vistaVehiculo.btnAgregarVehiculo.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.btnModificarVehiculo.setActionCommand("__modificarVehiculo");
        this.vistaVehiculo.btnModificarVehiculo.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.btnEliminarVehiculo.setActionCommand("__eliminarVehiculo");
        this.vistaVehiculo.btnEliminarVehiculo.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.btnLimpiarVehiculo.setActionCommand("__limpiarVehiculo");
        this.vistaVehiculo.btnLimpiarVehiculo.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.homeVehiculo.setActionCommand("__homeVehiculo");
        this.vistaVehiculo.homeVehiculo.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.btnVolverPropietario.setActionCommand("__volverCliente");
        this.vistaVehiculo.btnVolverPropietario.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.btnBuscar.setActionCommand("__buscar");
        this.vistaVehiculo.btnBuscar.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaVehiculo.btnAceptarPropietario.setActionCommand("__aceptarCliente");
        this.vistaVehiculo.btnAceptarPropietario.addActionListener(this);

        //añade e inicia el jtable con un DefaultTableModel vacio
        this.vistaVehiculo.tableVehiculo.addMouseListener(this);
        this.vistaVehiculo.tableVehiculo.setModel(modeloVehiculo.getTablaVehiculo());

        this.vistaVehiculo.tablePropietario.addMouseListener(this);
        this.vistaVehiculo.tablePropietario.setModel(modeloVehiculo.BuscarPropietario());
    }

    /**
     * Método para cuando se clickea obtenga los datos pulsados en los
     * JTextField
     *
     * @param e e con el evento de clickear con el ratón
     */
    public void mouseClicked(MouseEvent e) {
        // Cliente   
        if (e.getButton() == 1)//boton izquierdo
        {
            int fila = this.vistaVehiculo.tableVehiculo.rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.vistaVehiculo.txtMatricula.setText(String.valueOf(this.vistaVehiculo.tableVehiculo.getValueAt(fila, 0)));
                /**
                 * Asignamos en una variable aux la matrícula del vehículo
                 * mostrado una vez pinchada una tupla de la tabla, para en caso
                 * de modificación de datos, que no tome el viejo id del campo
                 * de texto sino de esta variable aux.
                 */
                matriculaaux = String.valueOf(this.vistaVehiculo.tableVehiculo.getValueAt(fila, 0));

                //Conversión de los valores de la base de datos en opciones del combobox
                if (this.vistaVehiculo.tableVehiculo.getValueAt(fila, 1).equals("audi")) {
                    this.vistaVehiculo.ComboMarca.setSelectedIndex(0);
                } else if (this.vistaVehiculo.tableVehiculo.getValueAt(fila, 1).equals("bmw")) {
                    this.vistaVehiculo.ComboMarca.setSelectedIndex(1);
                } else if (this.vistaVehiculo.tableVehiculo.getValueAt(fila, 1).equals("ford")) {
                    this.vistaVehiculo.ComboMarca.setSelectedIndex(2);
                } else if (this.vistaVehiculo.tableVehiculo.getValueAt(fila, 1).equals("mercedes")) {
                    this.vistaVehiculo.ComboMarca.setSelectedIndex(3);
                } else if (this.vistaVehiculo.tableVehiculo.getValueAt(fila, 1).equals("renault")) {
                    this.vistaVehiculo.ComboMarca.setSelectedIndex(4);
                } else {
                    this.vistaVehiculo.ComboMarca.setSelectedIndex(5);
                }
                this.vistaVehiculo.txtModelo.setText(String.valueOf(this.vistaVehiculo.tableVehiculo.getValueAt(fila, 2)));

            }
        }
        if (e.getButton() == 1)//boton izquierdo
        {

            int fila = this.vistaVehiculo.tablePropietario.rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.vistaVehiculo.txtIdClienteBuscar.setText(String.valueOf(this.vistaVehiculo.tablePropietario.getValueAt(fila, 0)));
                this.vistaVehiculo.txtNombreBuscar.setText(String.valueOf(this.vistaVehiculo.tablePropietario.getValueAt(fila, 1)));
                this.vistaVehiculo.txtTelefonoBuscar.setText(String.valueOf(this.vistaVehiculo.tablePropietario.getValueAt(fila, 2)));
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
                case __añadirVehiculo:
                    //añade un nuevo registro llamando al método del modeloVehiculo
                    //Se parsea previamente la opción escogida en el combobox y se recoge en una variable String
                    String marcaux = "";
                    if (this.vistaVehiculo.ComboMarca.getSelectedIndex() == -1) {

                        JOptionPane.showMessageDialog(vistaVehiculo, "Error: Debes elegir la marca del coche del desplegable");

                    } else {
                        Object x = this.vistaVehiculo.ComboMarca.getSelectedItem();
                        marcaux = String.valueOf(x);

                        if (this.vistaVehiculo.txtResultadoIdPropietario.getText().equals("IdCliente")) {
                            JOptionPane.showMessageDialog(vistaVehiculo, "Error: Debe identificar a un cliente ya registrado antes de añadir un nuevo vehículo");
                        } else if (this.modeloVehiculo.nuevoVehiculo(this.vistaVehiculo.txtMatricula.getText(),
                                marcaux, this.vistaVehiculo.txtModelo.getText(), Integer.parseInt(this.vistaVehiculo.txtIdClienteBuscar.getText())) == true) {
                            //Lanza mensaje de éxito
                            JOptionPane.showMessageDialog(vistaVehiculo, "Exito: Nuevo vehículo añadido.");
                            //Se actualiza la tabla cargada en vista con los cambios realizados
                            this.vistaVehiculo.tableVehiculo.setModel(modeloVehiculo.getTablaVehiculo());
                            //Se limpian los txtFields.
                            this.vistaVehiculo.txtMatricula.setText("");
                            this.vistaVehiculo.ComboMarca.getItemAt(-1);
                            this.vistaVehiculo.txtModelo.setText("");
                            this.vistaVehiculo.txtResultadoPropietario.setText("");
                            this.vistaVehiculo.txtResultadoIdPropietario.setText("IdCliente");
                            this.vistaVehiculo.txtResultadoPropietario.setText("nombre completo");
                        } else //ocurrio un error
                        {
                            JOptionPane.showMessageDialog(vistaVehiculo, "Error: No ha introducido todos los datos o éstos no están correctos.");
                        }

                    }

                    break;

                //Botón para buscar y añadir cliente
                case __buscarCliente:

                    this.vistaVehiculo.DialogBuscarCliente.setSize(650, 350);
                    int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.vistaVehiculo.DialogBuscarCliente.getWidth() / 2);
                    int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.vistaVehiculo.DialogBuscarCliente.getHeight() / 2);
                    this.vistaVehiculo.DialogBuscarCliente.setLocation(x, y);
                    //Se cierra la ventana principal y se abre el Dialog Específico
                    this.vistaVehiculo.DialogBuscarCliente.setVisible(true);
                    // this.vistaVehiculo.setVisible(false);

                    //Se limpian los campos de la vistaVehiculo para evitar que se guarden los datos de búsquedas anteriores.
                    this.vistaVehiculo.txtResultadoIdPropietario.setText("IdCliente");
                    this.vistaVehiculo.txtResultadoPropietario.setText("nombre completo");
                    break;

                case __buscar:

                    if (this.vistaVehiculo.txtSearch.getText().equals("") || this.vistaVehiculo.txtSearch.getText().equals("texto a buscar")) {
                        JOptionPane.showMessageDialog(vistaVehiculo.DialogBuscarCliente, "Error: Introduce el texto a buscar. Si no hay coincidencias la tabla aparecerá vacía");
                    } else {

                        String Search = this.vistaVehiculo.txtSearch.getText();

                        if (this.vistaVehiculo.comboBuscar.getSelectedIndex() == 0) {
                            //Metodo para buscar por nombre
                            this.vistaVehiculo.tablePropietario.setModel(this.modeloVehiculo.BuscarPropietarioNombre(Search));
                        } else {
                            //Llamada al metodo para buscar por telefono
                            this.vistaVehiculo.tablePropietario.setModel(this.modeloVehiculo.BuscarPropietarioTelefono(Search));
                        }
                    }

                    break;

                case __aceptarCliente:

                    if (this.vistaVehiculo.txtIdClienteBuscar.getText().equals("IdCliente")) {
                        JOptionPane.showMessageDialog(vistaVehiculo.DialogBuscarCliente, "Error: Debes elegir un registro para asignar el cliente al vehículo");
                    } else {
                        this.vistaVehiculo.txtResultadoIdPropietario.setText(this.vistaVehiculo.txtIdClienteBuscar.getText());
                        this.vistaVehiculo.txtResultadoPropietario.setText(this.vistaVehiculo.txtNombreBuscar.getText());
                        this.vistaVehiculo.txtMatricula.setText("");
                        this.vistaVehiculo.ComboMarca.getItemAt(-1);
                        this.vistaVehiculo.txtModelo.setText("");

                    }
                    this.vistaVehiculo.DialogBuscarCliente.setVisible(false);

                    break;

                case __volverCliente:
                    this.vistaVehiculo.DialogBuscarCliente.setVisible(false);
                    break;

                //Botón de modificar un vehiculo
                case __modificarVehiculo:

                    String matricula,
                     marca,
                     modelo;

                    if (this.vistaVehiculo.ComboMarca.getSelectedIndex() == -1) {

                        JOptionPane.showMessageDialog(vistaVehiculo, "Error: Debes elegir la marca del coche del desplegable");

                    } else {
                        Object xx = this.vistaVehiculo.ComboMarca.getSelectedItem();
                        marca = String.valueOf(xx);
                    }

                    //Aquí se obtiene los datos de los JtextField
                    matricula = this.vistaVehiculo.txtMatricula.getText();
                    modelo = this.vistaVehiculo.txtModelo.getText();
                    Object xx = this.vistaVehiculo.ComboMarca.getSelectedItem();
                    marca = String.valueOf(xx);

                    //Aquí se introducen en la consulta de la Base de datos
                    if (this.modeloVehiculo.modificarVehiculo(matricula, marca, modelo, matriculaaux)) {
                        JOptionPane.showMessageDialog(vistaVehiculo, "Exito: Registro modificado con éxito.");
                        this.vistaVehiculo.tableVehiculo.setModel(modeloVehiculo.getTablaVehiculo());
                        this.vistaVehiculo.txtMatricula.setText("");
                        this.vistaVehiculo.ComboMarca.getItemAt(-1);
                        this.vistaVehiculo.txtModelo.setText("");
                        this.vistaVehiculo.txtResultadoIdPropietario.setText("IdCliente");
                        this.vistaVehiculo.txtResultadoPropietario.setText("nombre completo");
                    } else {
                        JOptionPane.showMessageDialog(vistaVehiculo, "Error: algo salió mal.");
                    }
                    break;
                //Botón de eliminar cliente
                case __eliminarVehiculo:
                    if (this.modeloVehiculo.eliminarVehiculo(this.vistaVehiculo.txtMatricula.getText())) {
                        JOptionPane.showMessageDialog(vistaVehiculo, "Éxito: Se ha eliminado correctamente.");
                        this.vistaVehiculo.tableVehiculo.setModel(modeloVehiculo.getTablaVehiculo());
                        this.vistaVehiculo.txtMatricula.setText("");
                        this.vistaVehiculo.ComboMarca.getItemAt(-1);
                        this.vistaVehiculo.txtModelo.setText("");
                        this.vistaVehiculo.txtResultadoIdPropietario.setText("IdCliente");
                        this.vistaVehiculo.txtResultadoPropietario.setText("nombre completo");
                    } else {
                        JOptionPane.showMessageDialog(vistaVehiculo, "Error: No se ha podido eliminar.");
                    }
                    break;
                //Botón de limpiar campos del cliente
                case __limpiarVehiculo:
                    this.vistaVehiculo.txtMatricula.setText("");
                    this.vistaVehiculo.ComboMarca.getItemAt(-1);
                    this.vistaVehiculo.txtModelo.setText("");
                    this.vistaVehiculo.txtResultadoIdPropietario.setText("IdCliente");
                    this.vistaVehiculo.txtResultadoPropietario.setText("nombre completo");
                    break;
                //Botón de volver al menú home
                case __homeVehiculo:
                    this.vistaVehiculo.setVisible(false);
                    new controlador(new home()).iniciar();
                    break; //Fin del Switch case
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaVehiculo, "Faltan datos");
        }
    }
}
