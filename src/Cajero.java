import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class Cajero {
    JPanel cajerop;
    private JButton regresarButton;
    private JTextField codigotxt;
    private JButton buscarButton;
    private JTextField idtxt;
    private JButton buscarcliente;
    private JButton crearButton;
    private JTextField categoriatxt;
    private JTextField marcatxt;
    private JTextField nombretxt;
    private JTextField preciotxt;
    private JSpinner spinner1;
    private JButton agregarButton;
    private JButton venderButton;
    private JTable carro;
    boolean encontrado = false;
    boolean encontrado_client = false;
    String nombre = null;
    String marca=null;
    String categoria=null;
    int stock = 0;
    double preci = 0;
    String nombre_cli=null;
    String apellido=null;
    int cedula=0;
    String correo=null;
    String direccion=null;
    int telefono=0;
    public Cajero() {;
        configureTable();
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.ventana.setContentPane(new inicio().inicioJPanel);
                Main.ventana.pack();
                Main.ventana.setSize(1900, 870);
                Main.ventana.setVisible(true);
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encontrado = false;
                categoriatxt.setText("");
                nombretxt.setText("");
                preciotxt.setText("");
                marcatxt.setText("");
                Connection conn;
                PreparedStatement stmt;
                ResultSet rs;
                try {
                    conn = conector.obtenerConexion();
                    String sql = "SELECT * FROM productos";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    String codigoIngresado = codigotxt.getText();
                    while (rs.next()) {
                        String codigo = rs.getString("id_producto");
                        if (codigo.equals(codigoIngresado)) {
                            encontrado = true;
                            nombre=rs.getString("nombre");
                            preci= rs.getDouble("precio");
                            marca=rs.getString("marca");
                            categoria=rs.getString("categoria");
                            stock=rs.getInt("cantidad");
                        }
                    }
                    if (encontrado) {
                        nombretxt.setText(nombre);
                        preciotxt.setText(String.valueOf(preci));
                        categoriatxt.setText(categoria);
                        marcatxt.setText(marca);
                    } else {
                        JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    }
                } catch (SQLException ex) {
                    System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
                }
            }
        });
        buscarcliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encontrado_client=false;
                Connection conn;
                PreparedStatement stmt;
                ResultSet rs;
                try {
                    conn = conector.obtenerConexion();
                    String sql = "SELECT * FROM cliente";
                    stmt = conn.prepareStatement(sql);
                    rs = stmt.executeQuery();
                    String codigoIngresado = idtxt.getText();
                    while (rs.next()) {
                        String codigo = rs.getString("id_cliente");
                        if (codigo.equals(codigoIngresado)) {
                            encontrado = true;
                            cedula=rs.getInt("cedula");
                            nombre_cli=rs.getString("nombres");
                            apellido=rs.getString("apellidos");
                            correo=rs.getString("correo");
                            direccion=rs.getString("direccion");
                            telefono=rs.getInt("telefono");
                        }
                    }
                    if (!encontrado) {
                        JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                    }else{
                        JOptionPane.showMessageDialog(null, "Cliente encontrado.");
                    }
                } catch (SQLException ex) {
                    System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
                }
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int canti = (int) spinner1.getValue();
                if (canti<0){
                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad disponible.");
                } else if (canti>stock) {
                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad que no sea mayor al stock.");
                }else{
                    Connection conn = null;
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    try {
                        conn = conector.obtenerConexion();
                        String sql = "INSERT INTO carrito (nombre_produc,cantidad,precio,sub_total) VALUES (?,?,?,?)";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1,nombretxt.getText());
                        stmt.setInt(2, canti);
                        stmt.setDouble(3,Double.parseDouble(preciotxt.getText()));
                        stmt.setDouble(4,canti*Double.parseDouble(preciotxt.getText()));
                        int filasAfectadas = stmt.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Datos insertados correctamente.");
                        } else {
                            System.out.println("No se pudo insertar los datos.");
                        }
                        String sql2 = "UPDATE productos SET cantidad = ? WHERE id_producto = ?";
                        stmt = conn.prepareStatement(sql2);
                        stmt.setInt(1,stock-canti);
                        stmt.setInt(2,Integer.parseInt(codigotxt.getText()));
                        int filasAfecta = stmt.executeUpdate();
                        if (filasAfecta > 0) {
                            System.out.println("Valor modificado correctamente.");
                        } else {
                            System.out.println("No se pudo modificar el valor.");
                        }
                        conn.close();
                        categoriatxt.setText("");
                        nombretxt.setText("");
                        preciotxt.setText("");
                        marcatxt.setText("");
                        configureTable();
                    } catch (SQLException ex) {
                        System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
                    }
                }
            }
        });
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                Main.ventana.setContentPane(new cliente().client);
                Main.ventana.pack();
                Main.ventana.setSize(1900, 870);
                Main.ventana.setVisible(true);
            }
        });
        venderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fontFile = "C:\\Windows\\Fonts\\Arial.ttf";
                try {
                    Connection conn;
                    PreparedStatement stmt = null;
                    ResultSet rs = null;
                    try {
                        String sql2 ="SELECT nombre_produc,sub_total FROM carrito";
                        conn = conector.obtenerConexion();
                        stmt = conn.prepareStatement(sql2);
                        rs = stmt.executeQuery();
                        StringBuilder Productos = new StringBuilder();
                        double total=0;
                        while (rs.next()) {
                            String nombreProducto = rs.getString("nombre_produc");
                            Productos.append(nombreProducto).append(", ");
                            total=total+rs.getDouble("sub_total");
                        }
                        rs.close();
                        String productos=Productos.toString();
                        String sql = "INSERT INTO facturasad (nombre_cliente,cedula,productos,total) VALUES (?,?,?,?)";
                        stmt = conn.prepareStatement(sql);
                        stmt.setString(1,nombre_cli+" "+apellido);
                        stmt.setInt(2, cedula);
                        stmt.setString(3,productos);
                        stmt.setDouble(4,total+(total*0.15));
                        int filasAfectadas = stmt.executeUpdate();
                        if (filasAfectadas > 0) {
                            System.out.println("Datos insertados correctamente.");
                        } else {
                            System.out.println("No se pudo insertar los datos.");
                        }

                        String sql3 ="SELECT  numero FROM facturasad ORDER BY numero DESC LIMIT 1";
                        conn = conector.obtenerConexion();
                        stmt = conn.prepareStatement(sql3);
                        rs = stmt.executeQuery();
                        int num=0;
                        while (rs.next()) {
                            num = rs.getInt("numero");
                        }
                        rs.close();

                        conn = conector.obtenerConexion();
                        String consulta = "SELECT * FROM carrito";
                        try (Statement statement = conn.createStatement();
                             ResultSet resultSet = statement.executeQuery(consulta);
                             PDDocument document = new PDDocument()) {
                            PDPage page = new PDPage();
                            document.addPage(page);
                            PDPageContentStream contentStream = new PDPageContentStream(document, page);
                            PDType0Font font;
                            try {
                                font = PDType0Font.load(document, new File(fontFile));
                            } catch (IOException exception) {
                                exception.printStackTrace();
                                return;
                            }
                            contentStream.setFont(font, 12);
                            float y = 750;

                            contentStream.beginText();
                            contentStream.newLineAtOffset(100, y);
                            contentStream.showText(String.valueOf(cedula));
                            contentStream.endText();
                            y -= 20;

                            contentStream.beginText();
                            contentStream.newLineAtOffset(100, y);
                            contentStream.showText(nombre_cli + " " + apellido);
                            contentStream.endText();
                            y -= 20;

                            contentStream.beginText();
                            contentStream.newLineAtOffset(100, y);
                            contentStream.showText(correo);
                            contentStream.endText();
                            y -= 20;

                            contentStream.beginText();
                            contentStream.newLineAtOffset(100, y);
                            contentStream.showText(direccion);
                            contentStream.endText();
                            y -= 20;

                            contentStream.beginText();
                            contentStream.newLineAtOffset(100, y);
                            contentStream.showText(String.valueOf(telefono));
                            contentStream.endText();
                            y -= 20;

                            contentStream.beginText();
                            contentStream.newLineAtOffset(150, y);
                            contentStream.showText("Productos");
                            contentStream.endText();
                            y -= 20;

                            while (resultSet.next()) {
                                String columna1 = resultSet.getString("nombre_produc");
                                String columna2 = resultSet.getString("cantidad");
                                String columna3 = resultSet.getString("precio");
                                String columna4 = resultSet.getString("sub_total");
                                contentStream.beginText();
                                contentStream.newLineAtOffset(100, y);
                                contentStream.showText(columna1 + " - " + columna2 + " - " + columna3 + " - " + columna4);
                                contentStream.endText();
                                y -= 12;
                            }
                            contentStream.beginText();
                            contentStream.newLineAtOffset(100, y);
                            contentStream.showText("Total:"+total);
                            contentStream.endText();
                            y -= 20;

                            contentStream.close();
                            document.save("datos"+num+".pdf");
                            System.out.println("PDF creado correctamente.");

                            String dropTableSQL = "DROP TABLE IF EXISTS carrito";
                            Statement dropStatement = conn.createStatement();
                            dropStatement.executeUpdate(dropTableSQL);
                            dropStatement.close();

                            String createTableSQL = "CREATE TABLE carrito (" +
                                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                                    "nombre_produc VARCHAR(255), " +
                                    "cantidad INT, " +
                                    "precio DOUBLE, " +
                                    "sub_total DOUBLE)";
                            Statement createStatement = conn.createStatement();
                            createStatement.executeUpdate(createTableSQL);
                            createStatement.close();
                            conn.close();
                            configureTable();
                        } catch (Exception ex) {
                            System.err.println("Error al manipular el PDF: " + ex.getMessage());
                        }
                    } catch (Exception ex) {
                        System.err.println("Error al conectar con la base de datos: " + ex.getMessage());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        codigotxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (codigotxt.getText().equals("Ingrese el codigo")) {
                    codigotxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (codigotxt.getText().isEmpty()) {
                    codigotxt.setText("Ingrese el codigo");
                }
            }
        });
        idtxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (idtxt.getText().equals("ingrese el id del cliente")) {
                    idtxt.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (idtxt.getText().isEmpty()) {
                    idtxt.setText("ingrese el id del cliente");
                }
            }
        });
    }
    private void configureTable() {
        Connection conn;
        PreparedStatement stmt;
        ResultSet rs;
        try {
            conn = conector.obtenerConexion();
            String sql3 = "SELECT * FROM carrito";
            stmt = conn.prepareStatement(sql3);
            rs = stmt.executeQuery();
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Producto");
            modelo.addColumn("Cantidad");
            modelo.addColumn("Precio");
            modelo.addColumn("Sub_total");
            while (rs.next()) {
                String producto = rs.getString("nombre_produc");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                double sub_total = rs.getDouble("sub_total");
                modelo.addRow(new Object[]{producto,cantidad,precio,sub_total});
            }
            carro.setModel(modelo);
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
