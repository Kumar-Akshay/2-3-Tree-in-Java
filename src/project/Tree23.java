package project;

import java.util.*;
/**
 *
 * @author Akshay
 */

class Tree23<T extends Comparable<T>> extends Node {

	private Node root;              // The root of the tree
	private int size;              // Number of elements inside of the tree
	private static final int    Big_root = 1;
	private static final int    Small_root = -1;
        private boolean addition;       // A flag to know if the last element has been added correctly or not

    public Tree23() {
		
		this.root = new Node();
                size = 0;
	}
        
    public Tree23(Collection<T> elements) {
                    this.root = new Node();
                    this.size = 0;
                    elements.forEach(this::Insert);	
                }
    public boolean Insert(T element) {
		size++;
		addition = false;
        if(root == null || root.getLeftElement() == null) { // first case
		if(root == null)
                root = new Node();
		root.setLeftElement(element);
		addition = true;   }
        else {
		Node newRoot = InsertNotFull(root, element); // Immersion
		if(newRoot != null)
                    root = newRoot;
                                    }
	if(!addition)
             size--;
	     return addition;
                        	}

    
// when the Node returned by the method is not null.//
// If it is null, the node where the new element has been inserted was a 2-node (there was an element position still empty).
	
    private Node InsertNotFull(Node current, T element) {
                Node newParent = null;
	if(!current.isLeaf()) {
                Node NewLeaf = null;
               // if (current.leftElement.compareTo(element) == 0 || (current.is3Node() && current.rightElement.compareTo(element) == 0)){}	           
			// The new element is smaller than the left element
                        
/*else*/ if (current.leftElement.compareTo(element) == Big_root) {
        	NewLeaf = InsertNotFull(current.left, element);
            // Case NewLeaf != null --> the element has been added on a 3-node (there were 2 elements)
        if (NewLeaf != null) { // A new node comes from the left branch
            // The new element, in this case, is always less than the current.left
        if (current.is2Node()) {
                    current.rightElement    = current.leftElement;  // shift the current left element to the right
                    current.leftElement     = NewLeaf.leftElement;
                    current.right           = current.mid;
                    current.mid             = NewLeaf.mid;
                    current.left            = NewLeaf.left; }
            else { // In this case we have a new split, so the current element in the left will go up

		// We copy the right part of the subtree
        	Node rightCopy = new Node(current.rightElement, null, current.mid, current.right);
		// Now we create the new "structure", pasting the right part
                newParent = new Node(current.leftElement, null, NewLeaf, rightCopy);
                }
                 }
                   }
         // Case: the ascended element is bigger than the left element and less than the right element
        else if (current.is2Node() || (current.is3Node() && current.rightElement.compareTo(element) == Big_root)) {
                NewLeaf = InsertNotFull(current.mid, element);
                
                if (NewLeaf != null) { // A new split
  // The right element is empty, so we can set the ascended element in the left and the existing left element into the right
                    if (current.is2Node()) {
                            current.rightElement    = NewLeaf.leftElement;
                            current.right           = NewLeaf.mid;
                            current.mid             = NewLeaf.left;
                    }
                    else { // Another case we have to split again
                            Node left 	= new Node(current.leftElement, null, current.left, NewLeaf.left);
                            Node mid 	= new Node(current.rightElement, null, NewLeaf.mid, current.right);
                            newParent 	= new Node(NewLeaf.leftElement, null, left, mid);
                    }
                        }         }
        // The new element is bigger than the right element
        else if (current.is3Node() && current.rightElement.compareTo(element) == Small_root) {
                NewLeaf = InsertNotFull(current.right, element);
             if (NewLeaf != null) { // Split, the right element goes up
                 Node leftCopy   = new Node(current.leftElement, null, current.left, current.mid);
                    newParent       = new Node(current.rightElement, null, leftCopy, NewLeaf);  }
                                }
                                   }
             else {
                 // We are in the deepest level
                    addition = true;
                // The element already exists
   if (current.leftElement.compareTo(element) == 0 || (current.is3Node() && current.rightElement.compareTo(element) == 0)) {
                        addition = false;  }
         else if (current.is2Node()) { 
            // an easy case, there is not a right element
            // if the current left element is bigger than the new one --> we shift the left element to the right
        if (current.leftElement.compareTo(element) == Big_root) {
                current.rightElement    = current.leftElement;
                current.leftElement     = element;          }
            // if the new element is bigger, we Insert it in the right directly
            else if (current.leftElement.compareTo(element) == Small_root)
                    current.rightElement = element;         }
    // Case 3-node: there are 2 elements in the node and we want to Insert another one. We have to split the node
                else newParent = split(current, element);
                   }
                return newParent;
                                }
    
        // split the Node
    private Node split(Node current, T element) {
        Node newParent = null;

        // The left element is bigger, so it will go up letting the new element on the left
        if (current.leftElement.compareTo(element) == Big_root) {

            Node left   = new Node(element, null);
            Node right  = new Node(current.rightElement, null);
            newParent   = new Node(current.leftElement, null, left, right);

        } else if (current.leftElement.compareTo(element) == Small_root) {

            // The new element is bigger than the current on the right and less than the right element
            // The new element goes up
            if (current.rightElement.compareTo(element) == Big_root) {

                Node left   = new Node(current.leftElement, null);
                Node right  = new Node(current.rightElement, null);
                newParent   = new Node(element, null, left, right);

            } else { // The new element is the biggest one, so the current right element goes up

                Node left   = new Node(current.leftElement, null);
                Node right  = new Node(element, null);
                newParent   = new Node(current.rightElement, null, left, right);
            }
        }

        return newParent;
                                    }
// Removes all of the elements from this Tree23 instance.
//Searches an element inside of the tree.

    public T finditem(T element) {

		return findI(root, element);
	}
    private T findI(Node current, T element) {

		T found = null;
        if(current != null) {
			// Trivial case, we have found the element
	if(current.leftElement != null && current.leftElement.equals(element))
            found = (T) current.leftElement;
            else {
		// Second trivial case, the element to find equals the right element
	if(current.rightElement != null && current.rightElement.equals(element)) 
            found = (T) current.rightElement;
            else {
		// Recursive cases
        if(current.leftElement.compareTo(element) == Big_root) {
		found = findI(current.left, element);
		}
	    else if(current.right == null || current.rightElement.compareTo(element) == Big_root) {
		found = findI(current.mid, element);
		}
	    else if (current.rightElement.compareTo(element) == Small_root) {
		found = findI(current.right, element);
					}
            else 
                return null;          }   
        }
                }		
                return found;
	}
	/**
	 * @return The min element of the tree
	 */
    public T findMinItem() {
		if (isEmpty()) return null;
		return findMinI(root);   }
    private T findMinI(Node current) {
          
	if(current.getLeftSon() == null) return (T) current.leftElement;	// trivial case
	else{
            Node temp=current;
           while(temp.left!=null){
               temp=temp.left;  }
                   return (T) temp.leftElement;	}        }

      //return The max element of the tree
    public T findMaxItem() {
	if (isEmpty()) return null;
		return findMaxI(root);	}
      
    private T findMaxI(Node current) {

		if(current.rightElement != null && current.getRightSon() != null) return findMaxI(current.getRightSon());
		else if(current.getMidSon() != null) return findMaxI(current.getMidSon());

		if(current.rightElement != null) return (T) current.rightElement;
		else return (T) current.leftElement;
	}
    private boolean isEmpty() {
		if(root == null) return true;
                if(root.getLeftElement() == null) return true;
                return false;
                            }

    public void inOrder() {

		if(!isEmpty()) {

			inOrderI(root);    
		}
		else System.out.print("The tree is empty");
	}

	private void inOrderI(Node current) {

    if(current != null) {

    if(current.isLeaf()) {

            System.out.print(" "+current.getLeftElement().toString());
            if(current.getRightElement() != null)
                System.out.print(" "+current.getRightElement().toString());
    }
    else {

            inOrderI(current.getLeftSon());
            System.out.print(" "+current.getLeftElement().toString());

            inOrderI(current.getMidSon());

            if(current.getRightElement() != null) {

                    if(!current.isLeaf())
                        System.out.print(" "+current.getRightElement().toString());

                    inOrderI(current.getRightSon());
                    }
            }
    }
}

    public boolean remove(T element) {
		boolean deleted;

        // We decrease the number of levels at the start, if the element is not deleted, we increase the value at the end
        this.size--;
		deleted = removeI(root, element); 	
		root.rebalance();
                
		if(root.getLeftElement() == null) root = null; // We have deleted the last element of the tree
        if(!deleted) this.size++;
		return deleted;
	}

	 //return True if the element has been deleted or false if not
	 
   private boolean removeI(Node current, T element) {
	boolean deleted = true;
	// deepest level of the tree but we have not found the element (it does not exist)
	if(current == null) 
            deleted = false;
	else {
	// Recursive case, we are still finding the element to delete
	if(!current.getLeftElement().equals(element)) {
	// If there is no element in the right (2 Node) or the element to delete is less than the right element
	if(current.getRightElement() == null || current.getRightElement().compareTo(element) == Big_root) {
		// The left element is bigger than the element to delete, so we go through the left child
		if(current.getLeftElement().compareTo(element) == Big_root) {
			deleted = removeI(current.left, element);   }
                else { // If not, we go through the mid child				
		deleted = removeI(current.mid, element);  }                                 }
	else {// If the element to delete does not equals the right element, we go through the right child
		if(!current.getRightElement().equals(element)) {				
                    deleted = removeI(current.right, element);   }
	else { // If not, we have found the element
	// Situation A, the element equals the right element of a leaf so we just have to delete it
		if(current.isLeaf()) current.setRightElement(null);
		else { // We found the element but it is not in the leaf, this is the situation B
		// We get the min element of the right branch, remove it from its current position and put i
                    // where we found the element to delete
	T replacement = (T) current.getRightSon().replaceMin();
                current.setRightElement(replacement);               }
                }               }
                }
			// The left element equals the element to delete
		else {
            if(current.isLeaf()) { 
        // Situation A
	// The left element, the element to remove, is replaced by the right element
        	if(current.getRightElement() != null) {
                    current.setLeftElement(current.getRightElement());				
			current.setRightElement(null);		}
		else { // If there is no element in the right, a rebalance will be necessary
                current.setLeftElement(null); // We let the node empty
                    // We warn on the bottom up that a node has been deleted (is empty) and a rebalance is necessary
		return true; 	}   }
         	else {
                // Situation B
               // We move the max element of the left branch where we have found the element
		T replacement = (T) current.getLeftSon().replaceMax();			
			current.setLeftElement(replacement);
				} 
                                    }
                                            }
		if(current != null && !current.isBalanced()) {
			current.rebalance();  // The bottom level have to be rebalanced
		}
		else if(current != null && !current.isLeaf()) {
			boolean balanced = false;
			while(!balanced) {
				if(current.getRightSon() == null) {
			// Critical case of the situation B at the left child
		if(current.getLeftSon().isLeaf() && !current.getMidSon().isLeaf()) {
		T replacement = (T) current.getMidSon().replaceMin();
		T readdition = (T) current.getLeftElement();
                            current.setLeftElement(replacement);
                            Insert(readdition);
            	// Critical case of hte situation B at the right child
		} else if(!current.getLeftSon().isLeaf() && current.getMidSon().isLeaf()) {
				if(current.getRightElement() == null) {
		T replacement = (T) current.getLeftSon().replaceMax();
		T readdition = (T) current.getLeftElement();
			current.setLeftElement(replacement);
		Insert(readdition);  }
		}   }
	// It is important to note that we can't use the 'else' sentence because the situation could have changed in the if above
            if(current.getRightSon() != null) {
		if(current.getMidSon().isLeaf() && !current.getRightSon().isLeaf()) {
		current.getRightSon().rebalance();   }
	if(current.getMidSon().isLeaf() && !current.getRightSon().isLeaf()) {
		T replacement = (T) current.getRightSon().replaceMin();
		T readdition = (T) current.getRightElement();
		current.setRightElement(replacement);
        	Insert(readdition);	}
        	else balanced = true;			}
	if(current.isBalanced()) balanced = true;		}
		}
		return deleted;  }

  private int size() {
		return size; 	}
              }

