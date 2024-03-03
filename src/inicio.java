import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class inicio {
    JPanel inicioJPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton adminButton;
    private JButton cajeroButton;
    private JButton bodegaButton;
    private JButton button8;

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
                    // Crear una instancia de login para el tipo de usuario seleccionado
                    login loginInstance = new login(tipoUsuario);

                    // Establecer el panel de contenido de la ventana principal
                    Main.ventana.setContentPane(loginInstance.loginJPanel);
                    Main.ventana.revalidate();
                }
            }
        };
            adminButton.addActionListener(listener);
            cajeroButton.addActionListener(listener);
            bodegaButton.addActionListener(listener);
    }
}

