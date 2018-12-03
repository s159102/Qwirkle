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
public class forAllTiles {
    Matrix matrix;
    Boolean[][] alreadyChecked;
    
    private ArrayList<PartialSolution> checkTileSetOnCoordinate(int x, int y, ArrayList<Tile> tileSet){
        // x or y out of bound (Should not happen!)
        if (x < 0 || y < 0 || x > 181 || y > 181){
            throw new IllegalArgumentException("x or y out of bound");
        }
        // this coordinate is empty so return null
        if (matrix.getTile(x, y) == null){
            return null;
        }
        // makes sure no coordinate is being checked twice
        if (alreadyChecked[x][y]){
            return null;
        } else {
            alreadyChecked[x][y] = true;
        }
        
        ArrayList<PartialSolution> allPartSol = new ArrayList(); // stores all solutions found

        // find all solutions from the tile to the right of this tile
        ArrayList<PartialSolution> allPartSolRight   = checkTileSetOnCoordinate(x+1, y, tileSet);
        if (allPartSolRight != null){
            allPartSol.addAll(allPartSolRight);
        } else {
            if (!alreadyChecked[x+1][y]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x+1, y, tileSet);
                allPartSol.addAll(solutions);
            }
        }
        
        // find all solutions from the tile above this tile
        ArrayList<PartialSolution> allPartSolTop     = checkTileSetOnCoordinate(x, y-1, tileSet);
        if (allPartSolTop != null){
            allPartSol.addAll(allPartSolTop);
        } else {
            if (!alreadyChecked[x][y-1]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x, y-1, tileSet);
                allPartSol.addAll(solutions);
            }
        }
        
        // find all solutions from the tile to the left of this tile
        ArrayList<PartialSolution> allPartSolLeft    = checkTileSetOnCoordinate(x-1, y, tileSet); 
        if (allPartSolLeft != null){
            allPartSol.addAll(allPartSolLeft);
        } else {
            if (!alreadyChecked[x-1][y]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x-1, y, tileSet);
                allPartSol.addAll(solutions);
            }
        }
        
        // find all solutions from the tile under this tile
        ArrayList<PartialSolution> allPartSolBottom  = checkTileSetOnCoordinate(x, y+1, tileSet);
        if (allPartSolBottom != null){
            allPartSol.addAll(allPartSolBottom);
        } else {
            if (!alreadyChecked[x][y+1]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x, y+1, tileSet);
                allPartSol.addAll(solutions);
            }
        }
        
        return allPartSol;
    }
    
    private ArrayList<PartialSolution> tryTileSetOn(int x, int y, ArrayList<Tile> tileSet) {
        ArrayList<PartialSolution> allPartSol = new ArrayList();
        Tile tileOnTable = matrix.getTile(x, y);
        
        for (Tile tile: tileSet){
            if ((tileOnTable.getColor() == tile.getColor() && !(tileOnTable.getShape().equals(tile.getShape()))) || (tileOnTable.getShape().equals(tile.getShape()) && tileOnTable.getColor() != tile.getColor())){
                if (moveAllowed(x, y, tile)){
                    //add solution with this one tile
                    PartialSolution partSol = new PartialSolution();
                    ArrayList<Tile> listWithSingleTile = new ArrayList();
                    listWithSingleTile.add(tile);
                    partSol.set(x, y, "right", listWithSingleTile);
                    allPartSol.add(partSol);

                    allPartSol.addAll(tryTileSetHorizontal(x, y, tile, tileSet)); // add all solutions horizontal possible
                    allPartSol.addAll(tryTileSetVertical(x, y, tile, tileSet));  // add all solutions vertical possible
                }            
            }
        }
        return allPartSol;
    }
    
    private ArrayList<PartialSolution> tryTileSetHorizontal(int x, int y, Tile tile, ArrayList<Tile> tilesAvailable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList<PartialSolution> tryTileSetVertical(int x, int y, Tile tile, ArrayList<Tile> tilesAvailable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean moveAllowed(int x, int y, Tile tileInHand) {
        return (moveAllowedHorizontal(x, y, tileInHand) && moveAllowedVertical(x, y, tileInHand));
    }
    
    private boolean moveAllowedHorizontal(int x, int y, Tile tileToCheck){
        int oldX = x; // store original x
        String horPatern = "undefined"; // store patern
        ArrayList<Tile> tilesRight = new ArrayList(); // store tiles to the right of this tile
        
        // check right of the tile if move is allowed
        x = x+1;
        Tile tile = matrix.getTile(x, y);
        while(tile != null){
            tilesRight.add(tile); 
            if (tile.getColor() == tileToCheck.getColor() && !(tile.getShape().equals(tileToCheck.getShape())) && (horPatern.equals("undefined") || horPatern.equals("color"))){
                horPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor() != tileToCheck.getColor() && (horPatern.equals("undefined") || horPatern.equals("shape"))){
                horPatern = "shape";
            } else {
                return false;
            }
            
            x++;
            tile = matrix.getTile(x, y);
        }
        
        // check left of the tile if move is allowed
        x = oldX-1;
        tile = matrix.getTile(x, y);
        
        while(tile != null){
            if (tileInTileSet(tile, tilesRight)){
                return false; // the same tile in the same row is not allowed
            } else if (tile.getColor() == tileToCheck.getColor() && !(tile.getShape().equals(tileToCheck.getShape())) && (horPatern.equals("undefined") || horPatern.equals("color"))){
                horPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor() != tileToCheck.getColor() && (horPatern.equals("undefined") || horPatern.equals("shape"))){
                horPatern = "shape";
            } else {
                return false;
            }
            
            x--;
            tile = matrix.getTile(x, y);
        }
        
        return true;
    }
    
    private boolean moveAllowedVertical(int x, int y, Tile tileToCheck){
        int oldY = y; // store original y     
        String verPatern = "undefined"; // store patern
        ArrayList<Tile> tilesTop = new ArrayList(); // store the tiles on top of tileToCheck
        
        // check above the tile if move is allowed
        y = y-1;
        Tile tile = matrix.getTile(x, y);
        while(tile != null){
            tilesTop.add(tile);
            if (tile.getColor() == tileToCheck.getColor() && !(tile.getShape().equals(tileToCheck.getShape())) && (verPatern.equals("undefined") || verPatern.equals("color"))){
                verPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor() != tileToCheck.getColor() && (verPatern.equals("undefined") || verPatern.equals("shape"))){
                verPatern = "shape";
            } else {
                return false;
            }
            
            y--;
            tile = matrix.getTile(x, y);
        }
        
        // check below the tile if move is allowed
        x = oldY+1;
        tile = matrix.getTile(x, y);
        while(tile != null){
            if (tileInTileSet(tile, tilesTop)){
                return false;
            } else if (tile.getColor() == tileToCheck.getColor() && !(tile.getShape().equals(tileToCheck.getShape())) && (verPatern.equals("undefined") || verPatern.equals("color"))){
                verPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor() != tileToCheck.getColor() && (verPatern.equals("undefined") || verPatern.equals("shape"))){
                verPatern = "shape";
            } else {
                return false;
            }
            
            y++;
            tile = matrix.getTile(x, y);
        }
        
        return true;
    }

    private boolean tileInTileSet(Tile tile, ArrayList<Tile> list) {
        for(Tile tileInList: list){
            if (tile.getColor() == tileInList.getColor() && tile.getShape().equals(tileInList.getShape())){
                return true;
            }
        }
        return false;
    }
    
    
}
