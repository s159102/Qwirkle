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
public class Grid {
    ArrayList<ArrayList<Coordinate>> coordinates = new ArrayList();;
    
    Grid(){ 
    }
    
    void create(){
        for (int i = 0; i <= 181; i++){
            ArrayList<Coordinate> newArrayList = new ArrayList();
            coordinates.add(newArrayList);
        }
        createInitialTiles();
    }
    
    void createInitialTiles(){
        for (int i = 86; i <= 96; i++){
            for (int j = 86; j <= 96; j++){
                Coordinate newCoordinate = new Coordinate(i, j);
                addCoordinate(newCoordinate);
            }
        }
    }
    
    void addCoordinate(Coordinate coordinate){
        insertCoordinate(coordinates.get(coordinate.getY()), coordinate);
    }
    
    void insertCoordinate(ArrayList<Coordinate> list, Coordinate coordinate){
        
    }
    
    void addTiles(int x, int y, String direction, ArrayList<Tile> tiles){
        addTile(x, y, tiles.remove(0));
        while (!tiles.isEmpty()){
            switch(direction){
                case "right":
                    x = x + 1;
                    addTile(x, y, tiles.remove(0));
                    break;
                case "left":
                    x = x - 1;
                    addTile(x, y, tiles.remove(0));
                    break;
                case "up":
                    y = y + 1;
                    addTile(x, y, tiles.remove(0));
                    break;
                case "down":
                    y = y - 1;
                    addTile(x, y, tiles.remove(0));
                    break;
            }
        }
    }
    
    void addTile(int x, int y, Tile tile){
        Coordinate coordinate = new Coordinate(x, y);
        coordinate.add(tile);
    }
}
