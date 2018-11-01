/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Akshay
 */

public class Node <T extends Comparable<T>>  {
    		Node left;
		Node mid;
		Node right;
		T leftElement;
		T rightElement;

        // Creates an empty node/child
                
    public Node() {
	left =mid =right = null;
	leftElement =rightElement = null;
		}
         //leftElement   the element in the left // @param rightElement  the element in the right
    public Node(T leftElement, T rightElement) {
			this.leftElement = leftElement;
			this.rightElement = rightElement;
                        left =mid =right = null;    }

    // The left element must be less than the right element This is a private class but
    //if you want to make it externally accessible
    public Node(T leftElement, T rightElement, Node left, Node mid) {
			this.leftElement = leftElement;
			this.rightElement = rightElement;			
			this.left = left;
			this.mid = mid;   	}
    public T getLeftElement() {
			return this.leftElement; }
    public T getRightElement() {
			return this.rightElement;  }      
		
    public void setLeftElement(T element) {			
            this.leftElement = element; 	}
		
    public void setRightElement(T element) {
            this.rightElement = element;   }
	
    public void setLeftSon(Node son) {
            this.left = son;	}
		
    public Node getLeftSon() {
		return left;}
		
    public void setMidSon(Node son) {
        	this.mid = son; }
    public Node getMidSon() {
                return mid;     }
		
    public void setRightSon(Node son) {
		this.right = son; 	}
		
    public Node getRightSon() {
        	return right;	}
        //true if we are on the deepest level of the tree (a leaf) or false if not
    public boolean isLeaf() {
        	return left == null && mid == null && right == null;	}
    public boolean is2Node() {
                return rightElement == null;
         // also, right node is null but this will be always true if rightElement == null 
        }
    public boolean is3Node() {
            return rightElement != null; 
        // also, right node is not null but this will be always true if rightElement <> null
        }
	// Checks if the tree is well-balanced or not
    public boolean isBalanced() {
			
	boolean balanced = false;
		if(isLeaf()) { // If we are at the deepest level (the leaf), it is well-balanced for sure
		balanced = true;
			}
            // There are two cases: 2 Node or 3 Node
	else if(left.getLeftElement() != null && mid.getLeftElement() != null) {
                if(rightElement != null) { // 3 Node
		if(right.getLeftElement() != null) {
		balanced = true;
			}    }
	else {  // 2 Node
	balanced = true;   }  }
                	return balanced;}  
        
    public T replaceMax() {
                T max = null;
		if(!isLeaf()) { // Recursive case, we are not on the deepest level
		if(getRightElement() != null) {
	max = (T) right.replaceMax(); // If there is an element on the right, we continue on the right
				}
		else max = (T) mid.replaceMax();  // else, we continue on the mid
	}
	else {	// Trivial case, we are on the deepest level of the tree
	if(getRightElement() != null) {
			max = getRightElement();
			setRightElement(null);
			// We are luck, we don't need to rebalance anything
			}
	else {	max = getLeftElement();
			setLeftElement(null);
		// On the first up of the recursive function, there will be a rebalance
				}           }
        if(!isBalanced()) rebalance(); // Keep calm and rebalance
        	return max;  }
        
    public T replaceMin() {
		T min = null;
	if(!isLeaf()) { // Cas recursiu, mentre no arribem al nivell mes profund anem baixant per l'esquerra sempre
            min = (T) left.replaceMin();   }
	else { // Cas trivial, agafem l'element i ho intentem deixar tot maco
            min = leftElement; 
            leftElement = null;
            if(rightElement != null) { // Hi havia element a la dreta, el passem a l'esquerra i aqui no ha passat res!
            leftElement = rightElement;
            rightElement = null;   }	
                        }
	if(!isBalanced()) { // Aquesta situacio es dona quan a la dreta no hi havia element, en la 1a pujada rebalancejara
            rebalance();			}
        return min;  	}
        
            //Rebalances the deepest level of the tree from the second deepest.
    public void rebalance() {
        while(!isBalanced()) {
        if(getLeftSon().getLeftElement() == null) { 
      
            //The unbalance is in the left child We put the left element of current node as the left element of the left child

            getLeftSon().setLeftElement(getLeftElement());
            // Now we replace the left element of the mid child as the left element of the current node
            setLeftElement((T) getMidSon().getLeftElement());
               // If a right element on the mid child exists, we shift it to the left
        if(getMidSon().getRightElement() != null) {
                   getMidSon().setLeftElement(getMidSon().getRightElement());
                   getMidSon().setRightElement(null);        }
         // Else, we let the mid child EMPTY, so the next iteration may solve this situation
                        // if not, the party of the critical case starts here!
            else
               getMidSon().setLeftElement(null);
                        
        }
        else if(getMidSon().getLeftElement() == null) { 
                    // The unbalance is in the right child
                    // CRITICAL CASE, each node (child) of the deepest level have just one element (the right is empty)
                    // the algorithm will have to rebalance from a higher level of the tree
        if(getRightElement() == null) {
        if(getLeftSon().getLeftElement() != null && getLeftSon().getRightElement() == null && getMidSon().getLeftElement() == null) 
            {
                  setRightElement(getLeftElement());
                    setLeftElement((T) getLeftSon().getLeftElement());
                    // Let the party starts, we remove the current childs
                    setLeftSon(null); 
                    setMidSon(null);
                    setRightSon(null);
            }
        else {
                getMidSon().setLeftElement(getLeftElement());
                if(getLeftSon().getRightElement() == null) {
                    setLeftElement((T) getLeftSon().getLeftElement());
                   getLeftSon().setLeftElement(null);                     }
                else {
                setLeftElement((T) getLeftSon().getRightElement());
                getLeftSon().setRightElement(null);      }
        if(getLeftSon().getLeftElement() == null && getMidSon().getLeftElement() == null) {
          // The other but same case the party starts
                        setLeftSon(null); 
                        setMidSon(null);
                        setRightSon(null);      }       }
                            }
        else {
             // We put the right element of the current node as the left element of the mid son
                getMidSon().setLeftElement(getRightElement());
                        // We put the left element of the right child as the right element of the current node
                setRightElement((T) getRightSon().getLeftElement());
                            // If the right child, where we have taken the last element has a right element, we move it
                            // into the left of the same child
                if(getRightSon().getRightElement() != null) {
                            getRightSon().setLeftElement(getRightSon().getRightElement());
                            getRightSon().setRightElement(null);             }
                            else { // Else, we let the right child EMPTY
                            getRightSon().setLeftElement(null);
                            }
                    }
                        }
                 // Unbalance in the right child
                 else if(getRightElement() != null && getRightSon().getLeftElement() == null) {
                //   (1) The mid child is full, so we have to do a shift of the elements to the right
                //    (2) The mid child only has the left element, then we have to put the right element
                 if(getMidSon().getRightElement() != null) { // (1)
                  getRightSon().setLeftElement(getRightElement());
                   setRightElement((T) getMidSon().getRightElement());
                   getMidSon().setRightElement(null);
                        }
                 else {
                      getMidSon().setRightElement(getRightElement());
                       setRightElement(null);
                                        } 
                                  }   
                                    }			
                                        }
                                                }
    


