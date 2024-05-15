package gamestates;

import entities.enemies.EnemyManager;
import entities.player.Player;
import levels.LevelManager;
import main.Game;
import ui.GameOverScene;
import util.LoadSave;
import util.Rect;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods{


    private Player player;

    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private GameOverScene gameOverOverlay;
    private int xLvlOffset;
    private int leftBoreder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * Game.TILES_SIZE;

    private boolean gameOver;

    public Playing(Game game) {
        super(game);
        initClasses();
    }
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (160 * Game.SCALE), (int) (128 * Game.SCALE),this);
        player.loadLvlData(levelManager.getCurrentLvl().getLevelData());
        gameOverOverlay = new GameOverScene(this);

    }
    @Override
    public void update() {
        if(!gameOver){
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLvl().getLevelData(), player);
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;
        if(diff> rightBorder){
            xLvlOffset += diff -rightBorder;
        }else if(diff < leftBoreder){
            xLvlOffset+= diff - leftBoreder;
        }
        if(xLvlOffset > maxLvlOffsetX){
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.drawLevels(g, xLvlOffset);
        player.drawPlayer(g, xLvlOffset);
        enemyManager.drawEnemies(g, xLvlOffset);
        if(gameOver){
            gameOverOverlay.drawScene(g);
        }

    }
    public void ResetAll(){
        gameOver = false;
        player.resetAllValues();
        enemyManager.resetEnemies();
    }
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
    public void checkEnemyHit(Rect attackHitbox){
        enemyManager.checkMobHit(attackHitbox);
    }

    @Override
    public void mouseclicked(MouseEvent e) {
        if(!gameOver){
            if(e.getButton() == MouseEvent.BUTTON1){
                player.setAttack(true);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver){
            gameOverOverlay.keyPressed(e);
        } else{
            switch(e.getKeyCode()){
                case KeyEvent.VK_W:
                    player.setUp(true);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    Gamestate.state = Gamestate.MENU;
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(gameOver){
            gameOverOverlay.keyPressed(e);
        }else{
            switch(e.getKeyCode()){
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
            }
        }


    }
    public void windowFocusLost() {
    }
    public Player getPlayer(){
        return player;
    }
}
