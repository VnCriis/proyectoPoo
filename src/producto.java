import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class producto {
    JPanel productoJPanel;
    private JTable table1;
    private JButton regresarButton;
    private JButton button1;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JButton buscarButton;
    private JTextField textField6;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField7;
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
