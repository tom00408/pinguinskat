package skat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Skat {

    static Functions functions = new Functions();

    public static void main(String[] args) {

        playSkat();


    }

    public static void playSkat(){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pinguinskat");
        try {
            // Lade das Icon aus dem res-Verzeichnis
            BufferedImage icon = ImageIO.read(Objects.requireNonNull(Skat.class.getResourceAsStream("/icon.png")));
            window.setIconImage(icon); // Setze das Icon
        } catch (IOException e) {
            System.err.println("Icon konnte nicht geladen werden: " + e.getMessage());
        }
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGame();
        window.dispose();
    }



}