public class Producto implements Cloneable {
     
//defini atributos
private int id;
private String nombre;
private int cantidad;
private double valorUnitario;

public Producto(String nombre, int cantidad, double valorUnitario) {
     this.nombre = nombre;
     this.cantidad = cantidad;
     this.valorUnitario = valorUnitario;
}

public Producto(int id, String nombre, int cantidad, double valorUnitario) {
     this.id = id;
     this.nombre = nombre;
     this.cantidad = cantidad;
     this.valorUnitario = valorUnitario;
}
public int getId() {
     return id;
}

public void setId(int id) {
     this.id = id;
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
protected Object clone() throws CloneNotSupportedException {
     return super.clone(); 
}

}
