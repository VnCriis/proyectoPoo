import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class inicio {
    JPanel inicioJPanel;
    private JButton button2;
    private JButton SOIniciobutton3;
    private JButton AYbutton4;
    private JButton adminButton;
    private JButton cajeroButton;
    private JButton bodegaButton;
    private JButton button8;
    private JToggleButton Cambiar_color;
    private JPanel inicioJPanel2;
    private JPanel inicioJPanel3;
    private JPanel inicioJPanel4;
    private JPanel inicioJPanel5;
    private JPanel inicioJPanel6;

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
        AYbutton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        Cambiar_color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Cambiar_color.isSelected()){
                    inicioJPanel.setBackground(new Color(233,255,234));
                    inicioJPanel2.setBackground(new Color(233,255,234));
                    inicioJPanel3.setBackground(new Color(233,255,234));
                    inicioJPanel4.setBackground(new Color(233,255,234));
                    inicioJPanel5.setBackground(new Color(233,255,234));
                    inicioJPanel6.setBackground(new Color(233,255,234));

                }else{
                    inicioJPanel.setBackground(new Color(203,223,241));
                    inicioJPanel2.setBackground(new Color(203,223,241));
                    inicioJPanel3.setBackground(new Color(203,223,241));
                    inicioJPanel4.setBackground(new Color(203,223,241));
                    inicioJPanel5.setBackground(new Color(203,223,241));
                    inicioJPanel6.setBackground(new Color(203,223,241));

                }
            }
        });
    }
}

