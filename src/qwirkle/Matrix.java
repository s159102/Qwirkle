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
public class Matrix {
    Coordinate[][] matrix = new Coordinate[181][181];
    int numberOfTilesPlaced = 0;
    
    Matrix(){ 
    }
    
    public void initialise(){
        for(int i = 0; i < 181; i++){
            for(int j = 0; j < 181; j++){
                Coordinate newCoordinate = new Coordinate();
                matrix[i][j] = newCoordinate;
            }
        }
    }
    
    public void addTiles(PartialSolution sol){
        addTiles(sol.x, sol.y, sol.direction, sol.tiles);
    }
    
    public void removeTiles(PartialSolution sol){
        removeTiles(sol.x, sol.y, sol.direction, sol.tiles);
    }
    
    public void addTiles(int x, int y, String direction, ArrayList<Tile> tiles){
        
        if (tiles.size() > 6){
            throw new IllegalArgumentException("Matrix.addTiles(); Number of tiles > 6");
        }
        
        while (!tiles.isEmpty()){
            switch(direction){
                case "right":
                    addTile(x, y, tiles.remove(0));
                    x = x + 1;
                    break;
                case "left":
                    addTile(x, y, tiles.remove(0));
                    x = x - 1;
                    break;
                case "up":
                    addTile(x, y, tiles.remove(0));
                    y = y + 1;
                    break;
                case "down":
                    addTile(x, y, tiles.remove(0));
                    y = y - 1;
                    break;
            }
        }
    }
    
    public void removeTiles(int x, int y, String direction, ArrayList<Tile> tiles){
        
        if (tiles.size() > 6){
            throw new IllegalArgumentException("Matrix.addTiles(); Number of tiles > 6");
        }
        
        while (!tiles.isEmpty()){
            switch(direction){
                case "right":
                    removeTile(x, y, tiles.remove(0));
                    x = x + 1;
                    break;
                case "left":
                    removeTile(x, y, tiles.remove(0));
                    x = x - 1;
                    break;
                case "up":
                    removeTile(x, y, tiles.remove(0));
                    y = y + 1;
                    break;
                case "down":
                    removeTile(x, y, tiles.remove(0));
                    y = y - 1;
                    break;
            }
        }
    }
    
    public Coordinate getCoordinate(int x, int y){
        return matrix[x][y];
    }
    
    public void addTile(int x, int y, Tile tile){
        if (tile == null){
            throw new IllegalArgumentException("Matrix.addTile(); tile == null");
        }
        if (x < 0 || y < 0){
            throw new IllegalArgumentException("Matrix.addTile(); x < 0 || y < 0");
        }
        matrix[x][y].add(tile);
        numberOfTilesPlaced++;
    }
    
    public void removeTile(int x, int y, Tile tile){
        if (tile == null){
            throw new IllegalArgumentException("Matrix.addTile(); tile == null");
        }
        if (x < 0 || y < 0){
            throw new IllegalArgumentException("Matrix.addTile(); x < 0 || y < 0");
        }
        matrix[x][y].remove(tile);
        numberOfTilesPlaced--;
    }
}
