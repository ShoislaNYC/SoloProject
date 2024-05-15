package entities.player;

import entities.Entity;
import gamestates.Playing;
import main.Game;
import util.LoadSave;
import util.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

import static entities.player.PlayerConstants.*;
import static util.Collider.*;

public class Player extends Entity {
    //Animation Arrays
    private BufferedImage[][] knightAni;
    private boolean isDead;
    //Global Variables
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE_RIGHT;
    private boolean movingLeft = false;
    private boolean movingRight= false;
    private int playerDirection = -1;
    private float playerSpeed = 1.5f;
    private boolean isMoving = false , attacking = false;
    private boolean left,up,right,down;
    private int[][] lvlData;
    private Direction lastDirection = Direction.RIGHT;

    //Jumping AND Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private float xDrawOffset = 60 * Game.SCALE;
    private float yDrawOffset = 50 * Game.SCALE;
//    private Rect PlayerRect;

    //health Bar:
    private BufferedImage healthBarImg;
    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    public int playerMaxHealth = 100;
    public int currentPlayerHealth = playerMaxHealth;
    public int healthWidth = healthBarWidth;

    //Attacking
    // AttackBox
    private Rect attackHitbox;

    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y,width,height);
        loadAnimations();
        this.playing = playing;
        initHitbox(x,y,55,115);
        initAttackhitbox();
    }

    private void initAttackhitbox() {
        attackHitbox = new Rect((int)x,(int)y,(int)(40 * Game.SCALE),(int)(40 * Game.SCALE));
    }

    public void resetAllValues() {
        resetDirBooleans();
        attacking = false;
        isMoving = false;
        playerAction = IDLE_RIGHT;
        currentPlayerHealth = playerMaxHealth;

        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }

    public enum Direction {
        RIGHT,
        LEFT
    }

    public void update(){
        updateHealth();
        if(currentPlayerHealth <= 0){
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();
        updatePos();
        if(attacking){
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkAttack() {
        if(attackChecked || aniIndex!= 1){
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackHitbox);
    }

    private void updateAttackBox() {
        if(right){
            attackHitbox.x = hitbox.x+hitbox.w+(int)(Game.SCALE*10);
        }else if(left){
            attackHitbox.x = hitbox.x-hitbox.w-(int)(Game.SCALE*10);
        }
        attackHitbox.y = hitbox.y + (int)(Game.SCALE*10);
    }

    private void updateHealth() {
        healthWidth = (int)((currentPlayerHealth/ (float)playerMaxHealth) * healthBarWidth);
    }
    public void changeHealth(int value){
        currentPlayerHealth+= value;
        if(currentPlayerHealth <= 0){
            currentPlayerHealth = 0;
            //Game over!
        }else if(currentPlayerHealth >= playerMaxHealth){
            currentPlayerHealth = playerMaxHealth;
        }
    }

    public void drawPlayer(Graphics g, int lvlOffset){
        //SPRITE
        g.drawImage(knightAni[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset)- lvlOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g, lvlOffset);
        drawAttackHitbox(g,lvlOffset);
        drawUI(g);
    }

    private void drawAttackHitbox(Graphics g, int lvlOffset) {
        g.setColor(Color.GREEN);
        g.drawRect((int)attackHitbox.x - lvlOffset,(int)attackHitbox.y,(int)attackHitbox.w,(int)attackHitbox.h);
    }

    private void drawUI(Graphics g) {
        g.drawImage(healthBarImg,statusBarX,statusBarY,statusBarWidth,statusBarHeight,null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX,healthBarYStart + statusBarY,healthWidth,healthBarHeight);
    }

    private void setAnimation() {
        int startAni = playerAction;
        if (attacking) {
            if (lastDirection == Direction.LEFT) {
                playerAction = ATTACK_1_LEFT;
            } else if (lastDirection == Direction.RIGHT) {
                playerAction = ATTACK_1_RIGHT;
            }
        } else if (!isMoving) {
            if (lastDirection == Direction.RIGHT) {
                playerAction = IDLE_RIGHT;
            } else if (lastDirection == Direction.LEFT) {
                playerAction = IDLE_LEFT;
            }
        } else {
            if (movingRight) {
                playerAction = RUNNING_RIGHT;
                lastDirection = Direction.RIGHT;
            } else if (movingLeft) {
                playerAction = RUNNING_LEFT;
                lastDirection = Direction.LEFT;
            }
        }

        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }
//    public void resetAttackAniTick(int startAni, int playerState){
//        if(startAni != ATTACK_1_LEFT){
//            aniIndex = 1;
//            aniTick = 0;
//            return;
//        }
//    }


    private void updatePos() {
        isMoving = false;
        movingLeft = false; // Reset movement flags
        movingRight = false;

        if (!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left)
            xSpeed = playerSpeed;
        if (up && !down){
            ySpeed = -playerSpeed;
        } else if (down && !up)
            ySpeed = playerSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.w, hitbox.h, lvlData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            if (left){
                movingLeft = true;
            }
            if (right){
                movingRight = true;
            }
            isMoving = true;

        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex>= GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
//                isMoving = false;
            }
        }
    }

    private void loadAnimations() {
        BufferedImage basicAnimations = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
//         Basic Animations
        knightAni = new BufferedImage[8][10];
        for(int j = 0; j < knightAni.length; j++){
            for(int i = 0; i< knightAni[j].length; i++){
                knightAni[j][i] = basicAnimations.getSubimage(i* PLAYER_SPRITE_WIDTH,j* PLAYER_SPRITE_HEIGHT, PLAYER_SPRITE_WIDTH, PLAYER_SPRITE_HEIGHT);
            }
        }
        healthBarImg = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);

    }
    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
    }
    public void resetDirBooleans(){
        left = false;
        right = false;
        down = false;
        up= false;
        isMoving = false;
    }

    // Getters and Setters
    public void setAttack(boolean attacking){
        this.attacking = attacking;
    }
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

//    public void setJump(boolean b) {
//        this.jump = jump;
//    }
}
