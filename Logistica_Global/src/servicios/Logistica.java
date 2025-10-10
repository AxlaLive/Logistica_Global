package servicios;

import java.util.ArrayList;
import java.util.List;
import modelos.Envio; 

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

}