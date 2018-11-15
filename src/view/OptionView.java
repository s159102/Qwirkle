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
package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import qwirkle.Coordinate;
import qwirkle.Grid;
import qwirkle.Tile;
import qwirkle.TilesAvailable;

/**
 *
 * @author Mart
 */
public class OptionView extends JPanel {
    
    ArrayList<Coordinate> coordinates;

    public OptionView(ArrayList<Coordinate> coordinates) {
        super();
        this.coordinates = coordinates;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        
        g.setColor(Color.BLACK);
        for (int i = 0; i <= 300; i = i + 50){
            g.drawLine(i, 0, i, 300);
            g.drawLine(0, i, 300, i);
        }

        for (Coordinate coordinateToDraw: coordinates){
           drawCoordinate(coordinateToDraw, g);
        }

    }
    
    public void drawCoordinate(Coordinate coordinate, Graphics g){
        if (coordinate.tile != null){
            Integer x = coordinate.getX()*50;
            Integer y = coordinate.getY()*50;
            
            while (x > 250){
                x = x - 300;
                y = y + 50;
            }
            
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 50, 50);
            switch(coordinate.tile.getShape()){
                case "circle":
                    g.setColor(coordinate.tile.getColor());
                    g.fillOval(x+5, y+5, 40, 40);
                    break;
                case "star":
                    g.setColor(coordinate.tile.getColor());
                    int[] xpointsStar = {x+25, x+20, x+11, x+13, x+5, x+13, x+13, x+11, x+20, x+25, x+30, x+39, x+36, x+45, x+36, x+39, x+30};
                    int[] ypointsStar = {y+45, y+37, y+39, y+30, y+25, y+21, y+30, y+11, y+14, y+5, y+14, y+11, y+21, y+25, y+30, y+39, y+37};
                    g.fillPolygon(xpointsStar, ypointsStar, 17);
                    break;
                case "square":
                    g.setColor(coordinate.tile.getColor());
                    g.fillRect(x+7, y+7, 36, 36);
                    break;
                case "diamond":
                    g.setColor(coordinate.tile.getColor());
                    int[] xpoints = {x+25, x+5, x+25, x+45};
                    int[] ypoints = {y+45, y+25, y+5, y+25};
                    g.fillPolygon(xpoints, ypoints, 4);
                    break;
                case "clover":
                    g.setColor(coordinate.tile.getColor());
                    g.fillOval(x+5, y+5, 40, 40);
                    break;
                case "cross":
                    g.setColor(coordinate.tile.getColor());
                    int[] xpointsCross = {x+45, x+25, x+5, x+17, x+5, x+25, x+45, x+33};
                    int[] ypointsCross = {y+5, y+17, y+5, y+25, y+45, y+33, y+45, y+25};
                    g.fillPolygon(xpointsCross, ypointsCross, 8);
                    break;
            }
            
        }
    }

}