package util;
import main.Game;

import java.util.HashSet;

public class Collider {
    public static HashSet<Integer> initializeSet(int[] numbers) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : numbers) {
            set.add(num);
        }
        return set;
    }
    public static boolean isInSet(int value, HashSet<Integer> set) {
        if(set.contains(value)){
            return false;
        }else{
            return true;
        }
    }
    static int[] invisibleTiles = {0, 81,82,83,84,96,97,98,99,203,204,135,136,137,138,150,151,152,153};
    static HashSet<Integer> isInvisible = initializeSet(invisibleTiles);


    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        //Topleft
        if (!IsSolid(x, y, lvlData))
            //Bottom Right
            if (!IsSolid(x + width, y + height, lvlData))
                //Top Right
                if (!IsSolid(x + width, y, lvlData))
                    //Bottom Left
                    if (!IsSolid(x, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int lvlmaxWidth= lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= lvlmaxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isThisTileSolid((int)xIndex, (int)yIndex, lvlData);
    }
    public static boolean isThisTileSolid(int xTile, int yTile, int[][] lvlData){
        int value = lvlData[ yTile][xTile];
        if (value >= 225 || value < 0 || isInSet(value,isInvisible))
            return true;
        return false;
    }

//    public static boolean IsEntityOnFloor(Rect hitbox, int[][] lvlData) {
//        // Check the pixel below bottomleft and bottomright
//        if (!IsSolid(hitbox.x, hitbox.y + hitbox.h + 1, lvlData))
//            if (!IsSolid(hitbox.x + hitbox.w, hitbox.y + hitbox.h + 1, lvlData))
//                return false;
//
//        return true;
//
//    }
    public static boolean isTilePassable(int xStart, int xFinish, int y, int[][] lvlData){
            for(int i = 0; i < xFinish - xStart; i++) {
                if (isThisTileSolid(xStart + i, y, lvlData))
                    return false;
            }
            return true;
    }
    public static boolean isSightClear(int [][] lvlData,Rect firstHitbox, Rect secondHitbox,int yTile){
            int firstxTile = firstHitbox.x / Game.TILES_SIZE;
            int secondxTile = secondHitbox.x / Game.TILES_SIZE;

            if(firstxTile > secondxTile) {
                return isTilePassable(secondxTile, firstxTile, yTile, lvlData);
            }else {
                return isTilePassable(firstxTile, secondxTile, yTile, lvlData);
            }

    }
}