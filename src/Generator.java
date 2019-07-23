import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Generator extends JPanel {

    public static final int MAX_LEAF_SIZE = 150;
    public static final int MAP_MAX_HEIGHT = 400;
    public static final int MAP_MAX_WIDTH = 400;

    public ArrayList<Leaf> leafs = new ArrayList<>();

    private BufferedImage screen;
    private Graphics2D buffer;
    private JFrame jf;

    public static void main(String[] args){

        Generator gen = new Generator();
        Leaf root = new Leaf(0, 0, gen.MAP_MAX_HEIGHT, gen.MAP_MAX_WIDTH);
        gen.leafs.add(root);
        for (int i = 0; i < 4; i++) {
            gen.splitLeafs();
        }
        gen.createRooms();
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

        this.jf.setSize(MAP_MAX_WIDTH + 200, MAP_MAX_HEIGHT + 200);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

        this.setBackground(Color.black);
        this.setForeground(Color.WHITE);
    }

    public void splitLeafs(){
      ArrayList<Leaf> tempList = new ArrayList<>();
      for (Leaf l : leafs){
          if (l.isSplittable()){
              l.split();
              tempList.add(l);
              tempList.add(l.leftChild);
              tempList.add(l.rightChild);
          } else {
              tempList.add(l);
          }
      }
      this.leafs = tempList;
    }

    public void createRooms(){
        for (Leaf l : leafs){
            if (l.rightChild == null && l.leftChild == null){
                l.createRoom();
            }
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        buffer = screen.createGraphics();
        super.paintComponent(g2);
        for (Leaf l : leafs){
            l.drawImage(buffer);
        }
        g2.drawImage(screen, 97, 97, null);
    }

}
