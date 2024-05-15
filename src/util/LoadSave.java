package util;

import entities.enemies.Skelly;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static entities.player.PlayerConstants.EnemyConstants.*;

public class LoadSave {
    public static final String PLAYER_ATLAS = "res/knight_Sprite.png";
    public static final String SKELETON_ATLAS = "res/skeleton_sprite.png";

    public static final String LEVEL_ATLAS = "res/LandTiles.png";
    public static final String LEVEL_LONG_DATA = "res/level_one_LONG_1data.png";

    public static final String UI_ATLAS = "res/menu_atlas.png";

    public static final String MENU_BACKGROUND = "res/background.png";
    public static final String HEALTH_BAR = "res/health_power_bar.png";


    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        File knight_sprite = new File(fileName);

        try {
            img = ImageIO.read(new File(knight_sprite.toURI()));
//
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
    public static ArrayList<Skelly> GetSkeletons() {
        BufferedImage img = GetSpriteAtlas(LEVEL_LONG_DATA);
        ArrayList<Skelly> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == SKELLY)
                    list.add(new Skelly(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
            }
        return list;

    }
    public static int[][] GetLevelData() {
        BufferedImage img = GetSpriteAtlas(LEVEL_LONG_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value>= 225){
                    value = 0;
                }
                lvlData[j][i] = value;
            }
            return lvlData;
        }


}

