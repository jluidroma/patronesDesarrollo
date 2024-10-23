import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n|----------------------|");
            System.out.println("| Menú de opciones:    |");
            System.out.println("|----------------------|");
            System.out.println("|1. Ingresar producto  |");
            System.out.println("|2. Listar productos   |");
            System.out.println("|3. Salir              |");
            System.out.println("|----------------------|");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese la cantidad del producto: ");
                    int cantidad = scanner.nextInt();
                    System.out.print("Ingrese el precio del producto: ");
                    double precio = scanner.nextDouble();
                    scanner.nextLine();  // Limpiar el buffer

                    Producto nuevoProducto = new Producto(nombre, cantidad, precio);
                    ProductoFactory.registrarProducto(nuevoProducto);
                    break;

                case 2:
                    ProductoFactory.listarProductos();
                    break;

                case 3:
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida, intenta nuevamente.");
            }
        }

        scanner.close();
    }
}
