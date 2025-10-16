package modelos;

public enum TipoEnvio {
    TERRESTRE("Terrestre"),
    AEREO("Aéreo"),
    MARITIMO("Marítimo");

    private final String displayValue;

    TipoEnvio(String displayValue) {
        this.displayValue = displayValue;
    }

    @Override
    public String toString() {
        return displayValue;
    }
}