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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 *
 * @author s159102
 */
public class Qwirkle {
    
    public static void main(String[] args) {
        Matrix matrix = new Matrix();
        matrix.initialise();
        
        Tiles tiles = new Tiles();
        tiles.initialise();
        
        GridView view = new GridView();
        view.setMatrix(matrix);
        
        makeFrame(view);
        
        test(view, matrix, tiles);
    }
    
    static void makeFrame(GridView view){
        JFrame frame = new JFrame("Qwirkle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.add(view);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }
    
    static void test(GridView view, Matrix matrix, Tiles tiles){
        
        Solver solver = new Solver();
        
        ArrayList<Tile> playersTiles = new ArrayList();
        for (int i = 0; i < 6; i++){
            playersTiles.add(tiles.getRandomTile());
        }
        
        solver.initialise(matrix, playersTiles);
        PartialSolution solution = solver.solve();

        matrix.addTiles(solution);
        
        view.setMatrix(matrix);
        view.repaint();
        view.revalidate();
    }

    
}
