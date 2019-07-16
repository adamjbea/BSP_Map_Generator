import java.util.ArrayList;
import java.util.Stack;

public class Generator {

    public final int MAX_LEAF_SIZE = 80;
    public final int MAP_MAX_HEIGHT = 800;
    public final int MAP_MAX_WIDTH = 800;

    public Stack<Leaf> leafs = new Stack<>();

    public Leaf root = new Leaf(0, 0, MAP_MAX_HEIGHT, MAP_MAX_WIDTH);

    public static void main(String[] args){

        Generator gen = new Generator();
        Leaf root = new Leaf(0, 0, gen.MAP_MAX_HEIGHT, gen.MAP_MAX_WIDTH);
        gen.leafs.push(root);

        boolean did_split = true;

        while (did_split){
            did_split = false;
            for (Leaf l : gen.leafs){
                if (l.leftChild == null && l.rightChild == null){
                    if (l.height > gen.MAX_LEAF_SIZE || l.width > gen.MAX_LEAF_SIZE || )
                }
            }

        }



    }

}
