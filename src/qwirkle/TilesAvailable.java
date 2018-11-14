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
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author s159102
 */
public class TilesAvailable {
    String[] shapes = {"star", "cirkle", "square", "diamond", "clover", "cross"};
    String[] colors = {"blue", "red", "yellow", "green", "purple", "orange"};
    Integer numberOfTilesInBag = 108;
    ArrayList<Tile> tiles = new ArrayList();
    
    void TilesAvailable(){
        
    }
    
    public void initialise(){
        createTiles();
        shuffle();
        print();
    }
    
    public void print(){
        System.out.println("Printing tiles available:");
        for (Tile tile : tiles){
            System.out.println(tiles.indexOf(tile) + " -> color: " + tile.getColor() + ", shape: " + tile.getShape());
        }
    }
    
    private void createTiles(){
        for (String shape : shapes){
            System.out.println(shape);
            for (String color : colors){
                tiles.add(new Tile(shape, color));
                tiles.add(new Tile(shape, color));
                tiles.add(new Tile(shape, color));
            }
        }
    }
    
    private void shuffle(){
        Collections.shuffle(tiles);
    }
    
}
