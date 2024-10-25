import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class MySql implements MotorBaseDatos {
    private static MySql instancia;
    private Connection conexion;

    // Credenciales para la base de datos MySql
    private static final String URL = "jdbc:mysql://localhost:3306/inventariodb?serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    private MySql() {
        // Establecer la conexión a la base de datos MySql
        try {
            this.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión a MySql establecida correctamente.");
        } catch (SQLException e) {
            e.printStackTrace(); 
            System.out.println("Error al conectar con la base de datos.");
        }
    }

// Aqui se aplica el patron singleton, garantizando que solo haya una sola instancia de Mysql
    public static MySql getInstancia() {
        if (instancia == null) {
            instancia = new MySql();
        }
        return instancia;
    }

    // Método para guardar productos en la base de datos
    @Override
    public void guardarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, cantidad, valor_unitario) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getCantidad());
            stmt.setDouble(3, producto.getValorUnitario());
            stmt.executeUpdate();
            System.out.println("Producto guardado correctamente en MySql.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el producto en la base de datos.");
        }
    }

    // Método para obtener los productos
        public List<Producto> traerProductos() {
        String sql = "SELECT * FROM productos";
        List<Producto> listaProductos = new ArrayList<>();

        try (Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            // Iterar sobre el resultado y agregar cada producto a la lista
            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double valorUnitario = rs.getDouble("valor_unitario");

                // Crear un nuevo objeto Producto con los datos del resultado
                Producto producto = new Producto(id,nombre, cantidad, valorUnitario);
                listaProductos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los productos de la base de datos.");
        }

        return listaProductos;  
    }

    // Método para cerrar la conexión cuando sea necesario
    public void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
            System.out.println("Conexión a MySql cerrada correctamente.");
        }
    }
}
