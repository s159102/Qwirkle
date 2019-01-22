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

            simulation(view, view2, matrix, tiles);

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
    
    static void simulation(GridView view, SideView view2, Matrix matrix, Tiles tiles) {
        Solver solver = new Solver();
        
        boolean stopTest = false;
        
        Player[] player = {new Player(), new Player(), new Player(), new Player()};
        player[0].initialise(tiles);
        player[1].initialise(tiles);
        player[2].initialise(tiles);
        player[3].initialise(tiles);
        
        view2.setPlayers(player);
  
        while (true){
            if (stopTest){
                try
                {
                    Thread.sleep(1000); //50
                    return;
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
            
            for (int i = 0; i < player.length; i++){

                view2.setActivePlayer(i);
                
                try
                {
                    Thread.sleep(100); //50
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                
                solver.initialise(matrix, player[i].getTilesInHand());
                PartialSolution solution = solver.solve();

                try {
                    if (solution == null || solution.tiles.isEmpty()){
                        player[i].getNewTiles();
                    } else {
                        player[i].removeTiles(solution.tiles);
                    }
                }catch (IllegalStateException e) {}
                
                view2.repaint();
                view2.revalidate();
                matrix.addTiles(solution);
                view.repaint();
                view.revalidate();     
                
                if (player[i].getTilesInHand().isEmpty()){
                    stopTest = true;
                    break;
                }
            }
        }
        
    }

    
}