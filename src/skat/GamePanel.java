package skat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GamePanel extends JPanel {

    final int FPS = 60;
    final int screenWidth = 1400;
    final int screenHeight = 800;

    public Thread gameThread;

    public Board board;

    Functions f;

    boolean running = false;


    BufferedImage table;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.board = new Board();
        f = new Functions();

        loadImages();

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                board.getMouseClick(x,y);
                //System.out.println(x+" | "+y);
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                int keyCode = e.getKeyCode();
                //System.out.println(keyChar);
                //System.out.println("keyCode: "+keyCode+ " | keyChar: "+ keyChar);
                board.getKeypress(keyChar,keyCode);
            }
        });
    }


    public void startGame(){
        running = true;
        while (running){
            update();
            repaint();
            running = board.running;
        }
    }


    public void loadImages()  {

        try {
            table = ImageIO.read(getClass().getResourceAsStream("/table.png"));
        }
        catch (IOException e) {}
    }



    /*
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while (gameThread != null) {


            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                // System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

     */


    public void update(){
        board.update();
        f.sleeper(1000/FPS);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.drawImage(table,0,0,null);

        board.draw(g);

        g.dispose();
    }
}
