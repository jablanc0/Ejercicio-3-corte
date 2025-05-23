public class Bebida {
    private String nombre;
    private double precio;

    public Bebida(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String toString() {
        return nombre + " ($" + String.format("%.3f", precio) + ")";
    }
}