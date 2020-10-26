package Java_sources;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

/******************************************************************************
 *  Compilation:  javac SplayBST.java
 *  Execution:    java SplayBST
 *  Dependencies: none
 *
 *  Splay tree. Supports splay-insert, -search, and -delete.
 *  Splays on every operation, regardless of the presence of the associated
 *  key prior to that operation.
 *
 *  Written by Josh Israel.
 *
 *  Modified by Sanath Jayasena
 *
 ******************************************************************************/


public class SplayBST<Key extends Comparable<Key>, Value> {

    private Node root;   // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;            // key
        private Value value;        // associated data
        private Node left, right;   // left and right subtrees

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    // return value associated with the given key
    // if no such value, return null
    public Value get(Key key) {
        root = splay(root, key);
        int cmp = key.compareTo(root.key);
        if (cmp == 0) return root.value;
        else return null;
    }

    /***************************************************************************
     *  Splay tree insertion.
     ***************************************************************************/
    public void put(Key key, Value value) {
        // splay key to root
        if (root == null) {
            root = new Node(key, value);
            return;
        }

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        // Insert new node at root
        if (cmp < 0) {
            Node n = new Node(key, value);
            n.left = root.left;
            n.right = root;
            root.left = null;
            root = n;
        }

        // Insert new node at root
        else if (cmp > 0) {
            Node n = new Node(key, value);
            n.right = root.right;
            n.left = root;
            root.right = null;
            root = n;
        }

        // It was a duplicate key. Simply replace the value
        else {
            root.value = value;
        }

    }

    /***************************************************************************
     *  Splay tree deletion.
     ***************************************************************************/
    /* This splays the key, then does a slightly modified Hibbard deletion on
     * the root (if it is the node to be deleted; if it is not, the key was
     * not in the tree). The modification is that rather than swapping the
     * root (call it node A) with its successor, it's successor (call it Node B)
     * is moved to the root position by splaying for the deletion key in A's
     * right subtree. Finally, A's right child is made the new root's right
     * child.
     */
    public void remove(Key key) {
        if (root == null) return; // empty tree

        root = splay(root, key);

        int cmp = key.compareTo(root.key);

        if (cmp == 0) {
            if (root.left == null) {
                root = root.right;
            } else {
                Node x = root.right;
                root = root.left;
                splay(root, key);
                root.right = x;
            }
        }

        // else: it wasn't in the tree to remove
    }


    /***************************************************************************
     * Splay tree function.
     * **********************************************************************/
    // splay key in the tree rooted at Node h. If a node with that key exists,
    //   it is splayed to the root of the tree. If it does not, the last node
    //   along the search path for the key is splayed to the root.
    private Node splay(Node h, Key key) {
        if (h == null) return null;

        int cmp1 = key.compareTo(h.key);

        if (cmp1 < 0) {
            // key not in tree, so we're done
            if (h.left == null) {
                return h;
            }
            int cmp2 = key.compareTo(h.left.key);
            if (cmp2 < 0) {
                h.left.left = splay(h.left.left, key);
                h = rotateRight(h);
            } else if (cmp2 > 0) {
                h.left.right = splay(h.left.right, key);
                if (h.left.right != null)
                    h.left = rotateLeft(h.left);
            }

            if (h.left == null) return h;
            else return rotateRight(h);
        } else if (cmp1 > 0) {
            // key not in tree, so we're done
            if (h.right == null) {
                return h;
            }

            int cmp2 = key.compareTo(h.right.key);
            if (cmp2 < 0) {
                h.right.left = splay(h.right.left, key);
                if (h.right.left != null)
                    h.right = rotateRight(h.right);
            } else if (cmp2 > 0) {
                h.right.right = splay(h.right.right, key);
                h = rotateLeft(h);
            }

            if (h.right == null) return h;
            else return rotateLeft(h);
        } else return h;
    }


    /***************************************************************************
     *  Helper functions.
     ***************************************************************************/

    // height of tree (1-node tree has height 0)
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }


    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return 1 + size(x.left) + size(x.right);
    }

    // right rotate
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    // left rotate
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    // test client
    public static void main(String[] args) throws FileNotFoundException {
        SplayBST<Long, Integer> st = new SplayBST<>();

        //        TODO: Write the code here

        //        TODO: Write your code here
        // set 1
        File set_1_insert_data_1File = new File("src/data/insert/set1/data_1.txt");
        File set_1_insert_data_2File = new File("src/data/insert/set1/data_2.txt");
        File set_1_insert_data_3File = new File("src/data/insert/set1/data_3.txt");

        File set_1_search_data_1File = new File("src/data/search/set1/data_1.txt");
        File set_1_search_data_2File = new File("src/data/search/set1/data_2.txt");
        File set_1_search_data_3File = new File("src/data/search/set1/data_3.txt");

        File set_1_delete_data_1File = new File("src/data/delete/set1/data_1.txt");
        File set_1_delete_data_2File = new File("src/data/delete/set1/data_2.txt");
        File set_1_delete_data_3File = new File("src/data/delete/set1/data_3.txt");



//        System.out.println("====Start Set 1 Data 1 ===\n\n");
//        st = readAndInsert(set_1_insert_data_1File, 1);
//        mySearch(st, set_1_search_data_1File, 1);
//        myDelete(readAndInsert(set_1_insert_data_1File, 1), set_1_delete_data_1File, 1);
//        System.out.println("====End Set 1 Data 1 ===\n\n");


//        System.out.println("====Start Set 1 Data 2 ===\n\n");
//        st = readAndInsert(set_1_insert_data_2File, 1);
//        mySearch(st, set_1_search_data_2File, 1);
//        myDelete(readAndInsert(set_1_insert_data_2File, 1), set_1_delete_data_2File, 1);
//        System.out.println("====Start Set 1 Data 2 ===\n\n");

//        System.out.println("====Start Set 1 Data 3 ===\n\n");
//        st = readAndInsert(set_1_insert_data_3File, 1);
//        mySearch(st, set_1_search_data_3File, 1);
//        myDelete(readAndInsert(set_1_insert_data_3File, 1), set_1_delete_data_3File, 1);
//        System.out.println("====End Set 1 Data 3 ===\n\n");


        // set 2
        File set_2_insert_data_1File = new File("src/data/insert/set2/data_1.txt");
        File set_2_insert_data_2File = new File("src/data/insert/set2/data_2.txt");
        File set_2_insert_data_3File = new File("src/data/insert/set1/data_3.txt");

        File set_2_search_data_1File = new File("src/data/search/set2/data_1.txt");
        File set_2_search_data_2File = new File("src/data/search/set2/data_2.txt");
        File set_2_search_data_3File = new File("src/data/search/set2/data_3.txt");

        File set_2_delete_data_1File = new File("src/data/delete/set2/data_1.txt");
        File set_2_delete_data_2File = new File("src/data/delete/set2/data_2.txt");
        File set_2_delete_data_3File = new File("src/data/delete/set2/data_3.txt");



//        System.out.println("====Start Set 2 Data 1 ===\n\n");
//        st = readAndInsert(set_2_insert_data_1File, 2);
//        mySearch(st, set_2_search_data_1File, 2);
//        myDelete(readAndInsert(set_2_insert_data_1File, 2), set_2_delete_data_1File, 2);
//        System.out.println("====End Set 2 Data 1 ===\n\n");

//        System.out.println("====Start Set 2 Data 2 ===\n\n");
//        st = readAndInsert(set_2_insert_data_2File, 2);
//        mySearch(st, set_2_search_data_2File, 2);
//        myDelete(readAndInsert(set_2_insert_data_2File, 2), set_2_delete_data_2File, 2);
//        System.out.println("====End Set 2 Data 2 ===\n\n");


//        System.out.println("====Start Set 2 Data 3 ===\n\n");
//        st = readAndInsert(set_2_insert_data_3File, 2);
//        mySearch(st, set_2_search_data_3File, 2);
//        myDelete(readAndInsert(set_2_insert_data_3File, 2), set_2_delete_data_3File, 2);
//        System.out.println("====End Set 2 Data 3 ===\n\n");
    }

    private static SplayBST<Long, Integer> readAndInsert(File dataFile, int set)
            throws FileNotFoundException {
        SplayBST<Long, Integer> insertBST = new SplayBST<>();
        Scanner myReader = new Scanner(dataFile);
        Integer value = 0;

        System.out.println("Set :" + set);
        System.out.println("file name: " + dataFile.getName());
        System.out.println("Inserting Start");
        long startTime = System.currentTimeMillis();
        System.out.println("start time time :" + System.currentTimeMillis());
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String keyString = stringTokenizer.nextToken();
                Long key = Long.parseLong(keyString);
                insertBST.put(key, value);
                value++;
            }
        }
        System.out.println("Time taken to Insert (in ms): " + (System.currentTimeMillis() - startTime));
        myReader.close();
        System.out.println("\n\n");
        return insertBST;
    }

    private static void mySearch(SplayBST<Long, Integer> st, File dataFile, int set)
            throws FileNotFoundException {
        Scanner myReader = new Scanner(dataFile);
        Integer value = 0;

        System.out.println("Set :" + set);
        System.out.println("file name: " + dataFile.getName());
        System.out.println("Search Start");
        long startTime = System.currentTimeMillis();
        System.out.println("start time time :" + System.currentTimeMillis());
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String keyString = stringTokenizer.nextToken();
                Long key = Long.parseLong(keyString);
                st.contains(key);
                value++;
            }
        }
        System.out.println("Time taken to Search (in ms): " + (System.currentTimeMillis() - startTime));
        myReader.close();
        System.out.println("\n\n");
    }

    private static void myDelete(SplayBST<Long, Integer> st, File dataFile, int set)
            throws FileNotFoundException {
        Scanner myReader = new Scanner(dataFile);
        Integer value = 0;

        System.out.println("Set :" + set);
        System.out.println("file name: " + dataFile.getName());
        System.out.println("Search Delete");
        long startTime = System.currentTimeMillis();
        System.out.println("start time time :" + System.currentTimeMillis());
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(data, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String keyString = stringTokenizer.nextToken();
                Long key = Long.parseLong(keyString);
                st.remove(key);
                value++;
            }
        }
        System.out.println("Time taken to Delete (in ms): " + (System.currentTimeMillis() - startTime));
        myReader.close();
        System.out.println("\n\n");
    }
}