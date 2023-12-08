import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class Day8 {
    String directions;
    HashMap<String,Node> map = new HashMap<String, Node>();
    ArrayList<String> startLoc = new ArrayList<String>();

    public void solveDay8(){
        getInput();
        nodeConnect();
        int sum1 = solvePart1();
        System.out.println(sum1);
    }

    private void nodeConnect(){
        Iterator<Map.Entry<String,Node>> iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Node> entry = iter.next();
            Node n = entry.getValue();
            String left = n.leftVal, right = n.rightVal;
            n.left = map.get(left);
            n.right = map.get(right);
        }
        System.out.println("Map linked");
    }

    private int solvePart1(){
        Node entry = map.get("AAA");
        int nodesVisited = 0;
        int stringLoc = 0;
        while(!entry.ident.equals("ZZZ")){
            char c = directions.charAt(stringLoc%directions.length());
            stringLoc++;
            if(c=='L')
                entry = entry.left;
            else
                entry = entry.right;
            nodesVisited++;
        }
        return nodesVisited;
    }

    private void getInput(){
        try {
            File file = new File("./input/day8/day8.txt");
            Scanner myReader = new Scanner(file);
            directions = myReader.nextLine();
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Node newNode = new Node(data.substring(0,3),data.substring(7,10),data.substring(12,15));
                //System.out.println(data.substring(0,3)+data.substring(7,10)+data.substring(12,15));
                map.put(data.substring(0,3), newNode);
            }
            myReader = new Scanner(file);
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private class Node{
        private final String ident;
        Node left;
        String leftVal;
        Node right;
        String rightVal;

        private Node(String ident, String leftVal, String rightVal){
            this.ident = ident;
            this.leftVal = leftVal;
            this.rightVal = rightVal;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}
