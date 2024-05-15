package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class GameOverScene {
    private Playing playing;
    public String bitFontName;
    private Font arial_40;
    private Font arcadeFont;
    public GameOverScene(Playing playing){
        this.playing = playing;

        try{
            Font customFont = (Font.createFont(Font.TRUETYPE_FONT, new File("res/kongtext.ttf")));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            bitFontName = customFont.getFontName();
            ge.registerFont(customFont);

        }catch(Exception e){
            e.printStackTrace();
        }

        arial_40 = new Font("Arial", Font.BOLD,25);
        arcadeFont = new Font(bitFontName, Font.TRUETYPE_FONT, 30);
    }
    public void drawScene(Graphics g){
        Color purpleBackground = new Color(34,32,52);
        g.setColor(purpleBackground);
        g.fillRect(0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(arcadeFont);
        g.drawString("Game OVER!", (Game.GAME_WIDTH/2)-150, 150);
        g.drawString("Press Esc to Restart", (Game.GAME_WIDTH/2)-250,300);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
//            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

}
