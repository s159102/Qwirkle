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
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author s159102
 */
public class Qwirkle {
    
    public static void main(String[] args) {
        GridView view = new GridView();
        SideView view2 = new SideView();
        makeMainPanel(view, view2);

        while(true){

            Matrix matrix = new Matrix();
            matrix.initialise();

            Tiles tiles = new Tiles();
            tiles.initialise();

            
            view.setMatrix(matrix);

            simulation(view, matrix, tiles);

        }
    }
    
    static void makeMainPanel(GridView view, SideView view2){
        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.add(view);
        
        JPanel panel2 = new JPanel();
        panel2.setVisible(true);
        panel2.add(view2);
        
        
        JFrame frame = new JFrame("Quirkle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.add(panel, BorderLayout.EAST);
        frame.add(panel2, BorderLayout.WEST);
        
        frame.pack();
        frame.setVisible(true);
        

    }
    
    static void simulation(GridView view, Matrix matrix, Tiles tiles) {
        
        Solver solver = new Solver();
        
        ArrayList<Tile> playersTiles = new ArrayList();
        
        boolean stopTest = false;
  
        while (true){
            if (stopTest){
                return;
            }
            for (int i = playersTiles.size(); i < 6; i++){
                try {
                    Tile tile = tiles.getRandomTile();
                    playersTiles.add(tile);
                } catch (IllegalStateException e) {
                    stopTest = true;
                    break;
                }
            }
        
            solver.initialise(matrix, playersTiles);
            PartialSolution solution = solver.solve();
            
            if (solution == null || solution.tiles.isEmpty()){
                playersTiles.clear();
            } else {
                for (Tile tile: solution.tiles){
                    playersTiles.remove(tile);
                }
            }
            
            matrix.addTiles(solution);
            view.setMatrix(matrix.clone());
            view.repaint();
            view.revalidate();
            
            try
            {
                Thread.sleep(100);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
        
    }

    
}
