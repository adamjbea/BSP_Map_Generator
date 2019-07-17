import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Generator extends JPanel {

    public final int MAX_LEAF_SIZE = 300;
    public final int MIN_LEAF_SIZE = 100;
    public final int MAP_MAX_HEIGHT = 800;
    public final int MAP_MAX_WIDTH = 800;
    public final int MAX_LEAF_NUM = 25;

    public Random rand = new Random();

    public ArrayList<Leaf> leafs = new ArrayList<>();

    public Leaf root = new Leaf(0, 0, MAP_MAX_HEIGHT, MAP_MAX_WIDTH);

    private BufferedImage screen;
    private Graphics2D buffer;
    private JFrame jf;

    public static void main(String[] args){

        Generator gen = new Generator();
        Leaf root = new Leaf(0, 0, gen.MAP_MAX_HEIGHT, gen.MAP_MAX_WIDTH);
        gen.leafs.add(root);
        gen.splitLeafs();

        Thread x;
        gen.init();
        try{
            while(true){
                gen.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored){

        }



    }

    private void init(){
        this.jf = new JFrame("BSP_Map_Generator");
        this.screen = new BufferedImage(MAP_MAX_WIDTH, MAP_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        this.jf.setSize(MAP_MAX_WIDTH + 1, MAP_MAX_HEIGHT + 1);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

        this.setBackground(Color.black);
        this.setForeground(Color.WHITE);


    }

    public boolean isSplittable(Leaf l){
        if ((l.height > MAX_LEAF_SIZE || l.width > MAX_LEAF_SIZE) && (l.rightChild == null && l.leftChild == null) && (l.height >= this.MIN_LEAF_SIZE + 50 && l.width >= this.MIN_LEAF_SIZE + 50)){
            return true;
        } else {
            return false;
        }
    }

    public boolean listIsSplittable(){
        for (Leaf l : this.leafs){
            if (isSplittable(l)){
                return true;
            }
        }
        return false;
    }

    public void splitLeafs(){
        ArrayList<Leaf> new_leaf_list = new ArrayList<>();
        while (this.leafs.size() <= MAX_LEAF_NUM && this.listIsSplittable()){
            for (int i = 0; i < this.leafs.size(); i++){
                if (this.isSplittable(this.leafs.get(i))){
                    Leaf newLeaf = this.leafs.get(i);
                    newLeaf.split();
                    new_leaf_list.add(newLeaf);
                    new_leaf_list.add(newLeaf.leftChild);
                    new_leaf_list.add(newLeaf.rightChild);
                }
            }
            this.leafs = new_leaf_list;
        }

    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        buffer = screen.createGraphics();
        super.paintComponent(g2);
        for (Leaf l : leafs){
            l.drawImage(buffer);
        }
        g2.drawImage(screen, 0, 0, null);
    }

}
