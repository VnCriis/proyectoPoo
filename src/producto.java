import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class producto {
    JPanel productoJPanel;
    private JButton regresarButton;
    private JButton button1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton buscarButton;
    private JTextField textField6;
    private JTable table1;
    private JTextField textField0;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton eliminarButton;
    private JButton actualizarButton;
    private JButton agregarButton;
    private JButton ayudaButton;
    private Connection connection;
    private JLabel advertencia;

    public producto() {

        connection = conector.obtenerConexion();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configureTable();
            }
        });
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM productos");
            Set<String> uniqueProductNames = new HashSet<>();
            while (rs.next()) {
                String productName = rs.getString("categoria");
                uniqueProductNames.add(productName);
            }
            for (String productName : uniqueProductNames) {
                comboBox1.addItem(productName);
            }
            comboBox1.addActionListener(e -> {
                String selectedProductName = (String) comboBox1.getSelectedItem();
                try {
                    Statement newStatement = connection.createStatement();
                    ResultSet newRs = newStatement.executeQuery("SELECT * FROM productos WHERE categoria = '" + selectedProductName + "'");

                    // Definir los nombres de las columnas manualmente
                    String[] columnNames = {"id_producto", "categoria", "marca","nombre", "cantidad", "precio"};

                    // Crear un nuevo modelo de tabla con los nombres de las columnas definidos
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
                    while (newRs.next()) {
                        Object[] rowData = new Object[columnNames.length];
                        for (int i = 0; i < columnNames.length; i++) {
                            rowData[i] = newRs.getObject(i + 1);
                        }
                        model.addRow(rowData);
                    }
                    table1.setModel(model);
                    newRs.close();
                    newStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM productos");
            Set<String> uniqueProductNames = new HashSet<>();
            while (rs.next()) {
                String productName = rs.getString("marca");
                uniqueProductNames.add(productName);
            }
            for (String productName : uniqueProductNames) {
                comboBox2.addItem(productName);
            }
            comboBox2.addActionListener(e -> {
                String selectedProductName = (String) comboBox2.getSelectedItem();
                try {
                    Statement newStatement = connection.createStatement();
                    ResultSet newRs = newStatement.executeQuery("SELECT * FROM productos WHERE marca = '" + selectedProductName + "'");
                    DefaultTableModel model = new DefaultTableModel();
                    ResultSetMetaData metaData = newRs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        model.addColumn(metaData.getColumnName(i));
                    }
                    while (newRs.next()) {
                        Object[] rowData = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            rowData[i - 1] = newRs.getObject(i);
                        }
                        model.addRow(rowData);
                    }
                    table1.setModel(model);
                    newRs.close();
                    newStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = textField6.getText();
                String opcionSeleccionada = (String) comboBox3.getSelectedItem();
                if (!nombre.isEmpty()) {
                    switch (opcionSeleccionada) {
                        case "id_producto":
                            try {
                                String sql = "SELECT * FROM productos WHERE id_producto = ?";

                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }

                            break;
                        case "categoria":
                            try {
                                String sql = "SELECT * FROM productos WHERE categoria = ?";
                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "marca":
                            try {
                                String sql = "SELECT * FROM productos WHERE marca = ?";
                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "nombre":
                            try {
                                String sql = "SELECT * FROM productos WHERE nombre = ?";
                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "cantidad":
                            try {
                                String sql = "SELECT * FROM productos WHERE cantidad = ?";
                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                        case "precio":
                            try {
                                String sql = "SELECT * FROM productos WHERE precio = ?";
                                PreparedStatement statement = connection.prepareStatement(sql);
                                statement.setString(1, nombre);
                                ResultSet resultSet = statement.executeQuery();
                                DefaultTableModel model = new DefaultTableModel();
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                for (int i = 1; i <= columnCount; i++) {
                                    model.addColumn(metaData.getColumnName(i));
                                }
                                while (resultSet.next()) {
                                    Object[] rowData = new Object[columnCount];
                                    for (int i = 1; i <= columnCount; i++) {
                                        rowData[i - 1] = resultSet.getObject(i);
                                    }
                                    model.addRow(rowData);
                                }
                                table1.setModel(model);
                                resultSet.close();
                                statement.close();
                            } catch (SQLException exception) {
                                JOptionPane.showMessageDialog(null, "Error al ejecutar la consulta SQL: " + exception.getMessage());
                                exception.printStackTrace();
                            }
                            break;
                    }
                }else{
                    advertencia.setText("Ingrese sus credenciales");
                }
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    Main.ventana.setContentPane(new inicio().inicioJPanel);
                    Main.ventana.revalidate();
                } else {
                }
            }
        });
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table1.getSelectedRow();
                    if (selectedRow != -1) {
                        verDatos(selectedRow);
                    }
                }
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == eliminarButton) {
                    eliminarProducto();
                    JOptionPane.showMessageDialog(null, "PRODUCTO ELIMINADO");
                } else if (e.getSource() == actualizarButton) {
                    editarProducto();
                    JOptionPane.showMessageDialog(null, "PRODUCTO ACTUALIZADO");
                } else if (e.getSource() == agregarButton) {
                    agregarProducto();
                    JOptionPane.showMessageDialog(null, "PRODUCTO INGRESADO");
                }
            }
        };
        eliminarButton.addActionListener(listener);
        actualizarButton.addActionListener(listener);
        agregarButton.addActionListener(listener);

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

    private void agregarProducto() {
        textField0.setEditable(false);
        try {
            int idProducto = Integer.parseInt(textField0.getText());
            String categoria = textField1.getText();
            String marca = textField2.getText();
            String nombre = textField3.getText();
            int cantidad = Integer.parseInt(textField4.getText());
            double precio = Double.parseDouble(textField5.getText());
            String query = "INSERT INTO productos (id_producto, categoria, marca, nombre, cantidad, precio) " + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProducto);
            statement.setString(2, categoria);
            statement.setString(3, marca);
            statement.setString(4, nombre);
            statement.setInt(5, cantidad);
            statement.setDouble(6, precio);
            statement.executeUpdate();
            configureTable();
            textField0.setText("");
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void editarProducto() {
        textField0.setEditable(false);
        try {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un producto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idProducto = Integer.parseInt(textField0.getText());
            String categoria = textField1.getText();
            String marca = textField2.getText();
            String nombre = textField3.getText();
            int cantidad = Integer.parseInt(textField4.getText());
            double precio = Double.parseDouble(textField5.getText());

            String query = "UPDATE productos SET categoria=?, marca=?, nombre=?, cantidad=?, precio=? WHERE id_producto=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, categoria);
            statement.setString(2, marca);
            statement.setString(3, nombre);
            statement.setInt(4, cantidad);
            statement.setDouble(5, precio);
            statement.setInt(6, idProducto);
            statement.executeUpdate();
            configureTable();
            textField0.setText("");
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void eliminarProducto() {
        try {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idProducto = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            String query = "DELETE FROM productos WHERE id_producto=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idProducto);
            statement.executeUpdate();
            configureTable();
            textField0.setText("");
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void verDatos(int filaSeleccionada){
        textField0.setEditable(false);
        textField0.setText(table1.getValueAt(filaSeleccionada, 0).toString());
        textField1.setText(table1.getValueAt(filaSeleccionada, 1).toString());
        textField2.setText(table1.getValueAt(filaSeleccionada, 2).toString());
        textField3.setText(table1.getValueAt(filaSeleccionada, 3).toString());
        textField4.setText(table1.getValueAt(filaSeleccionada, 4).toString());
        textField5.setText(table1.getValueAt(filaSeleccionada, 5).toString());
    }
    private void configureTable() {
        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM productos");
                 ResultSet resultSet = statement.executeQuery()) {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("id_producto");
                model.addColumn("categoria");
                model.addColumn("marca");
                model.addColumn("nombre");
                model.addColumn("cantidad");
                model.addColumn("precio");
                while (resultSet.next()) {
                    int idProducto = resultSet.getInt("id_producto");
                    String categoria = resultSet.getString("categoria");
                    String marca = resultSet.getString("marca");
                    String nombre = resultSet.getString("nombre");
                    int cantidad = resultSet.getInt("cantidad");
                    double precio = resultSet.getDouble("precio");
                    model.addRow(new Object[]{idProducto,categoria,marca,nombre,cantidad,precio});
                }
                table1.setModel(model);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("La conexión a la base de datos es nula.");
        }
    }
}
