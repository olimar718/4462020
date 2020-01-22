package edu.montana.AI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

// places points randomly with a random color
//connects points from the order they were placed

public class GraphPanel extends JPanel {

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.black);

    int rad = 5;
    int data = 10;
    int[] x = new int[data];
    int[] y = new int[data];
    String[] colors = {"red", "green", "blue", "yellow"};

    //place points randomly on plane
    for (int i = 0; i < data; i++) {
      Dimension size = getSize();
      int w = size.width;
      int h = size.height;

      Random r = new Random();
      x[i] = Math.abs(r.nextInt()) % w;
      y[i] = Math.abs(r.nextInt()) % h;
      int j = (int)(Math.random()*4);
      switch (colors[j]) {
        case "red":
        g2d.setColor(Color.red);
        break;

        case "green":
        g2d.setColor(Color.green);
        break;
        case "blue":
        g2d.setColor(Color.blue);
        break;
        case "yellow":
        g2d.setColor(Color.yellow);
        break;
      
        default:
          break;
      }

      // Ellipse2D.Double circle = new Ellipse2D.Double(x[i])-rad, (y[i])-rad, rad*2, rad*2);
      g2d.fillOval((x[i])-rad, (y[i])-rad, rad*2, rad*2);
    }

    //connect dots based on what order they were placed
     g2d.setColor(Color.black);
    for(int i =0; i < x.length-1; i++){
      g2d.drawLine(x[i], y[i], x[i+1], y[i+1]);
    }

    
  }

  public static void main(String[] args) {
    GraphPanel points = new GraphPanel();
    JFrame frame = new JFrame("Points");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(points);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}