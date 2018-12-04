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

import java.util.ArrayList;

/**
 *
 * @author Mart
 */
public class PartialSolution {
    public ArrayList<Tile> tiles = new ArrayList();
    public ArrayList<Integer> xCoordinates = new ArrayList();
    public ArrayList<Integer> yCoordinates = new ArrayList();

    public void addOption(int x, int y, Tile tile){
        tiles.add(tile);
        xCoordinates.add(x);
        yCoordinates.add(y);
    }
    
    public String getDirection(){
        if (xCoordinates.size() < 2){
            return "horizontal";
        } else {
            if (xCoordinates.get(0) == xCoordinates.get(1)){
                return "vertical";
            } else {
                return "horizontal";
            }
        }
    }
}
