import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProductoFactory {
    public static void registrarProducto(Producto producto) {
        if (producto.getValorUnitario() > 100000) {
            MySql.getInstancia().guardarProducto(producto);
        } else {
            PostgreSQL.getInstancia().guardarProducto(producto);
        }
    }

    public static void listarProductos() throws SQLException{
        List<Producto> productosMySql = MySql.getInstancia().traerProductos();
        List<Producto> productosPosgreSQL = PostgreSQL.getInstancia().traerProductos();

        ArrayList<Producto> todosProductos = new ArrayList<>();  // Nueva lista para combinar ambos productos

        todosProductos.addAll(productosMySql);  
        todosProductos.addAll(productosPosgreSQL);
        System.out.println("------------------------------------------");
        System.out.println("NOMBRE      |  CANTIDAD   | VALOR UNITARIO");
        System.out.println("------------------------------------------");
        for (Producto producto : todosProductos) {
            //System.out.println(producto.getNombre()+"   "+producto.getCantidad()+"   "+producto.getValorUnitario());
        System.out.println(producto.toString());
        }
        System.out.println("------------------------------------------");
    }

}