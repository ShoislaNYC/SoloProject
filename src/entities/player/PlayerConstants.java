package entities.player;

import main.Game;

public class PlayerConstants {
    public static class EnemyConstants {
        //SKELETON
        public static final int SKELLY = 0;
        public static final int IDLE_RIGHT = 0;
        public static final int IDLE_LEFT = 1;
        public static final int RUNNING_RIGHT = 2;
        public static final int RUNNING_LEFT = 3;
        public static final int GETTING_HIT_LEFT= 4;
        public static final int GETTING_HIT_RIGHT = 5;
        public static final int ATTACK_RIGHT = 6;
        public static final int ATTACK_LEFT = 7;
        public static final int DYING_RIGHT = 8;
        public static final int DYING_LEFT = 9;
        //SKELETON

        public static final int SKELLY_WIDTH_DEFAULT = 155;
        public static final int SKELLY_HEIGHT_DEFAULT = 120;

        public static final int SKELLY_WIDTH = (int) (SKELLY_WIDTH_DEFAULT * Game.SCALE);
        public static final int SKELLY_HEIGHT = (int) (SKELLY_HEIGHT_DEFAULT * Game.SCALE);

        public static final int SKELLY_DRAWOFFSET_X = (int)(26*Game.SCALE);
        public static final int SKELLY_DRAWOFFSET_Y = (int)(9*Game.SCALE);
        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
                case SKELLY:
                    switch(enemy_state){
                        case IDLE_RIGHT:
                        case IDLE_LEFT:
                        case RUNNING_RIGHT:
                        case RUNNING_LEFT:
                        case GETTING_HIT_LEFT:
                        case GETTING_HIT_RIGHT:
                        case ATTACK_RIGHT:
                        case ATTACK_LEFT:
                        case DYING_RIGHT:
                        case DYING_LEFT:
                            return 4;
                    }
            }
            return 0;
        }
        public static int GetMaxHealth(int enemyType){
            switch(enemyType){
                case SKELLY:
                    return 10;
                default:
                    return 1;
            }
        }
        public static int GetEnemyDMG(int enemy_Type){
            switch(enemy_Type){
                case SKELLY:
                    return 15;
                default:
                    return 0;
            }
        }
    }


    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 64;
            public static final int B_HEIGHT_DEFAULT = 32;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);


        }
    }
    public static final int PLAYER_SPRITE_WIDTH = 80;

    public static final int PLAYER_SPRITE_HEIGHT = 64;


    //ANIMATION IDs
    public static final int IDLE_RIGHT = 0;
    public static final int IDLE_LEFT = 1;

    public static final int RUNNING_RIGHT = 2;
    public static final int RUNNING_LEFT = 3;
    public static final int ATTACK_1_RIGHT= 4;
    public static final int ATTACK_1_LEFT= 5;

    public static final int ATTACK_2_RIGHT = 6;
    public static final int ATTACK_2_LEFT = 7;

    public static int GetSpriteAmount(int player_action) {
        switch (player_action) {
            case IDLE_LEFT:
            case IDLE_RIGHT:
            case RUNNING_RIGHT:
            case RUNNING_LEFT:
                return 10;
            case ATTACK_2_LEFT:
            case ATTACK_2_RIGHT:
                return 6;
            case ATTACK_1_LEFT:
            case ATTACK_1_RIGHT:
                return 5;
            default:
                return 1;
        }
    }

}
