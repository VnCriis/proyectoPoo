import javax.swing  .*;
public class Main {
    static JFrame ventana = new JFrame("MINIMARKET-PRO");
    public static void main (String[]args) {
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(new producto().productoJPanel);
        ventana.pack();
        ventana.setSize(1900, 870);
        ventana.setVisible(true);
    }
}