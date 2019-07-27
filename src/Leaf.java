import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Leaf{

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

    public boolean isSplittable(){
        if ((this.height > Generator.MAX_LEAF_SIZE || this.width > Generator.MAX_LEAF_SIZE) && (this.rightChild == null && this.leftChild == null)){
            return true;
        } else {
            return false;
        }
    }

    public void split(){
        Random rand = new Random();
        int split;
        double ratio = ((double)this.width - (double)this.height) / 100;
        if (ratio > .25){
            split = ThreadLocalRandom.current().nextInt(x + (width * 3 / 10), x + (width * 7 / 10) + 1);
           this.verticalSplit(split);
        }else if (ratio < -.25) {
            split = ThreadLocalRandom.current().nextInt(y + (height * 3 / 10), y + (height * 7 / 10) + 1);
            this.horizontalSplit(split);
        } else {
            int split_choice = rand.nextInt(1);
            if (split_choice == 0){
                split = ThreadLocalRandom.current().nextInt(x + (width * 3 / 10), x + (width * 7 / 10) + 1);
                this.verticalSplit(split);
            } else {
                split = ThreadLocalRandom.current().nextInt(y + (height * 3 / 10), y + (height * 7 / 10) + 1);
                this.horizontalSplit(split);
            }
        }
    }

    public void verticalSplit(int split){
        this.leftChild = new Leaf(this.x, this.y, split - this.x, this.height );
        this.rightChild = new Leaf(split, this.y, this.x + this.width - split, this.height);

    }

    public void horizontalSplit(int split){
        this.leftChild = new Leaf(this.x, this.y, this.width, split - this.y);
        this.rightChild = new Leaf(this.x, split, this.width, this.y + this.height - split);
    }

    public void createRoom(){
        int randWidth = ThreadLocalRandom.current().nextInt(this.width * 3 / 10, this.width - 2);
        int randHeight = ThreadLocalRandom.current().nextInt(this.height * 3 / 10, this.height - 2);
        int randX = ThreadLocalRandom.current().nextInt(1, this.width - randWidth - 1);
        int randY = ThreadLocalRandom.current().nextInt(1, this.height - randHeight - 1);
        this.room = new Rectangle(this.x + randX, this.y + randY, randWidth, randHeight);
    }

    public boolean hasRoom(){
        if (this.room != null){
            return true;
        }else{
            return false;
        }
    }

    public void createHall(Rectangle r1, Rectangle r2){
        
    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRect(this.x, this.y, this.width - 1, this.height - 1);
        if (this.room != null){
            g2d.fillRect(this.room.x, this.room.y, this.room.width, this.room.height);
        }
    }


}
