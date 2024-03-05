import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    JPanel loginJPanel;

    private JButton button1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton SALIRButton;
    private JPanel login1;
    private JPanel login2;
    private JButton ayudaButton;
    private JButton button3;

    public login() {
    }

    public login(String tipoUsuario) {
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new inicio().inicioJPanel);
                Main.ventana.revalidate();
            }
        });
        ayudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("manualLogin.pdf");
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
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = textField1.getText();
                String contraseña = new String(passwordField1.getPassword());
                Connection conexion = null;
                PreparedStatement statement = null;
                ResultSet resultSet = null;

                try {
                    conexion = conector.obtenerConexion();

                    String sql = "";
                    if (tipoUsuario.equals("administrador")) {
                        sql = "SELECT * FROM administradores WHERE usuario=? AND contraseña=?";
                    } else if (tipoUsuario.equals("cajero")) {
                        sql = "SELECT * FROM cajeros WHERE usuario=? AND contraseña=?";
                    } else if (tipoUsuario.equals("bodeguero")) {
                        sql = "SELECT * FROM bodegueros WHERE usuario=? AND contraseña=?";
                    }

                    statement = conexion.prepareStatement(sql);
                    statement.setString(1, usuario);
                    statement.setString(2, contraseña);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        switch (tipoUsuario) {
                            case "administrador":
                                Main.ventana.setContentPane(new admin().adminJPanel);
                                Main.ventana.revalidate();
                                break;
                            case "cajero":
                                Main.ventana.setContentPane(new Cajero().cajerop);
                                Main.ventana.revalidate();
                                break;
                            case "bodeguero":
                                Main.ventana.setContentPane(new producto().productoJPanel);
                                Main.ventana.revalidate();
                                break;
                        }
                    }  else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos.");
                } finally {
                    try {
                        if (resultSet != null) resultSet.close();
                        if (statement != null) statement.close();
                        if (conexion != null) conexion.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}