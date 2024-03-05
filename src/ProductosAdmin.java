import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosAdmin {
    JPanel productosJPanel;
    private JTextField txtIDproduc;
    private JTextField txtTipo;
    private JTextField txtNombrepro;
    private JTextField txtMarca;
    private JTextField txtCantidad;
    private JTextField txtPrecio;
    private JButton agregarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton buscarButton;
    private JButton regresarButton;
    private JTable table1;
    private JButton ayudaButton;
    private Connection connection;

    public ProductosAdmin() {

        //Establecer la conexion
        connection = conector.obtenerConexion();

        // Configuración de la tabla
        configureTable();
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
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();

            }
        });
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarEliminacion();

            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new admin().adminJPanel);
                Main.ventana.revalidate();
            }
        });

        ayudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("manualAdmin.pdf");
                if (inputStream != null) {
                    try {
                        File tempFile = File.createTempFile("tempPDF", ".pdf");
                        FileOutputStream outputStream = new FileOutputStream(tempFile);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, length);
                        }
                        inputStream.close();
                        outputStream.close();
                        // Abrir el archivo temporal
                        Desktop.getDesktop().open(tempFile);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al abrir el archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El archivo PDF no se encuentra en el directorio de recursos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void configureTable() {
        try {
            // Obtener datos de la base de datos y configurar la tabla
            String query = "SELECT * FROM productos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Crear un modelo de tabla para almacenar los datos
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("id_producto");
            model.addColumn("categoria");
            model.addColumn("marca");
            model.addColumn("nombre");
            model.addColumn("cantidad");
            model.addColumn("precio");

            // Llenar el modelo con los datos de la base de datos
            while (resultSet.next()) {
                int id_producto = resultSet.getInt("id_producto");
                String tipo = resultSet.getString("categoria");
                String nombre = resultSet.getString("marca");
                String marca = resultSet.getString("nombre");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");
                model.addRow(new Object[]{id_producto,tipo,nombre,marca,cantidad,precio });
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
            int id_producto = Integer.parseInt(txtIDproduc.getText());
            String categoria = txtTipo.getText();
            String nombre = txtNombrepro.getText();
            String marca = txtMarca.getText();
            int cantidad = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());



            // Insertar datos en la base de datos
            String insertQuery = "INSERT INTO productos (id_producto,categoria,marca,nombre,cantidad,precio) VALUES (?, ?, ?,?,?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1,id_producto);
            insertStatement.setString(2, categoria);
            insertStatement.setString(3, marca);
            insertStatement.setString(4, nombre);
            insertStatement.setInt(5,cantidad);
            insertStatement.setDouble(6, precio);
            insertStatement.executeUpdate();

            // Actualizar la tabla
            configureTable();

            // Limpiar los campos de entrada después de la inserción
            txtIDproduc.setText("");
            txtTipo.setText("");
            txtNombrepro.setText("");
            txtMarca.setText("");
            txtCantidad.setText("");
            txtPrecio.setText("");

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
        return !txtIDproduc.getText().isEmpty() && !txtTipo.getText().isEmpty() && !txtNombrepro.getText().isEmpty()
                && !txtMarca.getText().isEmpty() && !txtCantidad.getText().isEmpty() && !txtPrecio.getText().isEmpty();
    }

    private void buscarProducto() {
        try {
            // Obtener el ID ingresado
            String idInput = JOptionPane.showInputDialog(this.productosJPanel, "Ingrese el ID a buscar:", "Busca - ID", JOptionPane.QUESTION_MESSAGE);

            // Verificar si se ingresó un ID
            if (idInput != null && !idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Consultar la existencia del ID en la base de datos
                if (exisproducto(id)) {
                    // Obtener la información del producto por ID
                    String query = "SELECT * FROM productos WHERE id_producto=?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setInt(1,id);
                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            // Mostrar la información en los campos de texto
                            if (resultSet.next()) {
                                int id_producto = resultSet.getInt("id_producto");
                                String categoria = resultSet.getString("categoria");
                                String nombre = resultSet.getString("marca");
                                String marca = resultSet.getString("nombre");
                                int cantidad = resultSet.getInt("cantidad");
                                double precio = resultSet.getDouble("precio");

                                txtIDproduc.setText(String.valueOf(id_producto));
                                txtTipo.setText(categoria);
                                txtNombrepro.setText(nombre);
                                txtMarca.setText(marca);
                                txtCantidad.setText(String.valueOf(cantidad));
                                txtPrecio.setText(String.valueOf(precio));


                                String mensaje = "ID: " + id_producto +"\nCategoria: "+ categoria +"\nNombre: " + nombre + "\nMarca : " + marca +
                                        "\nCantidad: " + cantidad+ "\nPrecio: "+precio+"$";
                                JOptionPane.showMessageDialog(this.productosJPanel, mensaje, "Resultado de búsqueda", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this.productosJPanel, "El producto con ID " + id + " no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private boolean exisproducto(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM productos WHERE id_producto=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        }
    }

    // Modificar el método actualizar Productos para usar el ID ingresado
    private void actualizarProducto() {
        try {
            // Obtener el ID ingresado
            String idInput = JOptionPane.showInputDialog(this.productosJPanel, "Ingrese el ID del empleado que desea actualizar:", "Actualizar por ID", JOptionPane.QUESTION_MESSAGE);

            // Verificar si se ingresó un ID
            if (idInput != null && !idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Consultar la existencia del ID en la base de datos
                if (exisproducto(id)) {
                    // Obtener los nuevos valores de los campos de texto
                    int id_producto = Integer.parseInt(txtIDproduc.getText());
                    String categoria = txtTipo.getText();
                    String nombre = txtNombrepro.getText();
                    String marca = txtMarca.getText();
                    int cantidad = Integer.parseInt(txtCantidad.getText());
                    double precio = Double.parseDouble(txtPrecio.getText());

                    // Actualizar la tabla de productos en la base de datos por ID
                    String query = "UPDATE productos SET id_producto=?, categoria=?, marca=?, nombre=?, cantidad=?, precio=? WHERE id_producto=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,id_producto);
                    preparedStatement.setString(2, categoria);
                    preparedStatement.setString(3, marca);
                    preparedStatement.setString(4, nombre);
                    preparedStatement.setInt(5,cantidad);
                    preparedStatement.setDouble(6, precio);
                    preparedStatement.setInt(7, id);

                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                    // Limpiar los campos de entrada después de la inserción
                    txtIDproduc.setText("");
                    txtTipo.setText("");
                    txtNombrepro.setText("");
                    txtMarca.setText("");
                    txtCantidad.setText("");
                    txtPrecio.setText("");

                    // Recargar datos en la tabla
                    configureTable();
                } else {
                    JOptionPane.showMessageDialog(this.productosJPanel, "El empleado con ID " + id + " no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void confirmarEliminacion() {
        try {
            // Obtener el ID ingresado
            //String idInput = txtIDdelete.getText();
            // Obtener el ID ingresado
            String idInput = JOptionPane.showInputDialog(this.productosJPanel, "Ingrese el ID del producto a eliminar:", "Eliminacion por ID", JOptionPane.QUESTION_MESSAGE);

            // Verificar si se ingresó un ID
            if (!idInput.isEmpty()) {
                int id = Integer.parseInt(idInput);

                // Mostrar un cuadro de diálogo de confirmación
                int confirmacion = JOptionPane.showConfirmDialog(productosJPanel,
                        "¿Estás seguro de eliminar el producto con ID " + id + "?",
                        "Confirmar Eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // Llamar al método para eliminar el producto
                    eliminarProducto(id);

                    // Volver a configurar la tabla después de la eliminación
                    configureTable();
                }
            } else {
                JOptionPane.showMessageDialog(productosJPanel, "Por favor, ingresa un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);


            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(productosJPanel, "Por favor, ingresa un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void eliminarProducto(int id) {
        try {
            String query = "DELETE FROM productos WHERE id_producto=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);
                int filasAfectadas = preparedStatement.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(productosJPanel,
                            "Producto eliminado exitosamente.",
                            "Eliminación Exitosa",
                            JOptionPane.INFORMATION_MESSAGE);

                } else {
                    JOptionPane.showMessageDialog(productosJPanel,
                            "No se encontró el producto con ID " + id,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
