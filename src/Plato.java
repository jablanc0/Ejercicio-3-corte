public class Plato {
    private String nombre;
    private double precio;
    private String cocina;

    public Plato(String nombre, double precio, String cocina) {
        this.nombre = nombre;
        this.precio = precio;
        this.cocina = cocina;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCocina() {
        return cocina;
    }

    public String toString() {
        return nombre + " ($" + String.format("%.3f", precio) + ")";
    }
}