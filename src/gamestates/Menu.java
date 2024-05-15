package gamestates;

import main.Game;
import ui.StartButton;
import util.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class Menu extends State implements Statemethods {
    private BufferedImage UI;

    private StartButton[] buttons = new StartButton[2];
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;
    private StartButton UIButton;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = 1248;
        menuHeight = 672;
        menuX = 0;
        menuY = 0;

    }

    private void loadButtons() {
        buttons[0] = new StartButton(Game.GAME_WIDTH/2, (int)(150 * Game.SCALE), 0,Gamestate.PLAYING);
        buttons[1] = new StartButton(Game.GAME_WIDTH/2, (int)(220 * Game.SCALE), 0,Gamestate.QUIT);
    }

    @Override
    public void update() {
        for(StartButton mb: buttons){
            mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg,menuX,menuY,menuWidth,menuHeight,null);
        for(StartButton mb: buttons) {
//            g.drawRect(mb.getBounds().x,mb.getBounds().y,mb.getBounds().w,mb.getBounds().h);
            mb.draw(g);
        }
    }

    @Override
    public void mouseclicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(StartButton mb: buttons){
            if(isInButton(e,mb)){
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(StartButton mb: buttons){
            if(isInButton(e,mb)){
                if(mb.isMousePressed()){
                 mb.changeGameState();
                 break;
                }
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(StartButton mb: buttons){
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(StartButton mb: buttons){
            mb.setMouseOver(false);
        }
        for(StartButton mb: buttons){
            if(isInButton(e,mb)){
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
