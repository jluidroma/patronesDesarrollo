public class Producto implements Cloneable {
     
//defini atributos
private String nombre;
private int cantidad;
private double valorUnitario;

public Producto(String nombre, int cantidad, double valorUnitario) {
     this.nombre = nombre;
     this.cantidad = cantidad;
     this.valorUnitario = valorUnitario;
}

public String getNombre() {
     return nombre;
}

public void setNombre(String nombre) {
     this.nombre = nombre;
}

public int getCantidad() {
     return cantidad;
}

public void setCantidad(int cantidad) {
     this.cantidad = cantidad;
}

public double getValorUnitario() {
     return valorUnitario;
}

public void setValorUnitario(double valorUnitario) {
     this.valorUnitario = valorUnitario;
}


@Override
public String toString() {
     return "Producto [nombre=" + nombre + ", cantidad=" + cantidad + ", valorUnitario=" + valorUnitario + "]";
}

@Override
public Producto clone() {
     try {
          return (Producto) super.clone();
     } catch (CloneNotSupportedException e) {
          e.printStackTrace();
          return null;
     }
}


// Para crear un producto a partir de uno existente:
//Producto nuevoProducto = productoExistente.clone();
}
