package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import servicios.Logistica;
import ui.FrmLogisticaGlobal;

public class QuitarEnvioListener implements ActionListener {
    private FrmLogisticaGlobal frame;
    private Logistica gestorLogistica;
    
    public QuitarEnvioListener(FrmLogisticaGlobal frame, Logistica gestor) {
        this.frame = frame;
        this.gestorLogistica = gestor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int filaSeleccionada = frame.getTblEnvios().getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) frame.getTblEnvios().getModel();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(frame, "Debe seleccionar un envío de la lista para quitarlo.", "Error de Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String codigoEnvio = (String) model.getValueAt(filaSeleccionada, 1); 
        boolean eliminadoDelModelo = gestorLogistica.eliminarEnvio(codigoEnvio);
        if (eliminadoDelModelo) {
            model.removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(frame, "Envío con código " + codigoEnvio + " eliminado correctamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Error: No se pudo encontrar el envío para eliminar.", "Error Interno", JOptionPane.ERROR_MESSAGE);
        }
    }
}