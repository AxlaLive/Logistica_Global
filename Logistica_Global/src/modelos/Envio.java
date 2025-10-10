package modelos;

public abstract class Envio {
    private String codigo;
    private String cliente;
    private double peso;      
    private double distancia; 

    public Envio(String codigo, String cliente, double peso, double distancia) {
        if (codigo == null || codigo.trim().isEmpty() ||
            cliente == null || cliente.trim().isEmpty() ||
            peso <= 0 || distancia <= 0) {
            
            throw new IllegalArgumentException("Error: Datos del envío inválidos.");
        }
        
        this.codigo = codigo.trim();
        this.cliente = cliente.trim();
        this.peso = peso;
        this.distancia = distancia;
    }

    public String getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public double getPeso() { return peso; }
    public double getDistancia() { return distancia; }

    public abstract double calcularTarifa();

    public String getTipo() {
        return this.getClass().getSimpleName(); 
    }
}