package friendgraphv2;

import java.awt.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class VisibleGraph extends JFrame {

    HashMap<Integer, Vertex> friends; // Key is currentID. Value is the vertex itself.
    PaintPanel paintPanel;
    int width = 600;
    int height = 600;
    int currentID = 0;
    int removeFlag = 0;
    int changeFlag = 0;
    String currentText = "";

    /**
     * utility method adds a vertex to the ArrayList<Vertex> friends
     * @param v the name of the vertex added to the list of friend vertices
     * returns false if a friend named "name" already exists in friends
     * otherwise, it adds a new friend of name "name" and returns "true"

    boolean addVertex(HashMap<Integer, Vertex> f, Vertex v){
    for (int i : f.keySet()){
    if (f.get(i).name == v.name)
    return false;
    }
    f.put(currentID, v);
    currentID++;
    return true;
    }
     */

    public boolean moveOverlaidVertex(Vertex v){
        for(int i : friends.keySet()){
            Vertex vertex = friends.get(i);
            if (vertex != null) {
                if (vertex != v) {
                    if ((vertex.x == v.x) && (vertex.y == v.y)) {
                        changeVertexLoc(v);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * utility method changes the vertex location of the vertex
     * @param v is the vertex whose location we are changing
     * @return true if the vertex location changes, false otherwise
     */
    public void changeVertexLoc(Vertex v){
        int x = (int) (600*Math.random());
        int y = (int) (600*Math.random());
        v.setVertexLoc(x, y);
    }

    /**
     * utility method gets vertex named "name" from friends
     * @param f the ArrayList<Vertex> friends
     * @param name the name of the Vertex you want to get
     * @return null if no vertex exists of name "name", else returns the vertex
     */
    Vertex getVertex(HashMap<Integer, Vertex> f, String name){
        for (int i : f.keySet()){
            if (f.get(i).name == name) {
                return f.get(i);
            }
        }

        return null;
    }

    /**
     * utility method gets all vertices in f that contain trait "trait"
     * @param f the ArrayList<Vertex> friends
     * @param trait the trait input
     * @return return an ArrayList<Vertex> friendsWithTrait
     */
    HashMap<Integer, Vertex> getFriendsByTrait(HashMap<Integer, Vertex> f, String trait){
        HashMap<Integer, Vertex> fWithTrait = new HashMap<>();
        for (int i : f.keySet()){
            for (String s : f.get(i).traits){
                if (f.get(i).traits.equals(trait)){
                    fWithTrait.put(currentID, f.get(i));
                    currentID++;
                }
            }
        }
        return fWithTrait;
    }

    boolean clickWithinDomain(MouseEvent e, Vertex v){
        int x = e.getX();
        if (distanceFromPoint(e, v) < 30){
            System.out.println("cWD true");
            return true;
        }
        System.out.println("cWD false");
        return false;
    }

    double distanceFromPoint(MouseEvent e, Vertex v){
        int cX = v.x+v.r;
        int cY = v.y+v.r;
        double d = Math.sqrt(Math.pow((e.getX()-cX), 2) + Math.pow((e.getY()-30-cY), 2));
        return d;
    }

    boolean clickWithinRange(MouseEvent e, Vertex v){
        int y = e.getY();
        if (distanceFromPoint(e, v) < 30) {
            System.out.println("cWR true");
            return true;
        }
        System.out.println("cWR false");
        return false;

    }

    boolean clickedVertex(MouseEvent e, Vertex v){
        if (clickWithinDomain(e, v) && clickWithinRange(e, v)){
            System.out.println("clickWithinDomain and ClickWithinRange true");
            return true;
        }
        System.out.println("clickWithinDomain false or ClickWithinRange false");
        return false;
    }

    Vertex getClickedVertex(MouseEvent e){
        for (int i : friends.keySet()) {
            Vertex v = friends.get(i);
            if (clickedVertex(e, v)) {
                System.out.println("getClickedVertex returned a vertex");
                return v;
            }
        }
        System.out.println("getClickedVertex returned null");
        return null;
    }


    void removeVertex(Vertex v){
        int id = 0;
        if (removeFlag == 1) {
            for (int i : friends.keySet()) {
                Vertex vertex = friends.get(i);
                if (v != null) {
                    if (v.vEquals(vertex)) {
                        id = i;
                    }
                }
            }
            friends.remove(id);
        }
    }

    void removeClickAction(MouseEvent e) {
        System.out.println("removeButtonAction method called");
        Vertex v = getClickedVertex(e);
        removeVertex(v);
        repaint();
    }

    void switchRFlag() {
        System.out.println("flag switched");
        if (removeFlag == 0){
            removeFlag = 1;
            changeFlag = (changeFlag == 1) ? 0 : changeFlag;
        } else removeFlag = 0;
    }

    void switchCFlag() {
        changeFlag = (changeFlag == 0) ? 1 : 0;
        removeFlag = (removeFlag == 1) ? 0 : removeFlag;
    }


    void changeClickAction(MouseEvent e){
        System.out.println("changeClickAction() called");
        Vertex v = getClickedVertex(e);
        v.addTrait(currentText);
        System.out.println("Traits of vClicked: " + v.traits.toString());
        repaint();
    }

    public VisibleGraph(String name){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel();
        JLabel label = new JLabel();
        label.setText("click vertices and buttons to change graph appearance.");
        labelPanel.add(label);
        add(labelPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());

        JTextField jTextField = new JTextField(20);
        jTextField.setBounds(5, 5, 20, 20);
        textPanel.add(jTextField, BorderLayout.SOUTH);
        add(textPanel, BorderLayout.SOUTH);
        jTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                listen();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listen();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listen();
            }

            void listen(){
                currentText = jTextField.getText();
                System.out.println(currentText);
                /**
                 if (!txt.isEmpty()){
                 for (int i : friends.keySet()){
                 Vertex v = friends.get(i);
                 v.traits.add(txt);
                 }
                 }
                 //repaint();
                 */

            }


        });

        /**
         String filename = "Vertex.txt";

         FileWriter writer = null;
         try {
         writer = new FileWriter(filename);
         jTextField.write(writer);
         } catch (IOException exception) {
         System.err.println("Save oops");
         } finally {
         if (writer != null) {
         try {
         writer.close();
         } catch (IOException exception) {
         System.err.println("Error closing writer");
         exception.printStackTrace();
         }
         }
         }
         */

        setPreferredSize(new Dimension(width, height));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                System.out.println("size of array of friends: " + friends.size());
                if (removeFlag == 1) {
                    removeClickAction(e);

                }

                if (changeFlag == 1){
                    changeClickAction(e);
                }

                System.out.println(friends.size());
                //paintPanel.repaintPPanel();

                if (changeFlag != 0) {
                    label.setText("type a trait and click on a vertex to add trait.");
                } else if (removeFlag != 0) {
                    label.setText("remove clicked vertex.");
                }
            }
        });


        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                friends.put(currentID, new Vertex());
                if (friends.size()>1)
                    moveOverlaidVertex(friends.get(currentID));
                System.out.println(friends.size());
                currentID++;
                repaint();
            }
        });
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Remove");

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchRFlag();
                System.out.println("Remove Flag: " + removeFlag);
                System.out.println("Switch flag : " + changeFlag);
            }
        });
        buttonPanel.add(removeButton);

        JButton changeButton = new JButton("Change");
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                switchCFlag();
                System.out.println("Change Flag: " + changeFlag);
                System.out.println("Remove flag: " + removeFlag);
            }

        });
        buttonPanel.add(changeButton);

        add(buttonPanel, BorderLayout.EAST);

        paintPanel = new PaintPanel(this);
        add(paintPanel, BorderLayout.CENTER);

        friends = new HashMap<Integer, Vertex>();

        pack();
        setSize(new Dimension(width, height));
        setVisible(true);
    }

    /**
     public void initial() {
     setBackground(Color.white);
     setForeground(Color.white);
     }
     */

    public static void main(String[] args) {
        VisibleGraph f = new VisibleGraph("Friend");

        ArrayList<String> dummy = new ArrayList<>();
        dummy.add("Soccer");
        dummy.add("Programming");
        dummy.add("Spanish");
        ArrayList<String> dost = new ArrayList<String>();
        dost.add("Programming");
        dost.add("Spanish");
        ArrayList<String> dust = new ArrayList<>();
        dust.add("Spanish");
        ArrayList<String> unconnected = new ArrayList<>();
        unconnected.add("Bricks");
        Vertex v1 = new Vertex("Bob", dummy, 0.5, 30, 40, 5);
        Vertex v2 = new Vertex("Tom", dost, 0.5, 300, 400, 5);
        Vertex v3 = new Vertex("Philip", dust, 0.5, 200, 500, 5);
        Vertex v4 = new Vertex("Jessie", unconnected, 0.5, 100, 100, 5);
        //v4.addTrait("Bricks");
        /**
         f.addVertex(v4);
         f.addVertex(v1);
         f.addVertex(v3);
         f.addVertex(v2);
         */
    }
}