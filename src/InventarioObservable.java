import java.util.ArrayList;
import java.util.List;

public class InventarioObservable {
    private List<InventarioObserver> observadores = new ArrayList<>();
    private int totalProductos;
    private double valorInventario;

    // Método para registrar observadores
    public void agregarObservador(InventarioObserver observer) {
        observadores.add(observer);
    }

    // Método para eliminar observadores
    public void eliminarObservador(InventarioObserver observer) {
        observadores.remove(observer);
    }

    // Método para notificar a todos los observadores
    private void notificarObservadores() {
        for (InventarioObserver observer : observadores) {
            observer.actualizar(totalProductos, valorInventario);
        }
    }

    // Método para actualizar los valores de productos y valor de inventario
    public void actualizarInventario(int totalProductos, double valorInventario) {
        this.totalProductos = totalProductos;
        this.valorInventario = valorInventario;
        notificarObservadores();  // Notificar a los observadores
    }
}

