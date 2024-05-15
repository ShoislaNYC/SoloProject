package entities.enemies;

import entities.Entity;
import entities.player.Player;
import entities.player.PlayerConstants;
import main.Game;
import util.Rect;

import static entities.player.PlayerConstants.EnemyConstants.*;
import static entities.player.PlayerConstants.Directions.*;

import java.awt.image.BufferedImage;

import static util.Collider.CanMoveHere;
import static util.Collider.isSightClear;

public class Enemy extends Entity {
    private BufferedImage[][] skeletonAni;
    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean movingLeft = false, movingRight= false;
    protected boolean enemyIsMoving = false , enemyIsAttacking = false;
    protected int walkspeed = 1;
    protected int walkDir = LEFT;
    protected int tileY;
    protected  float attackDistance = Game.TILES_SIZE;
    protected int enemyMaxHealth;
    protected int currentEnemeyHealth;
    protected boolean active = true;
    protected boolean attackChecked;


    public Enemy(float x, float y, int w, int h, int enemyType) {
        super(x, y,w,h);
        this.enemyType = enemyType;
        initHitbox(x,y,(int)(64*Game.SCALE),(int)(90*Game.SCALE));
        enemyMaxHealth = GetMaxHealth(enemyType);
        currentEnemeyHealth = enemyMaxHealth;
    }

    protected void moveEnemyLeftnRight(float xSpeed, float ySpeed){
        hitbox.x += xSpeed;
        hitbox.y += ySpeed;
        if (walkDir == LEFT) {
            movingLeft = true;
        }
        if (walkDir == RIGHT) {
            movingRight = true;
        }
        enemyIsMoving = true;
    }

    protected void changeWalkDir() {
        if(walkDir == LEFT){
            walkDir = RIGHT;
            newState(PlayerConstants.EnemyConstants.RUNNING_RIGHT);

        }else{
            walkDir = LEFT;
            newState(PlayerConstants.EnemyConstants.RUNNING_LEFT);
        }
    }
    protected void moveEnemy(int [][] lvlData){
        float xSpeed = 0, ySpeed = 0;
        if (walkDir == LEFT && walkDir != RIGHT) {
            xSpeed = -walkspeed;
        } else if (walkDir != LEFT && walkDir == RIGHT)
            xSpeed = walkspeed;
        if (walkDir == UP && walkDir != DOWN) {
            ySpeed = -walkspeed;
        } else if (walkDir != UP && walkDir == DOWN)
            ySpeed = walkspeed;
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.w, hitbox.h, lvlData)) {
            moveEnemyLeftnRight(xSpeed,ySpeed);
        } else  {
            changeWalkDir();
        }
    }

    protected void turnToPlayer(Player player){
        if(player.getHitbox().x > hitbox.x){
            walkDir = RIGHT;
        }else{
            walkDir = LEFT;
        }
        if(player.getHitbox().y > hitbox.y){
            walkDir = UP;
        }else{
            walkDir = DOWN;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerTileY = player.getHitbox().y/Game.TILES_SIZE;
        if(playerTileY == tileY){
            if(isPlayerInRange(player)){
                System.out.println("Player in Skeleton Range");
                if(isSightClear(lvlData,hitbox,player.getHitbox(),tileY))
                return true;
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        //Returns the distance between these two points
        int absValue = Math.abs(player.getHitbox().x - hitbox.x);
        return absValue <= attackDistance * 5;
    }
    protected boolean isPlayerInAttackRange(Player player){
        int absValue = Math.abs(player.getHitbox().x - hitbox.x);
        return absValue <= attackDistance;
    }

    protected void newState(int enemyState){
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }
    protected void checkPlayerHit(Rect attackHitbox, Player player){
        if(attackHitbox.overlaps(player.getHitbox())){
            player.changeHealth(-GetEnemyDMG(enemyType));
        }
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType,enemyState)) {
                aniIndex = 0;
                if(enemyState == ATTACK_LEFT ){
                    enemyState = IDLE_LEFT;
                } else if (enemyState == ATTACK_RIGHT) {
                    enemyState = IDLE_RIGHT;
                } else if(enemyState == GETTING_HIT_RIGHT || enemyState == GETTING_HIT_LEFT){
                    enemyState = IDLE_RIGHT;
                } else if (enemyState == DYING_LEFT || enemyState == DYING_RIGHT) {
                    active =false;
                }
            }
        }
    }


    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
    public boolean getActive(){
        return active;
    }

}
