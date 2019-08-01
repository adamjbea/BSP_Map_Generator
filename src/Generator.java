import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Generator extends JPanel {

    public static final int MAX_LEAF_SIZE = 50;
    public static final int MAP_MAX_HEIGHT = 500;
    public static final int MAP_MAX_WIDTH = 500;

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
        gen.createHalls();
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

    public void createHalls(){
        for (Leaf l : leafs){
            if (l.leftChild != null && l.leftChild.hasRoom() && l.rightChild != null && l.rightChild.hasRoom()){
                l.createHall(l.leftChild.room, l.rightChild.room);
            }
        }
    }

    public static int round(int num){
        return ((num + 5) / 10) * 10;
    }

    public static double round(double num){
        return ((num + 5) / 10) * 10;
    }



    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        buffer = screen.createGraphics();
        super.paintComponent(g2);
        g2.drawImage(screen, 97, 97, null);
        g2.setColor(Color.gray);
        for (int i = 0; i < Generator.MAP_MAX_WIDTH; i += 10){
            g2.drawLine(i + 97, 97, i + 97, Generator.MAP_MAX_HEIGHT + 97);
        }
        for (int i = 0; i < Generator.MAP_MAX_HEIGHT; i+= 10){
            g2.drawLine(97, i + 97, Generator.MAP_MAX_WIDTH + 97, i + 97);
        }
        for (Leaf l : leafs){
            l.drawImage(buffer);
        }

    }

}
