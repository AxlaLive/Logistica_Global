package modelos;

public class Terrestre extends Envio {

    // Constructor
    public Terrestre(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }

    // Implementación del método abstracto para calcular la tarifa
    @Override
    public double calcularTarifa() {
        final double TARIFA_POR_KM = 1500;
        final double RECARGO_POR_KG = 2000;

        // Fórmula: tarifa total = (distancia * tarifaPorKm) + (peso * recargoPorKg)
        double costo = (getDistancia() * TARIFA_POR_KM) + (getPeso() * RECARGO_POR_KG);
        return costo;
    }
}