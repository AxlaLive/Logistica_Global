package modelos;

public class Aereo extends Envio {
    private static final double TARIFA_BASE_KM = 5000;
    private static final double RECARGO_KG = 4000;

    public Aereo(String codigo, String cliente, double pesoKg, double distanciaKm) {
        super(codigo, cliente, pesoKg, distanciaKm); 
    }

    @Override
    public double calcularTarifa() {
        return (getDistancia() * TARIFA_BASE_KM) + (getPeso() * RECARGO_KG); 
    }

    @Override
    public String getTipo() {
        return "Aéreo";
    }

}
