package entities.enemies;
import entities.player.Player;
import entities.player.PlayerConstants;
import main.Game;
import util.Rect;

import java.awt.*;

import static entities.player.PlayerConstants.Directions.*;
import static entities.player.PlayerConstants.Directions.RIGHT;
import static entities.player.PlayerConstants.EnemyConstants.*;


public class Skelly extends Enemy{
    private Rect attackHitbox;
    private int attackHitboxOffsetX;

    public Skelly(float x, float y) {

        super(x, y, SKELLY_WIDTH,SKELLY_HEIGHT, SKELLY);
        initHitbox(x,y,(int)(64 *Game.SCALE),(int)(90* Game.SCALE));
        initAttackHitbox();
    }

    private void initAttackHitbox() {
        attackHitbox = new Rect((int)x,(int)y,(int)(55*Game.SCALE),(int)(88*Game.SCALE));
        attackHitboxOffsetX = (int)(Game.SCALE * 30);
    }

    public void drawEnemyHitbox(Graphics g, int LvlOffset){
        drawHitbox(g,LvlOffset);
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData, player);
        updateAnimationTick();
        updateAttackHitbox();
    }

    private void updateAttackHitbox() {
        attackHitbox.x = hitbox.x- attackHitboxOffsetX;
        attackHitbox.y = hitbox.y;
    }

    private void updateMove(int[][] lvlData, Player player){
        if(firstUpdate){
            firstUpdate = false;
        }
        else{
            switch(enemyState) {
                case PlayerConstants.EnemyConstants.IDLE_RIGHT:
                case PlayerConstants.EnemyConstants.IDLE_LEFT:
                    if (walkDir == LEFT) {
                        movingLeft = true;
                        newState(PlayerConstants.EnemyConstants.RUNNING_LEFT);
                    }else if (walkDir == RIGHT) {
                        movingRight = true;
                        newState(PlayerConstants.EnemyConstants.RUNNING_RIGHT);
                    }
                    break;
                case PlayerConstants.EnemyConstants.RUNNING_RIGHT:
                    attackPlayer(lvlData,player,ATTACK_RIGHT);
                    moveEnemy(lvlData);
                    break;
                case PlayerConstants.EnemyConstants.RUNNING_LEFT:
                    attackPlayer(lvlData,player,ATTACK_LEFT);
                    moveEnemy(lvlData);
                    break;
                case ATTACK_LEFT:
                case ATTACK_RIGHT:
                    if(aniIndex == 0 ){
                        attackChecked = false;
                    }
                    if(aniIndex == 3 && !attackChecked){
                        checkPlayerHit(attackHitbox,player);
                    }
                    break;
                case GETTING_HIT_LEFT:
                case GETTING_HIT_RIGHT:
                    break;

            }

        }

    }
    public void attackPlayer(int [][] lvlData ,Player player,int AtkType){
        if(canSeePlayer(lvlData,player)){
            turnToPlayer(player);
        }
        if(isPlayerInAttackRange(player)){
            newState(AtkType);
        }
    }
    public void drawAttackHitbox(Graphics g, int lvlOffset){
        g.setColor(Color.GREEN);
        g.drawRect((int)attackHitbox.x- lvlOffset,(int)attackHitbox.y,(int)attackHitbox.w,(int)attackHitbox.h);
    }
//    public void newStateBasedOnSide(int StateLEFT, int StateRIGHT){
//        if(walkDir == LEFT){
//            newState(StateLEFT);
//        }else if(walkDir == RIGHT){
//            newState(StateRIGHT);
//        }
//    }
    public void hurt(int damage) {
        currentEnemeyHealth -= damage;
        if(currentEnemeyHealth <= 0){
            newState(DYING_RIGHT);
//            newStateBasedOnSide(DYING_LEFT,DYING_RIGHT);
        }else{
            newState(GETTING_HIT_RIGHT);
//            newStateBasedOnSide(GETTING_HIT_LEFT,GETTING_HIT_RIGHT);
        }
    }

    public void resetEnemyValues() {
        hitbox.x = (int)x;
        hitbox.y = (int)y;
        firstUpdate = true;
        currentEnemeyHealth = enemyMaxHealth;
        newState(IDLE_RIGHT);
        active = true;
    }
}
