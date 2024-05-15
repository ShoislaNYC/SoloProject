package ui;

import gamestates.Gamestate;
import util.LoadSave;
import util.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static entities.player.PlayerConstants.UI.Buttons.*;

public class StartButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = B_WIDTH/2;
    private int yOffsetCenter = B_HEIGHT/2;

    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    ImageIcon menuBack;
    private Rect bounds;
    public StartButton(int x, int y, int rowIndex, Gamestate state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();

    }

    private void initBounds() {
        bounds = new Rect(xPos + xOffsetCenter, yPos+ yOffsetCenter, B_WIDTH*3, B_HEIGHT*3);
    }

    private void loadImgs() {
        imgs = new BufferedImage[2];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.UI_ATLAS);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos + xOffsetCenter, yPos + yOffsetCenter, B_WIDTH*3, B_HEIGHT*3, null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void update(){
        index = 0;
        if(mouseOver){
            index = 1;
        }
        if(mousePressed){
            index = 2;
        }

    }
    public void changeGameState(){
        Gamestate.state = state;
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public Rect getBounds() {
        return bounds;
    }
}
