package entities.enemies;

import entities.player.Player;
import main.Game;
import util.LoadSave;
import util.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

import gamestates.Playing;

import java.util.ArrayList;

import static entities.player.PlayerConstants.EnemyConstants.*;
import static entities.player.PlayerConstants.PLAYER_SPRITE_HEIGHT;
import static entities.player.PlayerConstants.PLAYER_SPRITE_WIDTH;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] skellyArr;
    private float xDrawOffset = 45 * Game.SCALE;
    private float yDrawOffset = 25 * Game.SCALE;
    private ArrayList<Skelly> skellies = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies(){
        skellies = LoadSave.GetSkeletons();
    }

    public void checkMobHit(Rect attackHitbox){
        for(Skelly s: skellies){
            if(s.getActive()) {
                if (attackHitbox.overlaps(s.getHitbox())) {
                    s.hurt(10);
                    System.out.println(s.currentEnemeyHealth);
                    return;
                }
            }
        }
    }
    private void loadEnemyImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SKELETON_ATLAS);
        skellyArr = new BufferedImage[10][4];
        for (int j = 0; j < skellyArr.length; j++)
            for (int i = 0; i < skellyArr[j].length; i++)
                skellyArr[j][i] = temp.getSubimage(i * PLAYER_SPRITE_WIDTH, j * PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
    }

    public void resetEnemies(){
        for (Skelly s: skellies){
            s.resetEnemyValues();
        }
    }

    public void update(int[][] lvlData, Player player) {
        for (Skelly s : skellies) {
            if (s.getActive()) {
                s.update(lvlData, player);
            }
        }
    }

    public void drawEnemies(Graphics g, int xLvlOffset) {
        drawSkeletons(g, xLvlOffset);

    }

    private void drawSkeletons(Graphics g, int xLvlOffset) {
        for (Skelly s : skellies) {
            if (s.getActive()) {
                    g.drawImage(skellyArr[s.getEnemyState()][s.getAniIndex()], (int) (s.getHitbox().x - xDrawOffset) - xLvlOffset, (int) (s.getHitbox().y - yDrawOffset), SKELLY_WIDTH, SKELLY_HEIGHT, null);
//                    Draw Hitboxes for Debug
                    s.drawEnemyHitbox(g, xLvlOffset);
                    s.drawAttackHitbox(g, xLvlOffset);
            }
        }
    }

}
