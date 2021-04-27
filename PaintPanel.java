package friendgraphv2;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PaintPanel extends JPanel {

    VisibleGraph parent;

    /**
     * utility method draws each vertex
     * @param f is the HashMap of friends
     */
    private void drawVertices(Graphics2D g2, HashMap<Integer, Vertex> f){
        for (int i : f.keySet()){
            Vertex v = f.get(i);
            g2.fillOval(v.x - v.r, v.y - v.r, v.r*2, v.r*2);
            System.out.println("drawVertices called");
        }
    }

    /**
     * utility method
     * @param v is the first vertex
     * @param w is the second vertex
     * @return boolean true if v and w share a trait, false otherwise
     */
    public static boolean shareTrait(Vertex v, Vertex w){
        System.out.println("shareTrait called");
        for (String vTrait : v.traits){
            System.out.println("outer for shareTrait()");
            for (String wTrait : w.traits){
                System.out.println("inner for shareTrait()");
                if (vTrait.equals(wTrait)){
                    System.out.println("if in inner for and shareTrait true" + vTrait + "::::" + wTrait);
                    return true;
                }
            }
        }
        System.out.println("shareTrait false");
        return false;
    }


    public void repaintPPanel(){
        System.out.println("repaintPPanel() called");
        this.removeAll();
        //this.add();
        this.validate();
        this.repaint();
    }

    /**
     * utility method draws an edge between two vertices with shared traits
     * @param v is the first vertex
     * @param w is the second vertex
     */
    private static void drawEdge(Graphics2D g2, Vertex v, Vertex w){ // do I need to make drawEdge non-static?
        if (shareTrait(v, w)){
            System.out.println("if statement in drawEdge true");
            g2.setPaint(Color.GREEN);
            g2.drawLine(v.x, v.y, w.x, w.y);
        }
        System.out.println("drawEdge called");
    }

    private static void drawEdges(Graphics2D g2, HashMap<Integer, Vertex> f){
        System.out.println("DrawEdges called");
        for (int i : f.keySet()){
            Vertex v = f.get(i);
            System.out.println("for loop i in drawEdges");
            for (int j : f.keySet()){
                Vertex w = f.get(j);
                System.out.println("For loop j in drawEdges");
                if (i != j){ // i and j are not null
                    drawEdge(g2, v, w);
                    System.out.println("if statement in drawEdges");
                }
            }
        }
    }



    public PaintPanel(VisibleGraph p){
        parent = p;
        System.out.println("paintPanel constructed");
    }

    public void paintComponent(Graphics g) {
        System.out.println("paintComponent method called");
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(Color.GREEN);

        // draw edges
        drawEdges(g2, parent.friends);

        // draw vertices
        drawVertices(g2, parent.friends);
    }
}
