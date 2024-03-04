import javax.swing  .*;
public class Main {
    static JFrame ventana = new JFrame("MINIMARKET-PRO");
    public static void main (String[]args) {
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setContentPane(new inicio().inicioJPanel);
        ventana.pack();
        ventana.setSize(1900, 870);
        ventana.setVisible(true);
    }
}