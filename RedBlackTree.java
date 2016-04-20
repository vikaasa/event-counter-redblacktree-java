
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Vikaasa on 3/14/2016.
 */
public class RedBlackTree {
    private Node nil=new Node();
    private Node root=nil;
	// A function to insert an event with two integer values, ID and count, in a Red Black Tree. 
	// It has a time complexity of O(log n).
    public void insert(int ID, int count) {
        Node node = new Node(ID,count);
        node.setColor('r');
        node.setLeft(nil);
        node.setRight(nil);
        if (root == nil) {
            root = node;
            root.setColor('b');
            root.setParent(nil);
            return;
        }
        Node cur = root;
        Node pre = root;
		// Go down the RBT from the root, turning left or right depending on
		// how the new node's ID compares with cur's ID, until we reach a nil node.
		
        while (cur != nil) {
            pre = cur;
            if (node.getID() < cur.getID()) {	
                cur = cur.getLeft();
            } else {
                cur = cur.getRight();
            }
        }
        node.setParent(pre);
        if (node.getID() < pre.getID()) {
            pre.setLeft(node);
        } else {
            pre.setRight(node);
        }
		// Call insertFixUp() to fix possible violation of Red Black Tree properties due to insert operation. 
        insertFixUp(node); 
    }

	// This function fixes the possible violation of the red-black properties of the Red Black Tree when the insert() function is invoked.  
	// The possible violation is pushed up toward the root, until it reaches the root, which can just be made black.
    public void insertFixUp(Node z){
        while(z.getParent().getColor()=='r'){
            if(z.getParent()==z.getParent().getParent().getLeft()){
				// z's parent is a left child.
                Node y=z.getParent().getParent().getRight();
                if(y.getColor()=='r'){
					// Case 1: z's uncle y is red.
                    z.getParent().setColor('b');
                    y.setColor('b');
                    z.getParent().getParent().setColor('r');
                    z=z.getParent().getParent();
                }
                else{
                    if(z==z.getParent().getRight()){
						// Case 2: z's uncle y is black and z is a right child.
                        z=z.getParent();
                        leftRotate(z);
                    }
					// Case 3: z's uncle y is black and z is a left child.
                    z.getParent().setColor('b');
                    z.getParent().getParent().setColor('r');
                    rightRotate(z.getParent().getParent());
                }
            }
            else{
				 // z's parent is a right child.
                Node y=z.getParent().getParent().getLeft();
                if(y.getColor()=='r'){
					// Case 1: z's uncle y is red.
                    z.getParent().setColor('b');
                    y.setColor('b');
                    z.getParent().getParent().setColor('r');
                    z=z.getParent().getParent();
                }
                else{
                    if(z==z.getParent().getLeft()){
						// Case 2: z's uncle y is black and z is a left child.
                        z=z.getParent();
                        rightRotate(z);
                    }
					// Case 3: z's uncle y is black and z is a right child.
                    z.getParent().setColor('b');
                    z.getParent().getParent().setColor('r');
                    leftRotate(z.getParent().getParent());
                }
            }
        }
		// The root is always black.
        root.setColor('b');
    }
	
	// This function performs a left rotation around the node ‘x’.
    public void leftRotate(Node x){
        Node y=x.getRight();
        x.setRight(y.getLeft());
        if(y.getLeft()!=nil){
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent()==nil)
            root=y;
        else if(x==x.getParent().getLeft())
            x.getParent().setLeft(y);
        else
            x.getParent().setRight(y);
        y.setLeft(x);
        x.setParent(y);
    }
	
	// This function performs a right rotation around the node ‘x’.
    public void rightRotate(Node x){
        Node y=x.getLeft();
        x.setLeft(y.getRight());
        if(y.getRight()!=nil){
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent()==nil)
            root=y;
        else if(x==x.getParent().getRight())
            x.getParent().setRight(y);
        else
            x.getParent().setLeft(y);
        y.setRight(x);
        x.setParent(y);
    }
	
	// This function replaces the subtree rooted at node u with the subtree rooted at node v.
    public void transplant(Node u, Node v){
        if (u.getParent()==nil)
            root=v;
        else if(u==u.getParent().getLeft())
            u.getParent().setLeft(v);
        else
            u.getParent().setRight(v);
        v.setParent(u.getParent());
    }
	
	// A function to delete the node ‘z’ from a red-black tree. It has a time complexity of O(log n).
    public void delete(Node z){
        Node y=z; // y is the node that was either removed or moved within the tree.
        Node x; // x is the node that will move into y's original position
        char origColor = y.getColor(); //// need to know whether y was black
        if(z.getLeft()==nil){
			// no left child?
            x=z.getRight();
			// replace z by its right child
            transplant(z,z.getRight());
        }
        else if(z.getRight()==nil){
			// no right child?
            x=z.getLeft();
			// replace z by its left child
            transplant(z,z.getLeft());
        }
        else{
			// Node z has two children.  
			// We find its successor y, which is in z's right subtree and has no left child.
            y=z.getRight();
            while(y.getLeft()!=nil)
                y=y.getLeft();
            origColor=y.getColor();
            x=y.getRight();
			// Remove y out of its current location, and replace z with it.
            if(y.getParent()==z)
                x.setParent(y);
            else{
				// If y is not z's right child, then replace y as a child of its parent by y's right child
				transplant(y,y.getRight());
				// turn z's right child into y's right child.
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }
			// Regardless of whether y was z's right child, replace z as a child of its parent by y
			transplant(z,y);
			// replace y's left child by z's left child.
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
			// y is colored with the same color as z.
            y.setColor(z.getColor());
        }
		// If we removed a black node, then must call deleteFixUp() because black-heights are now incorrect.
        if(origColor=='b')
            deleteFixUp(x);
    }
	// This function fixes the possible violation of the red-black properties 
	// caused by deleting a node using the delete() function. 
    public void deleteFixUp(Node x){
        while(x!=root&&x.getColor()=='b'){
            if(x==x.getParent().getLeft()){
                Node w=x.getParent().getRight();
                if(w.getColor()=='r'){
					// Case 1: x's sibling w is red.
                    w.setColor('b');
                    x.getParent().setColor('r');
                    leftRotate(x.getParent());
                    w=x.getParent().getRight();
                }
                if(w.getLeft().getColor()=='b'&&w.getRight().getColor()=='b'){
					// Case 2: x's sibling w is black, and both of w's children are black.
                    w.setColor('r');
                    x=x.getParent();
                }
                else{
                    if(w.getRight().getColor()=='b'){
						// Case 3: x's sibling w is black, and the left child of w is red,
						// and w's right child is black.
                        w.getLeft().setColor('b');
                        w.setColor('r');
                        rightRotate(w);
                        w=x.getParent().getRight();
                    }
					// Case 4: x's sibling w is black, and the right child of w is red.
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor('b');
                    w.getRight().setColor('b');
                    leftRotate(x.getParent());
                    x=root;
                }
            }
            else{
                Node w=x.getParent().getLeft();
                if(w.getColor()=='r'){
					// Case 1: x's sibling w is red.
                    w.setColor('b');
                    x.getParent().setColor('r');
                    rightRotate(x.getParent());
                    w=x.getParent().getLeft();
                }
                if(w.getRight().getColor()=='b'&&w.getLeft().getColor()=='b'){
					// Case 2: x's sibling w is black, and both of w's children are black.
                    w.setColor('r');
                    x=x.getParent();
                }
                else{
                    if(w.getLeft().getColor()=='b'){
						// Case 3: x's sibling w is black, wand the right child of w is red,
						// and w's left child is black.
                        w.getRight().setColor('b');
                        w.setColor('r');
                        leftRotate(w);
                        w=x.getParent().getLeft();
                    }
					// Case 4: x's sibling w is black, and the left child of w is red.
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor('b');
                    w.getLeft().setColor('b');
                    rightRotate(x.getParent());
                    x=root;
                }
            }
        }
        x.setColor('b');
    }
	// This function searches the Red Black Tree for a node with ID = ‘x', and returns the node if found. 
	// The time complexity of this function is O(log n).
    public Node search(int x) {
        Node ptr=root;
        while (ptr!=nil){
            if(ptr.getID()==x)
                return ptr;
            else if(ptr.getID()<x)
                ptr=ptr.getRight();
            else
                ptr=ptr.getLeft();
        }
        return nil;
    }
	// function to call search()
    public void callSearch(int ele) {
        search(ele);
    }
	
	// This function calls the search() function to find a node with ID = ‘x’. 
	// If found, it then calls the delete() function passing the node that was returned by the search() function as an argument. 
	// The time complexity of this function is O(log n).
    public void remove(int x){
        Node toDelete = search(x);
        delete(toDelete);
    }
	
	// This function calls the search() function to find a node with ID = ‘ID’. 
	// If found, it then increases the count of that node by ‘m’, and prints the count after increasing. 
	// Else, it calls the insert() function passing ‘ID’ and ‘m’ as arguments. 
	// The time complexity of this function in O(log n).
    public void increase(int ID, int m){
        Node toIncrease = search(ID);
        if(toIncrease!=nil) {
            int count = toIncrease.getCount();
            count = count + m;
            toIncrease.setCount(count);
            System.out.println(toIncrease.getCount());
        }
        else{
            insert(ID,m);
            System.out.println(m);
        }
    }
	
	// This function calls the search() function to find a node with ID = ‘ID’. 
	// If found, it then decreases the count of that node by ‘m’, and prints the count after decreasing. 
	// However, if the count <= 0 after decreasing, the delete function() is called passing the node as an argument. 
	// If the node is not present or is removed, then ‘0’ is printed. 
	// The time complexity of this function in O(log n).
    public void reduce(int ID, int m){
        Node toReduce = search(ID);
        if(toReduce!=nil) {
            int count = toReduce.getCount();
            count = count - m;
            if (count > 0) {
                toReduce.setCount(count);
                System.out.println(toReduce.getCount());
            } else {
                delete(toReduce);
                System.out.println("0");
            }
        }
        else
            System.out.println("0");
    }
	
	// This function calls the search() function to find a node with ID = ‘ID’. 
	// If found, the function then prints the count of the found node. Else, it prints ‘0’. 
	// This function has a time complexity of O(log n).
    public void count(int ID){
        Node toCount=search(ID);
        if(toCount!=nil) {
            System.out.println(toCount.getCount());
        }
        else
            System.out.println("0");
    }
    
	// This function is a recursive function that returns an array of two nodes 
	// which identify the next and previous nodes with value of ‘ID’ around the value of ‘key’, 
	// even when node with ID = key is not present in the tree. 
	// The time complexity of this function is O(log n).
    public Node[] findNextPrev(Node root, Node[] res, int key){
        if(root==nil){
            return null;
        }
        if(root.getID()==key){
            if(root.getLeft()!=nil){
                Node tmp=root.getLeft();
                while(tmp.getRight()!=nil){
                    tmp=tmp.getRight();
                }
                res[0]=tmp;
            }
            if(root.getRight()!=nil){
                Node tmp=root.getRight();
                while(tmp.getLeft()!=nil)
                    tmp=tmp.getLeft();
                res[1]=tmp;
            }
            return res;
        }
        if(root.getID()>key){
            res[1]=root;
            findNextPrev(root.getLeft(), res, key);
        }
        else{
            res[0]=root;
            findNextPrev(root.getRight(), res, key);
        }
        return res;
    }
	
	// This function calls the findNextPrev() function, passing ‘root’, ‘res’, and ‘ID’ as parameters, 
	// and prints the returned next node’s ID and count, if a next node was found. 
	// If no next node was found, then '0 0' is printed. 
	// The time complexity of this function is O(log n).
    public void next(int ID){
        Node[]res=new Node[2];
        Node[] ans=findNextPrev(root, res, ID);
        Node next=ans[1];
        if (next!=null)
            System.out.println(next.getID()+" "+next.getCount());
        else
            System.out.println("0 0");
    }
	
	// This function calls the findNextPrev() function, passing ‘root’, ‘res’, and ‘ID’ as parameters, 
	// and prints the returned previous node’s ID and count, if a previous node was found. 
	// If no previous node was found, then '0 0' is printed. 
	// The time complexity of this function is O(log n).
    public void previous (int ID){
        Node[]res=new Node[2];
        Node[] ans=findNextPrev(root,res,ID);
        Node previous=ans[0];
        if (previous!=null)
            System.out.println(previous.getID()+" "+previous.getCount());
        else
            System.out.println("0 0");
    }
	
	// This function is a recursive function that returns the sum of the counts of the nodes 
	// with IDs within the range of ID1 and ID2, even when nodes with ID1 and ID2 are not present in the tree. 
	// This function has a time complexity of O(log n + s) where ‘s’ is the number of IDs in the range.
    public int inRange(Node root, int ID1, int ID2) {
        if(root==nil)
            return 0;
        if(root.getID()==ID1&&root.getID()==ID2)
            return root.getCount();

        if(ID1<=root.getID()&&ID2>=root.getID()){
            //System.out.print(root.getID()+" ");
            // System.out.println("call");
            return root.getCount()+inRange(root.getLeft(),ID1,ID2)+inRange(root.getRight(),ID1,ID2);
        }
        else if(ID1<root.getID()){
            return inRange(root.getLeft(),ID1,ID2);

        }
        else {
            return inRange(root.getRight(), ID1, ID2);
        }
    }
	// This function calls the inRange() function and prints the returned value, 
	// which is the sum of the counts of all the nodes with IDs within the range of ID1 and ID2. 
	// This function has a time complexity of O(log n + s) where ‘s’ is the number of IDs in the range.
    public void callInRange(int ID1, int ID2){
        System.out.println(inRange(root, ID1, ID2));
    }
	
	// Function for in order traversal
    public void inOrder(Node root) {
        if (root == nil) {
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getID() + "->");
        inOrder(root.getRight());
    }
	// Function for pre order traversal
    public void preOrder(Node root) {
        if (root == nil) {
            return;
        }
        System.out.print(root.getID() + "->");
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }
	// Function for post order traversal
    public void postOrder(Node root) {
        if (root == nil) {
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.print(root.getID() + "->");
    }
	// Function to print traversal
    public void printTraversal(String path) {
        if (root != nil) {
            if (path.equals(new String("postOrder"))) {
                postOrder(root);
            } else if (path.equals(new String("preOrder"))) {
                preOrder(root);
            } else {
                inOrder(root);
            }
        }
    }
	// Function to print by level order 
    public void levelOrderTraversal(Node root, ArrayList<LinkedList<Node>> arrlist, int level) {
        if (root == null) {
            return;
        }
        LinkedList<Node> list = null;
        if (level == arrlist.size()) {
            list = new LinkedList<Node>();
            arrlist.add(list);
        } else {
            list = arrlist.get(level);
        }
        list.add(root);
        levelOrderTraversal(root.getLeft(), arrlist, level + 1);
        levelOrderTraversal(root.getRight(), arrlist, level + 1);
    }
	
	// Function to call levelOrderTraversal()
    public void callLevelOrderTraversal() {
        ArrayList<LinkedList<Node>> lists = new ArrayList<LinkedList<Node>>();
        int lvl = 0;
        levelOrderTraversal(root, lists, lvl);
        int lenArrayList=lists.size();
        for(int i=0;i<lenArrayList;i++){
            /*for(int k=lenArrayList-i;k>0;k--)
                System.out.print("  ");*/
            int lenLinkedList=lists.get(i).size();
            for(int j=0;j<lenLinkedList;j++) {
                Node tmp = lists.get(i).get(j);
                System.out.print(" " + "["+tmp.getID()+","+tmp.getCount()+"]" + tmp.getColor());
            }
            System.out.println(" ");

        }
    }
	
	// This function calls the initializeRBT() function, which then initializes the Red Black Tree from the sorted array of events. 
	// The returned node is then assigned as root of the Red Black Tree, and its parent is set to nil. 
	// The time complexity of this function is O(n). 
    public void callInitialize(int arr[][],int start,int end,int n){
        int h= (int) Math.ceil((Math.log(n))/Math.log(2));
        int lvl=0;
        root=initializeRBT(arr,start,end,lvl,h);
        root.setParent(nil);
    }
	
	// This function initializes the Red Black Tree from an array of events containing ID and count and sorted by ID. 
	// This is done by making the middle element of the sorted array as the root of the Red Black Tree, 
	// and then recursively doing it for the left half and the right half. 
	// The root of the entire Red Black Tree is then returned. 
	// All nodes other than the immediate parents of external nodes are colored black, 
	// while the immediate parents of external nodes are colored red. 
	// The time complexity of this function is O(n). 
    public Node initializeRBT(int arr[][],int start,int end,int lvl,int h){
        if(start>end)
            return nil;
        int mid=(start+end)/2;
        Node node=new Node();
        node.setID(arr[mid][0]);
        node.setCount(arr[mid][1]);
		// by default, node is colored black.
        if(lvl==0) // if current level is pointing to root level
            node.setColor('b');
        if(lvl==h-1) // current level is equal to the level immediately above sentinel nodes
            node.setColor('r');
        node.setLeft(initializeRBT(arr, start, mid-1,lvl+1,h));
        node.getLeft().setParent(node);
        node.setRight(initializeRBT(arr, mid+1, end,lvl+1,h));
        node.getRight().setParent(node);
        return node;
    }
}
