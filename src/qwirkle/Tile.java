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

/**
 *
 * @author s159102
 */
public class Tile {
    
    private String shape; //the shape of the tile
    private String color; //the color of the tile

    Tile(String shape, String color){
        this.shape = shape;
        this.color = color;
    }
    
    String getShape(){
        return shape;
    }
    
    String getColor(){
        return color;
    }
}
