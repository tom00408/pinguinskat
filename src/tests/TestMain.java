package tests;
import javax.swing.*;

public class TestMain {

        public static void main(String[] args) {
            // Erstes Fenster
            JFrame window1 = new JFrame("Fenster 1");
            window1.setSize(300, 200);
            window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel label1 = new JLabel("Dies ist Fenster 1", SwingConstants.CENTER);
            window1.add(label1);
            window1.setVisible(true);

            // Zweites Fenster
            JFrame window2 = new JFrame("Fenster 2");
            window2.setSize(300, 200);
            window2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JLabel label2 = new JLabel("Dies ist Fenster 2", SwingConstants.CENTER);
            window2.add(label2);
            window2.setVisible(true);
        }
    

}
