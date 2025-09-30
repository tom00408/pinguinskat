package menu;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Game {
    String name;
    public int x1,x2,y1,y2,h,w;

    boolean clickable;

    BufferedImage preview;

    public Game(String name){
        this.name = name;
    }

    public void setPos(int x,int y,int w,int h){
        this.x1 = x;
        this.y1 = y;
        this.x2 = x+w;
        this.y2 = y+h;
        this.h = h;
        this.w = w;
    }

    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(x1,y1,w,h);
        g.drawImage(preview,x1,y1,null);
    }

    public boolean clicked(int clickX,int clickY){
        return clickX >= x1
                && clickX <= x2
                && clickY >= y1
                && clickY <= y2;
    }

    public void setImg(BufferedImage img){
        preview = img;
    }

    public void setClickable(boolean flag){
        this.clickable = flag;
    }

    public boolean isClickable(){
        return this.clickable;
    }

}
