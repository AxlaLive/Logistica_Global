package ui;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane; 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

import controladores.GuardarEnvioListener; 
import controladores.QuitarEnvioListener;   
import componentes.PanelDatosEnvio; 

import modelos.Envio;
import servicios.Logistica; 

public class FrmLogisticaGlobal extends JFrame {
    
    private JTable tblEnvios;
    private DefaultTableModel model;
    private Logistica gestorLogistica; 
    private PanelDatosEnvio pnlDatos; 
    private ModoUI modoActual = ModoUI.NUEVO_REGISTRO;
    private String codigoOriginalEdicion = null; 

    public FrmLogisticaGlobal() {
        setSize(800, 500);
        setTitle("Operador Logístico");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        gestorLogistica = new Logistica(); 

        JToolBar tbOperaciones = new JToolBar();
        tbOperaciones.setFloatable(false);
        
        JButton btnEditar = new JButton("Editar Envío");
        btnEditar.setToolTipText("Carga el envío seleccionado en el formulario para modificarlo.");
        ImageIcon iconoEditar = crearIcono("editar.png"); 
        if (iconoEditar != null) {
            btnEditar.setIcon(iconoEditar);
        }
        btnEditar.addActionListener(e -> cargarEnvioSeleccionadoEnFormulario()); 
        tbOperaciones.add(btnEditar);
        
        JButton btnQuitar = new JButton("Eliminar Envío");
        btnQuitar.setToolTipText("Eliminar Envío Seleccionado");
        ImageIcon iconoQuitar = crearIcono("eliminar.png"); 
        if (iconoQuitar != null) {
            btnQuitar.setIcon(iconoQuitar);
        }
        btnQuitar.addActionListener(new QuitarEnvioListener(this, gestorLogistica));
        tbOperaciones.add(btnQuitar);
        pnlDatos = new PanelDatosEnvio(this); 
        pnlDatos.getBtnGuardar().addActionListener(new GuardarEnvioListener(this, gestorLogistica));

        JPanel pnlNorthContainer = new JPanel();
        pnlNorthContainer.setLayout(new BoxLayout(pnlNorthContainer, BoxLayout.Y_AXIS));
        pnlNorthContainer.add(tbOperaciones);
        pnlNorthContainer.add(pnlDatos);
        getContentPane().add(pnlNorthContainer, BorderLayout.NORTH);

        String[] columnas = {"Tipo", "Código", "Cliente", "Peso", "Distancia", "Costo"};
        model = new DefaultTableModel(columnas, 0); 
        tblEnvios = new JTable(model);

        tblEnvios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { 
                    cargarEnvioSeleccionadoEnFormulario();
                }
            }
        });
        
        JScrollPane spListaEnvios = new JScrollPane(tblEnvios);
        getContentPane().add(spListaEnvios, BorderLayout.CENTER);
        cargarEnviosIniciales(); 
        setVisible(true);
    }

    private ImageIcon crearIcono(String nombreArchivo) {
        try {
            java.net.URL imgURL = getClass().getResource("/iconos/" + nombreArchivo);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                java.awt.Image img = originalIcon.getImage();
                java.awt.Image resizedImg = img.getScaledInstance(
                    32, 
                    32,
                    java.awt.Image.SCALE_SMOOTH 
                );
                return new ImageIcon(resizedImg);
            } else {
                System.err.println("Error de recurso: Icono no encontrado en /iconos/" + nombreArchivo);
                return null; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JTextField getTxtNumero() { return pnlDatos.getTxtNumero(); }
    public JTextField getTxtCliente() { return pnlDatos.getTxtCliente(); }
    public JTextField getTxtPeso() { return pnlDatos.getTxtPeso(); }
    public JTextField getTxtDistancia() { return pnlDatos.getTxtDistancia(); }
    public JComboBox<String> getCmbTipo() { return pnlDatos.getCmbTipo(); }
    public JTable getTblEnvios() { return tblEnvios; }
    public String getCodigoOriginalEdicion() { return codigoOriginalEdicion; }
    public void setCodigoOriginalEdicion(String codigo) { codigoOriginalEdicion = codigo; }
    public ModoUI getModoActual() { return modoActual; }
    
    public void limpiarCampos() {
        getTxtNumero().setText("");
        getTxtCliente().setText("");
        getTxtPeso().setText("");
        getTxtDistancia().setText("");
        getCmbTipo().setSelectedIndex(0);        
        setCodigoOriginalEdicion(null); 

        this.modoActual = ModoUI.NUEVO_REGISTRO; 
        
        getTxtNumero().setEditable(true); 
        getTxtNumero().setEnabled(true); 
        getTxtNumero().requestFocusInWindow(); 
    }

    private void cargarEnvioSeleccionadoEnFormulario() {
        int filaSeleccionada = tblEnvios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un envío de la lista.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        String codigoEnvio = (String) model.getValueAt(filaSeleccionada, 1);
        Envio envioAEditar = gestorLogistica.buscarEnvioPorCodigo(codigoEnvio);
        if (envioAEditar != null) {
            setCodigoOriginalEdicion(codigoEnvio); 
 
            this.modoActual = ModoUI.EDICION; 
            
            getTxtNumero().setText(envioAEditar.getCodigo());
            getTxtCliente().setText(envioAEditar.getCliente());
            getTxtPeso().setText(String.valueOf(envioAEditar.getPeso()));
            getTxtDistancia().setText(String.valueOf(envioAEditar.getDistancia()));
            getCmbTipo().setSelectedItem(envioAEditar.getTipo());
            
            getTxtNumero().setEditable(true);
            getTxtNumero().setEnabled(true); 

            JOptionPane.showMessageDialog(this, "Modifique los campos y presione [Guardar].", "Modo Edición Activado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void recargarTabla() {
        model.setRowCount(0); 
        for (Envio e : gestorLogistica.getListaEnvios()) {
            double costo = e.calcularTarifa();
            Object[] fila = {
                e.getTipo(),
                e.getCodigo(),
                e.getCliente(),
                String.format("%.1f", e.getPeso()),
                String.format("%.1f", e.getDistancia()),
                String.format("%.2f", costo)
            };
            model.addRow(fila);
        }
    }

    public void actualizarTablaConNuevoEnvio(String tipo, String codigo, String cliente, String peso, String distancia, String costo) {
        Object[] nuevaFila = {tipo, codigo, cliente, peso, distancia, costo};
        model.addRow(nuevaFila);
    }

    private void cargarEnviosIniciales() {
         model.setRowCount(0);
         for (Envio e : gestorLogistica.getListaEnvios()) {
            double costo = e.calcularTarifa();
            Object[] fila = {
                e.getTipo(),
                e.getCodigo(),
                e.getCliente(),
                String.format("%.1f", e.getPeso()),
                String.format("%.1f", e.getDistancia()),
                String.format("%.2f", costo)
            };
            model.addRow(fila);
        }
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new FrmLogisticaGlobal();
        });
    }
}