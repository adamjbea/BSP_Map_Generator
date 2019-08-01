import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Leaf{

    private final int MIN_LEAF_SIZE = 100;

    public int x, y, width, height;

    public Leaf leftChild, rightChild;

    public Rectangle room;

    public ArrayList<Rectangle> hallways = new ArrayList<>();

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
            split = Generator.round(ThreadLocalRandom.current().nextInt(x + (width * 3 / 10), x + (width * 7 / 10) + 1));
           this.verticalSplit(split);
        }else if (ratio < -.25) {
            split = Generator.round(ThreadLocalRandom.current().nextInt(y + (height * 3 / 10), y + (height * 7 / 10) + 1));
            this.horizontalSplit(split);
        } else {
            int split_choice = rand.nextInt(1);
            if (split_choice == 0){
                split = Generator.round(ThreadLocalRandom.current().nextInt(x + (width * 3 / 10), x + (width * 7 / 10) + 1));
                this.verticalSplit(split);
            } else {
                split = Generator.round(ThreadLocalRandom.current().nextInt(y + (height * 3 / 10), y + (height * 7 / 10) + 1));
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
        int randWidth = Generator.round(ThreadLocalRandom.current().nextInt(this.width * 3 / 10, this.width - 20));
        int randHeight = Generator.round(ThreadLocalRandom.current().nextInt(this.height * 3 / 10, this.height - 20));
        int randX = Generator.round(ThreadLocalRandom.current().nextInt(10, this.width - randWidth - 5));
        int randY = Generator.round(ThreadLocalRandom.current().nextInt(10, this.height - randHeight - 5 ));
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
        Point r_1 = new Point(Generator.round(r1.x + r1.width / 2), Generator.round(r1.y + r1.height / 2));
        Point r_2 = new Point(Generator.round(r2.x +r2.width / 2), Generator.round(r2.y +r2.height / 2));
        Rectangle top, bottom, left, right;
        if (r_1.x <= r_2.x){
            left = r1;
            right = r2;
        }else{
            left = r2;
            right = r1;
        }
        if (r_1.y <= r_2.y){
            top = r1;
            bottom = r2;
        }else{
            top = r2;
            bottom = r1;
        }

        if(r_1.y == r_2.y){
            this.hallways.add(new Rectangle(Generator.round(left.x + left.width / 2), Generator.round(left.y + left.width / 2), Generator.round(Math.abs(r_1.x - r_2.x)), 10));
        }else if (r_1.x == r_2.x) {
            this.hallways.add(new Rectangle(Generator.round(top.x + top.width / 2), Generator.round(top.y + top.height / 2), 10, Generator.round(Math.abs(r_1.y - r_2.y))));
        }else{
            this.hallways.add(new Rectangle(Generator.round(left.x + left.width / 2), Generator.round(left.y + left.width / 2), Generator.round(Math.abs(r_1.x - r_2.x)), 10));
            this.hallways.add(new Rectangle(Generator.round(top.x + top.width / 2), Generator.round(top.y + top.height / 2), 10, Generator.round(Math.abs(r_1.y - r_2.y))));
        }

    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        if (this.leftChild != null && this.leftChild.room != null){
            g2d.fillRect(this.leftChild.room.x, this.leftChild.room.y, this.leftChild.room.width, this.leftChild.room.height);
        }
        if (this.rightChild != null && this.rightChild.room != null){
            g2d.fillRect(this.rightChild.room.x, this.rightChild.room.y, this.rightChild.room.width, this.rightChild.room.height);
        }
        g2d.setColor(Color.green);
        if (this.hallways != null){
            for (Rectangle hall : this.hallways){
                g2d.fillRect(hall.x, hall.y, hall.width, hall.height);
            }
        }

        g2d.drawRect(this.x+1, this.y+1, this.width - 2, this.height - 2);
    }


}
