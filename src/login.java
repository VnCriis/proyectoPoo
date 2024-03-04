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

    public login(String tipoUsuario) {
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
                                Main.ventana.setContentPane(new admin().adminJPanel);
                                Main.ventana.revalidate();
                                break;
                            case "bodeguero":
                                Main.ventana.setContentPane(new admin().adminJPanel);
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
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(SALIRButton);
                frame.dispose();
                Main.ventana.setContentPane(new inicio().inicioJPanel);
                Main.ventana.revalidate();
                Main.ventana.setSize(1800,870);
                Main.ventana.setLocationRelativeTo(null);
                Main.ventana.setVisible(true);
            }
        });
    }
}