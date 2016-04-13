/**
 * Created by Furkan on 10.04.2016.
 */

import sun.plugin2.message.Message;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class HuffmanCode {
    /** A datum in the Huffman tree. */
    public static class HuffData implements Serializable {
        // Data Fields

        /** The weight or probability assigned to this HuffData. */
        private double weight;
        /** The alphabet symbol if this is a leaf. */
        private Character symbol;

        public HuffData(double weight, Character symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }

        public Character getSymbol() {return symbol;}
    }
    // Data Fields
    /** A reference to the completed Huffman tree. */
    protected BinaryTree<HuffData> huffTree;

    /** A Comparator for Huffman trees; nested class. */
    private static class CompareHuffmanCodes
            implements Comparator<BinaryTree<HuffData>> {

        /**
         * Compare two objects.
         * @param treeLeft The left-hand object
         * @param treeRight The right-hand object
         * @return -1 if left less than right,
         * 0 if left equals right,
         * and +1 if left greater than right
         */
        public int compare(BinaryTree<HuffData> treeLeft,
                           BinaryTree<HuffData> treeRight) {
            double wLeft = treeLeft.getData().weight;
            double wRight = treeRight.getData().weight;
            return Double.compare(wLeft, wRight);
        }
    }

    /*<listing chapter="6" number="10">*/
    /**
     * Builds the Huffman tree using the given alphabet and weights.
     * @post  huffTree contains a reference to the Huffman tree.
     * @param symbols An array of HuffData objects
     */
    public void buildTree(HuffData[] symbols) {
        Queue<BinaryTree<HuffData>> theQueue =
                new PriorityQueue<BinaryTree<HuffData>>(symbols.length,
                        new CompareHuffmanCodes());
        // Load the queue with the leaves.
        for (HuffData nextSymbol : symbols) {
            BinaryTree<HuffData> aBinaryTree =
                    new BinaryTree<HuffData>(nextSymbol, null, null);
            theQueue.offer(aBinaryTree);
        }

        // Build the tree.
        while (theQueue.size() > 1) {
            BinaryTree<HuffData> left = theQueue.poll();
            BinaryTree<HuffData> right = theQueue.poll();
            double wl = left.getData().weight;
            double wr = right.getData().weight;
            HuffData sum = new HuffData(wl + wr, null);
            BinaryTree<HuffData> newTree =
                    new BinaryTree<HuffData>(sum, left, right);
            theQueue.offer(newTree);
        }

        // The queue should now contain only one item.
        huffTree = theQueue.poll();
    }
    /*</listing>*/

    /**
     * Outputs the resulting code.
     * @param out A PrintStream to write the output to
     * @param code The code up to this node
     * @param tree The current node in the tree
     */
    private void printCode(PrintStream out, String code,BinaryTree<HuffData> tree) {
        HuffData theData = tree.getData();
        if (theData.symbol != null) {
            if (theData.symbol.equals(' ')) {
                out.println("space: " + code);
            } else {
                out.println(theData.symbol + ": " + code);
            }
        } else {
            printCode(out, code + "0", tree.getLeftSubtree());
            printCode(out, code + "1", tree.getRightSubtree());
        }
    }

    /**
     * Outputs the resulting code.
     * @param out A PrintStream to write the output to
     */
    public void printCode(PrintStream out) {
        printCode(out, "", huffTree);
    }

    /*<listing chapter="6" number="11">*/
    /**
     * Method to decode a message that is input as a string of
     * digit characters '0' and '1'.
     * @param codedMessage The input message as a String of
     *        zeros and ones.
     * @return The decoded message as a String
     */
    public String decode(String codedMessage) {
        StringBuilder result = new StringBuilder();
        BinaryTree<HuffData> currentTree = huffTree;
        for (int i = 0; i < codedMessage.length(); i++) {
            if (codedMessage.charAt(i) == '1') {
                currentTree = currentTree.getRightSubtree();
            } else {
                currentTree = currentTree.getLeftSubtree();
            }
            if (currentTree.isLeaf()) {
                HuffData theData = currentTree.getData();
                result.append(theData.symbol);
                currentTree = huffTree;
            }
        }
        return result.toString();
    }
        /**
         * Print codes and their wiegths as string
         */
        public String toString(){

        String encodedCodes = "";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        this.printCode(ps);
        //Htree.printCode(ps);

        try {
            encodedCodes = os.toString("UTF8");
            System.out.println("Encoded Codes : \n"+encodedCodes);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HuffmanCode.class.getName()).log(Level.SEVERE, null, ex);
        }

        return encodedCodes;
    }

    /**
     * Method to encode string message into Huffman encodes.
     * @param message The input message as a String
     * which is composed on the specified alphabet in the book
     * @param huffmanTree It’s created huffman code for the alphabet
     * @return The encoded message as a String zero and ones.
     */
    public String encode(String message, BinaryTree huffmanTree){

        StringBuffer resultOfEncoding = new StringBuffer();
        StringBuffer strbuff = new StringBuffer();
        ArrayList<HuffData> test1 = new ArrayList<HuffData>();
        for(int i=0; i<message.length(); ++i)
            traverse(test1,huffmanTree,strbuff,message.charAt(i),resultOfEncoding);

        return(resultOfEncoding.toString());
    }

    /**
     * Recursive Method to search each leaf to find code of message
     * @param code copy data to arraylist(not used unless if you remove or add to tree)
     * @param node binary tree which we used
     * @param prefix move to right or left from leaf
     * @param ch element which is searching
     * @param path code of node which was found
     */
    private void traverse(ArrayList<HuffData> code, BinaryTree<HuffData> node,
                          StringBuffer prefix, char ch,StringBuffer path) {
        //return if this node is empty.
        if (node == null)
            return;
        if (node != null){
            // Save correct value path as an append into path
            if (node.getData().getSymbol() != null
                    && node.getData().getSymbol().compareTo(ch) == 0)
                path.append(prefix);
            //traverse left
            prefix.append('0');
            traverse(code, node.getLeftSubtree(), prefix,ch,path);
            prefix.deleteCharAt(prefix.length()-1);
        }
        if (node!=null){
            //traverse right
            prefix.append('1');
            traverse(code, node.getRightSubtree(), prefix,ch,path);
            prefix.deleteCharAt(prefix.length()-1);
        }
        // Save all nodes in array list which excepts null pointer
        code.add(node.getData());
    }
    /*</listing>*/
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        HuffmanCode Htree = new HuffmanCode();

        // Create symbol array
        HuffData[] symbols = {
                new HuffData(186, '_'),
                new HuffData(103, 'e'),
                new HuffData(80, 't'),
                new HuffData(64, 'a'),
                new HuffData(63, 'o'),
                new HuffData(57, 'i'),
                new HuffData(57, 'n'),
                new HuffData(51, 's'),
                new HuffData(48, 'r'),
                new HuffData(47, 'h'),
                new HuffData(32, 'd'),
                new HuffData(32, 'l'),
                new HuffData(23, 'u'),
                new HuffData(22, 'c'),
                new HuffData(21, 'f'),
                new HuffData(20, 'm'),
                new HuffData(18, 'w'),
                new HuffData(16, 'y'),
                new HuffData(15, 'g'),
                new HuffData(15, 'p'),
                new HuffData(13, 'b'),
                new HuffData(8, 'v'),
                new HuffData(5, 'k'),
                new HuffData(1, 'j'),
                new HuffData(1, 'q'),
                new HuffData(1, 'x'),
                new HuffData(1, 'z')
        };

        // Build hufffman tree
        Htree.buildTree(symbols);

        // Print huffman codes of the symbols
       // String EncodedSymbolList = Htree.toString();

        // Decode huffman codes to symbols
        String code = "11000010011111110010100001";
        String decodedCode = Htree.decode(code);
        System.out.println("Code to Message : \n"+code+" : \t"+decodedCode);

        System.out.println("Message to Code :");
        System.out.print("q__rg : \t");
        String message = "q__rg";
            if (message == null)
                throw new NullPointerException("Message couldn't read!");
        try {
            System.out.println(Htree.encode(message,Htree.huffTree));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
