
/**
 * Created by Vikaasa on 3/14/2016.
 */
public class Node {
    private int ID;
    private int count;
    private Node left;
    private Node right;
    private Node parent;
    private char color;

	// This is a constructor for the Node class. 
	// It creates a sentinel node by setting parent, right child, left child to null, and coloring it as ‘b’.
    public Node() {
        this.left = null;
        this.right = null;
        this.parent = null;
        this.color = 'b';
    }
	
	//This is a parameterized constructor for the Node class.
    public Node(int ID, int count){
        this.ID=ID;
        this.count=count;
    }
	
	//This function returns the color of the node.
    public char getColor(){
        return color;
    }

	//This function sets the color of the node to ‘color’. 
    public void setColor(char color){
        this.color=color;
    }
	
	//This function returns the ID of the node. 
    public int getID() {
        return ID;
    }
    
	//This function returns the count of the node. 
	public int getCount(){ return count; }
	
	//This function sets the ID of the node to ‘ID’. 
    public void setID(int ID){
        this.ID=ID;
    }
	//This function sets the count of the node to ‘count’. 
    public void setCount(int count){
        this.count=count;
    }
	//This function returns the current node’s parent.
    public Node getParent(){
        return parent;
    }
	//This function sets the current node’s parent to ‘parent’. 
    public void setParent(Node parent){
        this.parent=parent;
    }
	//This function returns the current node’s left child.
    public Node getLeft() {
        return left;
    }
	//This function sets the current node’s left child to ‘left’. 
    public void setLeft(Node left) {
        this.left = left;
    }
	//This function returns the current node’s right child.
    public Node getRight() {
        return right;
    }
	//This function sets the current node’s right child to ‘right’. 
    public void setRight(Node right) {
        this.right = right;
    }

}
