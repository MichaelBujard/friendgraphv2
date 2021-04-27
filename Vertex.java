package friendgraphv2;

import java.util.ArrayList;


public class Vertex {



    /**
     * each node has a name, the name of the friend.
     * nodes have information about the node: birthday, contact information, etc.
     * nodes have information that can be shared in common with other nodes.
     * when a node shares something in common with anothe node,
     * an edge connects them. Edges only connect commonalities, e.g. if same birthday, or if they go to same school.
     *
     * Edges are thicker or thinner depending on how many "potential" commonalities the nodes it connects share.
     * Edges change from red to orange to green in order of increasing "responsibility"
     * "Responsibility" is how much one accepts invitations to do something with a friend together with (plus)
     * the extent to which one invites the friend to activities that strengthen friendship.
     *
     * An activity that strengthens friendship is something you do with your friend (a) based on established interest or
     * (b) something you have never done with your friend before that you think will be good for him from experience
     *
     * node size is inversely proportional to the number of other nodes to which it is connected.
     * ? what if directly proportional?
     *     Then it's harder to see the need to reach out to the "lonely" nodes.
     *     This app has to encourage people to "branch out"
     *
     * The graph changes dynamically: the user inputs data about friends and the graph adjusts.
     * The idea is to organize what you know about your friend to make it easier to do things with him.
     * It is important to remember that doing things with friends is less important than wanting what's best for them.
     * This app should not be used too much. You'll end up obsessing about your relationships.
     *
     * Thanks to David Holsweiss
     */
    public String name; // the name of the friend, represented by the node
    public ArrayList<String> traits; // the list of characteristics you may/mayn't have in common with your friend
    public double response; // the ratio of number of traits you've acted on to total traits
    public int x; // the x-coordinate of the vertex
    public int y; // the y-coordinate of the vertex
    public int r; // the radius of the vertex determined by degree weighted by the size of the vertex

    /**
     * utility method sets the name to the input String name
     * @param name the name of the friend you are putting into the Vertex object
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * utility method adds a trait to this class
     * @param trait is the trait you are adding to the vertex
     */
    public void addTrait(String trait){ this.traits.add(trait); }

    /**
     *
     * @param x
     * @param y
     */
    public void setVertexLoc(int x, int y){
        this.x = x;
        this.y = y;
    }

    boolean nEquals(Vertex v){
        return this.name.equals(v.name);
    }

    boolean tEquals(Vertex v){
        return this.traits.equals(v.traits);
    }

    boolean rEquals(Vertex v){
        if (Double.compare(this.response, v.response) == 0) return true;
        return false;
    }

    boolean pEquals(Vertex v){
        return (this.x == v.x && this.y == v.y);
    }

    boolean rdEquals(Vertex v){
        return (this.r == v.r);
    }

    boolean vEquals(Vertex v){
        return (((rdEquals(v) && pEquals(v)) &&
                (rEquals(v) && tEquals(v))) &&
                nEquals(v));
    }




    /**
     * Constructor for node with no input parameters.
     * Builds a node with no name, no likes, and no work
     */
    public Vertex(){
        this.name = "";
        this.traits = new ArrayList<>();
        this.response = 0;
        this.x = 300;
        this.y = 300;
        this.r = 5;
    }

    /**
     * this constructor takes 2 input params
     * @param name is the name of the friend, who is the node
     * @param rs is the response of the vertex
     * @param x is the x-coordinate of the vertex
     * @param y is the y-coordinate of the vertex
     */
    public Vertex(String name, ArrayList<String> traits, double rs, int x, int y, int rd){
        this.name = name;
        this.traits = traits;
        this.response = rs;
        this.x = x;
        this.y = y;
        this.r = rd;
    }

    public Vertex(String name){
        this.name = name;
        this.traits = new ArrayList<>();
        this.response = 0;
        this.x = 300;
        this.y = 300;
        this.r = 5;
    }
}