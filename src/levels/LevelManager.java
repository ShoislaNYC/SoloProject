package levels;

import main.Game;
import util.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static javafx.scene.paint.Color.rgb;

public class LevelManager {
    private Game game;
    private BufferedImage [] levelSprite;
    private Level levelOne;
    private Level currentLevel;
    public LevelManager(Game game){
        this.game = game;
        importOutSideSprites();
        levelOne = new Level(LoadSave.GetLevelData());
    }

    private void importOutSideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[225];
        //Columns
        for(int j = 0; j < 15; j++){
            //Rows
            for(int i = 0; i < 15; i++){
                //Use the Row for number
                int index = j*15+i;
                levelSprite[index] = img.getSubimage(i*32,j*32,32,32);
            }
        }
    }

    public void drawLevels(Graphics g , int lvlOffset){
        Color purpleBackground = new Color(34,32,52);
        g.setColor(purpleBackground);
        g.fillRect(0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT);
        for(int j = 0; j < Game.TILES_IN_HEIGHT; j++)
            for(int i = 0; i< levelOne.getLevelData()[0].length; i++){
                int index = levelOne.getSpriteIndex(i,j);
                g.drawImage(levelSprite[index],i* Game.TILES_SIZE - lvlOffset,j* Game.TILES_SIZE,Game.TILES_SIZE,Game.TILES_SIZE,null);
            }
    }
    public void update(){

    }

    public Level getCurrentLvl() {
        return levelOne;
    }
}

