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

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import view.*;

/**
 *
 * @author s159102
 */
public class Qwirkle {

    public static void main(String[] args) throws InterruptedException {
        TilesAvailable tiles = new TilesAvailable();
        tiles.initialise();
        Grid grid = new Grid();
        grid.createInitialCoordinates();
        grid.addTiles(0, 0, "right", tiles.tiles);
        
        JFrame f = new JFrame("Qwirkle");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridView view = new GridView(grid.coordinates);
        f.add(view);
        f.setSize(550+16,550+39);
        f.setVisible(true);
    }

    
}
