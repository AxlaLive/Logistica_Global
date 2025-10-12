package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelos.Aereo;
import modelos.Envio;
import modelos.Maritimo;
import modelos.Terrestre;
import servicios.Logistica;
import ui.FrmLogisticaGlobal;

public class GuardarEnvioListener implements ActionListener {
    
    private FrmLogisticaGlobal frame;
    private Logistica gestorLogistica;
    
    public GuardarEnvioListener(FrmLogisticaGlobal frame, Logistica gestor) {
        this.frame = frame;
        this.gestorLogistica = gestor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String tipo = (String) frame.getCmbTipo().getSelectedItem();
            String codigo = frame.getTxtNumero().getText();
            String cliente = frame.getTxtCliente().getText();
            double peso = Double.parseDouble(frame.getTxtPeso().getText()); 
            double distancia = Double.parseDouble(frame.getTxtDistancia().getText());
            String codigoOriginalEdicion = frame.getCodigoOriginalEdicion();

            if (codigoOriginalEdicion != null) {
                if (gestorLogistica.actualizarEnvioConNuevoCodigo(
                        codigoOriginalEdicion, codigo, tipo, cliente, peso, distancia)) {
                    
                    JOptionPane.showMessageDialog(frame, "Envío con código " + codigoOriginalEdicion + 
                                                   " actualizado a " + codigo + " correctamente.", 
                                                   "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    
                    frame.recargarTabla(); 
                    frame.limpiarCampos(); 
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: El nuevo código " + codigo + " ya está en uso, o la edición falló.", 
                                                   "Error de Edición", JOptionPane.ERROR_MESSAGE);
                }
                
            } else {
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
                    frame.actualizarTablaConNuevoEnvio( 
                        nuevoEnvio.getTipo(),
                        nuevoEnvio.getCodigo(),
                        nuevoEnvio.getCliente(),
                        String.format("%.1f", nuevoEnvio.getPeso()),
                        String.format("%.1f", nuevoEnvio.getDistancia()),
                        String.format("%.2f", costo)
                    );
                    frame.limpiarCampos();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Error: Ya existe un envío con el código " + codigo, "Código Duplicado", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: Peso y Distancia deben ser números válidos.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }
}