/*
 * Copyright (C) 2018 s159102
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package qwirkle;

import java.util.ArrayList;

/**
 *
 * @author s159102
 */
public class Player {
    ArrayList<Tile> tiles = new ArrayList();
    Tiles tilesInBag;
    
    public void initialise(Tiles tilesClass){
        tilesInBag = tilesClass;
        for (int i = 0; i < 6; i++){
            tiles.add(tilesInBag.getRandomTile());
        }
    }
    
    public ArrayList<Tile> getTilesInHand(){
        return tiles;
    }
    
    public void getNewTiles(){
        for (Tile tile: tiles){
            tilesInBag.returnTile(tile);
        }
        tiles.clear();
        for (int i = 0; i < 6; i++){
            tiles.add(tilesInBag.getRandomTile());
        }
    }
    
    public void removeTiles(ArrayList<Tile> tilesToRemove){
        int number = tilesToRemove.size();
        for (Tile tileToRemove: tilesToRemove){
            for (Tile tile: tiles){
                if (tile.equal(tileToRemove)){
                    tiles.remove(tile);
                    break;
                }
            }
        }
        for (int i=0; i < number; i++){
            Tile newTile = tilesInBag.getRandomTile();
            tiles.add(newTile);
        }
    }
}
