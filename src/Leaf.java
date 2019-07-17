import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Leaf extends Rectangle {

    private final int MIN_LEAF_SIZE = 100;

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

    public void split(){
        Random rand = new Random();
        int split;
        int ratio = this.width - this.height;
        if (ratio > 0){
            split = ThreadLocalRandom.current().nextInt(x + (width / 4), x + (width * 3 / 4) + 1);
            this.leftChild = new Leaf(this.x, this.y, split - this.x, this.height );
            this.rightChild = new Leaf(split, this.y, this.x + this.width - split, this.height);

        }else if (ratio < 0) {
            split = ThreadLocalRandom.current().nextInt(y + (height / 4), y + (height * 3 / 4) + 1);
            this.leftChild = new Leaf(this.x, this.y, this.width, split - this.y);
            this.rightChild = new Leaf(this.x, split, this.width, this.y + this.height - split);
        } else {
            int split_choice = rand.nextInt(1);
            if (split_choice == 0){
                split = ThreadLocalRandom.current().nextInt(x + (width / 4), x + (width * 3 / 4) + 1);
                this.leftChild = new Leaf(this.x, this.y, split - this.x, this.height );
                this.rightChild = new Leaf(split, this.y, this.x + this.width - split, this.height);
            } else {
                split = ThreadLocalRandom.current().nextInt(y + (height / 4), y + (height * 3 / 4) + 1);
                this.leftChild = new Leaf(this.x, this.y, this.width, split - this.y);
                this.rightChild = new Leaf(this.x, split, this.width, this.y + this.height - split);
            }
        }
    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect(this.x, this.y, this.width, this.height);
    }


}
