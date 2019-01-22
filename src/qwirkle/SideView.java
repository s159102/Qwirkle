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

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Mart
 */
public class SideView extends JPanel {
    

    
    public SideView(){
        setMouseListener();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        drawLines(g);
    }
    
    private void drawLines(Graphics g){
        g.setColor(Color.BLUE);
        for (int i = 0; i < 5; i++){
            g.drawLine(i, 0, i, this.getHeight());//left
            g.drawLine(this.getWidth()-i, 0, this.getWidth()-i, this.getHeight());//right
            g.drawLine(0, i, this.getWidth(), i);//top
            g.drawLine(0, this.getHeight()-i, this.getWidth(), this.getHeight()-i);
        }
        g.setColor(Color.BLACK);
    }
    

    
    private void setMouseListener(){
        this.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              switch (e.getButton()) {
              case MouseEvent.NOBUTTON:
                  System.out.println("nobutton");
                  break;
              case MouseEvent.BUTTON1:
                  System.out.println("linkse muis knop");
                  break;
              case MouseEvent.BUTTON2:
                  System.out.println("middel muis knop");
                  break;
              case MouseEvent.BUTTON3:
                  System.out.println("rechter muis knop");
                  break;
              default:
                  break;
              }
          }
        });

    }
    
    @Override
    public Dimension getPreferredSize(){
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        d.width = (int) (50*6);
        d.height = (int) (d.height - 80);
        return d;
    }
    
    public void drawCoordinate(Coordinate coordinate, int x, int y, Graphics g){
        if (!coordinate.isEmpty()){
            x = x * 50;
            y = y * 50;
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 50, 50);
            g.setColor(coordinate.tile().getColor());
            switch(coordinate.tile().getShape()){
                case "circle":
                    g.fillOval(x+5, y+5, 40, 40);
                    break;
                case "star":
                    int[] xpointsStar = {x+25, x+20, x+11, x+13, x+5, x+13, x+13, x+11, x+20, x+25, x+30, x+39, x+36, x+45, x+36, x+39, x+30};
                    int[] ypointsStar = {y+45, y+37, y+39, y+30, y+25, y+21, y+30, y+11, y+14, y+5, y+14, y+11, y+21, y+25, y+30, y+39, y+37};
                    g.fillPolygon(xpointsStar, ypointsStar, 17);
                    break;
                case "square":
                    g.fillRect(x+7, y+7, 36, 36);
                    break;
                case "diamond":
                    int[] xpoints = {x+25, x+5, x+25, x+45};
                    int[] ypoints = {y+45, y+25, y+5, y+25};
                    g.fillPolygon(xpoints, ypoints, 4);
                    break;
                case "clover":
                    g.fillOval(x+5, y+16, 15, 15);
                    g.fillOval(x+29, y+16, 15, 15);
                    g.fillOval(x+17, y+5, 15, 15);
                    g.fillOval(x+17, y+28, 15, 15);
                    int[] xpointsClover = {x+30, x+25, x+20, x+17, x+20, x+25, x+30, x+33};
                    int[] ypointsClover = {y+20, y+17, y+20, y+25, y+30, y+33, y+30, y+25};
                    g.fillPolygon(xpointsClover, ypointsClover, 8);
                    break;
                case "cross":
                    int[] xpointsCross = {x+45, x+25, x+5, x+17, x+5, x+25, x+45, x+33};
                    int[] ypointsCross = {y+5, y+17, y+5, y+25, y+45, y+33, y+45, y+25};
                    g.fillPolygon(xpointsCross, ypointsCross, 8);
                    break;
            }
        }
    }

}