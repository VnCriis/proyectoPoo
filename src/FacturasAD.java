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
    private JTextField txtCod;
    private JTextField txtCliente;
    private JTextField txtCedula;
    private JTextField txtProductos;
    private JTextField txtTotal;
    private JTextField txtNombreE;
    private JButton buscarButton;
    private JButton regresarButton;
    private JTable table1;
    private JButton agregarButton;

    private Connection connection;


    public FacturasAD() {
        //Establecer la conexion
        connection = conector.obtenerConexion();

        // Configuración de la tabla
        configureTable();
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarfacturas();
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (camposLlenos()) {
                    agregarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, llene todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new admin().adminJPanel);
                Main.ventana.revalidate();
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
                int cod_facturas = resultSet.getInt("cod_facturas");
                String nombre_cliente = resultSet.getString("nombre_cliente");
                int cedula = resultSet.getInt("cedula");
                String productos = resultSet.getString("productos");
                double total = resultSet.getDouble("total");
                String nombre_empleado = resultSet.getString("nombre_empleado");
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

    private void agregarDatos() {
        try {
            // Obtener los valores ingresados por el usuario
            int cod_facturas= Integer.parseInt(txtCod.getText());
            String nombre_cliente = txtCliente.getText();
            int cedula = Integer.parseInt(txtCedula.getText());
            String productos = txtProductos.getText();
            double total = Double.parseDouble(txtTotal.getText());
            String nombre_empleado = txtNombreE.getText();



            // Insertar datos en la base de datos
            String insertQuery = "INSERT INTO facturasad(cod_facturas,nombre_cliente,cedula,productos,total,nombre_empleado) VALUES (?, ?, ?,?,?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1,cod_facturas);
            insertStatement.setString(2, nombre_cliente);
            insertStatement.setInt(3, cedula);
            insertStatement.setString(4, productos);
            insertStatement.setDouble(5,total);
            insertStatement.setString(6, nombre_empleado);
            insertStatement.executeUpdate();

            // Actualizar la tabla
            configureTable();

            // Limpiar los campos de entrada después de la inserción
            txtCod.setText("");
            txtCliente.setText("");
            txtCedula.setText("");
            txtProductos.setText("");
            txtTotal.setText("");
            txtNombreE.setText("");

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Datos agregados correctamente", "Correcto", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar recursos
            insertStatement.close();
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Verifiacion de que todos los campos esten llenos para agregar un producto
    private boolean camposLlenos() {
        return !txtCod.getText().isEmpty() && !txtCliente.getText().isEmpty() && !txtCedula.getText().isEmpty()
                && !txtProductos.getText().isEmpty() && !txtTotal.getText().isEmpty() && !txtNombreE.getText().isEmpty();
    }

    private void buscarfacturas() {
        try {
            // Obtener el Codigo de la factura
            String idInput = JOptionPane.showInputDialog(this.facturasADm, "Ingrese el Codigo de la factura:", "Busqueda de Datos", JOptionPane.QUESTION_MESSAGE);

            // Verificar si se ingresó un ID
            if (idInput != null && !idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Consultar la existencia del ID en la base de datos
                if (exisproducto(id)) {
                    // Obtener la información del producto por ID
                    String query = "SELECT * FROM facturasad WHERE cod_facturas = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1,id);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            // Mostrar la información en los campos de texto
                            if (resultSet.next()) {

                                int cod_facturas = resultSet.getInt("cod_facturas");
                                String nombre_cliente = resultSet.getString("nombre_cliente");
                                int cedula = resultSet.getInt("cedula");
                                String productos = resultSet.getString("productos");
                                double total = resultSet.getDouble("total");
                                String nombre_empleado = resultSet.getString("nombre_empleado");

//                                txtCod.setText(String.valueOf(cod_facturas));
//                                txtCliente.setText(nombre_cliente);
//                                txtCedula.setText(String.valueOf(cedula));
//                                txtProductos.setText(productos);
//                                txtTotal.setText(String.valueOf(total));
//                                txtNombreE.setText(nombre_empleado);


                                String mensaje = "Codigo: " + cod_facturas +"\nNombre-Empleado: "+ nombre_empleado +"\nTotal del Producto: " + total+"$" + "\nProductos : " + productos;
                                JOptionPane.showMessageDialog(this.facturasADm, mensaje, "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this.facturasADm, "La factura con el cod " + id + " no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private boolean exisproducto(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM facturasad WHERE cod_facturas=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }
}
