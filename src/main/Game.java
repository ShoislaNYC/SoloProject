package main;

import entities.player.Player;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import levels.LevelManager;
import window.GamePanel;
import window.Window;

import java.awt.*;
import static entities.player.PlayerConstants.*;

public class Game implements Runnable{
    private Window gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS = 120;
    private final int UPS = 200;

    private Playing playing;
    private Menu menu;

    //TILES
    public final static int TILES_DEFAULT_SIZE= 32;
    public final static float SCALE = 1.5f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE*SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;


    public Game(){
        // Initializes Entities
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new Window(gamePanel);
        gamePanel.requestFocus();

        //This method always goes last - Starts game Loop
        startGameLoop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    //UPDATE METHOD
    public void update(){

        switch(Gamestate.state){
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            default:
                break;
        }

    }
    //DRAW METHOD
    public void render(Graphics g){
        switch(Gamestate.state){
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
         double deltaU = 0;
        double deltaF = 0;

        try {
            // Main Game loop
            while (true) {
                long currentTime = System.nanoTime();

                deltaU += (currentTime - previousTime) / timePerUpdate;
                deltaF += (currentTime - previousTime) / timePerFrame;

                previousTime = currentTime;

                if (deltaU >= 1) {
                    update();
                    updates++;
                    deltaU--;
                }

                if (deltaF >= 1) {
                    gamePanel.repaint();
                    frames++;
                    deltaF--;

                }
                // Looks for if time passed is more than what we want
                if (System.currentTimeMillis() - lastCheck >= 1000) {
                    lastCheck = System.currentTimeMillis();
                    System.out.println("FPS : " + frames + " | UPS" + updates);
                    //Reset Frames
                    frames = 0;
                    updates = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void windowFocusLost(){
        if(Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }
    public Menu getMenu(){
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}

