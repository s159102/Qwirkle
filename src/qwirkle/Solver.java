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
    
    void initialise(Matrix matrix, ArrayList<Tile> tiles){
        this.matrix = matrix;
        this.tiles = tiles;
        this.maxPoints = 0;
    }
    
    public PartialSolution solve(){
        PartialSolution partialSol = new PartialSolution();

        if (matrix.numberOfTilesPlaced == 0){
            partialSol.setSolution(12, 12, "right", firstTiles());
            System.out.println("Points this solution gives: " + points(partialSol));
            return partialSol;
        } else {
            for (ArrayList<Tile> combination: combinationsFromHand()){
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
        ArrayList<PartialSolution> listOfPossiblePlaces = new ArrayList();
        
        for (int i = 1; i <= combination.size(); i++){
            if (i == 1){
                //TODO: implement
            } else {
                //TODO: implement
            }
        }
        
        
        return listOfPossiblePlaces;
    }

    public int points(PartialSolution solution){
        int numberOfPoints = 0;
        matrix.addTiles(solution);
        
        if ("right".equals(solution.direction) || "left".equals(solution.direction)){
            numberOfPoints = numberOfPoints + pointsHorRow(solution.x, solution.y);
            if ("right".equals(solution.direction)){
                for (int i = solution.x; i < solution.x + solution.tiles.size(); i++){
                    numberOfPoints = numberOfPoints + pointsVerRow(i, solution.y);
                }
            } else {
                for (int i = solution.x-solution.tiles.size()+1; i <= solution.x; i++){
                    numberOfPoints = numberOfPoints + pointsVerRow(i, solution.y);
                }
            }
        } else {
            numberOfPoints = numberOfPoints + pointsVerRow(solution.x, solution.y);
            if ("up".equals(solution.direction)){
                for (int i = solution.y; i < solution.y + solution.tiles.size(); i++){
                    numberOfPoints = numberOfPoints + pointsHorRow(solution.x, i);
                }
            } else {
                for (int i = solution.y-solution.tiles.size()+1; i <= solution.x; i++){
                    numberOfPoints = numberOfPoints + pointsVerRow(solution.x, i);
                }
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
            if (tile.getColor() == color){
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
        System.out.println(tilesToReturn.size());
        for (Tile tile1: tilesWithDup){
            for (Tile tile2: tilesWithDup){
                if (tile1 != tile2 && tile1.getColor().getRGB() == tile2.getColor().getRGB() && tile1.getShape().equals(tile2.getShape())){
                    if (tilesToReturn.contains(tile1) && tilesToReturn.contains(tile2)){
                        tilesToReturn.remove(tile1);
                    }
                }
            }
        }
        System.out.println(tilesToReturn.size());

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
    
}

