import java.sql.SQLException;
import java.util.List;

public interface MotorBaseDatos  {
     void guardarProducto(Producto producto);
     List<Producto> traerProductos();
     void cerrarConexion()throws SQLException;
}
