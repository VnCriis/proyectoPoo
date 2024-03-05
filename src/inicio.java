import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class inicio {
    JPanel inicioJPanel;
    private JButton CObutton1;
    private JButton button2;
    private JButton SOIniciobutton3;
    private JButton AYbutton4;
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

            }
        });
        AYbutton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}

