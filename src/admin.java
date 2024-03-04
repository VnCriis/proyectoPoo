import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class admin {
    JPanel adminJPanel;
    private JButton adminButton;
    private JButton cajeroButton;
    private JButton bodegaButton;
    private JButton button8;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;

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
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new empleado().empleadoJPanel);
                Main.ventana.revalidate();
            }
        });
        cajeroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new ProductosAdmin().productosJPanel);
                Main.ventana.revalidate();
            }
        });
        bodegaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.ventana.setContentPane(new FacturasAD().facturasADm);
                Main.ventana.revalidate();
            }
        });
    }
}
