import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturasAD {
    JPanel facturasADm;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JButton buscarButton;
    private JButton regresarButton;
    private JTable table1;

    private Connection connection;


    public FacturasAD() {
        //Establecer la conexion
        connection = conector.obtenerConexion();

        // Configuración de la tabla
        configureTable();
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void configureTable() {
        try {
            // Obtener datos de la base de datos y configurar la tabla
            String query = "SELECT * FROM facturasad";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Crear un modelo de tabla para almacenar los datos
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("cod_facturas");
            model.addColumn("nombre_cliente");
            model.addColumn("cedula");
            model.addColumn("productos");
            model.addColumn("total");
            model.addColumn("nombre_empleado");

            // Llenar el modelo con los datos de la base de datos
            while (resultSet.next()) {
                int cod_facturas = resultSet.getInt("id_producto");
                String nombre_cliente = resultSet.getString("categoria");
                int cedula = resultSet.getInt("marca");
                String productos = resultSet.getString("nombre");
                double total = resultSet.getDouble("cantidad");
                String nombre_empleado = resultSet.getString("precio");
                model.addRow(new Object[]{cod_facturas,nombre_cliente,cedula,productos,total,nombre_empleado});
            }

            // Configurar la tabla con el modelo
            table1.setModel(model);

            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
