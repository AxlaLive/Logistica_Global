package modelos;

public class Maritimo extends Envio {
    public Maritimo(String codigo, String cliente, double peso, double distancia) {
        super(codigo, cliente, peso, distancia);
    }
    //Calculo de tarifa
    @Override
    public double calcularTarifa() {
        final double TARIFABS_POR_KM = 800;
        final double RECARGOBS_POR_KG = 1000;

        // Tarifa total
        double costo = (getDistancia() * TARIFABS_POR_KM) + (getPeso() * RECARGOBS_POR_KG);
        return costo;
    }
}
