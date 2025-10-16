package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import modelos.Aereo;
import modelos.Envio;
import modelos.Maritimo;
import modelos.Terrestre;
import modelos.TipoEnvio; 
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
            String tipoSeleccionadoStr = (String) frame.getCmbTipo().getSelectedItem();
            TipoEnvio tipoEnumSeguro = getTipoEnvioDesdeString(tipoSeleccionadoStr);
            
            if (tipoEnumSeguro == null) {
                JOptionPane.showMessageDialog(frame, "Error interno: Tipo de envío no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }            
            String codigo = frame.getTxtNumero().getText();
            String cliente = frame.getTxtCliente().getText();
            double peso = Double.parseDouble(frame.getTxtPeso().getText()); 
            double distancia = Double.parseDouble(frame.getTxtDistancia().getText());
            String codigoOriginalEdicion = frame.getCodigoOriginalEdicion();
            
            if (codigoOriginalEdicion != null) {
                if (gestorLogistica.actualizarEnvioConNuevoCodigo(
                        codigoOriginalEdicion, codigo, tipoEnumSeguro, cliente, peso, distancia)) {
                    
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
                switch (tipoEnumSeguro) {
                    case TERRESTRE:
                        nuevoEnvio = new Terrestre(codigo, cliente, peso, distancia);
                        break;
                    case AEREO:
                        nuevoEnvio = new Aereo(codigo, cliente, peso, distancia);
                        break;
                    case MARITIMO:
                        nuevoEnvio = new Maritimo(codigo, cliente, peso, distancia);
                        break;
                }
                if (nuevoEnvio != null && gestorLogistica.agregarEnvio(nuevoEnvio)) {
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
                else if (nuevoEnvio != null) {
                    JOptionPane.showMessageDialog(frame, "Error: Ya existe un envío con el código " + codigo, "Código Duplicado", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: Peso y Distancia deben ser números válidos.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private TipoEnvio getTipoEnvioDesdeString(String displayValue) {
        for (TipoEnvio tipo : TipoEnvio.values()) {
            if (tipo.toString().equals(displayValue)) {
                return tipo;
            }
        }
        return null;
    }
}