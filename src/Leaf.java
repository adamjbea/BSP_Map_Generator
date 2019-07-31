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
            split = ((ThreadLocalRandom.current().nextInt(x + (width * 3 / 10), x + (width * 7 / 10) + 1) + 5) / 10) * 10;
           this.verticalSplit(split);
        }else if (ratio < -.25) {
            split = ((ThreadLocalRandom.current().nextInt(y + (height * 3 / 10), y + (height * 7 / 10) + 1) + 5) / 10) * 10;
            this.horizontalSplit(split);
        } else {
            int split_choice = rand.nextInt(1);
            if (split_choice == 0){
                split = ((ThreadLocalRandom.current().nextInt(x + (width * 3 / 10), x + (width * 7 / 10) + 1) + 5) / 10) * 10;
                this.verticalSplit(split);
            } else {
                split = ((ThreadLocalRandom.current().nextInt(y + (height * 3 / 10), y + (height * 7 / 10) + 1) + 5) / 10) * 10;
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
        int randWidth = ((ThreadLocalRandom.current().nextInt(this.width * 3 / 10, this.width - 20) + 5) / 10) * 10;
        int randHeight = ((ThreadLocalRandom.current().nextInt(this.height * 3 / 10, this.height - 20) + 5) / 10) * 10;
        int randX = ((ThreadLocalRandom.current().nextInt(10, this.width - randWidth - 5) + 5) / 10) * 10;
        int randY = ((ThreadLocalRandom.current().nextInt(10, this.height - randHeight - 5 ) + 5) / 10) * 10;
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

        Rectangle hall = null;
        int r1_center_x = (((r1.x + r1.width / 2)+5)/10)*10;
        int r1_center_y = (((r1.y + r1.height / 2)+5)/10)*10;
        int r2_center_x = (((r2.x + r2.width / 2)+5)/10)*10;
        int r2_center_y = (((r2.y + r2.height / 2)+5)/10)*10;

        if (r1_center_x == r2_center_x){
            int top_room_y;
            if (r1_center_y > r2_center_y){
                top_room_y = r1_center_y;
            }else{
                top_room_y = r2_center_y;
            }
            this.hallways.add(new Rectangle(r1_center_x, top_room_y, 10, Math.abs(r1_center_y - r2_center_y)));
        }else if(r1_center_y == r2_center_y){
            int left_room_x;
            if (r1_center_x > r2_center_x){
                left_room_x = r2_center_x;
            }else{
                left_room_x = r2_center_x;
            }
            this.hallways.add(new Rectangle(left_room_x, r1_center_y, Math.abs(r2_center_x - r1_center_x), 10));
        }


    }

    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.green);
        g2d.drawRect(this.x+1, this.y+1, this.width - 2, this.height - 2);
        if (this.room != null){
            g2d.setColor(Color.white);
            g2d.fillRect(this.room.x, this.room.y, this.room.width, this.room.height);
        }
        if (this.hallways != null){
            for (Rectangle hall : this.hallways){
                g2d.fillRect(hall.x, hall.y, hall.width, hall.height);
            }
        }
    }


}
