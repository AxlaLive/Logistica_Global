import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel; 

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector; 

public class FrmLogisticaGlobal extends JFrame {
    private JTextField txtNumero, txtCliente, txtPeso, txtDistancia;
    private JComboBox<String> cmbTipo;
    private JTable tblEnvios;
    private JPanel pnlEntradaDatos;
    private DefaultTableModel model; 

    public FrmLogisticaGlobal() {
        setSize(800, 500);
        setTitle("Operador Logístico");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
        getContentPane().setLayout(new BorderLayout());

        JToolBar tbOperaciones = new JToolBar();
        tbOperaciones.setFloatable(false);

        JButton btnAgregar = new JButton("➕ Envío");
        btnAgregar.setToolTipText("Agregar Nuevo Envío");
        btnAgregar.addActionListener(e -> System.out.println("Agregar Envío Clicked"));
        tbOperaciones.add(btnAgregar);

        JButton btnQuitar = new JButton("➖ Envío");
        btnQuitar.setToolTipText("Quitar Envío Seleccionado");
        btnQuitar.addActionListener(e -> System.out.println("Quitar Envío Clicked"));
        tbOperaciones.add(btnQuitar);

        pnlEntradaDatos = new JPanel();
        pnlEntradaDatos.setLayout(null); 
        pnlEntradaDatos.setPreferredSize(new Dimension(getWidth(), 150));

        int labelX = 20, textX = 150, col2X = 350, col2TextX = 510, row1Y = 20, row2Y = 60, row3Y = 100, fieldH = 25;
        
        addLabelAndField(pnlEntradaDatos, "Número", labelX, row1Y, txtNumero = new JTextField(), textX, row1Y);
        cmbTipo = new JComboBox<>(new String[]{"Marítimo", "Terrestre", "Aéreo"});
        cmbTipo.setSelectedItem("Marítimo");
        addLabelAndField(pnlEntradaDatos, "Tipo", col2X, row1Y, cmbTipo, col2TextX, row1Y);

        addLabelAndField(pnlEntradaDatos, "Cliente", labelX, row2Y, txtCliente = new JTextField(), textX, row2Y);
        addLabelAndField(pnlEntradaDatos, "Distancia en Km", col2X, row2Y, txtDistancia = new JTextField(), col2TextX, row2Y);

        addLabelAndField(pnlEntradaDatos, "Peso", labelX, row3Y, txtPeso = new JTextField(), textX, row3Y);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(col2TextX - 100, row3Y, 100, fieldH);

        btnGuardar.addActionListener(e -> agregarEnvio(
            (String) cmbTipo.getSelectedItem(),
            txtNumero.getText(),
            txtCliente.getText(),
            txtPeso.getText(),
            txtDistancia.getText(),
            "0.0" 
        ));
        pnlEntradaDatos.add(btnGuardar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(col2TextX + 10, row3Y, 100, fieldH);
        btnCancelar.addActionListener(e -> System.out.println("Cancelar Clicked"));
        pnlEntradaDatos.add(btnCancelar);

        JPanel pnlNorthContainer = new JPanel();
        pnlNorthContainer.setLayout(new BoxLayout(pnlNorthContainer, BoxLayout.Y_AXIS));
        pnlNorthContainer.add(tbOperaciones);
        pnlNorthContainer.add(pnlEntradaDatos);
        getContentPane().add(pnlNorthContainer, BorderLayout.NORTH);
        
        String[] columnas = {"Tipo", "Código", "Cliente", "Peso", "Distancia", "Costo"};
        model = new DefaultTableModel(columnas, 0); 

        tblEnvios = new JTable(model);
        JScrollPane spListaEnvios = new JScrollPane(tblEnvios);

        getContentPane().add(spListaEnvios, BorderLayout.CENTER);
        setVisible(true);
    }
    
    public void agregarEnvio(String tipo, String codigo, String cliente, String peso, String distancia, String costo) {
        Object[] nuevaFila = {tipo, codigo, cliente, peso, distancia, costo};
        
        model.addRow(nuevaFila);
        
        txtNumero.setText("");
        txtCliente.setText("");
        txtPeso.setText("");
        txtDistancia.setText("");
        cmbTipo.setSelectedIndex(0);
    }

    private void addLabelAndField(JPanel panel, String labelText, int labelX, int y, JComboBox<String> cmb, int componentX, int componentY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(labelX, y, 120, 25);
        panel.add(label);
        
        cmb.setBounds(componentX, componentY, 150, 25);
        panel.add(cmb);
    }
    
    private void addLabelAndField(JPanel panel, String labelText, int labelX, int y, JTextField field, int componentX, int componentY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(labelX, y, 120, 25);
        panel.add(label);
        
        field.setBounds(componentX, componentY, 150, 25);
        panel.add(field);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new FrmLogisticaGlobal();
        });
    }
}