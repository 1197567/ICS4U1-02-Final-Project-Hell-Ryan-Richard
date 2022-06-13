/** [Lolipop.java]
* ICS4U1-02
* @Richard Yang
* @Ryan Zareh
* @version 1.0
* June 2022
*/
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.File;
import javax.imageio.ImageIO;

public class Lolipop extends Enemy{

  private BufferedImage lolipopSprite;
  private Graphics2D g2d;
  private File f;  
  
/*Constructer*/
  public Lolipop(ArrayList<Enemy> enemyList){
    super(30,40,"Lolipop",170,0,new BasicRoom(0,0));
    lolipopSprite = new BufferedImage((int)getX(),(int)getY(),BufferedImage.TYPE_INT_ARGB);
    g2d = (Graphics2D)lolipopSprite.getGraphics();
    f = new File ("lolipop-enemy.png");
    try{
      lolipopSprite = ImageIO.read(f);
    }catch(Exception e){
      System.out.println("error");
    }
  }
  
/*Methods*/
  public void drawEnemy(double addedX, double addedY, Graphics g) {
    g.drawImage(lolipopSprite,(int)getX(),(int)getY(),null,null);
  }
  
  public void movement(){
  }
  
  public void heal(){
    
  }
  
}
