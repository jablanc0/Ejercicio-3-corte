import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private int numeroMesa;
    private List<Pedido> items;

    public Comanda(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.items = new ArrayList<>();
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void agregarItem(Pedido item) {
        this.items.add(item);
    }

    public List<Pedido> getItemsPorCocina(String cocina) {
        List<Pedido> itemsDeCocina = new ArrayList<>();
        for (Pedido item : this.items) {
            if (item.getCocina().equalsIgnoreCase(cocina)) {
                itemsDeCocina.add(item);
            }
        }
        return itemsDeCocina;
    }

    public List<Pedido> getItems() {
        return items;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Comanda para la mesa " + numeroMesa + ":\n");
        for (Pedido item : items) {
            sb.append("- ").append(item).append("\n");
        }
        return sb.toString();
    }
}