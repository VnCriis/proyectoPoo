import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cliente {
    JPanel client;
    private JButton Volver;
    private JTextField nombretxt;
    private JTextField apellidostxt;
    private JTextField CItxt;
    private JTextField correotxt;
    private JTextField diretxt;
    private JTextField teletxt;
    private JButton ingresarButton;
    public cliente() {
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;
                try {
                    conn = conector.obtenerConexion();
                    String sql = "INSERT INTO cliente (cedula,nombres,apellidos,correo,direccion,telefono) VALUES (?,?,?,?,?,?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1,CItxt.getText());
                    stmt.setString(2,nombretxt.getText());
                    stmt.setString(3, apellidostxt.getText());
                    stmt.setString(4,correotxt.getText());
                    stmt.setString(5,diretxt.getText());
                    stmt.setInt(6,Integer.parseInt(teletxt.getText()));
                    int filasAfectadas = stmt.executeUpdate();
                    if (filasAfectadas > 0) {
                        System.out.println("Datos insertados correctamente.");
                        JOptionPane.showMessageDialog(null, "Cliente Ingresado.");
                    } else {
                        System.out.println("No se pudo insertar los datos.");
                    }
                    apellidostxt.setText("");
                    nombretxt.setText("");
                    CItxt.setText("");
                    correotxt.setText("");
                    diretxt.setText("");
                    teletxt.setText("");
                } catch (SQLException ex) {
                    System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
                }
            }
        });
        Volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new Cajero().cajerop);
                Main.ventana.revalidate();
            }
        });
    }
}
