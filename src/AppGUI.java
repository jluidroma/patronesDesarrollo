import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppGUI extends JFrame implements InventarioObserver {

    private JTable productTable;
    private DefaultTableModel tableModel;
    private List<Producto> todosProductos = new ArrayList<>();

    // Campos de texto para ingresar datos del producto
    private JTextField nombreField;
    private JTextField cantidadField;
    private JTextField valorUnitarioField;
    private JButton guardarButton;

    // Campos para extraer registros existentes
    private JTextField idProductoField;
    private JButton extraerButton;

    // Campos para mostrar inventario
    private JLabel totalProductosLabel;
    private JTextField valorTotalInventarioField;

    private InventarioObservable inventarioObservable;

    public AppGUI() throws SQLException {
        // Inicializar el observable de inventario
        inventarioObservable = new InventarioObservable();
        inventarioObservable.agregarObservador(this);

        // Configuración de la ventana principal
        setTitle("Gestión de Productos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10)); 

        // Panel de la izquierda para los campos de ingreso
        JPanel panelIngreso = new JPanel();
        panelIngreso.setLayout(new BoxLayout(panelIngreso, BoxLayout.Y_AXIS));
        panelIngreso.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelIngreso.setPreferredSize(new Dimension(300, 600)); 
        panelIngreso.setBackground(Color.LIGHT_GRAY); 

        // Módulo de extracción de registros existentes
        JLabel extraerLabel = new JLabel("Extraer Registros Existentes");
        extraerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        extraerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIngreso.add(extraerLabel);

        panelIngreso.add(Box.createRigidArea(new Dimension(0, 10)));

        // Crear un panel para la extracción de registros
        JPanel panelExtraccion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idProductoLabel = new JLabel("ID Producto:");
        idProductoField = new JTextField(20);
        panelExtraccion.add(idProductoLabel);
        panelExtraccion.add(idProductoField);
        panelIngreso.add(panelExtraccion);

        panelIngreso.add(Box.createRigidArea(new Dimension(0, 10)));

        // Botón para extraer registros
        extraerButton = new JButton("Extraer Registros");
        extraerButton.setPreferredSize(new Dimension(150, 30));
        extraerButton.setFont(new Font("Arial", Font.BOLD, 14));
        extraerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIngreso.add(extraerButton);

        panelIngreso.add(Box.createRigidArea(new Dimension(0, 20))); 

        // Separador
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(250, 2)); 
        panelIngreso.add(separator);

        panelIngreso.add(Box.createRigidArea(new Dimension(0, 20))); 

        // Crear y agregar campos con etiquetas para agregar productos
        JLabel titleLabel = new JLabel("Ingreso de Producto");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIngreso.add(titleLabel);

        panelIngreso.add(Box.createRigidArea(new Dimension(0, 10))); 

        // Crear un panel para el ingreso de productos
        JPanel panelIngresoProducto = new JPanel(new GridLayout(3, 2, 10, 10));
        nombreField = new JTextField(10);
        cantidadField = new JTextField(5);
        valorUnitarioField = new JTextField(7);

        panelIngresoProducto.add(new JLabel("Nombre:"));
        panelIngresoProducto.add(nombreField);
        panelIngresoProducto.add(new JLabel("Cantidad:"));
        panelIngresoProducto.add(cantidadField);
        panelIngresoProducto.add(new JLabel("Valor Unitario:"));
        panelIngresoProducto.add(valorUnitarioField);

        panelIngreso.add(panelIngresoProducto); // Añadir el panel de ingreso de productos

        panelIngreso.add(Box.createRigidArea(new Dimension(0, 20))); 

        // Botón de guardar
        guardarButton = new JButton("Guardar Producto");
        guardarButton.setPreferredSize(new Dimension(150, 30)); 
        guardarButton.setFont(new Font("Arial", Font.BOLD, 14)); 
        guardarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelIngreso.add(guardarButton);

        add(panelIngreso, BorderLayout.WEST);

        // Tabla de productos 
        String[] columnNames = {"ID", "Nombre", "Cantidad", "Valor Unitario", "Base de Datos"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);

        // Personalizar la tabla
        productTable.setRowHeight(30);
        productTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); 
        productTable.setFont(new Font("Arial", Font.PLAIN, 14)); 

        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para mostrar cantidad de productos y valor de inventario
        JPanel panelInventario = new JPanel(new GridLayout(2, 2, 10, 10));
        totalProductosLabel = new JLabel("Total de productos: 0");
        valorTotalInventarioField = new JTextField("0.0", 10);
        valorTotalInventarioField.setEditable(false);  // No editable por el usuario

        panelInventario.add(new JLabel("Cantidad total de productos:"));
        panelInventario.add(totalProductosLabel);
        panelInventario.add(new JLabel("Valor total del inventario:"));
        panelInventario.add(valorTotalInventarioField);

        add(panelInventario, BorderLayout.NORTH); // Añadir panel al norte

        // Cargar los productos en la tabla
        cargarProductosEnTabla();

        // Acción del botón "Guardar Producto"
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    agregarProducto();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Acción del botón "Extraer Registros"
        extraerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    extraerRegistro();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Actualizar el inventario al inicio
        actualizarValoresInventario();
    }

    // Método que actualiza el inventario
    public void actualizarValoresInventario() throws SQLException {
        // Sumar la cantidad de cada producto en lugar de contar los productos distintos
    int totalProductos = todosProductos.stream().mapToInt(Producto::getCantidad).sum();
    double valorTotal = todosProductos.stream().mapToDouble(producto -> producto.getCantidad() * producto.getValorUnitario()).sum();

        // Actualizar el observable del inventario
        inventarioObservable.actualizarInventario(totalProductos, valorTotal);
    }

    // Método que será llamado cuando se actualicen los valores del inventario
    @Override
    public void actualizar(int totalProductos, double valorInventario) {
        totalProductosLabel.setText("Total de productos: " + totalProductos);
        valorTotalInventarioField.setText(String.valueOf("$"+valorInventario));
    }

    // Método para cargar los productos en la tabla
    public void cargarProductosEnTabla() throws SQLException {
        todosProductos = ProducdbFactory.getProductos();
        for (int i = 0; i < todosProductos.size(); i++) {
            Producto producto = todosProductos.get(i);
            String db = producto.getValorUnitario() > 100000 ? "MySql" : "PostgreSQL";
            Object[] row = {i, producto.getNombre(), producto.getCantidad(), producto.getValorUnitario(), db};
            tableModel.addRow(row);
        }
    }

    // Método para agregar el producto a la tabla y la base de datos
    public void agregarProducto() throws SQLException {
        String nombre = nombreField.getText();
        int cantidad = Integer.parseInt(cantidadField.getText());
        double valorUnitario = Double.parseDouble(valorUnitarioField.getText());

        // Crear  y registrar el producto en la base de datos
        Producto nuevoProducto = new Producto(nombre, cantidad, valorUnitario);
        ProducdbFactory.registrarProducto(nuevoProducto);

        nombreField.setText("");
        cantidadField.setText("");
        idProductoField.setText("");
        valorUnitarioField.setText("");

         // Actualizar la tabla y los valores del inventario
        todosProductos.clear();
        tableModel.setRowCount(0);
        cargarProductosEnTabla();
        actualizarValoresInventario();
}

// Método para extraer un producto basado en el ID
public void extraerRegistro() throws SQLException, CloneNotSupportedException {

int idProducto = Integer.parseInt(idProductoField.getText());
// Aplicar el modelo clonar
Producto clonProducto = (Producto) todosProductos.get(idProducto).clone();

// Si el producto existe, muestra los detalles
if (clonProducto != null) {
    nombreField.setText(clonProducto.getNombre());
    cantidadField.setText(String.valueOf(clonProducto.getCantidad()));
    valorUnitarioField.setText(String.valueOf(clonProducto.getValorUnitario()));
} else {
    JOptionPane.showMessageDialog(this, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
}
}

public static void main(String[] args) {
SwingUtilities.invokeLater(() -> {
try {
    new AppGUI().setVisible(true);
} catch (SQLException e) {
    e.printStackTrace();
}
});
}
}
