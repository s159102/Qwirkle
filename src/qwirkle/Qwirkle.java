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

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import view.*;

/**
 *
 * @author s159102
 */
public class Qwirkle {

    public static void main(String[] args) {
        TilesAvailable tiles = new TilesAvailable();
        tiles.initialise();
        Grid grid = new Grid();
        grid.createInitialCoordinates();
        grid.addTiles(0, 0, "right", tiles.tiles);
        GridView view = new GridView();
        
        JFrame f = new JFrame("Qwirkle");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.NORMAL); 
        f.setVisible(true);
        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));
        f.add(view);

        TilesAvailable tiles2 = new TilesAvailable();
        tiles2.initialise();
        Grid grid2 = new Grid();
        grid2.createInitialCoordinates();
        grid2.addTiles(0, 0, "right", tiles2.options);
        OptionView view2 = new OptionView(grid2.coordinates);  
        f.add(view2);
        view2.addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.NOBUTTON) {
                System.out.println("nobutton");
            } else if (e.getButton() == MouseEvent.BUTTON1) {
                System.out.println("linkse muis knop");
                int xTile = e.getX()/50;
                int yTile = e.getY()/50;
                int index = xTile + 6 * (yTile -1);
                Tile tileToPlace = tiles.options.get(index);
                view.addTile(tileToPlace);
            } else if (e.getButton() == MouseEvent.BUTTON2) {
                System.out.println("middel muis knop");
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                System.out.println("rechter muis knop");
            }
          }
        });
    }

    
}
