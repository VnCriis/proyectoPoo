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

public class inicio {
    JPanel inicioJPanel;
    private JButton CObutton1;
    private JButton button2;
    private JButton SOIniciobutton3;
    private JButton ayudaButton;
    private JButton adminButton;
    private JButton cajeroButton;
    private JButton bodegaButton;
    private JButton button8;
    private JPanel secundarioJPanel;

    public inicio() {
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalTime horaActual = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String horaFormateada = horaActual.format(formatter);
                button2.setText(horaFormateada);
            }
        });
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton sourceButton = (JButton) e.getSource();
                String tipoUsuario = null;

                if (sourceButton == adminButton) {
                    tipoUsuario = "administrador";
                } else if (sourceButton == cajeroButton) {
                    tipoUsuario = "cajero";
                } else if (sourceButton == bodegaButton) {
                    tipoUsuario = "bodeguero";
                }

                if (tipoUsuario != null) {
                    login loginInstance = new login(tipoUsuario);
                    Main.ventana.setContentPane(loginInstance.loginJPanel);
                    Main.ventana.revalidate();
                }
            }
        };
            adminButton.addActionListener(listener);
            cajeroButton.addActionListener(listener);
            bodegaButton.addActionListener(listener);
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "¡Muchas gracias por su visita!", "Gracias por Preferirnos", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        });
        SOIniciobutton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Para mas asesoria sobre el diseño del codigo llama al +59398765421 o al correo jzaldumbide@epn.education.ec", "Soporte", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        CObutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random random = new Random();
                int red = random.nextInt(256); // Valor entre 0 y 255 para el componente rojo
                int green = random.nextInt(256); // Valor entre 0 y 255 para el componente verde
                int blue = random.nextInt(256); // Valor entre 0 y 255 para el componente azul
                Color randomColor = new Color(red, green, blue);
                inicioJPanel.setBackground(randomColor);
                secundarioJPanel.setBackground(randomColor);
            }
        });
        ayudaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("manualInicio.pdf");
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
}

