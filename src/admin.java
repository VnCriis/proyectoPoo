import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class admin {
    JPanel adminJPanel;
    private JButton adminButton;
    private JButton facturaButton;
    private JButton bodegaButton;
    private JButton button8;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton ayudaButton;
    private JButton empleadoButton;
    private JPanel opcionesJPanel;

    public admin() {
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalTime horaActual = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String horaFormateada = horaActual.format(formatter);
                button2.setText(horaFormateada);
            }
        });
        empleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new empleado().empleadoJPanel);
                Main.ventana.revalidate();
            }
        });
        bodegaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new ProductosAdmin().productosJPanel);
                Main.ventana.revalidate();
            }
        });
        facturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new FacturasAD().facturasADm);
                Main.ventana.revalidate();
            }
        });
        button8.addActionListener(new ActionListener() {
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
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int red = random.nextInt(256); // Valor entre 0 y 255 para el componente rojo
                int green = random.nextInt(256); // Valor entre 0 y 255 para el componente verde
                int blue = random.nextInt(256); // Valor entre 0 y 255 para el componente azul
                Color randomColor = new Color(red, green, blue);
                adminJPanel.setBackground(randomColor);
                opcionesJPanel.setBackground(randomColor);
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Para mas asesoria sobre el diseño del codigo llama al +59398765421 o al correo jzaldumbide@epn.education.ec", "Soporte", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
