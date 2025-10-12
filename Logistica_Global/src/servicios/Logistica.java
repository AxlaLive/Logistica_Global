package servicios;

import java.util.ArrayList;
import java.util.List;
import modelos.Envio;
import modelos.Terrestre;
import modelos.Aereo;
import modelos.Maritimo;


public class Logistica {
    private List<Envio> listaEnvios;

    public Logistica() {
        this.listaEnvios = new ArrayList<>();
    }

    public boolean agregarEnvio(Envio nuevoEnvio) {
        if (buscarEnvioPorCodigo(nuevoEnvio.getCodigo()) != null) {
            return false;
        }
        return listaEnvios.add(nuevoEnvio);
    }

    public Envio buscarEnvioPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        String codigoBuscado = codigo.trim();
        
        for (Envio e : listaEnvios) {
            if (e.getCodigo().equals(codigoBuscado)) {
                return e;
            }
        }
        return null;
    }
    
    public boolean eliminarEnvio(String codigo) {
        Envio envioAEliminar = buscarEnvioPorCodigo(codigo);
        if (envioAEliminar != null) {
            return listaEnvios.remove(envioAEliminar);
        }
        return false;
    }

    public List<Envio> getListaEnvios() {
        return new ArrayList<>(listaEnvios);
    }
    
    private Envio crearNuevoEnvio(String codigo, String tipo, String cliente, double peso, double distancia) {
        switch (tipo) {
            case "Terrestre":
                return new Terrestre(codigo, cliente, peso, distancia);
            case "Aéreo":
                return new Aereo(codigo, cliente, peso, distancia);
            case "Marítimo":
                return new Maritimo(codigo, cliente, peso, distancia);
            default:
                throw new IllegalArgumentException("Tipo de envío no válido.");
        }
    }

    public boolean actualizarEnvioConNuevoCodigo(
            String codigoAntiguo, String codigoNuevo, 
            String nuevoTipo, String nuevoCliente, double nuevoPeso, double nuevaDistancia) {

        if (!codigoAntiguo.equals(codigoNuevo.trim())) {
            if (buscarEnvioPorCodigo(codigoNuevo.trim()) != null) {
                return false; 
            }
        }
        Envio envioAntiguo = buscarEnvioPorCodigo(codigoAntiguo);
        if (envioAntiguo != null) {
            Envio envioActualizado = crearNuevoEnvio(codigoNuevo, nuevoTipo, nuevoCliente, nuevoPeso, nuevaDistancia);

            listaEnvios.remove(envioAntiguo);
            return listaEnvios.add(envioActualizado);
        }
        return false;
    }
}