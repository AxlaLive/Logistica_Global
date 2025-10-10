package ui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; 
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

import modelos.Envio;
import modelos.Terrestre;
import modelos.Aereo;
import modelos.Maritimo;
import servicios.Logistica; 

public class FrmLogisticaGlobal extends JFrame {
    private JTextField txtNumero, txtCliente, txtPeso, txtDistancia;
    private JComboBox<String> cmbTipo;
    private JTable tblEnvios;
    private JPanel pnlEntradaDatos;
    private DefaultTableModel model;
    private Logistica gestorLogistica; 

    public FrmLogisticaGlobal() {
        setSize(800, 500);
        setTitle("Operador Logístico");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        gestorLogistica = new Logistica(); 

        JToolBar tbOperaciones = new JToolBar();
        tbOperaciones.setFloatable(false);

        JButton btnAgregar = new JButton("➕ Envío");
        btnAgregar.setToolTipText("Agregar Nuevo Envío");

        btnAgregar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad 'Agregar' ejecutada.")); 
        tbOperaciones.add(btnAgregar);

        JButton btnQuitar = new JButton("➖ Envío");
        btnQuitar.setToolTipText("Quitar Envío Seleccionado");

        btnQuitar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad 'Quitar' ejecutada."));
        tbOperaciones.add(btnQuitar);

        pnlEntradaDatos = new JPanel();
        pnlEntradaDatos.setLayout(null);
        pnlEntradaDatos.setPreferredSize(new Dimension(getWidth(), 150));


        int labelX = 20, 
            labelW = 120,      
            textX = 150, 
            col2X = 350, 
            col2TextX = 510, 
            row1Y = 20, 
            row2Y = 60, 
            row3Y = 100, 
            fieldH = 25;
        
        addLabelAndField(pnlEntradaDatos, "Número", labelX, row1Y, labelW, txtNumero = new JTextField(), textX, row1Y);
        cmbTipo = new JComboBox<>(new String[]{"Terrestre", "Aéreo", "Marítimo"});
        cmbTipo.setSelectedItem("Marítimo");
        addLabelAndField(pnlEntradaDatos, "Tipo", col2X, row1Y, labelW, cmbTipo, col2TextX, row1Y);

        addLabelAndField(pnlEntradaDatos, "Cliente", labelX, row2Y, labelW, txtCliente = new JTextField(), textX, row2Y);
        addLabelAndField(pnlEntradaDatos, "Distancia en Km", col2X, row2Y, labelW, txtDistancia = new JTextField(), col2TextX, row2Y);

        addLabelAndField(pnlEntradaDatos, "Peso", labelX, row3Y, labelW, txtPeso = new JTextField(), textX, row3Y);
        
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(col2TextX - 100, row3Y, 100, fieldH);

        btnGuardar.addActionListener(e -> {
            try {
                String tipo = (String) cmbTipo.getSelectedItem();
                String codigo = txtNumero.getText();
                String cliente = txtCliente.getText();
                double peso = Double.parseDouble(txtPeso.getText());
                double distancia = Double.parseDouble(txtDistancia.getText());
                
                Envio nuevoEnvio = null;

                switch (tipo) {
                    case "Terrestre":
                        nuevoEnvio = new Terrestre(codigo, cliente, peso, distancia);
                        break;
                    case "Aéreo":
                        nuevoEnvio = new Aereo(codigo, cliente, peso, distancia);
                        break;
                    case "Marítimo":
                        nuevoEnvio = new Maritimo(codigo, cliente, peso, distancia);
                        break;
                }

                if (gestorLogistica.agregarEnvio(nuevoEnvio)) {
                    double costo = nuevoEnvio.calcularTarifa();
                  
                    agregarEnvio(
                    nuevoEnvio.getTipo(),
                    nuevoEnvio.getCodigo(),
                    nuevoEnvio.getCliente(),
                    String.format("%.1f", nuevoEnvio.getPeso()),
                    String.format("%.1f", nuevoEnvio.getDistancia()),
                    String.format("%.2f", costo)
                );

                txtNumero.setText("");
                txtCliente.setText("");
                txtPeso.setText("");
                txtDistancia.setText("");
                }
                else {
            JOptionPane.showMessageDialog(this, "Error: Ya existe un envío con el código " + codigo, "Código Duplicado", JOptionPane.WARNING_MESSAGE);
        }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error: Peso y Distancia deben ser números válidos.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            }
        });

        pnlEntradaDatos.add(btnGuardar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(col2TextX + 10, row3Y, 100, fieldH);
        btnCancelar.addActionListener(e -> {
            txtNumero.setText("");
            txtCliente.setText("");
            txtPeso.setText("");
            txtDistancia.setText("");
            cmbTipo.setSelectedIndex(0);
        });
        pnlEntradaDatos.add(btnCancelar);

        JPanel pnlNorthContainer = new JPanel();
        pnlNorthContainer.setLayout(new BoxLayout(pnlNorthContainer, BoxLayout.Y_AXIS));
        pnlNorthContainer.add(tbOperaciones);
        pnlNorthContainer.add(pnlEntradaDatos);
        getContentPane().add(pnlNorthContainer, BorderLayout.NORTH);
        
        // --- 3. JTable (Lista de Envíos) ---
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
    }

    private void addLabelAndField(JPanel panel, String labelText, int labelX, int y, int labelW, JComboBox<String> cmb, int componentX, int componentY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(labelX, y, labelW, 25);
        panel.add(label);
        
        cmb.setBounds(componentX, componentY, 150, 25);
        panel.add(cmb);
    }
    
    private void addLabelAndField(JPanel panel, String labelText, int labelX, int y, int labelW, JTextField field, int componentX, int componentY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(labelX, y, labelW, 25);
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