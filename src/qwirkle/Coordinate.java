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
public class Coordinate {
    private Boolean empty = true;
    private Tile tile;
    
    public Coordinate(){
    }
    
    public void add(Tile tile){
        this.tile = tile;
        this.empty = false;
    }
    
    public void remove(Tile tile){
        this.tile = null;
        this.empty = true;
    }
    
    public void reset(){
        this.tile = null;
        
    }
    
    public Tile tile(){
         return tile;
    }
    
    public Boolean isEmpty(){
        return empty;
    }

}
