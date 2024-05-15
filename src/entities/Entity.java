package entities;

import util.Rect;

import java.awt.*;

public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rect hitbox;
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitbox(Graphics g, int xLvlOffset){
        // To debug hitbox
        g.setColor(Color.PINK);
        g.drawRect(hitbox.x - xLvlOffset,hitbox.y,hitbox.w,hitbox.h);
    }
    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rect((int)x,(int)y,width,height);
    }
//    protected void updateHitbox(){
//        //Updates position of hitbox
//        hitbox.x = (int)x;
//        hitbox.y = (int)y;
//    }
    public Rect getHitbox(){
        return hitbox;
    }
}
