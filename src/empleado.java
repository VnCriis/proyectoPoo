import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class empleado {
    JPanel empleadoJPanel;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField1;
    private JTextField textField4;
    private JTextField textField6;
    private JTable table1;
    private JTextField textField8;
    private JTextField textField7;
    private JTextField textField5;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton regresarButton;
    private Connection connection;

    public empleado() {
        connection = conector.obtenerConexion();
        configureTable();
        configureListeners();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button1) {
                    eliminarEmpleado();
                } else if (e.getSource() == button2) {
                    editarEmpleado();
                } else if (e.getSource() == button3) {
                    agregarEmpleado();
                }
            }
        };
        button1.addActionListener(listener);
        button2.addActionListener(listener);
        button3.addActionListener(listener);
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new admin().adminJPanel);
                Main.ventana.revalidate();
            }
        });
    }
    private void configureTable() {
        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados");
                 ResultSet resultSet = statement.executeQuery()) {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("id_empleado");
                model.addColumn("cedula");
                model.addColumn("nombres");
                model.addColumn("apellidos");
                model.addColumn("cargo");
                model.addColumn("direccion");
                model.addColumn("telefono");
                model.addColumn("observaciones");
                while (resultSet.next()) {
                    int idEmpleado = resultSet.getInt("id_empleado");
                    int cedula = resultSet.getInt("cedula");
                    String nombres = resultSet.getString("nombres");
                    String apellidos = resultSet.getString("apellidos");
                    String cargo = resultSet.getString("cargo");
                    String direccion = resultSet.getString("direccion");
                    int telefono = resultSet.getInt("telefono");
                    String observaciones = resultSet.getString("observaciones");
                    model.addRow(new Object[]{idEmpleado, cedula, nombres, apellidos, cargo, direccion, telefono, observaciones});
                }
                table1.setModel(model);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("La conexión a la base de datos es nula.");
        }
    }
    private void configureListeners() {
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table1.getSelectedRow();
                    if (selectedRow != -1 && selectedRow < table1.getRowCount()) {
                        textField4.setText(table1.getValueAt(selectedRow, 1).toString());
                        textField2.setText(table1.getValueAt(selectedRow, 2).toString());
                        textField3.setText(table1.getValueAt(selectedRow, 3).toString());
                        textField5.setText(table1.getValueAt(selectedRow, 4).toString());
                        textField6.setText(table1.getValueAt(selectedRow, 5).toString());
                        textField7.setText(table1.getValueAt(selectedRow, 6).toString());
                        textField8.setText(table1.getValueAt(selectedRow, 7).toString());
                    }
                }
            }
        });
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchEmployee();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                searchEmployee();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                searchEmployee();
            }
        });
    }
    private void searchEmployee() {
        String searchText = textField1.getText().trim();
        if (!searchText.isEmpty()) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE id_empleado LIKE ?");
                statement.setString(1, "%" + searchText + "%");
                ResultSet resultSet = statement.executeQuery();
                DefaultTableModel searchModel = new DefaultTableModel();
                searchModel.addColumn("id_empleado");
                searchModel.addColumn("cedula");
                searchModel.addColumn("nombres");
                searchModel.addColumn("apellidos");
                searchModel.addColumn("cargo");
                searchModel.addColumn("direccion");
                searchModel.addColumn("telefono");
                searchModel.addColumn("observaciones");
                while (resultSet.next()) {
                    int idEmpleado = resultSet.getInt("id_empleado");
                    int cedula = resultSet.getInt("cedula");
                    String nombres = resultSet.getString("nombres");
                    String apellidos = resultSet.getString("apellidos");
                    String cargo = resultSet.getString("cargo");
                    String direccion = resultSet.getString("direccion");
                    int telefono = resultSet.getInt("telefono");
                    String observaciones = resultSet.getString("observaciones");
                    searchModel.addRow(new Object[]{idEmpleado, cedula, nombres, apellidos, cargo, direccion, telefono, observaciones});
                }
                table1.setModel(searchModel);
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            configureTable();
        }
    }
    private void agregarEmpleado() {
        try {
            // Obtener los datos de los JTextField
            int idEmpleado = Integer.parseInt(textField1.getText());
            int cedula = Integer.parseInt(textField4.getText());
            String nombres = textField2.getText();
            String apellidos = textField3.getText();
            String cargo = textField5.getText();
            String direccion = textField6.getText();
            int telefono = Integer.parseInt(textField7.getText());
            String observaciones = textField8.getText();
            String query = "INSERT INTO empleados (id_empleado, cedula, nombres, apellidos, cargo, direccion, telefono, observaciones) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idEmpleado);
            statement.setInt(2, cedula);
            statement.setString(3, nombres);
            statement.setString(4, apellidos);
            statement.setString(5, cargo);
            statement.setString(6, direccion);
            statement.setInt(7, telefono);
            statement.setString(8, observaciones);
            statement.executeUpdate();
            configureTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void editarEmpleado() {
        try {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idEmpleado = Integer.parseInt(textField1.getText());
            int cedula = Integer.parseInt(textField4.getText());
            String nombres = textField2.getText();
            String apellidos = textField3.getText();
            String cargo = textField5.getText();
            String direccion = textField6.getText();
            int telefono = Integer.parseInt(textField7.getText());
            String observaciones = textField8.getText();
            String query = "UPDATE empleados SET cedula=?, nombres=?, apellidos=?, cargo=?, direccion=?, telefono=?, observaciones=? " + "WHERE id_empleado=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cedula);
            statement.setString(2, nombres);
            statement.setString(3, apellidos);
            statement.setString(4, cargo);
            statement.setString(5, direccion);
            statement.setInt(6, telefono);
            statement.setString(7, observaciones);
            statement.setInt(8, idEmpleado);
            statement.executeUpdate();
            configureTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private void eliminarEmpleado() {
        try {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idEmpleado = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
            String query = "DELETE FROM empleados WHERE id_empleado=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idEmpleado);
            statement.executeUpdate();
            configureTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
