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
    public int x;
    public int y;
    public String direction;
    public ArrayList<Tile> tiles;

    public void setSolution(int x, int y, String direction, ArrayList<Tile> tiles){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.tiles = tiles;
    }
}
