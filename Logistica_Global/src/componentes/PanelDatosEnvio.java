package componentes; 

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComponent;

import javax.swing.text.AbstractDocument; 
import javax.swing.text.DocumentFilter; 
import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;

import ui.FrmLogisticaGlobal; 

public class PanelDatosEnvio extends JPanel {
    
    private JTextField txtNumero, txtCliente, txtPeso, txtDistancia;
    private JComboBox<String> cmbTipo;
    private JButton btnGuardar, btnCancelar;
    
    private FrmLogisticaGlobal framePrincipal; 

    public PanelDatosEnvio(FrmLogisticaGlobal frame) {
        this.framePrincipal = frame;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setLayout(null); 
        setPreferredSize(new Dimension(800, 150)); 
        
        int labelX = 20, labelW = 120, textX = 150;
        int col2X = 350, col2TextX = 510;
        int row1Y = 20, row2Y = 60, row3Y = 100;
        int fieldH = 25;
 
        txtNumero = new JTextField();
        aplicarFiltroNumerico(txtNumero); 
        addLabelAndField("Número", labelX, row1Y, labelW, txtNumero, textX, row1Y);
        
        cmbTipo = new JComboBox<>(new String[]{"Terrestre", "Aéreo", "Marítimo"});
        addLabelAndField("Tipo", col2X, row1Y, labelW, cmbTipo, col2TextX, row1Y);

        txtCliente = new JTextField();
        addLabelAndField("Cliente", labelX, row2Y, labelW, txtCliente, textX, row2Y);
        
        txtDistancia = new JTextField();
        addLabelAndField("Distancia en Km", col2X, row2Y, labelW, txtDistancia, col2TextX, row2Y);

        txtPeso = new JTextField();
        addLabelAndField("Peso", labelX, row3Y, labelW, txtPeso, textX, row3Y);
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(col2TextX - 100, row3Y, 100, fieldH);
        add(btnGuardar); 
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(col2TextX + 10, row3Y, 100, fieldH);
        btnCancelar.addActionListener(e -> framePrincipal.limpiarCampos());
        add(btnCancelar);
    }
    
    private void addLabelAndField(String labelText, int labelX, int y, int labelW, JComponent component, int componentX, int componentY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(labelX, y, labelW, 25);
        add(label);
        component.setBounds(componentX, componentY, 150, 25);
        add(component);
    }
    
    private void aplicarFiltroNumerico(JTextField field) {
        AbstractDocument doc = (AbstractDocument) field.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null || string.isEmpty() || string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    return;
                }
                if (text.isEmpty() || text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    public JTextField getTxtNumero() { return txtNumero; }
    public JTextField getTxtCliente() { return txtCliente; }
    public JTextField getTxtPeso() { return txtPeso; }
    public JTextField getTxtDistancia() { return txtDistancia; }
    public JComboBox<String> getCmbTipo() { return cmbTipo; }
    public JButton getBtnGuardar() { return btnGuardar; }
}