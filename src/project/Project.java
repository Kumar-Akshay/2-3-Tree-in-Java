package project;

/**
 *
 * @author Akshay
 */
public class Project extends Tree23{
        public static void main(String[] args) {

      
        Tree23<Integer> Tree = new Tree23<>();

        
            System.out.println("* Element Insertion *");
        Tree.Insert(5);
        Tree.Insert(23);
        Tree.Insert(10);
        Tree.Insert(4);
        Tree.Insert(70);
        Tree.Insert(20);
        Tree.Insert(65);
        Tree.Insert(1);
        Tree.Insert(62);
        Tree.Insert(6);
        Tree.inOrder();
          System.out.print("\n* Remove element 23 *");
        Tree.remove(23);
          System.out.println("");
        Tree.inOrder();
        System.out.println("");
        System.out.println("*Element Find  "+Tree.finditem(62)+" *");
            System.out.println("");
        
    }
}
  
    

