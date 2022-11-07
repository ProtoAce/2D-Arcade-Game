package Logic;

import Board.*;
import Display.DisplayLayout;
import Display.myGame;
import Entities.Position;
import Game.ObjectData;
import Entities.Bonus;
import java.util.ArrayList;
import java.util.Random;

/**
 * Updates board to generate bonuses when bonus rewards are despawned, and respawns them when
 * a sufficient amount of time has passed since the objects last despawn
 * Moreover, bonuses despawn/respawn at different times, and at different locations to improve
 * randomness for the game
 *
 */
public class RewardLogic {
    public void updateRewards(ObjectData gameobjectData, int ticks){
        BoardData boardData = gameobjectData.getBoard();
        ArrayList<Bonus> bonuses = gameobjectData.getBonus();
        Random rand = new Random(); //instance of random class



        Objects[][] objectMap = boardData.getBoardData();
        for (Bonus bonusObj : bonuses){

            //if the object is spawned, we must know to despawn it here
            if (bonusObj.getisSpawned()) {


                //randomize despawns, the longer the time since item has been spawend the higher
                //the chance it will be despawn in this iteration
                int maxToDespawn = 100000; //100 seconds
                int minToDespawn = 8000; //8 seconds
                int lifetime = rand.nextInt(maxToDespawn - minToDespawn + 1) + minToDespawn;

                //if its been spawned for long enough
                if (ticks - bonusObj.getStartTime() > lifetime) {

                    //tile is just a reward
                    if (objectMap[bonusObj.getX()][bonusObj.getY()] == Objects.BONUS) {
                            //chooses a new empty tile

                            int X = bonusObj.getX();
                            int Y = bonusObj.getY();
                            //set object position

                            //replace objects and set new positions
                            objectMap[X][Y] = Objects.EMPTY;
                            bonusObj.setdespawnedTime(ticks);
                            bonusObj.setisSpawned(false);
                    }


                }
            }
            //if the object is despawned, respawn logic happens here
            else {
                int [] newcoords = getRandomXY(boardData);
                Position newpos = new Position(newcoords[0], newcoords[1]);

                //generate random numbers, the longer the item is despawned, the more chance
                //it has of respawning
                int maxToRespawn = 100000; //100 seconds
                int minToRespawn = 8000; //8 seconds
                int respawntime = rand.nextInt(maxToRespawn-minToRespawn+1) + minToRespawn;

                if (ticks - bonusObj.getdespawnTime() > respawntime){

                    //show the object on the map again only if the tile is empty
                    if (boardData.getTypeAt(newpos) == Objects.EMPTY){
                        boardData.setTypeAt(newpos, Objects.BONUS);

                        //set new coordinates for the object
                        bonusObj.setX(newpos.getX());
                        bonusObj.setY(newpos.getY());

                        bonusObj.setisSpawned(true);
                        bonusObj.setStartTime(ticks);
                        bonusObj.setdespawnedTime(ticks);
                    }
                }
            }
        }



    }

    /**
     * Used for generating viable positions on the Board
     * @param boardData 2D array of Objects, see {@link BoardData}
     * @return int[]   a random position on the board that satisfied the position BoardData[int[0]][int[1]] == EMPTY
     */
    private int[] getRandomXY(BoardData boardData) {

        Random rand = new Random(); //instance of random class
        boolean posfound = false;
        //initialize vars
        int x = -1;
        int y = -1;
        while (!posfound) {
            //-2 + 1 in order to not consider the border
            x = rand.nextInt(boardData.getboardwidth() - 2) + 1;
            y = rand.nextInt(boardData.getboardheight() - 2) + 1;
            //find a suitable position
            if (boardData.getBoardData()[x][y] == Objects.EMPTY){
                posfound = true;
            }
        }

        int[] xy = {x,y};

        return xy;
    }
}
