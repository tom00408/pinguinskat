package skat;

import java.awt.*;

public class ClickableBox {
    public int x1,x2,y1,y2,h,b;
    public String text;
    public Color color;
    public ClickableBox(String text,int x, int y, int h, int b, Color color){
        this.text = text;
        this.x1 = x;
        this.y1 = y;
        this.x2 = x+b;
        this.y2 = y+h;
        this.h = h;
        this.b = b;
        this.color = color;
    }

    public boolean clicked(int clickX,int clickY){
        return clickX >= x1
                && clickX <= x2
                && clickY >= y1
                && clickY <= y2;
    }

}
