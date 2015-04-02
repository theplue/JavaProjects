
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Robert Slavik
 * @author Josh Coyle
 */
public class exercise11 {
    /**
     * the number to be examined
     */
    private static final List number = new ArrayList();
    /**
     * the size of the new number
     */
    private static int in = 0;
    /**
     * The main class uses node.java. Asks the user for the size of a number
     * then has the user enter the number, digit by digit and prints out a tree
     * diagram of the values as the number is traversed through the tree.
     * @param args 
     */
    public static void main(String[] args) {
        System.out.println("enter the number of items (how many digits are in the number)");
        Scanner kbd = new Scanner(System.in);
        //in= kbd.nextInt();
        in = 6;
        System.out.println("eneter each digit, one at a time");
        //for(int i =1; i <=in ; i++){
          //number.add(kbd.nextInt());
          //System.out.println(number.toString());
        //}
        number.add(3);
        number.add(1);
        number.add(7);
        number.add(5);
        number.add(8);
        number.add(4);
        node master = new node(number, 0, null);
       // System.out.println(master.value + " list" + master.list);
        levelOrderTraversal(master);

    }
    /**
     * Prints out a tree like diagram of the traversal of the number.
     * @param startNode the top node of the tree.
     */
    public static void levelOrderTraversal(node startNode) {
        Queue<node> queue = new LinkedList<>();
        queue.add(startNode);
        int print;
        int format = in;
        int printNum = ((int) (Math.pow(2, (format - 1)) / 2) - 1);
        
        //System.out.println("printNum = " + printNum);
        while (!queue.isEmpty()) {
            print = queue.size();
            System.out.println();
            for (int j = 0; j < printNum; j++) {
                System.out.print("\t");
            }
            //printNum = printNum - queue.size(); for testing
            for (int i = 0; i < print; i++) {
                node tempNode = queue.poll();
                if (tempNode.value == 0) {
                    System.out.print("    ");
                }
                System.out.printf("%d ", tempNode.value);
                        System.out.print(tempNode.list);
                System.out.print("\t");
                if (tempNode.left != null) {
                    queue.add(tempNode.left);
                }
                if (tempNode.right != null) {
                    queue.add(tempNode.right);
                }
            }
            printNum = printNum - (print / 2);
        }
    }
}
