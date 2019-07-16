import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Leaf extends Rectangle {

    private final int MIN_LEAF_SIZE = 40;

    public int x, y, width, height;

    public Leaf leftChild, rightChild;

    public Rectangle room;

    public ArrayList<Rectangle> hallways;

    public Leaf(int x, int y, int width, int height){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public boolean split(){

        if (this.leftChild != null || this.rightChild != null){
            return false;
        }

        Random rand = new Random();
        double split_chance = rand.nextInt(100);
        boolean split_horizontal;
        if (split_chance > 50){
            split_horizontal = true;
        }
        else{
            split_horizontal = false;
        }
        if (this.width > this.height && width / height >= 1.25){
            split_horizontal = false;
        } else if (this.height > this.width && height / width >= 1.25){
            split_horizontal = true;
        }

        int max;

        if (split_horizontal){

            max = height;

        } else {

            max = width;
        }

        if (max <=  MIN_LEAF_SIZE){
            return false;
        }

        int split = rand.nextInt(max - MIN_LEAF_SIZE) + MIN_LEAF_SIZE;

        if (split_horizontal){

            this.leftChild = new Leaf(this.x, this.y, this.width, split);
            this.rightChild = new Leaf(this.x, this.y + split, this.width, this.height - split);

        } else {

            this.leftChild = new Leaf(this.x, this.y, split, this.height);
            this.rightChild = new Leaf(this.x + split, this.y, this.width - split, this.height);
        }

        return true;



    }


}
