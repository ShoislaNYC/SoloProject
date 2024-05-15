package util;

import java.awt.*;

public class Rect {

    public int x,y,w,h;


    public Rect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void moveBy(int dx, int dy)
    {
        x += dx;
        y += dy;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean contains(int mx, int my)
    {
        return (mx >= x)  &&
                (mx <= x + w)  &&
                (my >= y)  &&
                (my <= y + h);
    }

    public boolean overlaps(Rect r)
    {
        return (x + w >= r.x      ) &&
                (x     <= r.x + r.w) &&
                (y + h >= r.y      ) &&
                (y     <= r.y + r.h);
    }

    public void draw(Graphics pen)
    {
        pen.drawRect(x, y, w, h);
    }
    public void fill(Graphics pen)
    {
        pen.fillRect(x, y, w, h);
    }

}