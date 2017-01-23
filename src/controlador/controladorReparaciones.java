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
import modelo.modeloReparaciones;
import vista.home;
import vista.reparaciones;

/** 
 * Controlador de la vista reparaciones
 * @author Víctor. Proyecto taller.
 * @version 1.0
 */
public class controladorReparaciones implements ActionListener, MouseListener {

    //Se intancian la vista vehiculo y la home
    reparaciones vistaReparaciones;
    home vistaHome;
    // Variable matriculaux para que guarde el id anterior en las operaciones de modificación de un registro.
    int idreparacion;
    //Se crea un objeto del modelo vehiculo
    modeloReparaciones modeloReparaciones = new modeloReparaciones();

    String matriculaRecurrrente;
    String modeloRecurrente;
    String idMecanicoRecurrente;
    String nombreRecurrente;
    
    
    /**
     * Se declaran en un ENUM las acciones que se realizan desde la interfaz de
     * usuario Reparaciones y posterior ejecución desde el controladorReparaciones
     */
    public enum AccionCliente {
        // Enum Cliente

        __identificarVehiculo,
        __identificarMecanico,
        __buscarCoche,
        __buscarMecanico,
        __aceptarCoche,
        __aceptarMecanico,
        __volverCoche,
        __volverMecanico,
        __añadirReparaciones,
        __limpiarReparaciones,
        __modificarReparaciones,
        __eliminarReparaciones,
        __homeReparaciones,

    }

    /**
     * Constructor de clase
     *
     * @param vistaReparaciones interfaz de reparaciones
     */
    public controladorReparaciones(reparaciones vistaReparaciones) {
        this.vistaReparaciones = vistaReparaciones;

    }

    /**
     * Inicia el skin y las diferentes variables que se utilizan
     */
    public void iniciar() {
        // Skin tipo WINDOWS
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(vistaReparaciones);
            vistaReparaciones.setVisible(true);

        } catch (UnsupportedLookAndFeelException ex) {
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        }

        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnIdentificar1.setActionCommand("__identificarVehiculo");
        this.vistaReparaciones.btnIdentificar1.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnIdentificar2.setActionCommand("__identificarMecanico");
        this.vistaReparaciones.btnIdentificar2.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnLimpiarReparaciones.setActionCommand("__limpiarReparaciones");
        this.vistaReparaciones.btnLimpiarReparaciones.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnAgregarReparaciones.setActionCommand("__añadirReparaciones");
        this.vistaReparaciones.btnAgregarReparaciones.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnModificarReparaciones.setActionCommand("__modificarReparaciones");
        this.vistaReparaciones.btnModificarReparaciones.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnEliminarReparaciones.setActionCommand("__eliminarReparaciones");
        this.vistaReparaciones.btnEliminarReparaciones.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.homeReparaciones.setActionCommand("__homeReparaciones");
        this.vistaReparaciones.homeReparaciones.addActionListener(this);
        
        //JDIALOGBVEHICULO
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnBuscarVehiculoReparaciones.setActionCommand("__buscarCoche");
        this.vistaReparaciones.btnBuscarVehiculoReparaciones.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnAceptarVehiculoReparaciones.setActionCommand("__aceptarCoche");
        this.vistaReparaciones.btnAceptarVehiculoReparaciones.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnVolverBuscarVehiculo.setActionCommand("__volverCoche");
        this.vistaReparaciones.btnVolverBuscarVehiculo.addActionListener(this);
        
        
        
        //JDIALOGBMECANICO
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnBuscarMecanico.setActionCommand("__buscarMecanico");
        this.vistaReparaciones.btnBuscarMecanico.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnAceptarMecanico.setActionCommand("__aceptarMecanico");
        this.vistaReparaciones.btnAceptarMecanico.addActionListener(this);
        //declara una acción y añade un escucha al evento producido por el componente
        this.vistaReparaciones.btnVolverBMecanico.setActionCommand("__volverMecanico");
        this.vistaReparaciones.btnVolverBMecanico.addActionListener(this);
        
        
        
        //añade e inicia el jtable con un DefaultTableModel vacio
        this.vistaReparaciones.tableReparaciones.addMouseListener(this);
        this.vistaReparaciones.tableReparaciones.setModel(modeloReparaciones.getTablaReparaciones());

        this.vistaReparaciones.tableVehiculoReparaciones.addMouseListener(this);
        this.vistaReparaciones.tableVehiculoReparaciones.setModel(modeloReparaciones.gettableVehiculo());

        this.vistaReparaciones.tableBMecanico.addMouseListener(this);
        this.vistaReparaciones.tableBMecanico.setModel(modeloReparaciones.gettableBMecanico());
    }

    /**
     * Método para cuando se clickea obtenga los datos pulsados en los
     * JTextField
     *
     * @param e e con el evento de clickear con el ratón
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        
        
        
        if (e.getButton() == 1) //boton izquierdo
        {
            int fila = this.vistaReparaciones.tableReparaciones.rowAtPoint(e.getPoint());
            if (fila > -1) {
                idreparacion = Integer.parseInt(String.valueOf(this.vistaReparaciones.tableReparaciones.getValueAt(fila, 0)));
                this.vistaReparaciones.txtMatriculaReparaciones.setText(String.valueOf(this.vistaReparaciones.tableReparaciones.getValueAt(fila, 1)));
                this.vistaReparaciones.txtIdMecanicoReparaciones.setText(String.valueOf(this.vistaReparaciones.tableReparaciones.getValueAt(fila, 2)));
                this.vistaReparaciones.txtDiagnostico.setText(String.valueOf(this.vistaReparaciones.tableReparaciones.getValueAt(fila, 3)));
                this.vistaReparaciones.txtCoste.setText(String.valueOf(this.vistaReparaciones.tableReparaciones.getValueAt(fila, 4)));

            }
        }
        if (e.getButton() == 1)//boton izquierdo
        {

            int fila = this.vistaReparaciones.tableVehiculoReparaciones.rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.vistaReparaciones.txtMatriculaVehiculoReparaciones.setText(String.valueOf(this.vistaReparaciones.tableVehiculoReparaciones.getValueAt(fila, 0)));
                this.vistaReparaciones.txtModeloVehiculoReparaciones.setText(String.valueOf(this.vistaReparaciones.tableVehiculoReparaciones.getValueAt(fila, 3)));
            }
        }

        if (e.getButton() == 1)//boton izquierdo
        {

            int fila = this.vistaReparaciones.tableBMecanico.rowAtPoint(e.getPoint());
            if (fila > -1) {
                this.vistaReparaciones.txtIdMecanico.setText(String.valueOf(this.vistaReparaciones.tableBMecanico.getValueAt(fila, 0)));
                this.vistaReparaciones.txtNombreMecanico.setText(String.valueOf(this.vistaReparaciones.tableBMecanico.getValueAt(fila, 1)));
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

                //Botón de añadir reparaciones
                //Botón para buscar y añadir cliente
                case __identificarVehiculo:

                    this.vistaReparaciones.DialogBVehiculo.setSize(650, 350);
                    int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.vistaReparaciones.DialogBVehiculo.getWidth() / 2);
                    int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.vistaReparaciones.DialogBVehiculo.getHeight() / 2);
                    this.vistaReparaciones.DialogBVehiculo.setLocation(x, y);
                    //Se cierra la ventana principal y se abre el Dialog Específico
                    this.vistaReparaciones.DialogBVehiculo.setVisible(true);
                    // this.vistaVehiculo.setVisible(false);

                    //Se limpian los campos de la vistaVehiculo para evitar que se guarden los datos de búsquedas anteriores.
                    this.vistaReparaciones.txtMatriculaReparaciones.setText("");
                    this.vistaReparaciones.txtModeloReparaciones.setText("");
                    break;

                case __buscarCoche:

                    if (this.vistaReparaciones.txtBuscarMatriculaReparaciones.getText().equals("") || this.vistaReparaciones.txtBuscarMatriculaReparaciones.getText().equals("texto a buscar")) {
                        JOptionPane.showMessageDialog(vistaReparaciones.DialogBVehiculo, "Error: Introduce el texto a buscar. Si no hay coincidencias la tabla aparecerá vacía");
                    } else {
                        this.vistaReparaciones.tableVehiculoReparaciones.setModel(
                                this.modeloReparaciones.gettableVehiculoReparaciones(
                                        this.vistaReparaciones.txtBuscarMatriculaReparaciones.getText()));
                    }

                    break;

                case __aceptarCoche:

                    if (this.vistaReparaciones.txtModeloVehiculoReparaciones.getText().equals("Modelo")) {
                        JOptionPane.showMessageDialog(vistaReparaciones.DialogBVehiculo, "Error: Debes seleccionar un registro de la tabla para continuar");
                    } else {
                        
                        matriculaRecurrrente = this.vistaReparaciones.txtMatriculaVehiculoReparaciones.getText();
                        modeloRecurrente = this.vistaReparaciones.txtModeloVehiculoReparaciones.getText();
                        
                        
//                        this.vistaReparaciones.txtMatriculaReparaciones.setText(this.vistaReparaciones.txtMatriculaVehiculoReparaciones.getText());
//                        this.vistaReparaciones.txtModeloReparaciones.setText(this.vistaReparaciones.txtModeloVehiculoReparaciones.getText());
//                        matriculaRecurrrente = this.vistaReparaciones.txtMatriculaVehiculoReparaciones.getText();
//                        modeloRecurrente = this.vistaReparaciones.txtModeloVehiculoReparaciones.getText();
                       
                        
                        this.vistaReparaciones.txtIdMecanicoReparaciones.setText(idMecanicoRecurrente);
                        this.vistaReparaciones.txtNombreMecanicoReparaciones.setText(nombreRecurrente);
                        this.vistaReparaciones.txtMatriculaReparaciones.setText(matriculaRecurrrente);
                        this.vistaReparaciones.txtModeloReparaciones.setText(modeloRecurrente);
                        
                        this.vistaReparaciones.txtDiagnostico.setText("");
                        this.vistaReparaciones.txtCoste.setText("0");
                    }
                    this.vistaReparaciones.DialogBVehiculo.setVisible(false);

                    break;

                case __volverCoche:
                    this.vistaReparaciones.DialogBVehiculo.setVisible(false);
                    break;

                case __identificarMecanico:

                    this.vistaReparaciones.DialogBMecanico.setSize(650, 350);
                    int xx = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 - this.vistaReparaciones.DialogBMecanico.getWidth() / 2);
                    int yy = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2 - this.vistaReparaciones.DialogBMecanico.getHeight() / 2);
                    this.vistaReparaciones.DialogBMecanico.setLocation(xx, yy);
                    //Se cierra la ventana principal y se abre el Dialog Específico
                    this.vistaReparaciones.DialogBMecanico.setVisible(true);
                    // this.vistaVehiculo.setVisible(false);

                    //Se limpian los campos de la vistaVehiculo para evitar que se guarden los datos de búsquedas anteriores.
                    this.vistaReparaciones.txtMatriculaReparaciones.setText("");
                    this.vistaReparaciones.txtModeloReparaciones.setText("");
                    break;

                case __buscarMecanico:

                    if (this.vistaReparaciones.txtSearch1.getText().equals("") || this.vistaReparaciones.txtSearch1.getText().equals("texto a buscar")) {
                        JOptionPane.showMessageDialog(vistaReparaciones.DialogBMecanico, "Error: Introduce el texto a buscar. Si no hay coincidencias la tabla aparecerá vacía");
                    } else {
                        String aux = this.vistaReparaciones.txtSearch1.getText();
                        int id = Integer.parseInt(this.vistaReparaciones.txtSearch1.getText());
                        if (this.vistaReparaciones.comboBuscarMecanico.getSelectedIndex() == 0) {
                            //Metodo para buscar por nombre
                            this.vistaReparaciones.tableBMecanico.setModel(this.modeloReparaciones.gettableBMecanicoNombre(aux));
                        } else {
                            //Llamada al metodo para buscar por telefono
                            this.vistaReparaciones.tableBMecanico.setModel(this.modeloReparaciones.gettableBMecanicoId(id));
                        }
                    }

                    break;

                case __aceptarMecanico:

                    if (this.vistaReparaciones.txtNombreMecanico.getText().equals("Nombre completo")) {
                        JOptionPane.showMessageDialog(vistaReparaciones.DialogBMecanico, "Error: Debes seleccionar un registro de la tabla para continuar");
                    } else {
                        //Asignamos a una variables los valores.
                        idMecanicoRecurrente = this.vistaReparaciones.txtIdMecanico.getText();
                        nombreRecurrente = this.vistaReparaciones.txtNombreMecanico.getText();
                        
                        
                        //Los aplicamos a los elementos textfield
                        this.vistaReparaciones.txtIdMecanicoReparaciones.setText(idMecanicoRecurrente);
                        this.vistaReparaciones.txtNombreMecanicoReparaciones.setText(nombreRecurrente);
                        this.vistaReparaciones.txtMatriculaReparaciones.setText(matriculaRecurrrente);
                        this.vistaReparaciones.txtModeloReparaciones.setText(modeloRecurrente);
                        
                        
                        this.vistaReparaciones.txtDiagnostico.setText("");
                        this.vistaReparaciones.txtCoste.setText("0");
                    }
                    this.vistaReparaciones.DialogBMecanico.setVisible(false);

                    break;

                case __volverMecanico:
                    this.vistaReparaciones.DialogBMecanico.setVisible(false);
                    break;

                case __añadirReparaciones:
                    //añade un nuevo registro llamando al método del modeloVehiculo
                    //Se parsea previamente la opción escogida en el combobox y se recoge en una variable String

                    if (this.vistaReparaciones.txtMatriculaReparaciones.getText().equals("")) {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: Debe identificar a un vehículo a través del botón superior.");
                    } else if (this.vistaReparaciones.txtIdMecanicoReparaciones.getText().equals("")) {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: Debe identificar a un mecánico a través del botón superior.");
                    } else if (this.vistaReparaciones.txtDiagnostico.getText().equals("")) {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: Debe identificar a un diagnóstico del problema del coche.");
                    } else if (this.vistaReparaciones.txtCoste.getText().equals("")) {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: Debe identificar el coste de la reparación.");
                    } else if (this.modeloReparaciones.nuevaReparacion(this.vistaReparaciones.txtMatriculaReparaciones.getText(),
                            Integer.parseInt(this.vistaReparaciones.txtIdMecanicoReparaciones.getText()),
                            this.vistaReparaciones.txtDiagnostico.getText(),
                            Integer.parseInt(this.vistaReparaciones.txtCoste.getText())) == true) {
                        
                        this.vistaReparaciones.tableReparaciones.setModel(this.modeloReparaciones.getTablaReparaciones());
                        
                        JOptionPane.showMessageDialog(vistaReparaciones, "Exito: Nueva reparación añadida.");
                        this.vistaReparaciones.txtMatriculaReparaciones.setText("");
                        this.vistaReparaciones.txtModeloReparaciones.setText("");
                        this.vistaReparaciones.txtIdMecanicoReparaciones.setText("");
                        this.vistaReparaciones.txtNombreMecanicoReparaciones.setText("");
                        this.vistaReparaciones.txtDiagnostico.setText("");
                        this.vistaReparaciones.txtCoste.setText("");
                    } else {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: No ha introducido todos los datos o éstos no están correctos.");
                    }

                    break;

                //Botón de modificar un vehiculo
                case __modificarReparaciones:

                    String matricula = "";
                    int idmecanico = 0;
                    int coste = 0;
                    String diagnostico = "";

                    if (this.vistaReparaciones.txtDiagnostico.getText().equals("") || this.vistaReparaciones.txtCoste.getText().equals("")
                            || this.vistaReparaciones.txtMatriculaReparaciones.getText().equals("") || this.vistaReparaciones.txtIdMecanicoReparaciones.getText().equals("")) {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: faltan campos por rellenar.");
                    } else {

                        matricula = this.vistaReparaciones.txtMatriculaReparaciones.getText();
                        idmecanico = Integer.parseInt(this.vistaReparaciones.txtIdMecanicoReparaciones.getText());
                        diagnostico = this.vistaReparaciones.txtDiagnostico.getText();
                        coste = Integer.parseInt(this.vistaReparaciones.txtCoste.getText());

                    }

                    //Aquí se introducen en la consulta de la Base de datos
                    if (this.modeloReparaciones.modificarReparaciones(idreparacion, matricula, idmecanico, diagnostico, coste) == true) {
                        this.vistaReparaciones.tableReparaciones.setModel(this.modeloReparaciones.getTablaReparaciones());
                        JOptionPane.showMessageDialog(vistaReparaciones, "Exito: Registro modificado con éxito.");
                        this.vistaReparaciones.txtMatriculaReparaciones.setText("");
                        this.vistaReparaciones.txtModeloReparaciones.setText("");
                        this.vistaReparaciones.txtIdMecanicoReparaciones.setText("");
                        this.vistaReparaciones.txtNombreMecanicoReparaciones.setText("");
                        this.vistaReparaciones.txtDiagnostico.setText("");
                        this.vistaReparaciones.txtCoste.setText("");
                    } else {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: algo salió mal.");
                    }
                    break;
                //Botón de eliminar cliente
                case __eliminarReparaciones:

                    //FALTA METODO
                    if (this.modeloReparaciones.eliminarReparaciones(idreparacion) == true) {
                        
                        this.vistaReparaciones.tableReparaciones.setModel(this.modeloReparaciones.getTablaReparaciones());
                        JOptionPane.showMessageDialog(vistaReparaciones, "Éxito: Se ha eliminado el registro correctamente.");
                        this.vistaReparaciones.txtMatriculaReparaciones.setText("");
                        this.vistaReparaciones.txtModeloReparaciones.setText("");
                        this.vistaReparaciones.txtIdMecanicoReparaciones.setText("");
                        this.vistaReparaciones.txtNombreMecanicoReparaciones.setText("");
                        this.vistaReparaciones.txtDiagnostico.setText("");
                        this.vistaReparaciones.txtCoste.setText("");
                    } else {
                        JOptionPane.showMessageDialog(vistaReparaciones, "Error: No se ha podido eliminar.");
                    }
                    break;
                //Botón de limpiar campos del cliente
                case __limpiarReparaciones:
                    this.vistaReparaciones.txtMatriculaReparaciones.setText("");
                    this.vistaReparaciones.txtModeloReparaciones.setText("");
                    this.vistaReparaciones.txtIdMecanicoReparaciones.setText("");
                    this.vistaReparaciones.txtNombreMecanicoReparaciones.setText("");
                    this.vistaReparaciones.txtDiagnostico.setText("");
                    this.vistaReparaciones.txtCoste.setText("");
                    break;
                //Botón de volver al menú home
                case __homeReparaciones:
                    this.vistaReparaciones.setVisible(false);
                    new controlador(new home()).iniciar();
                    break; //Fin del Switch case
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vistaReparaciones, "Error en la entrada de datos");
        }
    }
}
