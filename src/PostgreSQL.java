import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQL implements MotorBaseDatos {
    private static PostgreSQL instancia;
    private Connection conexion;

    // Credenciales para acceder al motor postgres
    private static final String URL = "jdbc:postgresql://localhost:5432/inventariopgdb"; 
    private static final String USUARIO = "postgres";
    private static final String CONTRASENA = "jluidroma";

    private PostgreSQL() {
        // Establecer la conexión a la base de datos PostgreSQL
        try {
            this.conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexión a PostgreSQL establecida correctamente.");
        } catch (SQLException e) {
            e.printStackTrace(); 
            System.out.println("Error al conectar con la base de datos.");
        }
    }

    // Aqui se aplica el patron singleton, garantizando que solo haya una sola instancia de postgreSQL corriedo
    public static PostgreSQL getInstancia() {
        if (instancia == null) {
            instancia = new PostgreSQL();
        }else{
            System.out.println("Ya existe hay una instancia corriendo en el motor");
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
            System.out.println("Producto guardado correctamente en PostgreSQL.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el producto en la base de datos postgres");
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
                String nombre = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                double valorUnitario = rs.getDouble("valor_unitario");

                // Crear un nuevo objeto Producto con los datos del resultado
                Producto producto = new Producto(nombre, cantidad, valorUnitario);
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
            System.out.println("Conexión a PostgreSQL cerrada correctamente.");
        }
    }
}
