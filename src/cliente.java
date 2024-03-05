import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cliente {
    JPanel clienteJPanel;
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
    private JButton ayudaButton;
    private Connection connection;

    public cliente() {
        connection = conector.obtenerConexion();
        configureTable();
        configureListeners();
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button1) {
                    eliminarEmpleado();
                    int opcion = JOptionPane.showConfirmDialog(null, "Cliente agregado correctamente", "Confirmación", JOptionPane.YES_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        Main.ventana.setContentPane(new Cajero().cajerop);
                        Main.ventana.revalidate();
                    }
                } else if (e.getSource() == button2) {
                    editarEmpleado();
                    int opcion = JOptionPane.showConfirmDialog(null, "Cliente agregado correctamente", "Confirmación", JOptionPane.YES_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        Main.ventana.setContentPane(new Cajero().cajerop);
                        Main.ventana.revalidate();
                    }
                } else if (e.getSource() == button3) {
                    agregarEmpleado();
                    int opcion = JOptionPane.showConfirmDialog(null, "Cliente agregado correctamente", "Confirmación", JOptionPane.YES_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        Main.ventana.setContentPane(new Cajero().cajerop);
                        Main.ventana.revalidate();
                    }
                }
            }
        };
        button1.addActionListener(listener);
        button2.addActionListener(listener);
        button3.addActionListener(listener);

        ayudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("manualEmpleado.pdf");
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
        if (connection != null) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM clientes");
                 ResultSet resultSet = statement.executeQuery()) {
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("id_cliente");
                model.addColumn("cedula");
                model.addColumn("nombres");
                model.addColumn("apellidos");
                model.addColumn("correo");
                model.addColumn("direccion");
                model.addColumn("telefono");
                model.addColumn("observaciones");
                while (resultSet.next()) {
                    int idCliente= resultSet.getInt("id_cliente");
                    int cedula = resultSet.getInt("cedula");
                    String nombres = resultSet.getString("nombres");
                    String apellidos = resultSet.getString("apellidos");
                    String correo = resultSet.getString("correo");
                    String direccion = resultSet.getString("direccion");
                    int telefono = resultSet.getInt("telefono");
                    String observaciones = resultSet.getString("observaciones");
                    model.addRow(new Object[]{idCliente, cedula, nombres, apellidos, correo, direccion, telefono, observaciones});
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
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM clientes WHERE id_cliente LIKE ?");
                statement.setString(1, "%" + searchText + "%");
                ResultSet resultSet = statement.executeQuery();
                DefaultTableModel searchModel = new DefaultTableModel();
                searchModel.addColumn("id_cliente");
                searchModel.addColumn("cedula");
                searchModel.addColumn("nombres");
                searchModel.addColumn("apellidos");
                searchModel.addColumn("correo");
                searchModel.addColumn("direccion");
                searchModel.addColumn("telefono");
                searchModel.addColumn("observaciones");
                while (resultSet.next()) {
                    int idEmpleado = resultSet.getInt("id_cliente");
                    int cedula = resultSet.getInt("cedula");
                    String nombres = resultSet.getString("nombres");
                    String apellidos = resultSet.getString("apellidos");
                    String correo = resultSet.getString("correo");
                    String direccion = resultSet.getString("direccion");
                    int telefono = resultSet.getInt("telefono");
                    String observaciones = resultSet.getString("observaciones");
                    searchModel.addRow(new Object[]{idEmpleado, cedula, nombres, apellidos, correo, direccion, telefono, observaciones});
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
            int idCliente = Integer.parseInt(textField1.getText());
            int cedula = Integer.parseInt(textField4.getText());
            String nombres = textField2.getText();
            String apellidos = textField3.getText();
            String correo = textField5.getText();
            String direccion = textField6.getText();
            int telefono = Integer.parseInt(textField7.getText());
            String observaciones = textField8.getText();
            String query = "INSERT INTO clientes(id_cliente, cedula, nombres, apellidos, correo, direccion, telefono, observaciones) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idCliente);
            statement.setInt(2, cedula);
            statement.setString(3, nombres);
            statement.setString(4, apellidos);
            statement.setString(5, correo);
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
            int idCliente = Integer.parseInt(textField1.getText());
            int cedula = Integer.parseInt(textField4.getText());
            String nombres = textField2.getText();
            String apellidos = textField3.getText();
            String correo = textField5.getText();
            String direccion = textField6.getText();
            int telefono = Integer.parseInt(textField7.getText());
            String observaciones = textField8.getText();
            String query = "UPDATE clientes SET cedula=?, nombres=?, apellidos=?, correo=?, direccion=?, telefono=?, observaciones=? " + "WHERE id_cliente=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cedula);
            statement.setString(2, nombres);
            statement.setString(3, apellidos);
            statement.setString(4, correo);
            statement.setString(5, direccion);
            statement.setInt(6, telefono);
            statement.setString(7, observaciones);
            statement.setInt(8, idCliente);
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
            String query = "DELETE FROM clientes WHERE id_empleado=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idEmpleado);
            statement.executeUpdate();
            configureTable();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
