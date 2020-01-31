package edu.montana.AI;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;

public class DrawingPanel extends JPanel {
    int radius = 5;
    Map map;
    static final long serialVersionUID=0;//remove warning
    public void paintComponent(Graphics g){
        super.paintComponent(g);    
        for(Region region: map.regions){
            switch (region.color) {
            case "red":
                g.setColor(Color.red);
                break;
            case "green":
                g.setColor(Color.green);
                break;
            case "blue":
                g.setColor(Color.blue);
                break;
            case "yellow":
                g.setColor(Color.orange);
                break;
            default:
                break;
            }
            g.fillOval(region.x-radius, region.y-radius, radius*2, radius*2);
            String local = region.regionId+"";
            g.drawString(local, region.x-radius, region.y-radius);
        }
        g.setColor(Color.black);
        for(Connection connection: map.connections){
            g.drawLine(connection.connectedRegion1.x, connection.connectedRegion1.y, connection.connectedRegion2.x, connection.connectedRegion2.y);
        }

        

        // drawPoints(g);
    }

    // public void drawPoints(Graphics g){
    //     for(Region region: map.regions)
    //     g.fillOval(region.x-radius, region.y-radius, radius*2, radius*2);
    // }

    public DrawingPanel(Map map, String title){
        this.map = map;
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}