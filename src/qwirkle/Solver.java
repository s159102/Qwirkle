/*
 * Copyright (C) 2018 Mart
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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Mart
 */
public class Solver {
    Matrix matrix;
    ArrayList<Tile> tiles;
    Tiles tilesClass = new Tiles();
    PartialSolution currentSolution;
    int maxPoints;
    boolean[][] alreadyChecked;
    
    void initialise(Matrix matrix, ArrayList<Tile> tiles){
        this.matrix = matrix;
        this.tiles = tiles;
        this.maxPoints = 0;
        alreadyChecked = new boolean[182][182];
        currentSolution = new PartialSolution();
    }
    
    public PartialSolution solve(){
        PartialSolution partialSol = new PartialSolution();

        if (matrix.numberOfTilesPlaced == 0){
            ArrayList<Tile> firstTiles = firstTiles();
            for (int i = 0; i < firstTiles.size(); i++){
                partialSol.addOption(20+i, 12, firstTiles.get(i));
            }
            return partialSol;
        } else {
            for (ArrayList<Tile> combination: combinationsFromHand()){
                alreadyChecked = new boolean[182][182];
                ArrayList<PartialSolution> possiblePlaces = getPossiblePlaces(combination);
                for (PartialSolution sol: possiblePlaces){
                    int pointsSol = points(sol);
                    if (maxPoints < pointsSol){
                        maxPoints = pointsSol;
                        currentSolution = sol;
                    }
                }
            }
            partialSol = currentSolution;
            
            return partialSol;
        }  
    }
    
    private ArrayList<PartialSolution> getPossiblePlaces(ArrayList<Tile> combination){
        return checkTileSetOnCoordinate(20, 12, combination);
    }

    public int points(PartialSolution solution){
        int numberOfPoints = 0;
        matrix.addTiles(solution);
        
        if ("horizontal".equals(solution.getDirection())){
            numberOfPoints = numberOfPoints + pointsHorRow(solution.xCoordinates.get(0), solution.yCoordinates.get(0));
            for (int i = 0; i < solution.tiles.size(); i++){
                numberOfPoints = numberOfPoints + pointsVerRow(solution.xCoordinates.get(i), solution.yCoordinates.get(i));
            }
        } else {
            numberOfPoints = numberOfPoints + pointsVerRow(solution.xCoordinates.get(0), solution.yCoordinates.get(0));
            for (int i = 0; i < solution.tiles.size(); i++){
                numberOfPoints = numberOfPoints + pointsHorRow(solution.xCoordinates.get(i), solution.yCoordinates.get(i));
            }
        }
        
        matrix.removeTiles(solution);
        
        return numberOfPoints;
    }   
    
    private int pointsHorRow(int x, int y){
        int numberOfPoints = 0;
        
        while(x > 0){
            x--;
            if (matrix.getCoordinate(x, y).isEmpty()){
                x++;
                break;
            }
        }
        while (!matrix.getCoordinate(x, y).isEmpty()){
            numberOfPoints++;
            x++;
        }
        if (numberOfPoints == 6){
            numberOfPoints = 12;
        }
        
        return numberOfPoints;
    }
    
    private int pointsVerRow(int x, int y){
        int numberOfPoints = 0;
        
        while(y > 0){
            y--;
            if (matrix.getCoordinate(x, y).isEmpty()){
                y++;
                break;
            }
        }
        while (!matrix.getCoordinate(x, y).isEmpty()){
            numberOfPoints++;
            y++;
        }
        if (numberOfPoints == 6){
            numberOfPoints = 12;
        }
        
        return numberOfPoints;
    }
            
    
    private ArrayList<Tile> firstTiles(){
        return biggestCombination(combinationsFromHand());
    }
    
    private ArrayList<Tile> biggestCombination(ArrayList<ArrayList<Tile>> combinations){
        ArrayList<Tile> biggest = new ArrayList();
        for (ArrayList<Tile> list: combinations){
            if (list.size() > biggest.size()){
                biggest = list;
            }
        }
        return biggest;
    }
    
    private ArrayList<ArrayList<Tile>> combinationsFromHand(){
        ArrayList<ArrayList<Tile>> combinations = new ArrayList();
        ArrayList<Tile> tilesInHand = removeDuplicates(tiles);
        
        for (Color color: tilesClass.colors){
            combinations.add(getAllColor(tilesInHand, color));
            
        }
        for (String shape: tilesClass.shapes){
            combinations.add(getAllShape(tilesInHand, shape));
        }
        
        combinations = removeDuplicatesList(combinations);

        return combinations;
    }
    
    
    private ArrayList<Tile> getAllColor(ArrayList<Tile> tilesInHand, Color color){
        ArrayList<Tile> tilesWithColor = new ArrayList();
        
        for (Tile tile: tilesInHand){
            if (tile.getColor().getRGB() == color.getRGB()){
                tilesWithColor.add(tile);
            }
        }
        
        return tilesWithColor;
    }
    
    private ArrayList<Tile> getAllShape(ArrayList<Tile> tilesInHand, String shape){
        ArrayList<Tile> tilesWithShape = new ArrayList();
        
        for (Tile tile: tilesInHand){
            if (tile.getShape().equals(shape)){
                tilesWithShape.add(tile);
            }
        }
        
        return tilesWithShape;
    }
    
    private ArrayList<Tile> removeDuplicates(ArrayList<Tile> tilesWithDup){
        ArrayList<Tile> tilesToReturn = new ArrayList();
        for (Tile tile: tilesWithDup){
            tilesToReturn.add(tile);
        }
        for (Tile tile1: tilesWithDup){
            for (Tile tile2: tilesWithDup){
                if (tile1 != tile2 && tile1.getColor().getRGB() == tile2.getColor().getRGB() && tile1.getShape().equals(tile2.getShape())){
                    if (tilesToReturn.contains(tile1) && tilesToReturn.contains(tile2)){
                        tilesToReturn.remove(tile1);
                    }
                }
            }
        }

        return tilesToReturn;
    }
    
    private ArrayList<ArrayList<Tile>> removeDuplicatesList(ArrayList<ArrayList<Tile>> list){
        ArrayList<ArrayList<Tile>> tileListToReturn = new ArrayList();
        ArrayList<ArrayList<Tile>> removeLater = new ArrayList();
        for (ArrayList<Tile> tileList: list){
            tileListToReturn.add(tileList);
            if (tileList.size() > 1){
                removeLater.add(tileList);
            } else if (tileList.isEmpty()){
                removeLater.add(tileList);
                tileListToReturn.remove(tileList);
            }
        }
        
        list.removeAll(removeLater);       
        removeLater = new ArrayList();
        
        //Remove all duplicates
        for (ArrayList<Tile> tile1: list){
            for (ArrayList<Tile> tile2: list){
                if (tile1.get(0) == tile2.get(0)){
                    if (!(removeLater.contains(tile1)) && !(tileListToReturn.contains(tile2))){
                        removeLater.add(tile1);
                    }
                }
            }
        }
        tileListToReturn.removeAll(removeLater);

        return tileListToReturn;
    }
    
    private ArrayList<PartialSolution> checkTileSetOnCoordinate(int x, int y, ArrayList<Tile> tileSet){
        ArrayList<PartialSolution> allPartSol = new ArrayList(); // stores all solutions found
        
        // x or y out of bound (Should not happen!)
        if (x < 0 || y < 0 || x > 181 || y > 181){
            throw new IllegalArgumentException("x or y out of bound, x:"+x+", y:"+y);
        }
        if (x == 0 || y == 0){
            return allPartSol;
        }
        // this coordinate is empty so return null
        if (matrix.getCoordinate(x, y).isEmpty()){ 
            return allPartSol;
        }
        // makes sure no coordinate is being checked twice
        if (alreadyChecked[x][y]){
            return allPartSol;
        } else {
            alreadyChecked[x][y] = true;
        }


        // find all solutions from the tile to the right of this tile
        ArrayList<PartialSolution> allPartSolRight = checkTileSetOnCoordinate(x+1, y, (ArrayList<Tile>) tileSet.clone());
        if (!allPartSolRight.isEmpty()){
            allPartSol.addAll(allPartSolRight);
        } else {
            if (!alreadyChecked[x+1][y]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x+1, y, tileSet);
                allPartSol.addAll(solutions); 
            }
        }
        
        // find all solutions from the tile above this tile
        ArrayList<PartialSolution> allPartSolTop = checkTileSetOnCoordinate(x, y-1, (ArrayList<Tile>) tileSet.clone());
        if (!allPartSolTop.isEmpty()){
            allPartSol.addAll(allPartSolTop);
        } else {
            if (!alreadyChecked[x][y-1]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x, y-1, tileSet);
                allPartSol.addAll(solutions);
            }
        }
        
        // find all solutions from the tile to the left of this tile
        ArrayList<PartialSolution> allPartSolLeft    = checkTileSetOnCoordinate(x-1, y, (ArrayList<Tile>) tileSet.clone()); 
        if (!allPartSolLeft.isEmpty()){
            allPartSol.addAll(allPartSolLeft);
        } else {
            if (!alreadyChecked[x-1][y]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x-1, y, tileSet);
                allPartSol.addAll(solutions);
            }
        }
        
        // find all solutions from the tile under this tile
        ArrayList<PartialSolution> allPartSolBottom  = checkTileSetOnCoordinate(x, y+1, (ArrayList<Tile>) tileSet.clone());
        if (!allPartSolBottom.isEmpty()){
            allPartSol.addAll(allPartSolBottom);
        } else {
            if (!alreadyChecked[x][y+1]){
                ArrayList<PartialSolution> solutions = tryTileSetOn(x, y+1, (ArrayList<Tile>) tileSet.clone());
                allPartSol.addAll(solutions);
            }
        }
        
        return allPartSol;
    }
    
    private ArrayList<PartialSolution> tryTileSetOn(int x, int y, ArrayList<Tile> tileSet) {
        ArrayList<PartialSolution> allPartSol = new ArrayList();
        
        allPartSol.addAll(tryTileSetHorizontal(x, y, tileSet, new PartialSolution())); // add all solutions horizontal possible
        allPartSol.addAll(tryTileSetVertical(x, y, tileSet, new PartialSolution()));  // add all solutions vertical possible
        
        return allPartSol;
    }
    
    private ArrayList<PartialSolution> tryTileSetHorizontal(int x, int y, ArrayList<Tile> tileList, PartialSolution solution1) {
        ArrayList<PartialSolution> allSol = new ArrayList();
        int oldX = x;
        
        for (Tile tileFromList: tileList){
            
            if (moveAllowed(x, y, tileFromList)){
                matrix.addTile(x, y, tileFromList);
                solution1.addOption(x, y, tileFromList); // add this tile to partSolution
                
                //copy partial solutions
                PartialSolution newSol = new PartialSolution();
                PartialSolution solution2 = new PartialSolution();
                for (int i = 0; i < solution1.tiles.size(); i++){
                    newSol.addOption(solution1.xCoordinates.get(i), solution1.yCoordinates.get(i), solution1.tiles.get(i));
                    solution2.addOption(solution1.xCoordinates.get(i), solution1.yCoordinates.get(i), solution1.tiles.get(i));
                }
                
                allSol.add(newSol); // add solution until now to list with all solutions

                ArrayList<Tile> tileListRemaining = (ArrayList<Tile>) tileList.clone();
                tileListRemaining.remove(tileFromList);
                
                if (tileListRemaining.size() == tileList.size()){
                    throw new IllegalStateException("list the same");
                }
                
                while (x-1 > oldX-5 && x-1 >=0 && matrix.getCoordinate(x-1, y).tile() == null ){
                    x--;
                }
                if (x > 0 && matrix.getCoordinate(x-1, y).tile() != null){
                    allSol.addAll(tryTileSetHorizontal(x-1, y, tileListRemaining, solution1));
                }
                while (matrix.getCoordinate(x+1, y).tile() == null && x+1 < oldX+5){
                    x++;
                }
                if (matrix.getCoordinate(x+1, y).tile() != null){
                    allSol.addAll(tryTileSetHorizontal(x+1, y, tileListRemaining, solution2));
                }
                
                matrix.removeTile(x, y, tileFromList);            
            }
        }
        return allSol;
    }

    private ArrayList<PartialSolution> tryTileSetVertical(int x, int y, ArrayList<Tile> tileList, PartialSolution solution1) {
        ArrayList<PartialSolution> allSol = new ArrayList();
        int oldY = y;

        for (Tile tileFromList: tileList){
            
            if (moveAllowed(x, y, tileFromList)){
                matrix.addTile(x, y, tileFromList);
                solution1.addOption(x, y, tileFromList); // add this tile to partSolution
                
                //copy partial solutions
                PartialSolution newSol = new PartialSolution();
                PartialSolution solution2 = new PartialSolution();
                for (int i = 0; i < solution1.tiles.size(); i++){
                    newSol.addOption(solution1.xCoordinates.get(i), solution1.yCoordinates.get(i), solution1.tiles.get(i));
                    solution2.addOption(solution1.xCoordinates.get(i), solution1.yCoordinates.get(i), solution1.tiles.get(i));
                }
                
                allSol.add(newSol); // add solution until now to list with all solutions

                ArrayList<Tile> tileListRemaining = (ArrayList<Tile>) tileList.clone();
                tileListRemaining.remove(tileFromList);
                
                if (tileListRemaining.size() == tileList.size()){
                    throw new IllegalStateException("list the same");
                }
                
                while (y-1 > oldY-5 && y-1 >=0 && matrix.getCoordinate(x, y-1).tile() == null){
                    y--;
                }
                if (y > 0 && matrix.getCoordinate(x, y-1).tile() != null){
                    allSol.addAll(tryTileSetHorizontal(x, y-1, tileListRemaining, solution1));
                }
                while (matrix.getCoordinate(x, y+1).tile() == null && y+1 < oldY+5){
                    y++;
                }
                if (matrix.getCoordinate(x, y+1).tile() != null){
                    allSol.addAll(tryTileSetHorizontal(x, y+1, tileListRemaining, solution2));
                }
                
                matrix.removeTile(x, y, tileFromList);            
            }
        }
        return allSol;
    }
    
    boolean isConnected = false;
    
    private boolean moveAllowed(int x, int y, Tile tileInHand) {
        if (!matrix.getCoordinate(x, y).isEmpty()){
            return false;
        }
        isConnected = false;
        Boolean allowed = (moveAllowedHorizontal(x, y, tileInHand) && moveAllowedVertical(x, y, tileInHand));

        return allowed && isConnected;
    }
    
    private boolean moveAllowedHorizontal(int x, int y, Tile tileToCheck){
        int oldX = x; // store original x
        String horPatern = "undefined"; // store patern
        ArrayList<Tile> tilesRight = new ArrayList(); // store tiles to the right of this tile
        
        // check right of the tile if move is allowed
        x = x+1;
        Tile tile = matrix.getCoordinate(x, y).tile();
        while(tile != null){
            tilesRight.add(tile); 
            isConnected = true;
            if (tile.getColor().getRGB() == tileToCheck.getColor().getRGB() && !(tile.getShape().equals(tileToCheck.getShape())) && (horPatern.equals("undefined") || horPatern.equals("color"))){
                horPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor().getRGB() != tileToCheck.getColor().getRGB() && (horPatern.equals("undefined") || horPatern.equals("shape"))){
                horPatern = "shape";
            } else {
                return false;
            }
            
            x++;
            tile = matrix.getCoordinate(x, y).tile();
        }
        
        // check left of the tile if move is allowed
        x = oldX-1;
        if (x < 0){
            return true;
        }
        tile = matrix.getCoordinate(x, y).tile();
        
        while(tile != null && x > 0){
            isConnected = true;
            if (tileInTileSet(tile, tilesRight)){
                return false; // the same tile in the same row is not allowed
            } else if (tile.getColor().getRGB() == tileToCheck.getColor().getRGB() && !(tile.getShape().equals(tileToCheck.getShape())) && (horPatern.equals("undefined") || horPatern.equals("color"))){
                horPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor().getRGB() != tileToCheck.getColor().getRGB() && (horPatern.equals("undefined") || horPatern.equals("shape"))){
                horPatern = "shape";
            } else {
                return false;
            }
            
            x--;
            tile = matrix.getCoordinate(x, y).tile();
        }
        
        return true;
    }
    
    private boolean moveAllowedVertical(int x, int y, Tile tileToCheck){
        int oldY = y; // store original y     
        String verPatern = "undefined"; // store patern
        ArrayList<Tile> tilesTop = new ArrayList(); // store the tiles on top of tileToCheck
        
        // check above the tile if move is allowed
        y = y-1;
        if (y < 0){
            return true;
        }
        Tile tile = matrix.getCoordinate(x, y).tile();
        while(tile != null){
            tilesTop.add(tile);
            isConnected = true;
            if (tile.getColor().getRGB() == tileToCheck.getColor().getRGB() && !(tile.getShape().equals(tileToCheck.getShape())) && (verPatern.equals("undefined") || verPatern.equals("color"))){
                verPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor().getRGB() != tileToCheck.getColor().getRGB() && (verPatern.equals("undefined") || verPatern.equals("shape"))){
                verPatern = "shape";
            } else {
                return false;
            }
            
            y--;
            tile = matrix.getCoordinate(x, y).tile();
        }
        
        // check below the tile if move is allowed
        y = oldY+1;
        tile = matrix.getCoordinate(x, y).tile();
        while(tile != null){
            isConnected = true;
            if (tileInTileSet(tile, tilesTop)){
                return false;
            } else if (tile.getColor().getRGB() == tileToCheck.getColor().getRGB() && !(tile.getShape().equals(tileToCheck.getShape())) && (verPatern.equals("undefined") || verPatern.equals("color"))){
                verPatern = "color";
            } else if (tile.getShape().equals(tileToCheck.getShape()) && tile.getColor().getRGB() != tileToCheck.getColor().getRGB() && (verPatern.equals("undefined") || verPatern.equals("shape"))){
                verPatern = "shape";
            } else {
                return false;
            }
            
            y++;
            tile = matrix.getCoordinate(x, y).tile();
        }
        
        return true;
    }

    private boolean tileInTileSet(Tile tile, ArrayList<Tile> list) {
        for(Tile tileInList: list){
            if (tile.getColor().getRGB() == tileInList.getColor().getRGB() && tile.getShape().equals(tileInList.getShape())){
                return true;
            }
        }
        return false;
    }
    
}

