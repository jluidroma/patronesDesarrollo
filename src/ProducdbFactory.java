import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProducdbFactory {
    static Scanner scanner = new Scanner(System.in);
    public static void registrarProducto(Producto producto) {
        if (producto.getValorUnitario() > 100000) {
            MySql.getInstancia().guardarProducto(producto);
        } else {
            PostgreSQL.getInstancia().guardarProducto(producto);
        }
    }

    public static List<Producto> getProductos() throws SQLException{
        List<Producto> todosProductos = new ArrayList<Producto>();

        todosProductos.addAll(MySql.getInstancia().traerProductos());
        todosProductos.addAll(PostgreSQL.getInstancia().traerProductos());
        
        return todosProductos;
    }

    /*public static Producto getClon(int id_producto, int motor){
        //listarProductos();

        if(motor == 1){
            System.out.println("mYSQL");
        }else if(motor == 2){
            System.out.println("PostgreSQL");
        }

        return new Producto(5, "yucas", 12, 230000);
    }*/
}